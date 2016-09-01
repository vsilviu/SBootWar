package generic.module.two.service.impl;

import generic.module.two.common.IConstants;
import generic.module.two.dto.GenericDTO;
import generic.module.two.entity.Auditable;
import generic.module.two.entity.User;
import generic.module.two.entity.decorator.Identificable;
import generic.module.two.entity.decorator.Ownable;
import generic.module.two.entity.decorator.SoftDelete;
import generic.module.two.exception.EntityNotFoundException;
import generic.module.two.service.GenericService;
import generic.module.two.service.util.ClassUtil;
import generic.module.two.service.util.DtoTransformer;
import generic.module.two.service.util.QueryExecutor;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by mitziuro on 8/11/16.
 */
abstract class GenericServiceImpl<T> implements GenericService<T> {

    private static ThreadLocal<Exception> _exception = new ThreadLocal<Exception>();

    protected abstract PagingAndSortingRepository<T, Long> getRepository();
    protected abstract DetachedCriteria getCriteria();

    @Autowired
    protected ClassUtil classUtil;

    @Autowired
    protected QueryExecutor<T> queryExecutor;

    @Autowired
    private DtoTransformer dtoTransformer;

    protected Criterion getCriterion() {
        if(classUtil.hasInterface(classUtil.getFirstSuperClass(getClass()), SoftDelete.class)) {
            return Restrictions.eq("deleted", IConstants.NOT_DELETED);
        }

        return null;
    }

    @Override
    public Stream<T> findAll() throws Exception {
        return queryExecutor.executeCriteria(getCriterion(), classUtil.getFirstSuperClass(getClass()));
    }

    @Override
    public Page<T> findAll(Pageable pageable) throws Exception {
        return queryExecutor.executeCriteria(getCriterion(), pageable, classUtil.getFirstSuperClass(getClass()));
    }

    @Override
    public void delete(Stream<T> s) throws Exception {
        _exception.set(null);
        s.parallel().forEach(t -> {
            try {
                delete(t);
            } catch (Exception e) {
                _exception.set(e);
            }
        });

        if(_exception.get() == null) {
            return;
        }

        throw _exception.get();
    }

    public T delete(T t) throws Exception {
       return delete(((Identificable)t).getId());
    }

    public T delete(Long id) throws Exception {

        T t = findOne(id);
        if(t instanceof SoftDelete) {
            ((SoftDelete) t).setDeleted(IConstants.DELETED);
            //all the check are done for save (TODO)
            getRepository().save(t);
            return t;
        }

        getRepository().delete(id);
        return t;
    }

    public Stream<T> save(Stream<T> s) throws Exception {
        _exception.set(null);

        s.parallel().forEach(t -> {
            try {
                save(t);
            } catch (Exception e) {
                _exception.set(e);
            }
        });

        if(_exception.get() == null) {
            return s;
        }

        throw _exception.get();
    }

    public T save(T t) throws Exception {

        T persistent = ((Identificable)t).getId() != null ? findOne(((Identificable)t).getId()) : null;
        setAuditable(t, persistent);

        if(t instanceof Ownable) {
            ((Ownable) t).setOwner(new User() {{
                setId(1L);
            }});
        }

        return getRepository().save(t);
    }

    public T findOne(Long id) throws Exception {

        if(id == null) {
            throw new EntityNotFoundException(null, getClass());
        }

        T t = getRepository().findOne(id);

        if(t == null) {
            throw new EntityNotFoundException(id, getClass());
        }

        if(t instanceof SoftDelete) {
            if(((SoftDelete)t).isDeleted().equals(IConstants.DELETED)) {
                throw new EntityNotFoundException(id, getClass());
            }
        }
        return t;
    }

    @Override
    public GenericDTO findOne(Long id, Class<? extends GenericDTO> dto) throws Exception {
       return dtoTransformer.transform((Identificable) findOne(id), dto);
    }

    @Override
    public Stream<GenericDTO> findAll(Class<? extends GenericDTO> dto) throws Exception {
        _exception.set(null);
        Stream<GenericDTO> ret = findAll().map(t -> {
            try {
                return dtoTransformer.transform((Identificable) t, dto);
            } catch (Exception e) {
                _exception.set(e);
            }

            return null;
        });

        if(_exception.get() != null) {
            throw _exception.get();
        }

        return ret;
    }

    @Override
    public Page<GenericDTO> findAll(Pageable pageable, Class<? extends GenericDTO> dto) throws Exception {
        List<GenericDTO> results = new ArrayList<GenericDTO>();
        _exception.set(null);
        Page<T> initialResults = findAll(pageable);
        initialResults.getContent().stream().map(t -> {
            try {
                return dtoTransformer.transform((Identificable) t, dto);
            } catch (Exception e) {
                _exception.set(e);
            }
            return null;
        }).forEach(results::add);

        if(_exception.get() != null) {
            throw _exception.get();
        }

        return new PageImpl<GenericDTO>(results, pageable, initialResults.getTotalElements());
    }


    private void setAuditable(Object auditableObject) {

        if(!(auditableObject instanceof Auditable)) {
            return;
        }

        Auditable auditable = (Auditable)auditableObject;
        LocalDateTime now = LocalDateTime.now();
        auditable.setCreateUser(new User() {{setId(1L);}});
        auditable.setCreateDate(now);
        auditable.setUpdateUser(new User() {{setId(1L);}});
        auditable.setUpdateDate(now);

    }

    private void setAuditable(Object auditableObject, Object object) {

        if(!(auditableObject instanceof Auditable)) {
            return;
        }

        if(object == null) {
            setAuditable(auditableObject);
            return;
        }

        Auditable auditable = (Auditable)auditableObject;
        LocalDateTime now = LocalDateTime.now();
        auditable.setCreateUser(((Auditable)object).getCreateUser());
        auditable.setCreateDate(((Auditable)object).getCreateDate());
        auditable.setUpdateUser(new User() {{setId(1L);}});
        auditable.setUpdateDate(now);

    }

}

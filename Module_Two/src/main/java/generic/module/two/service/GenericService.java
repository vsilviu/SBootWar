package generic.module.two.service;


import generic.module.two.dto.GenericDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

/**
 * Created by mitziuro on 8/11/16.
 */
@Transactional
public abstract interface GenericService<T> {

    public Stream<T> findAll() throws Exception;

    public Page<T> findAll(Pageable pageable)  throws Exception;

    public void delete(Stream<T> t)  throws Exception;

    public T delete(T t)  throws Exception;

    public T delete(Long id)  throws Exception;

    public Stream<T> save(Stream<T> t)  throws Exception;

    public T save(T t) throws Exception;

    public T findOne(Long id) throws Exception;

    public GenericDTO findOne(Long id, Class<? extends GenericDTO> dto) throws Exception;

    public Stream<GenericDTO> findAll(Class<? extends GenericDTO> dto) throws Exception;

    public Page<GenericDTO> findAll(Pageable pageable, Class<? extends GenericDTO> dto)  throws Exception;

}

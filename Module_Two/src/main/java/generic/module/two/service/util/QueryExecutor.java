package generic.module.two.service.util;


import generic.module.two.util.ExceptionUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.Field;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by mitziuro on 8/18/16.
 */
@Component
@Transactional
public class QueryExecutor<T> {

    private static final Logger logger = LoggerFactory.getLogger(QueryExecutor.class);

    @PersistenceContext
    private EntityManager em;

    private Session getSession() {
        return em.unwrap(Session.class);
    }


    public Stream<T> executeCriteria(Criterion criterion, Class clazz) {
        return executeCriteria(criterion, new PageRequest(0, Integer.MAX_VALUE), clazz).getContent().stream();
    }

    public Page<T> executeCriteria(Criterion criterion, Pageable pageable, Class clazz) {

        Criteria countCriteria = getSession().createCriteria(clazz);
        Criteria criteria = getSession().createCriteria(clazz);

        if (criterion != null) {
            criteria.add(criterion);
            countCriteria.add(criterion);
        }

        int totalRows = Integer.valueOf(countCriteria.setProjection(Projections.count("id")).uniqueResult().toString());

        if (pageable == null) {
            pageable = new PageRequest(0, 20, Sort.Direction.ASC);
        }

        criteria.setFirstResult(pageable.getOffset());
        criteria.setMaxResults(pageable.getPageSize());

        if (pageable.getSort() != null) {
            StreamSupport.stream(pageable.getSort().spliterator(), false).forEach(o -> {
                Order order = null;
                if (o.isAscending()) {
                    order = Order.asc(o.getProperty());
                } else {
                    order = Order.desc(o.getProperty());
                }
                criteria.addOrder(order);
                try {
                    Class<?> c = order.getClass();
                    Field f = c.getDeclaredField("ignoreCase");
                    f.setAccessible(true);
                    f.setBoolean(order, Boolean.TRUE);
                } catch (Exception e) {
                    logger.error(ExceptionUtils.getMessage(e), e);
                }
            });

        }

        return new PageImpl<>(criteria.list(), pageable, totalRows);
    }


}

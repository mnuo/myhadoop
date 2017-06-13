/**
 * IHibernateDao.java created at 2016年10月21日 下午4:46:56
 */
package org.monuo.sshredis.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @author saxon
 * 通用的操作接口
 */
public interface IHibernateDao<T extends Serializable> {
	T findOne(final Integer id);

    List<T> findAll();

    void create(final T entity);

    T update(final T entity);

    void delete(final T entity);

    void deleteById(final Integer entityId);
}

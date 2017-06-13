/**
 * AbstractHibernateDao.java created at 2016年10月21日 下午4:49:00
 */
package org.monuo.sshredis.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.monuo.sshredis.common.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author saxon
 */
public class AbstractHibernateDao<T extends Serializable> implements IHibernateDao<T> {
	private Class<T> clazz;
    
    @Autowired
    private SessionFactory sessionFactory;

	protected final void setClazz(final Class<T> clazzToSet) {
    	if(clazzToSet != null){
    		this.clazz = clazzToSet;
    	}
    }
    
    protected final Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

	@SuppressWarnings("unchecked")
	@Override
	public T findOne(final Integer id) {
		return (T) this.getCurrentSession().get(clazz, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll() {
		return this.getCurrentSession().createQuery(" from " + clazz.getName()).list();
	}

	@Override
	public void create(final T entity) {
		Preconditions.checkIsNotNull(entity);
		this.getCurrentSession().saveOrUpdate(entity);
	}

	@Override
	public T update(final T entity) {
		Preconditions.checkIsNotNull(entity);
		this.getCurrentSession().update(entity);
		return entity;
	}

	@Override
	public void delete(final T entity) {
		Preconditions.checkIsNotNull(entity);
        this.getCurrentSession().delete(entity);
	}

	@Override
	public void deleteById(Integer entityId) {
		final T entity = findOne(entityId);
        Preconditions.checkIsNotNull(entity);
        delete(entity);
	}
	
}

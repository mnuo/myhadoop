/**
 * ClasDaoImpl.java created at 2017年6月13日 下午3:56:43
 */
package org.monuo.sshredis.dao.impl;

import org.monuo.sshredis.dao.AbstractHibernateDao;
import org.monuo.sshredis.dao.ClasDao;
import org.monuo.sshredis.entity.Clas;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author saxon
 */
@Repository
@Transactional
public class ClasDaoImpl extends AbstractHibernateDao<Clas> implements ClasDao {
	public ClasDaoImpl() {
		super();
		setClazz(Clas.class);
	}
}

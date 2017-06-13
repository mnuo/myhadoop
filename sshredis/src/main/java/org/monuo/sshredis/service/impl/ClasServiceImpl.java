/**
 * ClasServiceImpl.java created at 2017年6月13日 下午3:46:54
 */
package org.monuo.sshredis.service.impl;

import org.monuo.sshredis.dao.ClasDao;
import org.monuo.sshredis.entity.Clas;
import org.monuo.sshredis.service.ClasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author saxon
 */
@Service
@Transactional
public class ClasServiceImpl implements ClasService{
	@Autowired
	public ClasDao clasDao;
	
	@Override
	public void save(Clas entity) {
		clasDao.create(entity);
	}

	@Override
	public void delelte(Integer id) {
		Clas entity = clasDao.findOne(id);
		clasDao.delete(entity);
	}

	@Override
	public void update(Clas entity) {
		clasDao.update(entity);
	}
}

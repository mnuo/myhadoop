/**
 * ClasService.java created at 2017年6月13日 下午3:46:34
 */
package org.monuo.sshredis.service;

import org.monuo.sshredis.entity.Clas;

/**
 * @author saxon
 */
public interface ClasService {
	void save(Clas entity);
	void delelte(Integer id);
	void update(Clas entity);
}

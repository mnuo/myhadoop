/**
 * Clas.java created at 2017年6月13日 上午11:12:15
 */
package org.monuo.sshredis.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author saxon
 */
@Entity
@Table(name = "clas", catalog = "brieflife")
public class Clas implements Serializable{
	private static final long serialVersionUID = -7956122502826756810L;
	
	private Integer id;
	private String name;
	private String comment;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "comment")
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}

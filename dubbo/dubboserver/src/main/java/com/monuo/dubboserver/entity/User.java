/**
 * User.java created at 2017年6月7日 下午1:24:26
 */
package com.monuo.dubboserver.entity;

import java.io.Serializable;

/**
 * @author saxon
 */
public class User implements Serializable {
	private static final long serialVersionUID = -1776596657814853993L;

    private long id;
    private String name;
    private int age;
    private String sex;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", age=" + age + "]";
    }
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
}

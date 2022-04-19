package com.kong.starter.model;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * @author: xiangbinkong
 * @Date: 2021/7/25 21:08
 * @Description:
 */
public class School implements ISchool{

	@Autowired(required = true)
	private Klass class1;

	@Resource(name = "student100")
	private Student student100;

	@Override
	public void ding(){

		System.out.println("Class1 have " + this.class1.getStudents().size() + " students and one is " + this.student100);

	}
}

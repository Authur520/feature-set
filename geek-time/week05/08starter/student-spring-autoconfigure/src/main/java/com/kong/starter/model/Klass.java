package com.kong.starter.model;

import lombok.Data;

import java.util.List;

/**
 * @author: xiangbinkong
 * @Date: 2021/7/25 21:04
 * @Description:
 */

@Data
public class Klass {

	List<Student> students;

	public void dong(){
		System.out.println(this.getStudents());
	}
}

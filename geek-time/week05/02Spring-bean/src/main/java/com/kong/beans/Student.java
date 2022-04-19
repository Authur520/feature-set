package com.kong.beans;

import lombok.Data;

/**
 * @author: xiangbinkong
 * @Date: 2021/7/25 15:51
 * @Description: 学生信息
 */
@Data
public class Student {

	/**
	 * 年龄
	 */
	private Integer age;

	/**
	 * 姓名
	 */
	private String name;

	public Student(Integer age, String name) {
		this.age = age;
		this.name = name;
	}
}

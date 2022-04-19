package com.kong.jdbc.model;

import lombok.Data;

/**
 * @author: xiangbinkong
 * @Date: 2021/7/25 22:09
 * @Description:
 */
@Data
public class Student {

	private Integer id;

	private String name;

	public Student() {
	}

	public Student(String name) {
		this.name = name;
	}

	public Student(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
}

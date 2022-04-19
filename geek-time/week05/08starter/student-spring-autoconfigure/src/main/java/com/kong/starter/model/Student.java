package com.kong.starter.model;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author: xiangbinkong
 * @Date: 2021/7/25 21:10
 * @Description:
 */
@Data
@ToString
@Builder
public class Student implements Serializable{

	private int id;

	private String name;

	public Student(int id, String name) {
		this.id = id;
		this.name = name;
	}
}

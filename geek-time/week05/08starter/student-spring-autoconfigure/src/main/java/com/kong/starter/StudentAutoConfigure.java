package com.kong.starter;

import com.kong.starter.model.Student;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: xiangbinkong
 * @Date: 2021/7/25 21:17
 * @Description:
 */
@Configuration
@EnableConfigurationProperties(value = StudentProperties.class)
public class StudentAutoConfigure {
	@Bean
	public Student student(){
		return new Student(studentProperties.getId(), studentProperties.getName());
	}

	private final StudentProperties studentProperties;
	public StudentAutoConfigure(StudentProperties studentProperties) {
		this.studentProperties = studentProperties;
	}
}

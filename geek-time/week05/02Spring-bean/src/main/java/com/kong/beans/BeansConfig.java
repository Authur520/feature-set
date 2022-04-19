package com.kong.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: xiangbinkong
 * @Date: 2021/7/25 16:01
 * @Description:
 */
@Configuration
public class BeansConfig {

	@Bean
	public Student studentBean(){
		return new Student(10, "billy");
	}
}

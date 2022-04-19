package com.kong.starter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.kong.starter.StudentProperties.DEFAULT_PREFIX;

/**
 * @author: xiangbinkong
 * @Date: 2021/7/25 21:12
 * @Description:
 */
@Data
@ConfigurationProperties(value = DEFAULT_PREFIX)
public class StudentProperties {

	public static final String DEFAULT_PREFIX = "com.kong.student";

	private int id;
	private String name;
}

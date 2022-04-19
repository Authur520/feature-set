package com.kong.beans;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: xiangbinkong
 * @Date: 2021/7/25 15:55
 * @Description:
 */

public class ApplicationStart {
	public static void main(String[] args) {
		// xml
		ApplicationContext application = new ClassPathXmlApplicationContext("applicationContext.xml");
		final Student studentXml = (Student) application.getBean("student");
		System.out.println("student:" + studentXml.toString());

		// @bean
		ApplicationContext context = new AnnotationConfigApplicationContext(BeansConfig.class);
		final Student studentBeans = (Student) context.getBean("studentBean");
		System.out.println("student:" + studentBeans.toString());
	}
}

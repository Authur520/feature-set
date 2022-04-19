package com.kong.student;

import com.kong.starter.model.Student;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class StudentSpringStarterTestApplicationTests {
	@Autowired
	private Student student;
	@Test
	void contextLoads() {

		System.out.println(student);
	}

}

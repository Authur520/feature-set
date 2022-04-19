package com.kong.jdbc.repo;

import com.kong.jdbc.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: xiangbinkong
 * @Date: 2021/7/25 22:41
 * @Description:
 */
@SpringBootTest
public class StudentRepoForJdbcTest {

	@Autowired
	private StudentRepoForJdbc studentRepoForJdbc;

	@Test
	public void findAllTest(){
		final List<Student> all = studentRepoForJdbc.findAll();
		System.out.println(all);
	}

	@Test
	public void addTest() throws SQLException {
		Student student = new Student("kong");
		studentRepoForJdbc.add(student);
	}

	@Test
	public void addForBatch() throws SQLException {
		List<Student> students = new ArrayList<>();
		students.add(new Student("b1"));
		students.add(new Student("b2"));
		students.add(new Student("b3"));
		studentRepoForJdbc.addForBatch(students);
	}
}

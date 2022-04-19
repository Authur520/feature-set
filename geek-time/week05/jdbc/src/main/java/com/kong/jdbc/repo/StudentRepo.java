package com.kong.jdbc.repo;

import com.kong.jdbc.model.Student;

import java.sql.SQLException;
import java.util.List;

/**
 * @author: xiangbinkong
 * @Date: 2021/7/25 22:10
 * @Description:
 */

public interface StudentRepo {

	List<Student> findAll();

	void deleteById(Integer id);

	void add(Student student) throws SQLException;

	void updateNameById(Integer id, String name);

	void addForBatch(List<Student> student) throws SQLException;
}

package com.kong.jdbc.repo;

import com.kong.jdbc.model.Student;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author: xiangbinkong
 * @Date: 2021/7/25 22:27
 * @Description:
 */
@Service
public class StudentRepoForJdbc implements StudentRepo{

	private String drive = "com.mysql.jdbc.Driver";
	private String url="jdbc:mysql://localhost:3306/geek_school?serverTimezone=UTC";
	private String username="root";
	private String password="root";


	public StudentRepoForJdbc() {
		try {
			Class.forName(drive);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}


	@Override
	public List<Student> findAll() {
		List<Student> students = new ArrayList<>();
		try {
			String sql = "select * from student";
			Connection connection = getConnection();
			final Statement statement = connection.createStatement();
			final ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				students.add(new Student(resultSet.getInt("id"), resultSet.getString("name")));
			}
			resultSet.close();
			connection.close();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return students;
	}

	@Override
	public void deleteById(Integer id) {

	}

	@Override
	public void add(Student student) throws SQLException {
		String sql = "INSERT INTO student(`name`) VALUES ("+ "'" +student.getName() + "'"+")";
		Connection connection = getConnection();
		final Statement statement = connection.createStatement();
		statement.executeUpdate(sql);
		statement.close();
		connection.close();
	}

	@Override
	public void updateNameById(Integer id, String name) {

	}

	@Override
	public void addForBatch(List<Student> students) throws SQLException {
		String sql = "INSERT INTO student(`name`) VALUES (?)";
		Connection connection = getConnection();
		connection.setAutoCommit(false);
		final PreparedStatement statement = connection.prepareStatement(sql);
		for (Student student : students){
			statement.setString(1, student.getName());
			statement.addBatch();
		}
		statement.executeBatch();
		connection.commit();
		statement.close();
		connection.close();
	}


}

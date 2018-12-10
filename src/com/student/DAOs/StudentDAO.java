package com.student.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.naming.NamingException;

import com.student.DataSources.MySQLDataSource;
import com.student.Models.Student;

public class StudentDAO implements DAO<Student> {

	@Override
	public Optional<Student> get(String id) {
		return null;
	}

	@Override
	public List<Student> getAll() throws SQLException, NamingException {
		List<Student> students = new ArrayList<>();

		String strSql = "SELECT * FROM student";

		Connection conn = MySQLDataSource.getConnection();
		Statement stmt = conn.createStatement();

		ResultSet rs = stmt.executeQuery(strSql);

		while (rs.next()) {
			students.add(resultSetToStudent(rs));
		}

		return students;
	}

	@Override
	public void save(Student s) throws SQLException, NamingException {
		String strSql = "INSERT INTO student VALUES (?, ?, ?, ?)";

		Connection conn = MySQLDataSource.getConnection();
		PreparedStatement pStmt = conn.prepareStatement(strSql);

		pStmt.setString(1, s.getSid());
		pStmt.setString(2, s.getcID());
		pStmt.setString(3, s.getName());
		pStmt.setString(4, s.getAddress());

		pStmt.execute();
	}

	@Override
	public void update(Student s) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Student s) throws SQLException, NamingException {
		String strSql = "DELETE FROM student WHERE sid=?";
		Connection conn = MySQLDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement(strSql);
		pstmt.setString(1, s.getSid());

		int result = pstmt.executeUpdate();
		System.out.println(result);

	}

	private static Student resultSetToStudent(ResultSet rs) {
		Student s = null;
		try {
			s = new Student(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), null);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return s;
	}

}

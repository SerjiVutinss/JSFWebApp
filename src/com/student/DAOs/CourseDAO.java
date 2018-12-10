package com.student.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.student.DataSources.MySQLDataSource;
import com.student.Models.Course;

public class CourseDAO implements DAO<Course> {

	@Override
	public Optional<Course> get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Course> getAll() throws SQLException {

		ArrayList<Course> courses = new ArrayList<>();

		String strSql = "SELECT * FROM course";

		Connection conn = MySQLDataSource.getConnection();
		Statement stmt = conn.createStatement();

		ResultSet rs = stmt.executeQuery(strSql);

		while (rs.next()) {
			courses.add(resultSetToCourse(rs));
		}

		return courses;
	}

	@Override
	public void save(Course c) throws SQLException {

		String strSql = "INSERT INTO course VALUES (?, ?, ?)";

		Connection conn = MySQLDataSource.getConnection();
		PreparedStatement pStmt = conn.prepareStatement(strSql);

		pStmt.setString(1, c.getId());
		pStmt.setString(2, c.getName());
		pStmt.setInt(3, c.getDuration());

		pStmt.execute();
	}

	@Override
	public void update(Course c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Course c) throws SQLException {

		String strSql = "DELETE FROM course WHERE cID=?";
		Connection conn = MySQLDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement(strSql);
		pstmt.setString(1, c.getId());

		int result = pstmt.executeUpdate();
		System.out.println(result);
	}

	private static Course resultSetToCourse(ResultSet rs) {
		Course c = null;
		try {
			c = new Course(rs.getString(1), rs.getString(2), rs.getInt(3));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return c;
	}

}

package com.student.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.student.Models.Course;
import com.student.Models.Student;

public class MySqlDAO {

	private DataSource mysqlDs;

	public MySqlDAO() throws Exception {

		Context context = new InitialContext();
		String dbName = "java:comp/env/studentDB";
		mysqlDs = (DataSource) context.lookup(dbName);

		System.out.println("Found data source: " + context.lookup(dbName));
		System.out.println("Connected");

	}

	public ArrayList<Student> loadStudents() {

		ArrayList<Student> students = new ArrayList<>();

		String strSql = "SELECT * FROM student";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Get connection...");
			Connection conn = mysqlDs.getConnection();
			System.out.println("Got connection...");
			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(strSql);

			while (rs.next()) {
				students.add(resultToStudent(rs));
			}

		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return students;

	}

	public ArrayList<Student> loadStudents(Course course) {

		ArrayList<Student> students = new ArrayList<>();

		String strSql = "SELECT c.cID, c.cName, c.duration, s.sid, s.cID, s.name, s.address"
				+ " FROM course c INNER JOIN student s ON c.cID=s.cID WHERE c.cID=?";

		try {
			Connection conn = mysqlDs.getConnection();
			PreparedStatement pStmt = conn.prepareStatement(strSql);

			pStmt.setString(1, course.getId());

			ResultSet rs = pStmt.executeQuery();

			Student s;
			while (rs.next()) {

				s = new Student(rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), null);
				s.setCourse(new Course(rs.getString(1), rs.getString(2), rs.getInt(3)));

				students.add(s);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return students;

	}

	public void addCourse(Course c) {

		String strSql = "INSERT INTO course VALUES (?, ?, ?)";
		try {
			Connection conn = mysqlDs.getConnection();
			PreparedStatement pStmt = conn.prepareStatement(strSql);

			pStmt.setString(1, c.getId());
			pStmt.setString(2, c.getName());
			pStmt.setInt(3, c.getDuration());

			pStmt.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Course> loadCourses() {

		ArrayList<Course> courses = new ArrayList<>();

		String strSql = "SELECT * FROM course";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Get connection...");
			Connection conn = mysqlDs.getConnection();
			System.out.println("Got connection...");
			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(strSql);

			while (rs.next()) {
				courses.add(resultToCourse(rs));
			}

		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return courses;

	}

	public void delete(Course c) {
		String strSql = "DELETE FROM course WHERE cID=?";
		try {
			Connection conn = mysqlDs.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(strSql);
			pstmt.setString(1, c.getId());

			int result = pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Student resultToStudent(ResultSet rs) {
		Student s = null;
		try {
			s = new Student(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), null);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return s;
	}

	private Course resultToCourse(ResultSet rs) {
		Course c = null;
		try {
			c = new Course(rs.getString(1), rs.getString(2), rs.getInt(3));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return c;
	}

}

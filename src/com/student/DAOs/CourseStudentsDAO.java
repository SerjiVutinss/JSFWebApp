package com.student.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.student.DataSources.MySQLDataSource;
import com.student.Models.Course;
import com.student.Models.Student;

public class CourseStudentsDAO {

	public List<Student> getStudents(Course c) throws SQLException {

		List<Student> students = new ArrayList<>();

		Connection conn = MySQLDataSource.getConnection();

		String strSql = "SELECT c.cID, c.cName, c.duration, s.sid, s.cID, s.name, s.address"
				+ " FROM course c INNER JOIN student s ON c.cID=s.cID WHERE c.cID=?";

		PreparedStatement pStmt = conn.prepareStatement(strSql);

		pStmt.setString(1, c.getId());

		ResultSet rs = pStmt.executeQuery();

		Student s;
		while (rs.next()) {
			s = new Student(rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),
					new Course(rs.getString(1), rs.getString(2), rs.getInt(3)));
			students.add(s);
		}
		return students;
	}

	public Student getCourse(Student s) throws SQLException {

//		List<Student> students = new ArrayList<>();

		Connection conn = MySQLDataSource.getConnection();

		String strSql = "SELECT c.cID, c.cName, c.duration, s.sid, s.cID, s.name, s.address"
				+ " FROM course c INNER JOIN student s ON c.cID=s.cID WHERE s.sid=?";

		PreparedStatement pStmt = conn.prepareStatement(strSql);

		pStmt.setString(1, s.getSid());

		ResultSet rs = pStmt.executeQuery();

		Student student = null;
		while (rs.next()) {
			student = new Student(rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),
					new Course(rs.getString(1), rs.getString(2), rs.getInt(3)));
//			students.add(student);
		}
		return student;
	}

}

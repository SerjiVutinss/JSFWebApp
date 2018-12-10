package com.student.Controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.NamingException;

import com.student.DAOs.CourseStudentsDAO;
import com.student.Models.Course;
import com.student.Models.Student;

@SessionScoped
@ManagedBean
public class CourseStudentsController {

	CourseStudentsDAO courseStudentsDAO;
	private Course course;
	private List<Student> students;

	public CourseStudentsController() {

		courseStudentsDAO = new CourseStudentsDAO();

	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public String showCourseStudents(Course c) {

		this.course = c;
		try {
			this.students = this.courseStudentsDAO.getStudents(c);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "list_course_students";
	}

	public String showStudentCourse(Student s) {
		System.out.println(s.getSid());
		this.students = new ArrayList<>();
		try {
			this.students.add(this.courseStudentsDAO.getCourse(s));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "full_student_details";
	}
}

package com.student.Controllers;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.student.DAOs.MySqlDAO;
import com.student.Models.Course;
import com.student.Models.Student;

@SessionScoped
@ManagedBean
public class CourseStudentsController {

	MySqlDAO sqlDAO;
	private Course course;

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public ArrayList<Student> getStudents() {
		return students;
	}

	public void setStudents(ArrayList<Student> students) {
		this.students = students;
	}

	private ArrayList<Student> students;

	public CourseStudentsController() {

		try {
			sqlDAO = new MySqlDAO();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String showStudents(Course c) {

		this.course = c;
		this.students = this.sqlDAO.loadStudents(c);

		return "list_course_students";
	}
}

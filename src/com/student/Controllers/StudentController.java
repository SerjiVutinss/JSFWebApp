package com.student.Controllers;

import java.sql.SQLException;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.student.DAOs.DAO;
import com.student.DAOs.StudentDAO;
import com.student.Models.Student;

@ManagedBean
@SessionScoped
public class StudentController {

	private List<Student> students;

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	DAO<Student> studentDao;

	public StudentController() {

		try {
			studentDao = new StudentDAO();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void loadStudents() {

		try {
			this.students = this.studentDao.getAll();
		} catch (SQLException e) {
			FacesMessage msg = new FacesMessage("Students Loaded");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			e.printStackTrace();
		}
	}

	public String addStudent(Student s) {
		try {
			this.studentDao.save(s);
		} catch (SQLException e) {
			FacesMessage msg = new FacesMessage("Could not add student");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			e.printStackTrace();
		}

		return "list_student";
	}

	public String delete(Student s) {

		try {
			this.studentDao.delete(s);
		} catch (SQLException e) {
			FacesMessage msg = new FacesMessage("Error: Cannot delete");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			e.printStackTrace();
		}

		// returns page to navigate to - can also return null on error
		return "index.html";
	}
}

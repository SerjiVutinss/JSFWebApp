package com.student.Controllers;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.student.DAOs.MySqlDAO;
import com.student.Models.Student;

@ManagedBean
@SessionScoped
public class StudentController {

	private ArrayList<Student> students;

	public ArrayList<Student> getStudents() {
		return students;
	}
	public void setStudents(ArrayList<Student> students) {
		this.students = students;
	}

	MySqlDAO sqlDAO;

	public StudentController() {

		try {
			sqlDAO = new MySqlDAO();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
public void test() {
	System.out.println("we");
}
	public void loadStudents() {
		
		FacesMessage msg = new FacesMessage("Students Loaded");
		FacesContext.getCurrentInstance().addMessage(null, msg);

		this.students = sqlDAO.loadStudents();
	}

	public String delete() {
		FacesMessage msg = new FacesMessage("Error: Cannot delete");
		FacesContext.getCurrentInstance().addMessage(null, msg);

		// returns page to navigate to - can also return null on error
		return "index.html";
	}
}

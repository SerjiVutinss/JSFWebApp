package com.student.Controllers;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.student.DAOs.MySqlDAO;
import com.student.Models.Course;

@ManagedBean
@SessionScoped
public class CourseController {

	MySqlDAO sqlDAO;
	private ArrayList<Course> courses;

	public CourseController() {

		try {
			sqlDAO = new MySqlDAO();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<Course> getCourses() {
		return courses;
	}

	public void setCourses(ArrayList<Course> courses) {
		this.courses = courses;
	}

	public void test() {
		System.out.println("we");
	}

	public void loadCourses() {

//		FacesMessage msg = new FacesMessage("Courses Loaded");
//		FacesContext.getCurrentInstance().addMessage(null, msg);

		this.courses = sqlDAO.loadCourses();
	}

	public String addCourse(Course c) {
		System.out.println(c.getName());
		this.sqlDAO.addCourse(c);

		return "list_courses";
	}

	public String delete(Course c) {
//		FacesMessage msg = new FacesMessage("Error: Cannot delete");
//		FacesContext.getCurrentInstance().addMessage(null, msg);

		this.sqlDAO.delete(c);

		// returns page to navigate to - can also return null on error
		return "index.html";
	}
}

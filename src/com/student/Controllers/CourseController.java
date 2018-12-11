package com.student.Controllers;

import java.sql.SQLException;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;

import com.student.ErrorHandler;
import com.student.DAOs.CourseDAO;
import com.student.DAOs.DAO;
import com.student.Models.Course;

@ManagedBean
@SessionScoped
public class CourseController {

	DAO<Course> courseDao;
	private List<Course> courses;

	public CourseController() {
		try {
			courseDao = new CourseDAO();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public void test() {
		System.out.println("we");
	}

	public void loadCourses() {

		try {
			this.courses = courseDao.getAll();
		} catch (SQLException e) {
			FacesMessage msg = new FacesMessage(ErrorHandler.handleSqlException(e));
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public String addCourse(Course c) {
		try {
			this.courseDao.save(c);
		} catch (SQLException e) {

			FacesMessage msg;
			msg = new FacesMessage(ErrorHandler.handleSqlException(e, c));
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		} catch (NamingException e) {
			e.printStackTrace();
		}

		return "list_courses";
	}

	public String delete(Course c) {

		try {
			this.courseDao.delete(c);
		} catch (SQLException e) {
			FacesMessage msg;// = new FacesMessage("Error: Cannot delete");

			msg = new FacesMessage(ErrorHandler.handleSqlException(e, c));

			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		} catch (NamingException e) {
			e.printStackTrace();
			return null;
		}
		return "list_courses";
	}
}

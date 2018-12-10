package com.student.Controllers;

import java.sql.SQLException;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
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
			// TODO Auto-generated catch block
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
			FacesMessage msg = new FacesMessage("Could not load courses");
			FacesContext.getCurrentInstance().addMessage(null, msg);

			ErrorHandler.printSQLException(e);
			
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String addCourse(Course c) {
		try {
			this.courseDao.save(c);
		} catch (SQLException e) {

			FacesMessage msg;
//			if (e.getErrorCode() == 1062) {
//				FacesMessage msg = new FacesMessage("Error: CourseID " + c.getId() + " already exists");
//			}
//			ErrorHandler.printSQLException(e);
			msg = new FacesMessage(ErrorHandler.handleSqlException(e, c));
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		} catch (NamingException e) {
			// TODO Auto-generated catch block
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
//			e.printStackTrace();
//			ErrorHandler.printSQLException(e);
//			return null;
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// returns page to navigate to - can also return null on error
		return "index.html";
	}
}

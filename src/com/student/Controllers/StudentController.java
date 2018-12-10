package com.student.Controllers;

import java.sql.SQLException;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;

import com.student.ErrorHandler;
import com.student.DAOs.DAO;
import com.student.DAOs.StudentDAO;
import com.student.DAOs.StudentNeoDAO;
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
	StudentNeoDAO studentNeoDao;

	public StudentController() {

		studentDao = new StudentDAO();
		studentNeoDao = new StudentNeoDAO();

		try {
			studentDao.delete(new Student());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
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
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String addStudent(Student s) {
		try {
			this.studentDao.save(s);
			this.studentNeoDao.save(s);
		} catch (SQLException e) {

			FacesMessage msg;
			msg = new FacesMessage(ErrorHandler.handleSqlException(e, s));
			FacesContext.getCurrentInstance().addMessage(null, msg);

			ErrorHandler.printSQLException(e);
			return null;

		} catch (NamingException e) {
			e.printStackTrace();
		}

		return "list_student";
	}

	public String delete(Student s) {

		if (studentNeoDao.getNumberOfRelationShips(s.getName()) > 0) {
			// user has neo4j relationships, show message and do not delete
			FacesMessage msg = new FacesMessage("Error: Student " + s.getName()
					+ " has not been deleted from any database as he/she has relationships in Neo4j");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		} else {

			// user has no neo4j relation ships, proceed with deleting from both databases
			try {
				this.studentDao.delete(s);
				this.studentNeoDao.delete(s);

			} catch (SQLException e) {
				FacesMessage msg = new FacesMessage("Error: Cannot delete");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return null;
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			return "list_students";
		}
	}

	public String getNeo4jStudents() {

		System.out.println("GETTING NEO4J STUDENTS!!!");

		StudentNeoDAO studentNeoDAO = new StudentNeoDAO();

		studentNeoDAO.getStudents();

		return null;
	}
}

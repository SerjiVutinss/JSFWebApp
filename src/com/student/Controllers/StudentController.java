package com.student.Controllers;

import java.sql.SQLException;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;

import org.neo4j.driver.v1.exceptions.ServiceUnavailableException;

import com.student.ErrorHandler;
import com.student.DAOs.DAO;
import com.student.DAOs.StudentDAO;
import com.student.DAOs.StudentNeoDAO;
import com.student.Models.Student;

@ManagedBean
@SessionScoped
public class StudentController {

	DAO<Student> studentDao;
	StudentNeoDAO studentNeoDao; // this will be newed up and set to null on each use

	private List<Student> students;
	FacesMessage msg;

	public StudentController() {
		studentDao = new StudentDAO();
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public void loadStudents() {

		try {
			this.students = this.studentDao.getAll();
		} catch (SQLException e) {
			this.msg = new FacesMessage(ErrorHandler.handleSqlException(e));
			FacesContext.getCurrentInstance().addMessage(null, this.msg);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public String addStudent(Student s) {

		// if any exception is thrown here, all database operations will be cancelled
		try {
			this.studentNeoDao = new StudentNeoDAO();
			this.studentDao.save(s);
			this.studentNeoDao.save(s);

		} catch (SQLException e) {
			// get the correct message from error handler
			this.msg = new FacesMessage(ErrorHandler.handleSqlException(e, s));
			FacesContext.getCurrentInstance().addMessage(null, this.msg);
			return null;
		} catch (ServiceUnavailableException e) {
			// neo4j is not available
			this.msg = new FacesMessage(
					"Warning: Neo4j DB is unavailable - no operation performed for Student '" + s.getName() + "'");
			FacesContext.getCurrentInstance().addMessage(null, this.msg);
			return null;
		} catch (NamingException e) {
			e.printStackTrace();
			return null;
		} finally {
			// ensure that neo4j goes out of scope
			this.studentNeoDao = null;
		}

		return "list_students";
	}

	public String delete(Student s) {

		try {
			this.studentNeoDao = new StudentNeoDAO();

			// see if student has any relationships in neo4j
			if (studentNeoDao.getNumberOfRelationShips(s.getName()) == 0) {
				// no relationships, proceed with deletes
				this.studentDao.delete(s); // mysql
				this.studentNeoDao.delete(s); // neo4j

				// transaction complete, refresh this page
				return "list_students";

			} else {
				// user has no neo4j relationships, show message and do not delete
				this.msg = new FacesMessage("Error: Student " + s.getName()
						+ " has not been deleted from any database as he/she has relationships in Neo4j");
				FacesContext.getCurrentInstance().addMessage(null, this.msg);
				return null; // do nothing and return
			}
		} catch (ServiceUnavailableException e) {
			// neo4j is not available
			this.msg = new FacesMessage(
					"Warning: Student " + s.getName() + " has not been removed from Neo4j DB as it is unavailable");
			return null;
		} catch (SQLException e) {
			// if mysql database not available
			if (e.getErrorCode() == 0) {
				// set a no database available message
				this.msg = new FacesMessage(ErrorHandler.handleSqlException(e));
			} else {
				// handle more specific cases
				this.msg = new FacesMessage(ErrorHandler.handleSqlException(e, s));
			}
			// set the message
			FacesContext.getCurrentInstance().addMessage(null, this.msg);
			return null;
		} catch (NamingException e) {
			e.printStackTrace();
			return null;
		} finally {
			this.studentNeoDao = null;
		}
	}
}

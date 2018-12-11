package com.student;

import java.sql.SQLException;

import com.student.Models.Course;
import com.student.Models.Student;

public class ErrorHandler {

	public static final int ER_CONN_NOT_AVAILABLE = 0;

//	 Message: Duplicate entry '%s' for key %d 
	public static final int ER_DUP_ENTRY = 1062;

//	Message: Cannot delete or update a parent row: a foreign key constraint fails
	public static final int ER_ROW_IS_REFERENCED = 1451;

//	Message: Cannot add or update a child row: a foreign key constraint fails (%s) 
	public static final int ER_NO_REFERENCED_ROW = 1452;

	public static String handleSqlException(SQLException ex) {

		if (ex.getErrorCode() == 0) {
			return "Error: Cannot connect to MySQL database";
		}
		return ex.getMessage();

	}

	public static String handleSqlException(SQLException ex, Object o) {

		int errCode = ex.getErrorCode();

		switch (errCode) {

		case ER_CONN_NOT_AVAILABLE:
			return "Error: Cannot connect to MySQL database";

		// handle duplication - either primary key or unique column
		case ER_DUP_ENTRY:
			if (o instanceof Course) {
				// return a specific message
				return "Error: CourseID " + ((Course) o).getId() + " already exists";
			} else if (o instanceof Student) {
				// return the SQL error message
				return "Error: " + ex.getMessage();
			}
			break;

		case ER_ROW_IS_REFERENCED:
			if (o instanceof Course) {
				// return a specific message
				return "Error: Cannot delete Course (CourseID: " + ((Course) o).getId()
						+ ") as there are associated Students";
			}
			break;

		case ER_NO_REFERENCED_ROW:
			if (o instanceof Student) {
				return "Error: CourseID " + ((Student) o).getcID() + " does not exist";
			}
			break;

		}

		return "Error: " + ex.getMessage();

	}

	public static void printSQLException(SQLException ex) {

		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				if (ignoreSQLException(((SQLException) e).getSQLState()) == false) {

//					e.printStackTrace(System.err);
					System.err.println("SQLState: " + ((SQLException) e).getSQLState());

					System.err.println("Error Code: " + ((SQLException) e).getErrorCode());

					System.err.println("Message: " + e.getMessage());

					Throwable t = ex.getCause();
					while (t != null) {
						System.out.println("Cause: " + t);
						t = t.getCause();
					}
				}
			}
		}
	}

	public static boolean ignoreSQLException(String sqlState) {

		if (sqlState == null) {
			System.out.println("The SQL state is not defined!");
			return false;
		}

		// X0Y32: Jar file already exists in schema
		if (sqlState.equalsIgnoreCase("X0Y32"))
			return true;

		// 42Y55: Table already exists in schema
		if (sqlState.equalsIgnoreCase("42Y55"))
			return true;

		return false;
	}

}

package com.student.DataSources;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MySQLDataSource {

	private static DataSource ds;

	public static Connection getConnection() throws SQLException, NamingException {
		if (ds == null) {
			Context context;
//			try {
			context = new InitialContext();
			String dbName = "java:comp/env/studentDB";
			ds = (DataSource) context.lookup(dbName);
//			} catch (NamingException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
//			}
		}
		return ds.getConnection();
	}

}

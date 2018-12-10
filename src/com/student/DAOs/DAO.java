package com.student.DAOs;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.naming.NamingException;

public interface DAO<T> {

	Optional<T> get(String id);

	List<T> getAll() throws SQLException, NamingException;

	void save(T t) throws SQLException, NamingException;

	void update(T t);

	void delete(T t) throws SQLException, NamingException;
}

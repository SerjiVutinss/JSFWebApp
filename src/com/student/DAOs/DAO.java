package com.student.DAOs;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DAO<T> {

	Optional<T> get(String id);

	List<T> getAll() throws SQLException;

	void save(T t) throws SQLException;

	void update(T t);

	void delete(T t) throws SQLException;
}

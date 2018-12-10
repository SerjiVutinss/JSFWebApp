package com.student.DAOs;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

import com.student.DataSources.Neo4jDataSource;

import static org.neo4j.driver.v1.Values.parameters;

public class StudentNeoDAO {

	public void getStudents() {

		String name = "John";

		Session session = Neo4jDataSource.getDriver().session();

//		StatementResult result = session.run("match(s:Student{name: $name})-[:KNOWS]->(student) return student.name",
//				parameters("name", name));

		StatementResult result = session.run("match(s:STUDENT) return s.name");

		while (result.hasNext()) {
			Record record = result.next();
			System.out.println(record.get("s.name"));
		}

	}

}

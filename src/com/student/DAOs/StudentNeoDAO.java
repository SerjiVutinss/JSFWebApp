package com.student.DAOs;

import static org.neo4j.driver.v1.Values.parameters;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.exceptions.ServiceUnavailableException;

import com.student.DataSources.Neo4jDataSource;
import com.student.Models.Student;

public class StudentNeoDAO {

	private Session session;

	public StudentNeoDAO() throws ServiceUnavailableException {
		session = Neo4jDataSource.getDriver().session();
	}

	public void getStudents() {

		String name = "James";

		StatementResult result = session.run("match(s:STUDENT{name: $name})-[:KNOWS]->(student) return COUNT(student)",
				parameters("name", name));

		int count = 0;
		while (result.hasNext()) {
			Record record = result.next();
			count = record.get("COUNT(student)").asInt();
		}
		System.out.println(count);
	}

	public void save(Student s) {
		String strCypher = "CREATE (s:STUDENT{ name : $name, address: $address })";
		session.run(strCypher, parameters("name", s.getName(), "address", s.getAddress()));
	}

	public void delete(Student s) {
		String strCypher = "MATCH (s:STUDENT{ name : $name}) DELETE s";
		session.run(strCypher, parameters("name", s.getName()));
	}

	public int getNumberOfRelationShips(String name) {
		StatementResult result = session.run("match(s:STUDENT{name: $name})-[:KNOWS]-(student) return COUNT(student)",
				parameters("name", name));

		int count = 0;
		while (result.hasNext()) {
			Record record = result.next();
			count = record.get("COUNT(student)").asInt();
		}
		return count;
	}

	public int getNumberOfRelationShips(Student s) {
		return this.getNumberOfRelationShips(s.getName());
	}

	public void getStudentsNames() {

		StatementResult result = session.run("match(s:STUDENT) return s");

		while (result.hasNext()) {
			Record record = result.next();
			System.out.println(record.get("s").get("name"));
		}
	}

}

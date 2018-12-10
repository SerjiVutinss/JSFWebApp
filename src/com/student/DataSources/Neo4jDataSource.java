package com.student.DataSources;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;

public class Neo4jDataSource {

	public static final String address = "bolt://localhost:7687";
	public static final String username = "neo4j";
	public static final String password = "neo4jdb";

	private static Neo4jDataSource neo4jDataSource;
	private static Driver driver;

	private Neo4jDataSource() {
		driver = GraphDatabase.driver(address, AuthTokens.basic(username, password));
	}

	public static Driver getDriver() {
		if (neo4jDataSource == null) {
			neo4jDataSource = new Neo4jDataSource();
		}
		return driver;
	}

}

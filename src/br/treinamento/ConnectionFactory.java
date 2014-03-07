package br.treinamento;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

	private static Connection conn;
	
	public static Connection getConnection() throws Exception{
			String localBanco = "banco-hsql";
			
			Class.forName("org.hsqldb.jdbcDriver");
			conn = DriverManager.getConnection("jdbc:hsqldb:file:"+localBanco, "sa", "");
		return conn;
	}
}

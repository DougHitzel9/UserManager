package com.dhitzel.server.servlet;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.zaxxer.hikari.HikariDataSource;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

@WebServlet(urlPatterns = "/dataSourceFactory", loadOnStartup = 1)
public class DataSourceFactory extends HttpServlet {

	private static final Logger logger = LogManager.getLogger(DataSourceFactory.class);

	private static HikariDataSource dataSource = null;

	/**
	 * Setup connection pool when servlet is started.
	 */
	@Override
	public void init() throws ServletException {

		logger.info("*** DataSourceFactory.init()");

		try {
			InitialContext initialContext = new InitialContext();
			dataSource = (HikariDataSource) initialContext.lookup( "java:/comp/env/jdbc/postgres" );
		}
		catch (Exception e){
			throw new ServletException("Unable to retrieve DataSource. Exception encountered - " + e);
		}

		if ( dataSource == null ) {
			throw new ServletException("DataSource not found.");
		}
	}

	public static synchronized DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * Get database connection
	 */
	public static synchronized Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	/**
	 * Close the database connection to return it to pool
	 */
	public static synchronized void freeConnection(Connection connection) {

		if (connection == null) {
			return;
		}

		try {
			connection.close();
		} catch (Exception e) {
			logger.error("Unable to close a database connection. Exception encountered - " + e);
			e.printStackTrace();
		}
	}
}
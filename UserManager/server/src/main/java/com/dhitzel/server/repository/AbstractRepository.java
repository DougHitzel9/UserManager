package com.dhitzel.server.repository;

import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dhitzel.server.servlet.DataSourceFactory;

public abstract class AbstractRepository {

    protected Logger logger = LogManager.getLogger(AbstractRepository.class);

    protected List<?> listQuery(String sql, ResultSetHandler<?> resultSetHandler) throws Exception {
        DataSource dataSource = DataSourceFactory.getDataSource();

        QueryRunner queryRunner = new QueryRunner(dataSource);

        List<?> resultList = (List<?>) queryRunner.query(sql, resultSetHandler);
 
        return resultList;
    }

    protected List<?> listQuery(String sql, ResultSetHandler<?> resultSetHandler, Object[] params) throws Exception {
        DataSource dataSource = DataSourceFactory.getDataSource();

        QueryRunner queryRunner = new QueryRunner(dataSource);

        List<?> resultList = (List<?>) queryRunner.query(sql, resultSetHandler, params);
 
        return resultList;
    }

    /**
     * Perform the specified delete statement.
     *
     * @param sql
     * @param params
     * @return rows deleted
     * @throws Exception
     */
    protected int delete(String sql, Object[] params) throws Exception {
        DataSource dataSource = DataSourceFactory.getDataSource();

        QueryRunner queryRunner = new QueryRunner(dataSource);

        return queryRunner.update(sql, params);
    }

    /**
     * Perform the specified delete statement.
     *
     * @param sql
     * @param params
     * @throws Exception
     */
    protected void execute(String sql, Object[] params) throws Exception {
        DataSource dataSource = DataSourceFactory.getDataSource();

        QueryRunner queryRunner = new QueryRunner(dataSource);

        queryRunner.execute(sql, params);
    }

    /**
     * Perform the specified insert statement.
     *
     * @param sql
     * @param params
     * @return the unique row id
     * @throws Exception
     */
    protected Long insert(String sql, ResultSetHandler<?> resultSetHandler, Object[] params) throws Exception {
        DataSource dataSource = DataSourceFactory.getDataSource();

        QueryRunner queryRunner = new QueryRunner(dataSource);

        return (Long)queryRunner.insert(sql, resultSetHandler, params);
    }

    protected Object query(String sql, ResultSetHandler<?> resultSetHandler, Object[] params) throws Exception {
        DataSource dataSource = DataSourceFactory.getDataSource();

        QueryRunner queryRunner = new QueryRunner(dataSource);

        return queryRunner.query(sql, resultSetHandler, params);
    }

    /**
     * Perform the specified update statement.
     *
     * @param sql
     * @param params
     * @return rows updated
     * @throws Exception
     */
    protected int update(String sql, Object[] params) throws Exception {
        DataSource dataSource = DataSourceFactory.getDataSource();

        QueryRunner queryRunner = new QueryRunner(dataSource);

        return queryRunner.update(sql, params);
    }
}

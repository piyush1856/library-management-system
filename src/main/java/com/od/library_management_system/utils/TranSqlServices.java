package com.od.library_management_system.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class TranSqlServices {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public TranSqlServices() {
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = DataSourceUtils.getConnection(Objects.requireNonNull(jdbcTemplate.getDataSource()));
        } catch (CannotGetJdbcConnectionException ex) {
            throw new DataAccessException("Failed to get connection", ex) {
            };
        }
        return conn;
    }

    public void execute(String sql) {
        jdbcTemplate.execute(sql);
    }

    public int persist(String query, SqlParameterSource paramMap) throws DataAccessException {
        NamedParameterJdbcTemplate namedTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        return namedTemplate.update(query, paramMap);
    }

    public int getInteger(String sql, SqlParameterSource parmaMap) {
        NamedParameterJdbcTemplate namedTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        return namedTemplate.queryForObject(sql, parmaMap, Integer.class);
    }

    public Map getMap(String sql, SqlParameterSource paramMap) throws DataAccessException {
        NamedParameterJdbcTemplate namedTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        return namedTemplate.queryForMap(sql, paramMap);
    }

    public int updateDelete(String sql, Map<String, Object> params) throws DataAccessException {
        NamedParameterJdbcTemplate namedTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        return namedTemplate.update(sql, params);
    }

    public List<Map<String, Object>> getList(String sql) throws DataAccessException {
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getList(String sql, SqlParameterSource paramMap) throws DataAccessException {
        NamedParameterJdbcTemplate namedTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        return namedTemplate.queryForList(sql, paramMap);
    }

}


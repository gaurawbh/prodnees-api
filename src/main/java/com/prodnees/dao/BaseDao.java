package com.prodnees.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import javax.sql.DataSource;
import java.util.Map;
@Configuration
public abstract class BaseDao extends NamedParameterJdbcDaoSupport {

    @Autowired
    public void setDatasource2(DataSource ds) {
        super.setDataSource(ds);
    }

    public SimpleJdbcCall getSimpleJdbcCall() {
        return new SimpleJdbcCall(getJdbcTemplate());
    }

    /**
     * use to save object
     *
     * @param storedProc
     * @return
     */
    public SimpleJdbcCall getSimpleJdbcCall(String storedProc) {
        return getSimpleJdbcCall()
                .withProcedureName(storedProc);
    }

    /**
     * use to getObjectBy
     *
     * @param storedProc
     * @param returningResultSet
     * @param rowMapper
     * @return
     */
    public SimpleJdbcCall getSimpleJdbcCall(String storedProc, String returningResultSet, RowMapper<?> rowMapper) {
        return getSimpleJdbcCall()
                .withProcedureName(storedProc)
                .returningResultSet(returningResultSet, rowMapper);
    }

    public SqlParameterSource withInParam(String paramName, Object paramValue) {
        return new MapSqlParameterSource().addValue(paramName, paramValue);

    }

    public SqlParameterSource withMap(Map<String, ?> map) {
        return new MapSqlParameterSource(map);
    }
}

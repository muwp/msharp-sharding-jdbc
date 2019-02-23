package com.msharp.single.jdbc.jtemplate.single;

import org.springframework.jdbc.core.RowMapper;

import java.util.List;

/**
 * SimpleJdbcOperations
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public interface SimpleJdbcOperations {

    /**
     * insert
     *
     * @param bean
     * @param <T>
     * @return 返回影响的个数
     */
    <T> int insert(T bean);

    /**
     * update
     *
     * @param bean
     * @param <T>
     * @return
     */
    <T> int update(T bean);

    /**
     * delete
     *
     * @param id
     * @param clazz
     * @param <T>
     * @return
     */
    <T> int delete(long id, Class<T> clazz);

    /**
     * load
     *
     * @param id
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T load(long id, final Class<T> clazz);

    /**
     * load
     *
     * @param id
     * @param clazz
     * @param rowMapper
     * @param <T>
     * @return
     */
    <T> T load(long id, final Class<T> clazz, RowMapper<T> rowMapper);

    /**
     * get
     *
     * @param name
     * @param value
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T get(String name, Object value, final Class<T> clazz);

    /**
     * query
     *
     * @param request
     * @param <Request>
     * @return
     */
    <Request> List<Request> query(final Request request);

    /**
     * query
     *
     * @param request
     * @param offset
     * @param pageSize
     * @param <Request>
     * @return
     */
    <Request> List<Request> query(final Request request, int offset, int pageSize);

    /**
     * query
     *
     * @param request
     * @param rowMapper
     * @param <Request>
     * @param <Result>
     * @return
     */
    <Request, Result> List<Result> query(final Request request, RowMapper<Result> rowMapper);

    /**
     * query
     *
     * @param request   查询sql
     * @param rowMapper row 转化器
     * @param offset    分页的偏移量
     * @param pageSize  分页的大小
     * @param <Request> 请求参数model
     * @param <Result>  返回结果
     * @return 结果列表
     */
    <Request, Result> List<Result> query(final Request request, RowMapper<Result> rowMapper, int offset, int pageSize);

    /**
     * query
     *
     * @param sql      查询sql
     * @param params   查询参数
     * @param <Result> 返回结果
     * @return 结果列表
     */
    <Result> List<Result> query(String sql, Object[] params, final Class<Result> clazz);

    /**
     * query
     *
     * @param sql       查询sql
     * @param params    查询参数
     * @param rowMapper row 转化器
     * @param <Result>  返回结果
     * @return 结果列表
     */
    <Result> List<Result> query(String sql, Object[] params, RowMapper<Result> rowMapper);
}

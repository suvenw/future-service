package com.suven.framework.core.es.service;


import com.suven.framework.core.es.EsPage;
import com.suven.framework.core.es.model.UserTest;

import java.util.List;

/**
 * @Author dongxie
 * @Description: es公共api代码例子 服务类，  类似于 操作数据库的Service类
 * @CreateDate 2019-09-11  16:25
 **/

public interface UserTestService {

    /**
     *  保存对象
     * @param user
     */
    void saveUserTest(UserTest user);

    /**
     *  保存多个对象
     * @param users
     */
    void saveUserTestList(List<UserTest> users);

    /**
     * 更新
     * @param user
     */
    void updateUserTest(UserTest user);

    /**
     * 删除
     * @param id
     */
    void deleteUserTestById(String id);


    /**
     * 获取对象明细
     * @param id
     * @return
     */
    UserTest getUserTestById(String id);

    /**
     * 分页返回数据
     * @param page
     * @return
     */
    List<UserTest> getUserTestListByPage(EsPage page, UserTest userTest);

    /**
     * 批量删除
     * @param list idlist
     */
    void deleteBatch(List<String> list);


    /**
     *  执行全文检索 Multi Match Query (对所有字段进行扫描 )
     * @param page
     * @param query
     * @return
     */
    List<UserTest> multiBatchQuery(EsPage page, String query);

    /**
     * 同一个值 进行在多字段检索 (自定义需要 哪几个字段进行一起过滤搜索 )
     * @param page
     * @param query
     * @return
     */
    List<UserTest> multiFieldQuery(EsPage page, String query);


    /**
     * Wildcard Query 通配符检索
     * 要查找具有以 "t" 字母开头的所有记录
     * @param page
     * @param fieldName
     * @param pattern
     * @return
     */
    List<UserTest> wildcardQuery(EsPage page, String fieldName, String pattern);


    /**
     * 正则表达式检索( Regexp Query)
     * = "t[a-z]*y";regexp
     * @param page
     * @param fieldName
     * @param regexp
     * @return
     */
    List<UserTest> regexpQuery(EsPage page, String fieldName, String regexp);


    /**
     * 获取平均值聚合示例，最大值、最小值、求和类似
     */
    void aggregation();

    /**
     * Stats统计
     */
    void stats();

}
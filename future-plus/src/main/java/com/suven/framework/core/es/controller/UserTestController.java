package com.suven.framework.core.es.controller;

import com.alibaba.fastjson.JSON;
import com.suven.framework.core.es.EsPage;
import com.suven.framework.core.es.model.UserTest;
import com.suven.framework.core.es.service.UserTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author dongxie
 * @Description: es客户端 操作例子  控制器
 * @CreateDate 2019-09-11  16:32
 **/

@RestController
public class UserTestController {

    private Logger log = LoggerFactory.getLogger(UserTestController.class);
    @Autowired
    private UserTestService userTestRepository;

    public interface UrlCommand {
        public static String es_saveUserTest = "/es/saveUserTest";
        public static String es_saveUserTestList = "/es/saveUserTestList";
        public static String es_updateUserTest = "/es/updateUserTest";
        public static String es_deleteUserTestById = "/es/deleteUserTestById";
        public static String es_getUserTestById = "/es/getUserTestById";
        public static String es_deleteBatch = "/es/deleteBatch";
        public static String es_getUserTestListByPage = "/es/getUserTestListByPage";
        public static String es_multiBatchQuery = "/es/multiBatchQuery";
        public static String es_multiFieldQuery = "/es/multiFieldQuery";
        public static String es_wildcardQuery = "/es/wildcardQuery";
        public static String es_regexpQuery = "/es/regexpQuery";
        public static String es_aggregation = "/es/aggregation";
        public static String es_stats = "/es/stats";


    }

    @GetMapping(UrlCommand.es_saveUserTest)
    @ResponseBody
    public void saveUserTest() {
        UserTest userTest = new UserTest();
        userTest.setId("1");
        userTest.setName("天下东邪");
        userTest.setAge(18);
        userTest.setSex("男");
        userTest.setContext("es7 我终于把代码写完了");
        userTest.setUserId("001");
        userTestRepository.saveUserTest(userTest);

    }

    @GetMapping(UrlCommand.es_saveUserTestList)
    @ResponseBody
    public void saveUserTestList() {

        UserTest userTest = new UserTest();
        userTest.setId("2");
        userTest.setName("天下西毒");
        userTest.setAge(20);
        userTest.setSex("男");
        userTest.setContext("天下我最牛逼");
        userTest.setUserId("002");


        UserTest userTest2 = new UserTest();
        userTest2.setId("3");
        userTest2.setName("天下南帝");
        userTest2.setAge(30);
        userTest2.setSex("男");
        userTest2.setContext("天下我最风流");
        userTest2.setUserId("003");


        UserTest userTest10 = new UserTest();
        userTest10.setId("10");
        userTest10.setName("天下测试10");
        userTest10.setAge(30);
        userTest10.setSex("男");
        userTest10.setContext("潇洒哥");
        userTest10.setUserId("003");

        UserTest userTest11 = new UserTest();
        userTest11.setId("11");
        userTest11.setName("我测试11");
        userTest11.setAge(30);
        userTest11.setSex("女");
        userTest11.setContext("潇洒哥");
        userTest11.setUserId("003");

        UserTest userTest12 = new UserTest();
        userTest12.setId("12");
        userTest12.setName("测试12");
        userTest12.setAge(30);
        userTest12.setSex("女");
        userTest12.setContext("潇洒哥");
        userTest12.setUserId("003");


        UserTest userTest13 = new UserTest();
        userTest13.setId("13");
        userTest13.setName("测试13");
        userTest13.setAge(30);
        userTest13.setSex("女");
        userTest13.setContext("潇洒哥");
        userTest13.setUserId("003");


        UserTest userTest14 = new UserTest();
        userTest14.setId("14");
        userTest14.setName("测试14");
        userTest14.setAge(80);
        userTest14.setSex("女");
        userTest14.setContext("tasdfghjk");
        userTest14.setUserId("003");

        UserTest userTest15 = new UserTest();
        userTest15.setId("15");
        userTest15.setName("测试15");
        userTest15.setAge(55);
        userTest15.setSex("女");
        userTest15.setContext("sdfdsta");
        userTest15.setUserId("003");


        List<UserTest> userTestList = new ArrayList<UserTest>();
        userTestList.add(userTest);
        userTestList.add(userTest2);
        userTestList.add(userTest10);
        userTestList.add(userTest11);
        userTestList.add(userTest12);
        userTestList.add(userTest13);
        userTestList.add(userTest14);
        userTestList.add(userTest15);
        userTestRepository.saveUserTestList(userTestList);

    }


    @GetMapping(UrlCommand.es_updateUserTest)
    @ResponseBody
    public void updateUserTest() {
        UserTest userTest = new UserTest();
        userTest.setId("YMJgNG0Bdchl1VhYJra8");
        userTest.setName("北丐");
        userTest.setAge(15);
        userTest.setSex("男");
        userTest.setContext("别看我穿的布鞋，其实我是最有钱的");
//        userTest.setUserId("001");
        userTestRepository.updateUserTest(userTest);

    }


    @GetMapping(UrlCommand.es_deleteUserTestById)
    @ResponseBody
    public void deleteUserTestById() {
        userTestRepository.deleteUserTestById("10");

    }

    @GetMapping(UrlCommand.es_getUserTestById)
    @ResponseBody
    public void getUserTestById() {
        UserTest userTest = userTestRepository.getUserTestById("1");
        if (userTest != null) {
            log.info("查询数据成功");
            log.info("name:{}", userTest.getName());
            log.info("context:{}", userTest.getContext());
        }
    }


    @GetMapping(UrlCommand.es_deleteBatch)
    @ResponseBody
    public void deleteBatch() {
        List idList = new ArrayList();
        idList.add("11");
        idList.add("12");
        userTestRepository.deleteBatch(idList);
        log.info("批量删除成功");
    }


    @GetMapping(UrlCommand.es_getUserTestListByPage)
    @ResponseBody
    public void getUserTestListByPage() {
        EsPage<UserTest> page = new EsPage<>();
        page.setCurrentPage(0);
        page.setPageSize(10);
        UserTest userTest = new UserTest();
        userTest.setSex("男");
        userTest.setContext("天下");
        List<UserTest> UserTestList = userTestRepository.getUserTestListByPage(page, userTest);
        log.info("getUserTestListByPage 查询数量:{}", UserTestList.size());
        log.info("getUserTestListByPage 结果:{}", JSON.toJSONString(UserTestList));
    }


    @GetMapping(UrlCommand.es_multiBatchQuery)
    @ResponseBody
    public void multiBatchQuery() {
        EsPage<UserTest> page = new EsPage<>();
        page.setCurrentPage(0);
        page.setPageSize(10);
        List<UserTest> UserTestList = userTestRepository.multiBatchQuery(page, "我");
        log.info("multiBatchQuery 查询数量:{}", UserTestList.size());
        log.info("multiBatchQuery 结果:{}", JSON.toJSONString(UserTestList));
    }


    @GetMapping(UrlCommand.es_multiFieldQuery)
    @ResponseBody
    public void multiFieldQuery() {
        EsPage<UserTest> page = new EsPage<>();
        page.setCurrentPage(0);
        page.setPageSize(10);
        List<UserTest> UserTestList = userTestRepository.multiFieldQuery(page, "天下");
        log.info("multiFieldQuery 查询数量:{}", UserTestList.size());
        log.info("multiFieldQuery 结果:{}", JSON.toJSONString(UserTestList));
    }


    @GetMapping(UrlCommand.es_wildcardQuery)
    @ResponseBody
    public void wildcardQuery() {
        //此方法未做测试验证
        EsPage<UserTest> page = new EsPage<>();
        page.setCurrentPage(0);
        page.setPageSize(10);
        List<UserTest> UserTestList = userTestRepository.wildcardQuery(page, "context", "t");
        log.info("wildcardQuery 查询数量:{}", UserTestList.size());
        log.info("wildcardQuery 结果:{}", JSON.toJSONString(UserTestList));
    }


    @GetMapping(UrlCommand.es_regexpQuery)
    @ResponseBody
    public void regexpQuery() {
        //此方法未做测试验证
        EsPage<UserTest> page = new EsPage<>();
        page.setCurrentPage(0);
        page.setPageSize(10);
        List<UserTest> UserTestList = userTestRepository.regexpQuery(page, "context", "t[a-z]*y");
        log.info("regexpQuery 查询数量:{}", UserTestList.size());
        log.info("regexpQuery 结果:{}", JSON.toJSONString(UserTestList));
    }


    @GetMapping(UrlCommand.es_aggregation)
    @ResponseBody
    public void aggregation() {
        userTestRepository.aggregation();
    }

    @GetMapping(UrlCommand.es_stats)
    @ResponseBody
    public void stats() {
        userTestRepository.stats();
    }

}

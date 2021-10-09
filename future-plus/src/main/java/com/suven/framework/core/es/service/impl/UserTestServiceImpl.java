//package com.suven.framework.core.es.service.impl;
//
//import com.suven.framework.core.es.service.UserTestService;
//import com.suven.framework.core.es.UserTestRepository;
//import com.suven.framework.core.es.EsPage;
//import com.suven.framework.core.es.model.UserTest;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
///**
// * @Author dongxie
// * @Description: es公共api代码例子 服务实现类，  类似于 操作数据库的ServiceImpl类
// * @CreateDate 2019-09-12  14:58
// **/
//@Component
//public class UserTestServiceImpl implements UserTestService {
//
//
//    private Logger logger = LoggerFactory.getLogger(UserTestServiceImpl.class);
//
//    @Autowired
//    private UserTestRepository userTestRepository;
//
//
//    @Override
//    public void saveUserTest(UserTest user) {
//        userTestRepository.saveUserTest(user);
//    }
//
//    @Override
//    public void saveUserTestList(List<UserTest> users) {
//        userTestRepository.saveUserTestList(users);
//    }
//
//    @Override
//    public void updateUserTest(UserTest user) {
//        userTestRepository.updateUserTest(user);
//    }
//
//    @Override
//    public void deleteUserTestById(String id) {
//        userTestRepository.deleteUserTestById(id);
//    }
//
//    @Override
//    public UserTest getUserTestById(String id) {
//        return userTestRepository.getUserTestById(id);
//    }
//
//    @Override
//    public List<UserTest> getUserTestListByPage(EsPage page, UserTest userTest) {
//        String name=userTest.getName();
//        String context=userTest.getContext();
//        String userId=userTest.getUserId();
//        String sex=userTest.getSex();
//        Integer gte=18;
//        Integer lte=60;
//        return userTestRepository.getUserTestListByPage(page, name, context, userId, sex, gte, lte);
//    }
//
//    @Override
//    public void deleteBatch(List<String> list) {
//        userTestRepository.deleteBatch(list);
//    }
//
//    @Override
//    public List<UserTest> multiBatchQuery(EsPage page, String query) {
//        return userTestRepository.multiBatchQuery(page,query);
//    }
//
//    @Override
//    public List<UserTest> multiFieldQuery(EsPage page, String query) {
//        return userTestRepository.multiFieldQuery(page,query);
//    }
//
//    @Override
//    public List<UserTest> wildcardQuery(EsPage page, String fieldName, String pattern) {
//        return userTestRepository.wildcardQuery(page, fieldName, pattern);
//    }
//
//    @Override
//    public List<UserTest> regexpQuery(EsPage page, String fieldName, String regexp) {
//        return userTestRepository.regexpQuery(page, fieldName, regexp);
//    }
//
//    @Override
//    public void aggregation() {
//        userTestRepository.aggregation();
//    }
//
//    @Override
//    public void stats() {
//        userTestRepository.stats();
//    }
//
//
//}

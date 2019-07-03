//package com.coachtam.tqt;
//
//import org.activiti.engine.RepositoryService;
//import org.activiti.engine.repository.Deployment;
//import org.activiti.engine.repository.ProcessDefinition;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
///**
// * @Copyright (C), 2018-2019
// * @Author: JAVA在召唤
// * @Date: 2019-02-05 23:00
// * @Description:
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class LeaveTest {
//    @Autowired
//    private RepositoryService repositoryService;
//
//    @Test
//    public void test1(){
//        Deployment deploy = repositoryService.createDeployment().addClasspathResource("processes/qingjia.bpmn").deploy();
//
//        //通过RepositoryService -->查询流程定义
//        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deploy.getId()).singleResult();
//        System.out.println("流程key:"+processDefinition.getKey());
//        System.out.println("流程ID:"+processDefinition.getId());
//    }
//}

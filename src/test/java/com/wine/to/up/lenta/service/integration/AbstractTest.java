//package com.wine.to.up.lenta.service.integration;
//
//import com.wine.to.up.lenta.service.ServiceApplication;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = ServiceApplication.class)
//@WebAppConfiguration
//public abstract class AbstractTest {
//    protected MockMvc mvc;
//    @Autowired
//    WebApplicationContext webApplicationContext;
//    protected void setUp() {
//        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//    }
//}
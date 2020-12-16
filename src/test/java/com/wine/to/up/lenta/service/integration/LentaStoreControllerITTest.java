package com.wine.to.up.lenta.service.integration;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;

public class LentaStoreControllerITTest extends AbstractTest {
    @Override
    @Before
    public void setUp() {
        super.setUp();
    }
    //GET API test
    @Test
    @Ignore
    public void getProductsList() throws Exception {
        String uri = "/lenta";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }
}
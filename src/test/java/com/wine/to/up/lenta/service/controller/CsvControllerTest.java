package com.wine.to.up.lenta.service.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class CsvControllerTest extends AbstractTest{
    @Override
    @Before
    public void setUp() {
        super.setUp();
    }
    //GET API test
    @Test
    public void getProductsList() throws Exception {
        String uri = "/csv";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept("text/csv")).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }
}
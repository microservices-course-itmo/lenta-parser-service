package com.wine.to.up.lenta.service;

import com.wine.to.up.lenta.service.controller.LentaStoreController;
import com.wine.to.up.lenta.service.controller.SendKafkaController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LentaStoreController lentaStoreController;

    @Autowired
    private SendKafkaController sendKafkaController;

    @Test
    public void test1() throws Exception {
        assertThat(lentaStoreController).isNotNull();
    }

    @Test
    public void test2() throws Exception {
        assertThat(sendKafkaController).isNotNull();
    }


}

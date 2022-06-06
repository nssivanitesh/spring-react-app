package com.sbab.demo.controller;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.MethodName.class)
class TrafikLabControllerTest {
    @Autowired
    private TrafikLabController controller;

    @Autowired
    private MockMvc mockMvc;

    @Order(1)
    @Test
    public void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Order(2)
    @Test
    public void testHelloRoute() throws Exception {
        this.mockMvc.perform(get("/hello")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello")));
    }

    @Order(3)
    @Test
    public void testGetJourData() throws Exception {
        this.mockMvc.perform(get("/getJourData")).andDo(print()).andExpect(status().isOk());
    }

    @Order(4)
    @Test
    public void testGetStopsDataFor819() throws Exception {
        this.mockMvc.perform(get("/getStopsForLine")
                        .queryParam("lineNumber", "819")
                        .queryParam("directionCode", "1"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Order(5)
    @Test
    public void testGetStopsDataFor14() throws Exception {
        this.mockMvc.perform(get("/getStopsForLine")
                        .queryParam("lineNumber", "14")
                        .queryParam("directionCode", "1"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Order(6)
    @Test
    public void testGetStopsDataNegParam() throws Exception {
        this.mockMvc.perform(get("/getStopsForLine")
                        .queryParam("lineNumber", "-7")
                        .queryParam("directionCode", "-4"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Order(7)
    @Test
    public void testGetStopsDataNoParam() throws Exception {
        this.mockMvc.perform(get("/getStopsForLine")).andDo(print()).andExpect(status().is4xxClientError());
    }

    @Order(8)
    @Test
    public void testGetCatchAll() throws Exception {
        this.mockMvc.perform(get("/getRouteUnavailable")).andDo(print()).andExpect(status().is4xxClientError());
    }

    @Order(9)
    @Test
    public void testPutCatchAll() throws Exception {
        this.mockMvc.perform(put("/getRouteUnavailable")).andDo(print()).andExpect(status().is4xxClientError());
    }

    @Order(10)
    @Test
    public void testPostCatchAll() throws Exception {
        this.mockMvc.perform(post("/getRouteUnavailable")).andDo(print()).andExpect(status().is4xxClientError());
    }

    @Order(11)
    @Test
    public void testDeleteCatchAll() throws Exception {
        this.mockMvc.perform(delete("/getRouteUnavailable")).andDo(print()).andExpect(status().is4xxClientError());
    }
}

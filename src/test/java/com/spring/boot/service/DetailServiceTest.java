package com.spring.boot.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DetailServiceTest {

    @Autowired
    private DetailService detailService;

    @Test
    void getGwasGeneGraph() {
        System.out.println(detailService.getGwasGeneGraph("GSE121267"));
    }
}
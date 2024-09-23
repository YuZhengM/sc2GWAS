package com.spring.boot.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GeneMapperTest {

    @Autowired
    private GeneMapper geneMapper;

    @Test
    void searchGeneByGseId() {
        System.out.println(geneMapper.searchGeneByGseId("GSE221648"));
    }
}
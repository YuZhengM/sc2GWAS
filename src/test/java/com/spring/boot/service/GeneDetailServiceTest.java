package com.spring.boot.service;

import com.spring.boot.pojo.CommonSnp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class GeneDetailServiceTest {

    @Autowired
    private GeneDetailService geneDetailService;

    @Test
    void listGeneInteractiveCommonSnp() {
        List<CommonSnp> commonSnpList = geneDetailService.listGeneInteractiveCommonSnp("ZNFX1");
        System.out.println(commonSnpList);
    }
}
package com.spring.boot.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ClusterAnnoMapperTest {

    @Autowired
    private ClusterAnnoMapper clusterAnnoMapper;

    @Test
    void selectBySampleIdAndCellTypeAndNumber() {
        System.out.println(clusterAnnoMapper.selectBySampleIdAndCellTypeAndNumber("GSE221648", 0, 0));
    }

    @Test
    void searchGsmClusterCountByGseId() {
        System.out.println(clusterAnnoMapper.searchGsmClusterCountByGseId("GSE221648"));
    }
}
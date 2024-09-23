package com.spring.boot.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class ClusterCellCountMapperTest {

    @Autowired
    private ClusterCellCountMapper clusterCellCountMapper;

    @Test
    void searchCellTypeCountByGseIdList() {
        System.out.println(clusterCellCountMapper.searchCellTypeCountByGseIdList(Arrays.asList("GSE124898", "GSE190726"), "CD4+ T cell"));
    }
}
package com.spring.boot.mapper;

import com.spring.boot.pojo.GeneInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneInfoMapper {

    GeneInfo selectByGene(String gene);
}

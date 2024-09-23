package com.spring.boot.mapper;

import com.spring.boot.pojo.GeneDiseaseAssociation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeneDiseaseAssociationMapper {

    List<GeneDiseaseAssociation> selectByGene(String gene);
}
package com.spring.boot.mapper;

import com.spring.boot.pojo.SampleCount;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SampleCountMapper {

    List<SampleCount> searchByGseId(String gseId);
}

package com.spring.boot.mapper;

import com.spring.boot.pojo.ClusterAnno;
import com.spring.boot.pojo.SampleCount;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClusterAnnoMapper {

    List<ClusterAnno> searchByGseId(@Param("gseId") String gseId);

    List<ClusterAnno> selectBySampleIdAndCellTypeAndNumber(@Param("gseId") String gseId, @Param("cluster") Integer cluster, @Param("number") Integer number);

    List<ClusterAnno> selectBySampleIdAndGsmIdAndNumber(@Param("gseId") String gseId, @Param("gsmId") String gsmId, @Param("number") Integer cellNumber);

    List<SampleCount> searchGsmClusterCountByGseId(String gseId);
    List<String> searchCellTypeByGseId(String gseId);
}

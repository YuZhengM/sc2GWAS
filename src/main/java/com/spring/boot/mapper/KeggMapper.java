package com.spring.boot.mapper;

import com.spring.boot.pojo.Kegg;
import com.spring.boot.pojo.vo.EnrichmentVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeggMapper {

    List<Kegg> searchBySampleIdAndCluster(@Param("enrichment") EnrichmentVO enrichmentVO);

    List<Kegg> searchBySampleIdAndClusterAndNumber(@Param("enrichment") EnrichmentVO enrichmentVO);

    List<Kegg> searchBySampleIdAndClusterAndP(@Param("enrichment") EnrichmentVO enrichmentVO);
}

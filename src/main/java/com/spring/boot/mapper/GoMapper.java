package com.spring.boot.mapper;

import com.spring.boot.pojo.Go;
import com.spring.boot.pojo.vo.EnrichmentVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Yu Zhengmin
 */
@Repository
public interface GoMapper {

    List<Go> searchBySampleIdAndCluster(@Param("enrichment") EnrichmentVO enrichmentVO);

    List<Go> searchBySampleIdAndClusterAndNumber(@Param("enrichment") EnrichmentVO enrichmentVO);

    List<Go> searchBySampleIdAndClusterAndP(@Param("enrichment") EnrichmentVO enrichmentVO);

}
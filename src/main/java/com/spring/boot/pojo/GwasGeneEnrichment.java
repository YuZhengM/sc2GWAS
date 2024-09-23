package com.spring.boot.pojo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Data
public class GwasGeneEnrichment implements Serializable {

    private String gwasId;
    private String gseId;
    private String annotatedGene;
    private Integer inter;
    private Integer targetGeneNumber;
    private Double jaccard;
    private Double pValue;
    private Double fdr;
    private String fdrStr;
    private Double bonferroni;
    private String percentage;
    private String cluster;
    private String cellType;

}

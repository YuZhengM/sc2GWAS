package com.spring.boot.pojo.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class GwasGeneEnrichmentVO implements Serializable {

    private String content;

    private Double threshold;

    private Integer fdr;

    private String type;

    private Integer isFile;

    private String fileId;

}

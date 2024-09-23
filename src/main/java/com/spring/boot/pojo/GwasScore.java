package com.spring.boot.pojo;

import java.io.Serial;
import java.io.Serializable;

import lombok.Data;

/**
 * t_gwas_001_score
 */
@Data
public class GwasScore implements Serializable {
    private String barcode;

    private Integer cluster;

    private String cellType;

    private Double rawScore;

    private Double normScore;

    private Double mcPValue;

    private Double pValue;

    private Double nlog10PValue;

    private Double zScore;

    private String gwasId;

    private String gseId;

    @Serial
    private static final long serialVersionUID = 1L;
}
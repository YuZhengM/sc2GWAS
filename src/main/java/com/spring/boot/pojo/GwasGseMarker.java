package com.spring.boot.pojo;

import java.io.Serial;
import java.io.Serializable;

import lombok.Data;

/**
 * t_gwas_gse_marker
 */
@Data
public class GwasGseMarker implements Serializable {
    private String gwasId;

    private String gseId;

    private String gene;

    private String pValue;

    private String avgLog2fc;

    private Double pct1;

    private Double pct2;

    private Double score;

    private String pValueAdjust;

    private Double threshold;

    @Serial
    private static final long serialVersionUID = 1L;
}
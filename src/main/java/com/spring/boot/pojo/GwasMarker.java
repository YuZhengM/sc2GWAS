package com.spring.boot.pojo;

import java.io.Serial;
import java.io.Serializable;

import lombok.Data;

/**
 * t_gwas_marker_001
 */
@Data
public class GwasMarker implements Serializable {
    private String gwasId;

    private String gseId;

    private String gene;

    private String pValue;

    private String avgLog2fc;

    private Double pct1;

    private Double pct2;

    private String pValueAdjust;

    @Serial
    private static final long serialVersionUID = 1L;
}
package com.spring.boot.pojo;

import java.io.Serial;
import java.io.Serializable;

import lombok.Data;

/**
 * t_risk_snp
 */
@Data
public class RiskSnp implements Serializable {
    private String chr;

    private Integer start;

    private Integer end;

    private String rsId;

    private String ref;

    private String alt;

    private String gene;

    private String disease;

    private String type;

    private String pValue;

    private String gwasOr;

    private String pmid;

    @Serial
    private static final long serialVersionUID = 1L;
}
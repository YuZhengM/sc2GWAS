package com.spring.boot.pojo;

import java.io.Serial;
import java.io.Serializable;

import lombok.Data;

/**
 * t_cell_type_gse_gwas
 */
@Data
public class FdrGseGwas implements Serializable {
    private String gseId;

    private String gwasId;

    private Double stdDevFdr05;

    private Double stdDevFdr1;

    private Double stdDevFdr2;

    private Gwas gwas;
    private SingleCell singleCell;

    @Serial
    private static final long serialVersionUID = 1L;
}
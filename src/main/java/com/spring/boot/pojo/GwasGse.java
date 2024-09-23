package com.spring.boot.pojo;

import java.io.Serial;
import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

/**
 * t_gwas_gse
 */
@ToString
@Data
public class GwasGse implements Serializable {
    private String gwasId;

    private String gseId;

    private Gwas gwas;

    private SingleCell singleCell;

    @Serial
    private static final long serialVersionUID = 1L;
}

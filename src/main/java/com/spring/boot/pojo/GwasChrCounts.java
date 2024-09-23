package com.spring.boot.pojo;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

/**
 * t_gwas_chr_counts
 */
@Data
public class GwasChrCounts implements Serializable {
    private String gwasId;

    private String chr;

    private Integer count;

    @Serial
    private static final long serialVersionUID = 1L;
}
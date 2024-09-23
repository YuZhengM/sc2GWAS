package com.spring.boot.pojo;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

/**
 * t_common_snp
 */
@Data
public class CommonSnp implements Serializable {
    private String chr;

    private Integer start;

    private Integer end;

    private String rsId;

    private String ref;

    private String alt;

    @Serial
    private static final long serialVersionUID = 1L;
}
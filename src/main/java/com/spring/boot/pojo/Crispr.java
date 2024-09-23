package com.spring.boot.pojo;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

/**
 * t_crispr
 */
@Data
public class Crispr implements Serializable {
    private String chr;

    private Integer start;

    private Integer end;

    private String crisprTarget;

    @Serial
    private static final long serialVersionUID = 1L;
}
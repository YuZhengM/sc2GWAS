package com.spring.boot.pojo;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

/**
 * t_eqtl
 */
@Data
public class Eqtl implements Serializable {
    private String chr;

    private Integer start;

    private Integer end;

    private String rsId;

    private String ref;

    private String alt;

    private String gene;

    private String tissue;

    private String source;

    @Serial
    private static final long serialVersionUID = 1L;
}
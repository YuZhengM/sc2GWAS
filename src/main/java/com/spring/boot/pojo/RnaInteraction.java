package com.spring.boot.pojo;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

/**
 * t_rna_interaction
 */
@Data
public class RnaInteraction implements Serializable {
    private String chrA;

    private Integer startA;

    private Integer endA;

    private String chrB;

    private Integer startB;

    private Integer endB;

    private String cellType;

    @Serial
    private static final long serialVersionUID = 1L;
}
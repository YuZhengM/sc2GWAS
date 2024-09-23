package com.spring.boot.pojo;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

/**
 * t_gene
 */
@Data
public class Gene implements Serializable {
    private String gseId;

    private String gene;

    @Serial
    private static final long serialVersionUID = 1L;
}
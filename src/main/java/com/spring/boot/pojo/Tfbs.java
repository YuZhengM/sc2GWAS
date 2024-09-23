package com.spring.boot.pojo;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

/**
 * t_tfbs
 */
@Data
public class Tfbs implements Serializable {
    private String chr;

    private Integer start;

    private Integer end;

    private String tfName;

    private Integer score;

    private String strand;

    private String description;

    @Serial
    private static final long serialVersionUID = 1L;
}
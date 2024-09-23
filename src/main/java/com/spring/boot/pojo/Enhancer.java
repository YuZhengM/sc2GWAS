package com.spring.boot.pojo;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

/**
 * t_enhancer
 */
@Data
public class Enhancer implements Serializable {
    private String chr;

    private Integer start;

    private Integer end;

    private String enhancer;

    private String type;

    @Serial
    private static final long serialVersionUID = 1L;
}
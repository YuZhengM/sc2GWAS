package com.spring.boot.pojo;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

/**
 * t_super_enhancer_dbsuper
 */
@Data
public class SuperEnhancerDbsuper implements Serializable {
    private String chr;

    private Integer start;

    private Integer end;

    private String cellType;

    private String source;

    @Serial
    private static final long serialVersionUID = 1L;
}
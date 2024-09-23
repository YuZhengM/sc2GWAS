package com.spring.boot.pojo;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

/**
 * t_super_enhancer_seav3
 */
@Data
public class SuperEnhancerSeav3 implements Serializable {
    private String chr;

    private Integer start;

    private Integer end;

    private String cellType;

    private String source;

    @Serial
    private static final long serialVersionUID = 1L;
}
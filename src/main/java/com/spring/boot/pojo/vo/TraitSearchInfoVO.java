package com.spring.boot.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TraitSearchInfoVO implements Serializable {

    private List<String> domainList;

    private List<String> traitList;

}

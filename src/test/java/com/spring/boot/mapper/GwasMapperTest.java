package com.spring.boot.mapper;

import com.spring.boot.pojo.vo.SearchVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GwasMapperTest {

    @Autowired
    private GwasMapper gwasMapper;

    @Test
    void selectFieldByInfo() {
        SearchVO searchVO = new SearchVO();
        searchVO.setDomain("Psychiatric");
        System.out.println(gwasMapper.selectFieldByInfo("f_trait", searchVO));
    }
}
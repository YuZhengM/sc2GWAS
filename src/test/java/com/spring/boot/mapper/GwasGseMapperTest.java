package com.spring.boot.mapper;

import com.spring.boot.pojo.GwasGse;
import com.spring.boot.util.util.result.Page;
import com.spring.boot.util.util.result.PageResultUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class GwasGseMapperTest {

    @Autowired
    private GwasGseMapper gwasGseMapper;

    @Test
    void selectAll() {
        List<GwasGse> gwasGseList = gwasGseMapper.selectAll();
        System.out.println(gwasGseList);
    }

    @Test
    void selectByGseIdList() {
        System.out.println(gwasGseMapper.selectByGseIdList(Arrays.asList("GSE134649", "GSE190726")));
    }

    @Test
    void selectByGwasIdList() {
        System.out.println(gwasGseMapper.selectByGwasIdList(Arrays.asList("SC_G_001", "SC_G_002")));
    }

    @Test
    void selectAllWithPage() {
        Page page = Page.builder().build();
        System.out.println(PageResultUtil.format(page, () -> gwasGseMapper.selectAllWithPage(page)));
    }
}
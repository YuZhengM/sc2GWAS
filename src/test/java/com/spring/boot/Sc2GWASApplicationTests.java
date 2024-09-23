package com.spring.boot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Sc2GWASApplicationTests {

    @Test
    void contextLoads() {
    }

    public static void main(String[] args) {
        System.out.println("Alzheimer's Diseases".replaceAll("( disease.*| Disease.*|'s|’s)", ""));
    }

}

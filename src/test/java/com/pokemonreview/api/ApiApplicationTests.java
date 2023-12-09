package com.pokemonreview.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.test.database.replace=none"
})
@Testcontainers
class ApiApplicationTests {

    @Test
    void contextLoads() {
    }

}

package com.kh.fitness.integration;

import com.kh.fitness.integration.annotation.IT;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;

@Sql({
        "classpath:sql/clear-test-data.sql",
        "classpath:sql/test-data.sql"
})
@IT
public abstract class IntegrationTestBase {
    private static final String IMAGE_VERSION = "postgres:13.7";
    private static final PostgreSQLContainer<?> CONTAINER;

    static {
        CONTAINER = new PostgreSQLContainer<>(IMAGE_VERSION);
    }

    @BeforeAll
    static void runContainer() {
        CONTAINER.start();
    }

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", CONTAINER::getJdbcUrl);
    }
}
package com.kh.fitness.api.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PathUtilsTest {

    public static final String EXPECTED_URL_API_V1 = "/api/v1";

    @Test
    void checkApiV1Path() {
        assertThat(PathUtils.API_V1).isEqualTo(EXPECTED_URL_API_V1);
    }
}
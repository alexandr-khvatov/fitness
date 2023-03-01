package com.kh.fitness.integration.annotation;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ActiveProfiles({"test"})
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@SpringBootTest
public @interface IT {
}
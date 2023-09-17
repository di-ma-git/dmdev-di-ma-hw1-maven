package com.dima.service;

import com.dima.util.TestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
class ImageServiceTest extends TestBase {

    @Value("${app.image.bucket}")
    private final String bucket;
    private final ImageService imageService;

    @Test
    void upload() {

    }

    @Test
    void get() {

    }
}
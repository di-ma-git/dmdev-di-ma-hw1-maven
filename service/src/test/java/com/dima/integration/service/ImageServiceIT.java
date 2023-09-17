package com.dima.integration.service;

import com.dima.service.ImageService;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RequiredArgsConstructor
public class ImageServiceIT {

    private final ImageService imageService;

    @Value("${app.image.bucket}")
    private final String bucket;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("app.image.bucket", () -> System.getProperty("java.io.tmpdir"));
    }

    @Test
    void uploadImage() throws IOException {
        String testImageName = "test.jpg";
        var absolutePath = Path.of(bucket, testImageName);
        InputStream resourceInputStream = getClass().getClassLoader().getResourceAsStream(testImageName);
        var expectedResult = resourceInputStream.readAllBytes();

        imageService.upload("test.jpg", getClass().getClassLoader().getResourceAsStream(testImageName));

        var file = new File(absolutePath.toString());
        var actualResult = Files.readAllBytes(absolutePath);

        assertThat(file.exists()).isTrue();
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void getImage() throws IOException {
        String testImageName = "test.jpg";
        var absolutePath = Path.of(bucket, testImageName);
        InputStream resourceInputStream = getClass().getClassLoader().getResourceAsStream(testImageName);
        var expectedResult = resourceInputStream.readAllBytes();
        Files.copy(getClass().getClassLoader().getResourceAsStream(testImageName), absolutePath, StandardCopyOption.REPLACE_EXISTING);

        var actualResult = imageService.get(testImageName);

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(expectedResult);
    }

    @Test
    void getIfImageNotExist() {
        Optional<byte[]> retrievedImage = imageService.get("dummy.png");

        assertThat(retrievedImage.isEmpty()).isTrue();
    }

}

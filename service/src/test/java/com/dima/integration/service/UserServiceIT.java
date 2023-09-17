package com.dima.integration.service;

import com.dima.dto.UserCreateDto;
import com.dima.enums.Role;
import com.dima.repository.UserRepository;
import com.dima.service.UserService;
import com.dima.testdata.TestSimpleData;
import com.dima.util.TestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class UserServiceIT extends TestBase {

    private final UserService userService;
    private final UserRepository userRepository;

    @Test
    void findAll() {
        var result = userService.findAll();

        assertThat(result).hasSize(3);
    }

    @Test
    void findById() {
        var user = userRepository.save(TestSimpleData.getSimpleTestUser());

        var result = userService.findById(user.getId());

        assertThat(result).isPresent();
        result.ifPresent(u -> {
            assertThat(u.getName()).isEqualTo(user.getName());
            assertThat(u.getEmail()).isEqualTo(user.getEmail());
            assertThat(u.getPhoneNumber()).isEqualTo(user.getPhoneNumber());
            assertThat(u.getRole()).isEqualTo(user.getRole());
        });
    }

    @Test
    void create() {
        var userDto = UserCreateDto.builder()
                .name("test")
                .email("test@test.gamil.com")
                .phoneNumber("71111111111")
                .role(Role.ADMIN)
                .build();

        var actualResult = userService.create(userDto);

        assertThat(actualResult.getId()).isNotNull();
        assertThat(actualResult.getName()).isEqualTo(userDto.getName());
        assertThat(actualResult.getEmail()).isEqualTo(userDto.getEmail());
        assertThat(actualResult.getPhoneNumber()).isEqualTo(userDto.getPhoneNumber());
        assertThat(actualResult.getRole()).isSameAs(userDto.getRole());
    }

    @Test
    void update() {
        var user = userRepository.save(TestSimpleData.getSimpleTestUser());
        var userDto = UserCreateDto.builder()
                .name("test")
                .email("test@test.gamil.com")
                .phoneNumber("71111111111")
                .role(Role.ADMIN)
                .build();

        var actualResult = userService.update(user.getId(), userDto);

        assertThat(actualResult).isPresent();
        actualResult.ifPresent(u -> {
            assertThat(u.getName()).isEqualTo(userDto.getName());
            assertThat(u.getEmail()).isEqualTo(userDto.getEmail());
            assertThat(u.getPhoneNumber()).isEqualTo(userDto.getPhoneNumber());
            assertThat(u.getRole()).isSameAs(userDto.getRole());
        });
    }

    @Test
    void delete() {
        var user = userRepository.save(TestSimpleData.getSimpleTestUser());

        assertThat(userService.delete(user.getId())).isTrue();
        assertThat(userService.delete(-1L)).isFalse();
    }
}

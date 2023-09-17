package com.dima.integration.repository;

import com.dima.enums.Role;
import com.dima.repository.UserRepository;
import com.dima.util.TestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
class UserRepositoryIT extends TestBase {

    private final UserRepository userRepository;

    @Test
    void findUsersByRole() {

        var result = userRepository.findAllByRole(Role.ADMIN);

        assertThat(result.get(0).getEmail()).isEqualTo("admin@admin.pharmacy.com");
    }

    @Test
    void findUserInfoProjection() {
        var userInfo = userRepository.findByPhoneNumber("79876543211");

        assertThat(userInfo.get().getPhoneNumber()).isEqualTo("79876543211");
    }

}
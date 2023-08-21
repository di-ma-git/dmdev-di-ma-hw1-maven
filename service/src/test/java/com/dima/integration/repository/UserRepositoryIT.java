package com.dima.integration.repository;

import com.dima.enums.Role;
import com.dima.repository.UserRepository;
import com.dima.testdata.TestSimpleData;
import com.dima.util.TestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryIT extends TestBase {
    @Autowired
    private UserRepository userRepository;

    @Test
    void saveUserSuccessful() {
        var user = TestSimpleData.getSimpleTestUser();
        userRepository.save(user);
        entityManager.flush();
        entityManager.clear();

        assertThat(user.getId()).isNotNull();
    }

    @Test
    void findUserSuccessful() {
        var user = TestSimpleData.getSimpleTestUser();
        var order = TestSimpleData.getSimpleTestOrder();
        user.addOrder(order);
        userRepository.save(user);
        entityManager.flush();
        entityManager.clear();

        var actualResult = userRepository.findById(user.getId());

        actualResult.ifPresent(u -> assertThat(u).isEqualTo(user));
    }

    @Test
    void updateUserSuccessful() {
        var user = TestSimpleData.getSimpleTestUser();
        var order = TestSimpleData.getSimpleTestOrder();
        user.addOrder(order);
        userRepository.save(user);
        user.setPassword("54321");
        entityManager.flush();
        entityManager.clear();

        var actualResult = userRepository.findById(user.getId());

        assertThat(actualResult.get().getPassword()).isEqualTo("54321");
    }

    @Test
    void deleteUserSuccessful() {
        var user = TestSimpleData.getSimpleTestUser();
        var order = TestSimpleData.getSimpleTestOrder();
        user.addOrder(order);
        userRepository.save(user);
        entityManager.flush();
        entityManager.clear();
        userRepository.delete(user);

        var actualResult = userRepository.findById(user.getId());

        assertThat(actualResult).isEmpty();
    }

    @Test
    void findUsersByRole() {

        var result = userRepository.findUsersByRole(Role.ADMIN);

        assertThat(result.get(0).getEmail()).isEqualTo("admin@admin.pharmacy.com");
    }
}
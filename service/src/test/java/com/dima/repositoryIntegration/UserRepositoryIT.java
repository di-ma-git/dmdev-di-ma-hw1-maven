package com.dima.repositoryIntegration;

import com.dima.enums.Role;
import com.dima.repository.UserRepository;
import com.dima.testData.TestSimpleData;
import com.dima.util.TestBase;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryIT extends TestBase {

    private final UserRepository userRepository = context.getBean(UserRepository.class);

    @Test
    void saveUserSuccessful() {
        var user = TestSimpleData.getSimpleTestUser();
        userRepository.save(user);
        session.flush();
        session.clear();

        assertThat(user.getId()).isNotNull();
    }

    @Test
    void findUserSuccessful() {
        var user = TestSimpleData.getSimpleTestUser();
        var order = TestSimpleData.getSimpleTestOrder();
        user.addOrder(order);
        userRepository.save(user);
        session.flush();
        session.clear();

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
        session.flush();
        session.clear();

        var actualResult = userRepository.findById(user.getId());

        assertThat(actualResult.get().getPassword()).isEqualTo("54321");
    }

    @Test
    void deleteUserSuccessful() {
        var user = TestSimpleData.getSimpleTestUser();
        var order = TestSimpleData.getSimpleTestOrder();
        user.addOrder(order);
        userRepository.save(user);
        session.flush();
        session.clear();
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
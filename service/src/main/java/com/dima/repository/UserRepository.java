package com.dima.repository;

import com.dima.dto.UserInfo;
import com.dima.entity.User;
import com.dima.enums.Role;
import com.dima.repository.filters.FilterUserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
public interface UserRepository extends
        JpaRepository<User, Long>,
        FilterUserRepository,
        RevisionRepository<User, Long, Integer>,
        JpaSpecificationExecutor<User> {
    List<User> findAllByRole(Role role);
    Optional<UserInfo> findByPhoneNumber(String phone);
    @Query("select u from User u where u.name like %:fragment%")
    List<User> findAllByNameFragment(String fragment);

    @Query("select u from User u where u.name like %:fragment%")
    List<User> findAllByNameContainingIgnoreCase(String fragment);


}

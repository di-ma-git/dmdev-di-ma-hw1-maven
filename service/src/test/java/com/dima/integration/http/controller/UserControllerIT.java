package com.dima.integration.http.controller;

import com.dima.enums.Role;
import com.dima.repository.UserRepository;
import com.dima.testdata.TestSimpleData;
import com.dima.util.TestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static com.dima.dto.UserCreateDto.Fields.email;
import static com.dima.dto.UserCreateDto.Fields.name;
import static com.dima.dto.UserCreateDto.Fields.phoneNumber;
import static com.dima.dto.UserCreateDto.Fields.role;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.Matchers.hasProperty;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class UserControllerIT extends TestBase {

    private final MockMvc mockMvc;
    private final UserRepository userRepository;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/users"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users", hasSize(3)));
    }

    @Test
    void findById() throws Exception {
        var user = userRepository.save(TestSimpleData.getSimpleTestUser());

        mockMvc.perform(get("/users/" + user.getId()))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        view().name("user/user"),
                        model().attributeExists("user"),
                        model().attribute("user", hasProperty("name", equalTo("someuser2"))),
                        model().attributeExists("roles"),
                        model().attribute("roles", equalTo(Role.values()))
                );
    }

    @Test
    void findByIdFailedIfUserIsNotExist() throws Exception {
        mockMvc.perform(get("/users/50000"))
                .andExpect(view().name("error/error404"));
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/users")
                        .param(name, "Dmitry")
                        .param(email, "dm123@gmail.com")
                        .param(phoneNumber, "77777777777")
                        .param(role, "ADMIN")
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/users/{\\d+}")
                );
    }

    @Test
    void update() throws Exception {
        var user = userRepository.save(TestSimpleData.getSimpleTestUser());

        mockMvc.perform(post("/users/" + user.getId() + "/update")
                        .param(name, "Dmitry")
                        .param(email, "dm123@gmail.com")
                        .param(phoneNumber, "77777777777")
                        .param(role, "ADMIN"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/users/{\\d+}"));
    }

    @Test
    void updateFailedIfUserIsNotExist() throws Exception {
        mockMvc.perform(post("/users/50000/update")
                        .param(name, "Dmitry")
                        .param(email, "dm123@gmail.com")
                        .param(phoneNumber, "77777777777")
                        .param(role, "ADMIN"))
                .andExpect(view().name("error/error404"));

    }

    @Test
    void delete() throws Exception {
        var user = userRepository.save(TestSimpleData.getSimpleTestUser());

        mockMvc.perform(post("/users/" + user.getId() + "/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));
    }

    @Test
    void deleteFailedIfUserIsNotExist() throws Exception {
        mockMvc.perform(post("/users/50000/delete"))
                .andExpect(view().name("error/error404"));
    }

}
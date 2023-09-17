package com.dima.http.controller;

import com.dima.dto.PageableResponse;
import com.dima.dto.UserCreateDto;
import com.dima.dto.filters.UserFilter;
import com.dima.enums.Role;
import com.dima.repository.UserRepository;
import com.dima.service.UserService;
import com.dima.validation.CreateAction;
import com.dima.validation.UpdateAction;
import javax.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping
    public String findAll(Model model, UserFilter filter, Pageable pageable) {
        var page = userService.findAll(filter, pageable);
        model.addAttribute("filter", filter);
        model.addAttribute("users", PageableResponse.of(page));
        model.addAttribute("roles", Role.values());

        return "user/users";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        return userService.findById(id)
                .map(user -> {
                    model.addAttribute("user", user);
                    model.addAttribute("roles", Role.values());
                    return "user/user";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/registration")
    public String registration(Model model, @ModelAttribute("user") UserCreateDto user) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "user/registration";
    }

    @PostMapping
    public String create(@ModelAttribute @Validated({Default.class, CreateAction.class}) UserCreateDto user,
                         BindingResult bindingResult, // должен идти сразу после валидации
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("name", user);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/users/registration";
        }
        return "redirect:/users/" + userService.create(user).getId();
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, @ModelAttribute @Validated({Default.class, UpdateAction.class}) UserCreateDto userDto) {
        return userService.update(id, userDto)
                .map(it -> "redirect:/users/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        if (!userService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/users";
    }

}

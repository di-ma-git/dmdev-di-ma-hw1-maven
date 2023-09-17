package com.dima.dto.filters;

import com.dima.enums.Role;

public record UserFilter(String name,
                         String email,
                         String phoneNumber,
                         Role role) {
}

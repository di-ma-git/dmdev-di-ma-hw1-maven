package com.dima.dto.filters;

import com.dima.enums.Role;

public record UserFilter(String username,
                         Role role) {
}

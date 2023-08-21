package com.dima.repository.filters;

import com.dima.dto.filters.UserFilter;
import com.dima.entity.User;
import java.util.List;

public interface FilterUserRepository {

    List<User> findAllByFilter(UserFilter filter);
}

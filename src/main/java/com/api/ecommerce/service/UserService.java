package com.api.ecommerce.service;

import com.api.ecommerce.dto.UserResponse;

public interface UserService {

    UserResponse getUsers(int pageNo, int pageSize );
}

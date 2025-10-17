package com.api.ecommerce.dto;

import com.api.ecommerce.models.User;
import lombok.Data;

import java.util.List;

@Data
public class UserResponse {
    private List<UserDto> content;
    private int pageNo;
    private int pageSize;
    private int totalPages;
    private long totalElements;
    private boolean last;
}

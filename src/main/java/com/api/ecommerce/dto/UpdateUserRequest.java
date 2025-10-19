package com.api.ecommerce.dto;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String lastName;
    private String firstName;
    private String email;
}

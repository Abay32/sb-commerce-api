package com.api.ecommerce.dto;

import com.api.ecommerce.models.Address;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class UserDto {
    @JsonProperty("user_id")
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<Address> addresses;

    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:SS")
    private LocalDateTime createdDate;

}

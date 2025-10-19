package com.api.ecommerce.mappers;

import com.api.ecommerce.dto.CreateUserRequest;
import com.api.ecommerce.dto.UpdateUserRequest;
import com.api.ecommerce.dto.UserDto;
import com.api.ecommerce.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "createdDate", expression = "java(java.time.LocalDateTime.now())")
    UserDto toDto(User user);

    User toEntity(CreateUserRequest createUserRequest);

    void update(UpdateUserRequest request, @MappingTarget User user);
}

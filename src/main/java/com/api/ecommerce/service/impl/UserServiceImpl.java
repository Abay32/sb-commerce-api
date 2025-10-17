package com.api.ecommerce.service.impl;

import com.api.ecommerce.dto.UserDto;
import com.api.ecommerce.dto.UserResponse;
import com.api.ecommerce.exceptions.UserNotFoundException;
import com.api.ecommerce.models.User;
import com.api.ecommerce.repositories.UserRepository;
import com.api.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse getUsers(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<User> users = userRepository.findAll(pageable);

        List<User> listOfUsers = users.getContent();

        List<UserDto> content = listOfUsers.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        UserResponse userResponse = new UserResponse();
        userResponse.setContent(content);
        userResponse.setPageNo(pageNo);
        userResponse.setPageSize(pageSize);
        userResponse.setTotalPages(users.getTotalPages());
        userResponse.setTotalElements(users.getTotalElements());
        userResponse.setLast(users.isLast());


        return userResponse;
    }


    @Override
    public UserDto getUserById(Long id){
        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found"));
        return  mapToDto(user);
    }

    private UserDto mapToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());

        return userDto;
    }


}

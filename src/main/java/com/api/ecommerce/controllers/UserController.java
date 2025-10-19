package com.api.ecommerce.controllers;

import com.api.ecommerce.dto.UserDto;
import com.api.ecommerce.dto.UserResponse;
import com.api.ecommerce.exceptions.UserNotFoundException;
import com.api.ecommerce.mappers.UserMapper;
import com.api.ecommerce.models.User;
import com.api.ecommerce.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping("user/{id}")
    public ResponseEntity<User> userDetails(@PathVariable Long id){
        var user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        return  ResponseEntity.ok( user );
    }


    @GetMapping("users")
    public ResponseEntity<UserResponse> getUsers(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam String sort
            ){

            Pageable pageable = PageRequest.of(pageNo, pageSize);
            Page<User> users = userRepository.findAll(pageable);

            List<User> listOfUsers = users.getContent();

            List<UserDto> content = listOfUsers.stream()
                    .map(userMapper::toDto)
                    .collect(Collectors.toList());

            UserResponse userResponse = new UserResponse();
            userResponse.setContent(content);
            userResponse.setPageNo(pageNo);
            userResponse.setPageSize(pageSize);
            userResponse.setTotalPages(users.getTotalPages());
            userResponse.setTotalElements(users.getTotalElements());
            userResponse.setLast(users.isLast());

        return new ResponseEntity<>(userResponse, HttpStatus.OK);

        //return new ResponseEntity<>(userService.getUsers(pageNo, pageSize), HttpStatus.OK);
    }



}


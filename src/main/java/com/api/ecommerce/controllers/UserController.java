package com.api.ecommerce.controllers;

import com.api.ecommerce.dto.*;
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
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Set;
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
            @RequestParam(name = "sortBy", defaultValue = "", required = false) String sortBy
            ){

            if (!Set.of("firstName", "lastName", "createdDate").contains(sortBy)){
                sortBy = "firstName";
            }

            Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
            Page<User> users = userRepository.findAll((pageable));

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

    @PostMapping("user/create")
    public ResponseEntity<UserDto> createUser(
            UriComponentsBuilder uriBuilder,
            @RequestBody CreateUserRequest createUserRequest){
        var user = userMapper.toEntity(createUserRequest);
        userRepository.save(user);

        var userDto = userMapper.toDto(user);
        var uri = uriBuilder.path("/user/create/{id}").buildAndExpand(userDto.getId()).toUri();

        return  ResponseEntity.created(uri).body(userDto);
    }

    @PutMapping("user/{id}/update")
    public ResponseEntity<UserDto> updateUser(
            @RequestBody UpdateUserRequest updateRequest,
            @PathVariable(name = "id") Long id){
        var user = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User not found!"));

        userMapper.update(updateRequest, user);
        userRepository.save(user);

        return ResponseEntity.ok(userMapper.toDto(user));

    }

    @DeleteMapping("user/{id}/delete")
    public ResponseEntity<Void> deleteUser(@PathVariable(name="id") Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User not found!"));

        userRepository.delete(user);
        return ResponseEntity.noContent().build();


    }

    @PostMapping("user/{id}/change-password")
    public ResponseEntity<Void> changePassword(
            @PathVariable(name = "id") Long id,
            @RequestBody ChangePasswordRequest changeRequest
        ){
        var user = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User not found!"));
        if (!user.getPassword().equals(changeRequest.getOldPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        user.setPassword(changeRequest.getNewPassword());
        userRepository.save(user);

        return ResponseEntity.noContent().build();
    }



}


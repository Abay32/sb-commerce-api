package com.api.ecommerce.controllers;

import com.api.ecommerce.dto.UserResponse;
import com.api.ecommerce.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private UserService userService;

    @GetMapping("users")
    public ResponseEntity<UserResponse> getUsers(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize

            ){
        return new ResponseEntity<>(userService.getUsers(pageNo, pageSize), HttpStatus.OK);
    }


}


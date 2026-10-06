package com.lms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.dto.UserDto;
import com.lms.service.IUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
@Tag(
    name = "User APIs",
    description = "APIs related to Library Users"
)
public class UserController {

    @Autowired
    IUserService userService;


    // ==============================
    // SAVE USER
    // ==============================

    @Operation(
        operationId = "CreateUser",
        summary = "Adding User",
        description = "This REST endpoint is used to create a user along with address"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "User created successfully"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid user details"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Address not found"
        )
    })
    @PostMapping("/addressId/{addressId}")
    public ResponseEntity<UserDto> saveUser(
            @Valid
            @RequestBody UserDto userDto,
            @PathVariable int addressId) {

        return userService.saveUser(userDto, addressId);
    }


    // ==============================
    // GET USER BY ID
    // ==============================

    @Operation(
        operationId = "FetchUser",
        summary = "Fetch One User",
        description = "Fetch one user based on user ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "User fetched successfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "User not found"
        )
    })
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(
            @PathVariable int userId) {

        return userService.findUserById(userId);
    }


    // ==============================
    // GET ALL USERS
    // ==============================

    @Operation(
        operationId = "FetchAllUsers",
        summary = "Fetch All Users",
        description = "Fetch all users from the library"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Users fetched successfully"
        )
    })
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {

        return userService.findAllUsers();
    }


    // ==============================
    // UPDATE USER
    // ==============================

    @Operation(
        operationId = "UpdateUser",
        summary = "Update User",
        description = "Update user details"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "User updated successfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "User not found"
        )
    })
    @PutMapping
    public ResponseEntity<UserDto> updateUser(
            @RequestBody UserDto userDto) {

        return userService.updateUser(userDto);
    }


    // ==============================
    // DELETE USER
    // ==============================

    @Operation(
        operationId = "DeleteUser",
        summary = "Delete User",
        description = "Delete user using user ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "User deleted successfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "User not found"
        )
    })
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(
            @PathVariable int userId) {

        return userService.deleteUser(userId);
    }
}
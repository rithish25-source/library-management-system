package com.lms.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.lms.dto.UserDto;

public interface IUserService {

    ResponseEntity<UserDto> saveUser(UserDto userDto, int addressId);

    ResponseEntity<UserDto> findUserById(int userId);

    ResponseEntity<List<UserDto>> findAllUsers();

    ResponseEntity<UserDto> updateUser(UserDto userDto);

    ResponseEntity<String> deleteUser(int userId);
}
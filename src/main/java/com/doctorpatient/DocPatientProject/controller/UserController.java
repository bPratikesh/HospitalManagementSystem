package com.doctorpatient.DocPatientProject.controller;

import com.doctorpatient.DocPatientProject.dto.UserRequestDto;
import com.doctorpatient.DocPatientProject.dto.UserResponseDto;
import com.doctorpatient.DocPatientProject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public UserResponseDto createUser(@RequestBody UserRequestDto userRequestDto){
        return userService.createUser(userRequestDto);
    }

    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @GetMapping("/getAll")
    public List<UserResponseDto> getAllUser(){
        return userService.getAllUsers();
    }

    @PutMapping("/update/{id}")
    public UserResponseDto updateUser(@PathVariable Long id, @RequestBody UserRequestDto userRequestDto){
        return userService.updateUser(id, userRequestDto);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User Deleted Successfully with id " + id + "!!!";
    }
}
package com.doctorpatient.DocPatientProject.service;

import com.doctorpatient.DocPatientProject.dto.UserRequestDto;
import com.doctorpatient.DocPatientProject.dto.UserResponseDto;
import com.doctorpatient.DocPatientProject.entity.User;
import com.doctorpatient.DocPatientProject.exception.ResourceNotFoundException;
import com.doctorpatient.DocPatientProject.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final ModelMapper modelMapper;

    public UserResponseDto createUser(UserRequestDto userRequestDto) {

        User user = modelMapper.map(userRequestDto, User.class);
        User savedUser = userRepo.save(user);
        return modelMapper.map(savedUser, UserResponseDto.class);
    }

    public UserResponseDto getUserById(Long id) {

        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User is not present with id: " + id));
        return modelMapper.map(user, UserResponseDto.class);
    }

    public List<UserResponseDto> getAllUsers() {

        return userRepo.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserResponseDto.class))
                .collect(Collectors.toList());
    }

    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {

        User existingUser = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User is not found with id: " + id));

        if (userRequestDto.getEmail() != null)
            existingUser.setEmail(userRequestDto.getEmail());
        if (userRequestDto.getName() != null)
            existingUser.setName(userRequestDto.getName());
        if (userRequestDto.getPassword() != null)
            existingUser.setPassword(userRequestDto.getPassword());
        User updatedUser = userRepo.save(existingUser);
        return modelMapper.map(updatedUser, UserResponseDto.class);
    }

    public void deleteUser(Long id) {

        User existingUser = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User is not present with id: " + id));
        userRepo.delete(existingUser);
    }

}
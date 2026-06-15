package com.doctorpatient.DocPatientProject.service;

import com.doctorpatient.DocPatientProject.entity.User;
import com.doctorpatient.DocPatientProject.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    public User createUser(User user){
        return userRepo.save(user);
    }

    public User getUserById(Long id){
        return userRepo.findById(id).orElseThrow(()->
                new RuntimeException("User is not present with id: "+id));
    }

    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    public User updateUser(Long id, User user){

        User existingUser = userRepo.findById(id).orElseThrow(()->
                new RuntimeException("User is not present with id: "+id));

        if (user.getEmail() != null) existingUser.setEmail(user.getEmail());
        if (user.getName() != null) existingUser.setName(user.getName());
        if (user.getPassword() != null) existingUser.setPassword(user.getPassword());

        return userRepo.save(existingUser);
    }

    public void deleteUser(Long id){
        User existingUser = userRepo.findById(id).orElseThrow(()->
                new RuntimeException("User is not present with id: "+id));
        userRepo.delete(existingUser);
    }

}

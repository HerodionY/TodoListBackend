package com.example.todolist.Service;

import com.example.todolist.Repository.UserRepository;
import com.example.todolist.model.User;
import com.example.todolist.model.UserCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User loginUser(UserCredentials credentials) {
        return userRepository.findByUsernameAndPassword(credentials.getUsername(), credentials.getPassword());
    }

    public User registerUser(User user) {
        // Implementasikan logika pendaftaran pengguna
        return userRepository.save(user);
    }
}

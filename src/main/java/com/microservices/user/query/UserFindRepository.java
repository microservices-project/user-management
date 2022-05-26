package com.microservices.user.query;

import com.microservices.user.core.data.User;
import com.microservices.user.core.data.UserRepository;
import com.microservices.common.exception.DataNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserFindRepository {

    private final UserRepository userRepository;

    public UserFindRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User find(Long id){
        return userRepository.findById(id).orElseThrow(() -> new DataNotFoundException("User not found"));
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new DataNotFoundException("User not found"));
    }
}

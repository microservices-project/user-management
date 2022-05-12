package com.microservices.user.command;

import com.microservices.user.core.data.User;
import com.microservices.user.core.data.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserCreateRepository{

    private final UserRepository userRepository;

    public UserCreateRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    void create(User user){
        userRepository.save(user);
    }

    void delete(Long id){
        userRepository.deleteById(id);
    }
}

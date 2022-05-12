package com.microservices.user.query;

import com.microservices.user.core.data.User;
import com.microservices.user.core.data.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserFindRepository {

    private final UserRepository userRepository;

    public UserFindRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User find(Long id){
        return userRepository.findById(id).get();
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }
}

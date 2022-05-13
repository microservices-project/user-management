package com.microservices.user.command;

import com.microservices.user.command.mapper.CreateUserMapper;
import com.microservices.user.command.rest.CreateUserRestModel;
import org.springframework.stereotype.Component;

@Component
public class UserAggregate {
    private final UserCreateRepository userCreateRepository;
    private final CreateUserMapper createUserMapper;

    public UserAggregate(UserCreateRepository userCreateRepository, CreateUserMapper createUserMapper) {
        this.userCreateRepository = userCreateRepository;
        this.createUserMapper = createUserMapper;
    }

    public void handleCreateUserCommand(CreateUserRestModel user){
        userCreateRepository.create(createUserMapper.toEntity(user));
        // TODO send event to Kafka
    }

    public void handleDeleteUserCommand(Long id){
        userCreateRepository.delete(id);
        // TODO send event to Kafka
    }
}

package com.microservices.user.command;

import com.microservices.user.command.mapper.CreateUserMapper;
import com.microservices.user.command.rest.CreateUserRestModel;
import com.microservices.user.command.rest.LoginRequestRestModel;
import com.microservices.user.command.rest.LoginResponseRestModel;
import com.microservices.user.core.config.security.JwtTokenUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserAggregate {
    private final UserCreateRepository userCreateRepository;
    private final CreateUserMapper createUserMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    public UserAggregate(UserCreateRepository userCreateRepository, CreateUserMapper createUserMapper, AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, PasswordEncoder passwordEncoder) {
        this.userCreateRepository = userCreateRepository;
        this.createUserMapper = createUserMapper;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public void handleCreateUserCommand(CreateUserRestModel user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userCreateRepository.create(createUserMapper.toEntity(user));
        // TODO send event to Kafka
    }

    public void handleDeleteUserCommand(Long id){
        userCreateRepository.delete(id);
        // TODO send event to Kafka
    }

    public LoginResponseRestModel handleSignInCommand(LoginRequestRestModel user){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        String jwt = jwtTokenUtil.generateToken(user);

        return new LoginResponseRestModel(jwt);
    }
}

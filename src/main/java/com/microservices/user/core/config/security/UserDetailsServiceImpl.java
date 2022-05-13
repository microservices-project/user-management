package com.microservices.user.core.config.security;

import com.microservices.user.query.UserProjection;
import com.microservices.user.query.rest.FindUserQueryModel;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserProjection userProjection;

    public UserDetailsServiceImpl(UserProjection userProjection) {
        this.userProjection = userProjection;
    }

    @Override
    public UserDetailsImpl loadUserByUsername(String s) {
        FindUserQueryModel user  = userProjection.handleFindUserByEmail(s);

        UserDetailsImpl userToReturn = new UserDetailsImpl();
        userToReturn.setEmail(user.getEmail());
        userToReturn.setPassword(user.getPassword());
        userToReturn.setFirstName(user.getFirstName());
        userToReturn.setLastName(user.getLastName());
        userToReturn.setAddress(user.getAddress());

        return userToReturn;
    }
}

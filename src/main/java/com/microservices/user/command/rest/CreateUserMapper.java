package com.microservices.user.command.rest;

import com.microservices.user.core.data.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateUserMapper {

    User toEntity(CreateUserRestModel user);
}

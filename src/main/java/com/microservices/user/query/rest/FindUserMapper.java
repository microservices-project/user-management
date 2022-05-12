package com.microservices.user.query.rest;

import com.microservices.user.core.data.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FindUserMapper {

    FindUserQueryModel toModel(User user);

    List<FindUserQueryModel> toModelList(List<User> users);
}

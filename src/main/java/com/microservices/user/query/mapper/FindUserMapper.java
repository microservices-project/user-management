package com.microservices.user.query.mapper;

import com.microservices.user.core.data.User;
import com.microservices.user.query.rest.FindUserQueryModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FindUserMapper {

    FindUserQueryModel toModel(User user);

    List<FindUserQueryModel> toModelList(List<User> users);
}

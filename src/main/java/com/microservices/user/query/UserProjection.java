package com.microservices.user.query;

import com.microservices.user.query.mapper.FindUserMapper;
import com.microservices.user.query.rest.FindUserQueryModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserProjection {

    private final UserFindRepository userFindRepository;
    private final FindUserMapper findUserMapper;

    public UserProjection(UserFindRepository userFindRepository, FindUserMapper findUserMapper) {
        this.userFindRepository = userFindRepository;
        this.findUserMapper = findUserMapper;
    }

    public FindUserQueryModel handleFindUser(Long id){
        return findUserMapper.toModel(userFindRepository.find(id));
    }

    public List<FindUserQueryModel> handleFindUsers(){
        return findUserMapper.toModelList(userFindRepository.findAll());
    }
}

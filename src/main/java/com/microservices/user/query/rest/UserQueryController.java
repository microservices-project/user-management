package com.microservices.user.query.rest;

import com.microservices.user.query.UserProjection;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user-management")
public class UserQueryController {

    private final UserProjection userProjection;

    public UserQueryController(UserProjection userProjection) {
        this.userProjection = userProjection;
    }

    @GetMapping("/users")
    public ResponseEntity<List<FindUserQueryModel>> getUsers(){
        return ResponseEntity.ok(userProjection.handleFindUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<FindUserQueryModel> getUser(@PathVariable Long id){
        return ResponseEntity.ok(userProjection.handleFindUser(id));
    }
}

package com.microservices.user.command.rest;

import com.microservices.user.command.UserAggregate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-management")
public class UserCommandController {

    private final UserAggregate userAggregate;

    public UserCommandController(UserAggregate userAggregate) {
        this.userAggregate = userAggregate;
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id){
        userAggregate.handleDeleteUserCommand(id);
        return ResponseEntity.noContent().build();
    }

}

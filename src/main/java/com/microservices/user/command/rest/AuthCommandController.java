package com.microservices.user.command.rest;

import com.microservices.user.command.UserAggregate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth-management")
public class AuthCommandController {
    private final UserAggregate userAggregate;

    public AuthCommandController(UserAggregate userAggregate) {
        this.userAggregate = userAggregate;
    }

    @PostMapping("/signin")
    public ResponseEntity<LoginResponseRestModel> login(@RequestBody @Valid LoginRequestRestModel loginRequestRestModel){
        return ResponseEntity.ok(userAggregate.handleSignInCommand(loginRequestRestModel));
    }

    @PostMapping("/signup")
    public ResponseEntity createUser(@RequestBody @Valid CreateUserRestModel user){
        userAggregate.handleCreateUserCommand(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}

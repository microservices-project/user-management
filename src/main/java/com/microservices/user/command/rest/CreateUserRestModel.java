package com.microservices.user.command.rest;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRestModel {

    private String firstName;

    private String lastName;

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    private String address;

}

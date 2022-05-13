package com.microservices.user.command.rest;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRestModel {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String address;

}

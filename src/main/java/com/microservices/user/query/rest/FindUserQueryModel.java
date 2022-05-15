package com.microservices.user.query.rest;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindUserQueryModel implements Serializable {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String address;

    @JsonIgnore
    private String password;

}

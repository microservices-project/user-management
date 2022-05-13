package com.microservices.user.query.rest;


import lombok.*;

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

}

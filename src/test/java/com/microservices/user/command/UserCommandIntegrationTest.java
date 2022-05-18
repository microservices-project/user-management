package com.microservices.user.command;

import com.microservices.user.command.rest.CreateUserRestModel;
import com.microservices.user.command.rest.LoginRequestRestModel;
import com.microservices.user.command.rest.LoginResponseRestModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
public class UserCommandIntegrationTest {
    public static final String AUTH_MANAGEMENT_URL = "/auth-management";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @Sql({"classpath:user_data.sql"})
    public void makeSignInShouldReturnJwt() {
        LoginRequestRestModel requestBody = new LoginRequestRestModel("test.user@gmail.com", "password");
        ResponseEntity<LoginResponseRestModel> response = testRestTemplate.postForEntity(AUTH_MANAGEMENT_URL + "/signin", requestBody, LoginResponseRestModel.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(response.getBody());
        assertThat(response.getBody().getJwt()).isNotEmpty();
    }

    @Test
    public void makeSignInShouldReturnBadRequest() {
        LoginRequestRestModel requestBody = new LoginRequestRestModel("test.usergmail.com", "password");
        ResponseEntity<LoginResponseRestModel> response = testRestTemplate.postForEntity(AUTH_MANAGEMENT_URL + "/signin", requestBody, LoginResponseRestModel.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void makeSignUpShouldReturnOK() {
        CreateUserRestModel requestBody = new CreateUserRestModel("first", "last","test2.user@gmail.com", "password","adr");
        ResponseEntity response = testRestTemplate.postForEntity(AUTH_MANAGEMENT_URL + "/signup", requestBody, null);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }


}

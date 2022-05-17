package com.microservices.user.command;

import com.microservices.user.command.rest.LoginRequestRestModel;
import com.microservices.user.command.rest.LoginResponseRestModel;
import com.microservices.user.core.config.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
public class UserCommandIntegrationTest {
    public static final String AUTH_MANAGEMENT_URL = "/auth-management";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @WithMockUser
    public void makeSignInShouldReturnJwt() {
        LoginRequestRestModel requestBody = new LoginRequestRestModel("email-test@email.com", "password");
        ResponseEntity<LoginResponseRestModel> response = testRestTemplate.postForEntity(AUTH_MANAGEMENT_URL + "/signin", requestBody, LoginResponseRestModel.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getJwt()).isEqualTo("jwt");
    }

}

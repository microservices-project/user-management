package com.microservices.user.query;

import com.microservices.user.query.rest.FindUserQueryModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@Sql({"classpath:user_data.sql"})
public class UserQueryIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @WithMockUser
    public void getUsersShouldReturnListOfUsers() {
        ResponseEntity<List<FindUserQueryModel>> response =
                testRestTemplate.exchange("/user-management/users", HttpMethod.GET,null,new ParameterizedTypeReference<List<FindUserQueryModel>>(){});
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}

package com.mitocode.reservation.adapter.in.rest.gymclass;

import com.mitocode.reservation.application.port.in.gymclass.FindGymClassUseCase;
import com.mitocode.reservation.model.gymclass.GymClass;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.mitocode.reservation.adapter.in.rest.HttpTestCommons.assertThatResponseIsError;
import static com.mitocode.reservation.adapter.in.rest.gymclass.GymClassControllerAssertions.assertThatResponseIsGymClassList;
import static com.mitocode.reservation.model.gymclass.TestGymClassFactory.createTestClass;
import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GymClassControllerTest {

    private static final GymClass TEST_GYM_CLASS_1 = createTestClass(30, 20);
    private static final GymClass TEST_GYM_CLASS_2 = createTestClass(25, 10);

    private String token;

    //Para obtener el puerto del servidor de pruebas
    @LocalServerPort
    private Integer TEST_PORT;

    @MockBean
    FindGymClassUseCase findGymClassUseCase;

    @BeforeEach
    void LoadToken() {
        token = given()
                .port(TEST_PORT)
                .param("grant_type", "password")
                .param("realm", "spring-keycloak-realm")
                .param("client_id", "spring-keycloak-client")
                .param("client_secret", "VglXLyJAaAt0iN1isfFw9i8vjlSGQMJa")
                .param("username", "user1")
                .param("password", "user1")
                .headers("Content-Type", "application/x-www-form-urlencoded")
                .when()
                .post("http://ecs-keycloak-24-dev.eu-north-1.elasticbeanstalk.com/realms/spring-keycloak-realm/protocol/openid-connect/token")
                .then()
                .extract()
                .jsonPath()
                .getString("access_token");
    }

    @Test
    void givenAQueryAndAListOfGymClasses_findGymClasses_requestsGymClassesViaQueryAndReturnsThem() {
        String query = "Yoga";
        List<GymClass> gymClassList = List.of(TEST_GYM_CLASS_1, TEST_GYM_CLASS_2);

        when(findGymClassUseCase.findByTypeOrDescription(query)).thenReturn(gymClassList);

        Response response = given()
                .port(TEST_PORT)
                .header(AUTHORIZATION, "Bearer " + token)
                .queryParam("query", query)
                .get("/gym-classes")
                .then()
                .extract()
                .response();

        assertThatResponseIsGymClassList(response, gymClassList);
    }

    @Test
    void givenANullQuery_findGymClasses_returnsError() {
        Response response = given()
                .port(TEST_PORT)
                .header(AUTHORIZATION, "Bearer " + token)
                .get("/gym-classes")
                .then()
                .extract()
                .response();

        assertThatResponseIsError(response, HttpStatus.BAD_REQUEST, "Missing 'query'");
    }

    @Test
    void givenATooShortQuery_findGymClasses_returnsError() {
        String query = "e";
        when(findGymClassUseCase.findByTypeOrDescription(query))
                .thenThrow(IllegalArgumentException.class);

        Response response = given()
                .port(TEST_PORT)
                .header(AUTHORIZATION, "Bearer " + token)
                .queryParam("query", query)
                .get("/gym-classes")
                .then()
                .extract()
                .response();

        assertThatResponseIsError(response, HttpStatus.BAD_REQUEST, "Invalid 'query'");
    }
}
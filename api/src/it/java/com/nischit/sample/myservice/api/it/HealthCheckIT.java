package com.nischit.sample.myservice.api.it;

import com.nischit.sample.myservice.AbstractApiIT;
import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.HttpStatus.OK;

public class HealthCheckIT extends AbstractApiIT {

    @Test
    @DisplayName("When health check succeeds")
    public void healthCheckSucceeds() {

        given()
                .config(RestAssured.config().sslConfig(SSLConfig.sslConfig().sslConfig().relaxedHTTPSValidation()))
                    .baseUri(BASE_HEALTH_SERVER_URL)
                .accept("application/json")
                    .get(HEALTH_CHECK_URL)
                    .prettyPeek()
                .then()
                    .statusCode(OK.value())
                    .body("status", equalTo("UP"));
    }
}

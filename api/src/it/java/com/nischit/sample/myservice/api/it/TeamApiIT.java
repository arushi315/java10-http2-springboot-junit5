package com.nischit.sample.myservice.api.it;

import com.nischit.sample.myservice.AbstractApiIT;
import com.nischit.sample.myservice.util.JsonUtils;
import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.nischit.sample.myservice.api.it.ApiUrls.API_CONTEXT_PATH;
import static com.nischit.sample.myservice.api.it.ApiUrls.TEAMS_API_URI;
import static com.nischit.sample.myservice.api.web.controller.TeamController.TEAM_REQUEST_API_VERSION;
import static com.nischit.sample.myservice.api.web.controller.TeamController.TEAM_RESPONSE_API_VERSION;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;

public class TeamApiIT extends AbstractApiIT {

    @Test
    @DisplayName("When team creation succeeds")
    public void createTeamSucceeds() {
        createTeamSucceeds("teamCreateSuccess-it.json");
        deleteTeamSucceeds("myteamid");
    }

    @Test
    @DisplayName("When team creation fails for bad payload")
    public void createTeamFailsBadPayload() {
        given()
                .config(RestAssured.config().sslConfig(SSLConfig.sslConfig().sslConfig().relaxedHTTPSValidation()))
                .baseUri(BASE_SERVER_URL)
                .contentType(TEAM_REQUEST_API_VERSION)
                    .body(JsonUtils.convertFileToJsonString("teamCreateFailsBadPayload-it.json"))
                .log()
                    .all(true)
                .post(API_CONTEXT_PATH + TEAMS_API_URI)
                    .prettyPeek()
                .then()
                    .statusCode(BAD_REQUEST.value());
    }

    @Test
    @DisplayName("When team creation fails for incorrect content type")
    public void createTeamFailsIncorrectContentType() {
        given()
                .config(RestAssured.config().sslConfig(SSLConfig.sslConfig().sslConfig().relaxedHTTPSValidation()))
                .baseUri(BASE_SERVER_URL)
                .contentType("application/json")
                .body(JsonUtils.convertFileToJsonString("teamCreateFailsBadPayload-it.json"))
                .log()
                    .all(true)
                .post(API_CONTEXT_PATH + TEAMS_API_URI)
                    .prettyPeek()
                .then()
                    .statusCode(UNSUPPORTED_MEDIA_TYPE.value());
    }

    @Test
    @DisplayName("When team retrieval succeeds")
    public void getTeamSucceeds() {
        createTeamSucceeds("teamGetSuccess-it.json");
        given()
                .config(RestAssured.config().sslConfig(SSLConfig.sslConfig().sslConfig().relaxedHTTPSValidation()))
                    .baseUri(BASE_SERVER_URL)
                .accept(TEAM_RESPONSE_API_VERSION)
                    .log()
                    .all(true)
                .get(API_CONTEXT_PATH + TEAMS_API_URI + "/getteamid")
                    .prettyPeek()
                .then()
                    .statusCode(OK.value())
                    .body("teamId", equalTo("getteamid"));
        deleteTeamSucceeds("getteamid");
    }

    @Test
    @DisplayName("When a team is not found")
    public void getTeamFailsAsItIsNotFound() {
        given()
                .config(RestAssured.config().sslConfig(SSLConfig.sslConfig().sslConfig().relaxedHTTPSValidation()))
                .baseUri(BASE_SERVER_URL)
                .accept(TEAM_RESPONSE_API_VERSION)
                .log()
                    .all(true)
                .get(API_CONTEXT_PATH + TEAMS_API_URI + "/notFoundTeam")
                    .prettyPeek()
                .then()
                    .statusCode(NOT_FOUND.value());
    }

    @Test
    @DisplayName("When a team retrieval fails as accept header is invalid")
    public void getTeamFailsAcceptHeaderInvalid() {
        given()
                .config(RestAssured.config().sslConfig(SSLConfig.sslConfig().sslConfig().relaxedHTTPSValidation()))
                .baseUri(BASE_SERVER_URL)
                .accept("application/json")
                .log()
                .all(true)
                .get(API_CONTEXT_PATH + TEAMS_API_URI + "/invalidAcceptHeader")
                .prettyPeek()
                .then()
                .statusCode(NOT_ACCEPTABLE.value());
    }

    public void createTeamSucceeds(final String payloadFileName) {
        given()
                .config(RestAssured.config().sslConfig(SSLConfig.sslConfig().sslConfig().relaxedHTTPSValidation()))
                .baseUri(BASE_SERVER_URL)
                    .contentType(TEAM_REQUEST_API_VERSION)
                    .body(JsonUtils.convertFileToJsonString(payloadFileName))
                    .log()
                        .all(true)
                .post(API_CONTEXT_PATH + TEAMS_API_URI)
                    .prettyPeek()
                .then()
                    .statusCode(NO_CONTENT.value());
    }

    public void deleteTeamSucceeds(final String teamId) {
        given()
                .config(RestAssured.config().sslConfig(SSLConfig.sslConfig().sslConfig().relaxedHTTPSValidation()))
                .baseUri(BASE_SERVER_URL)
                .log()
                    .all(true)
                .delete(API_CONTEXT_PATH + TEAMS_API_URI + "/" + teamId)
                    .prettyPeek()
                .then()
                    .statusCode(NO_CONTENT.value());
    }
}

package com.restBooker.steps

import com.jayway.restassured.http.ContentType
import groovy.json.JsonBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static com.jayway.restassured.RestAssured.given

abstract class BaseStep {
    protected final Logger LOG = LoggerFactory.getLogger(getClass())
    protected final BASE_URL_DEFAULT = "https://restful-booker.herokuapp.com/"
    protected final BASE_URL = System.getProperty("domain") ?: BASE_URL_DEFAULT
    protected final AUTH_ENDPOINT = BASE_URL + "auth/"
    protected final defaultCredential = [username: 'admin', password: 'password123']
    protected String accessToken

    def payloadOf(args){
        new JsonBuilder(args).toPrettyString()
    }

    def getToken(args){
        // when token is empty, get the token
        if (!accessToken){
            def credential = args?.credential ?: defaultCredential

            accessToken =
                    given().log().ifValidationFails()
                    .contentType(ContentType.JSON)
                    .body(payloadOf(credential))
                    .when()
                    .post(AUTH_ENDPOINT)
                    .then().log().ifValidationFails()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .extract().path('token')
        }
        return accessToken
    }
}

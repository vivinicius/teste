package com.diegoaquino.rest.api;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RecursosRest {
    public static Response get(String rota) {
            return given()
                    .when()
                    .get("/" + rota)
                    .then()
                    .extract()
                    .response();
    }


    public static Response getAuth(String rota, String token) {
            return given()
                    .header("Authorization", "Bearer " + token)
                    .when()
                    .get("/" + rota)
                    .then()
                    .extract()
                    .response();
    }


    public static <T> Response post(String rota, T body) {

        return given()
                .body(body)
                .when()
                .post("/" + rota)
                .then()
                .extract()
                .response()
                ;
    }

}

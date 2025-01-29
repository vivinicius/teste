package com.diegoaquino.rest.api;

import io.restassured.http.ContentType;

public interface Constantes {
    String ADD_PRODUCT_ENDPOINT = "products/add";
    String AUTH_PRODUCTS_ENDPOINT = "auth/products";
    String BASE_URL = "https://dummyjson.com";
    ContentType CONTENT_TYPE = ContentType.JSON;

    String LOGIN_ENDPOINT = "auth/login";
    String PASSWORD = System.getenv("API_PASSWORD") != null ? System.getenv("API_PASSWORD") : "emilyspass";
    String PRODUCTS_ENDPOINT = "products";
    String STATUS_ENDPOINT = "test";
    String USER_ENDPOINT = "users";
    String USERNAME = System.getenv("API_USERNAME") != null ? System.getenv("API_USERNAME") : "emilys";
}

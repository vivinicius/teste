package com.diegoaquino.rest.utils;

import com.diegoaquino.rest.api.Constantes;
import com.diegoaquino.rest.models.Produto;
import io.restassured.RestAssured;

import java.util.HashMap;
import java.util.Map;

public class Utils implements Constantes {


    public static String gerarToken() {
        Map<String, String> credenciais = new HashMap<>();
        credenciais.put("username", USERNAME);
        credenciais.put("password", PASSWORD);

        return RestAssured
        .given()
                .contentType(CONTENT_TYPE)
                .body(credenciais)
        .when()
                .post(LOGIN_ENDPOINT)
        .then()
                .statusCode(200)
                .extract().path("accessToken");
    }

    public static Produto criarProduto() {
        return new Produto()
                .setTitle("Perfume Oil")
                .setDescription("Mega Discount, Impression of A...")
                .setPrice(13f)
                .setDiscountPercentage(8.4f)
                .setRating(4.26f)
                .setStock(65)
                .setBrand("Impression of Acqua Di Gio")
                .setCategory("fragrances")
                .setThumbnail("https://i.dummyjson.com/data/products/11/thumnail.jpg");
    }

    public static Map<String, Object> criarProdutoComValoresInvalidos() {
        Map<String, Object> produtoMap = new HashMap<>();

        produtoMap.put("title", "Perfume Oil");
        produtoMap.put("description", "Mega Discount, Impression of A...");
        produtoMap.put("price", "13");
        produtoMap.put("discountPercentage", "8.4");
        produtoMap.put("rating", "4.26");
        produtoMap.put("stock", "65");
        produtoMap.put("brand", "Impression of Acqua Di Gio");
        produtoMap.put("category", "fragrances");
        produtoMap.put("thumbnail", "https://i.dummyjson.com/data/products/11/thumnail.jpg");
        return produtoMap;
    }

    public static Produto criarProdutoComDescontoInvalido() {
        return new Produto()
                .setTitle("Super Discount Product")
                .setDescription("Product with more than 100% discount")
                .setPrice(100f)
                .setDiscountPercentage(150f)
                .setRating(4.5f)
                .setStock(10)
                .setBrand("Discount Brand")
                .setCategory("sale")
                .setThumbnail("https://i.dummyjson.com/data/products/11/thumnail.jpg");
    }

    public static Map<String, String> gerarCredenciais(String username, String password) {
        Map<String, String> credenciais = new HashMap<>();
        if (username != null && !username.isEmpty()) {
            credenciais.put("username", username);
        }
        if (password != null && !password.isEmpty()) {
            credenciais.put("password", password);
        }
        return credenciais;
    }
}

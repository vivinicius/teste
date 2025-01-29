package com.diegoaquino.rest.api.aplicacaoApi;

import com.diegoaquino.rest.api.RecursosRest;
import io.restassured.response.Response;

public class ProdutoApi {
    public static Response get(String rota) {
        return RecursosRest.get(rota);
    }


    public static Response getAuth(String rota, String token) {
        return RecursosRest.getAuth(rota, token);
    }


    public static <T> Response post(String rota, T body) {
        return RecursosRest.post(rota, body);
    }

}

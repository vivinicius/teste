package com.diegoaquino.rest.api;

import com.diegoaquino.rest.utils.Utils;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import org.junit.jupiter.api.BeforeAll;
import io.qameta.allure.restassured.AllureRestAssured;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

public class BaseTest implements Constantes {

    private static final Logger logger = Logger.getLogger(BaseTest.class.getName());
    public static String accessToken;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URL;
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        reqBuilder.setContentType(CONTENT_TYPE);
        reqBuilder.addFilter(new AllureRestAssured());
        RestAssured.requestSpecification = reqBuilder.build();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        accessToken = Utils.gerarToken();

        File allureResultsDir = new File("./allure-results");
        try {
            FileUtils.cleanDirectory(allureResultsDir);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Falha ao limpar o diretório de resultados Allure", e);
        }
    }
}

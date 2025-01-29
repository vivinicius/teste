package com.desafio.api;

import com.desafio.factory.LoginDataFactory;
import com.desafio.factory.ProdutoDataFactory;
import com.desafio.pojo.Login;
import com.desafio.pojo.Produto;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;


public class DesafioTest {

    @Before
    public void SetUp() {
        //Configuracoes Rest-Assured
        baseURI = "https://dummyjson.com";
    }

    @Test
    public void testBuscarStatusDaAplicacao() {

        // Faz a requisição GET para /test
        given()
                .get("/test")
                .then()
                .assertThat()
                .statusCode(200)
                .body("status", equalTo("ok"));
    }

    @Test
    public void testBuscarUsuarioParaAutenticacao() {

        // Faz a requisição GET para /users
        given()
                .get("/users")
                .then()
                .assertThat()
                .statusCode(200)
                .body("total", greaterThan(0)) // Campo "total" deve ser maior que 0
                .body("users", notNullValue()) // O array de usuários não deve ser nulo
                .body("users[0].email", matchesPattern("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) // Verifica se o email tem formato válido
        ;
    }

    @Test
    public void testLoginSucesso() {

        Login login = LoginDataFactory.fazerLogin();


        given()
                .contentType(ContentType.JSON)
                .body(login)
                .when()
                .post("/auth/login")
                .then()
                .assertThat()
                .statusCode(201)
                .body("id", instanceOf(Integer.class)) //Deve ser um número inteiro
                .body("username", equalTo("emilys"));


    }

    @Test
    public void testLoginSemSenha() {

        Login login = LoginDataFactory.fazerLoginSemSenha();

        given()
                .contentType(ContentType.JSON)
                .body(login)
                .when()
                .post("/auth/login")
                .then()
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Username and password required"));

    }

    @Test
    public void testBuscarProdutosComAutenticacao() {

        Login login = LoginDataFactory.fazerLogin();

        String token = given()
                .contentType(ContentType.JSON)
                .body(login)
                .when()
                .post("/auth/login")
                .then()
                .extract()
                .path("accessToken");


        given()
                .header("Authorization", token)
                .when()
                .get("/auth/products")
                .then()
                .assertThat()
                .statusCode(200)
                .body("products", notNullValue()) // Garante que o array de produtos não é nulo
                .body("products.size()", equalTo(30));// Verifica o limite

    }

    @Test
    public void testBuscarProdutosSemAutenticacao() {

        given()
                .when()
                .get("/products")
                .then()
                .assertThat()
                .statusCode(200)
                .body("products", notNullValue()) // Garante que o array de produtos não é nulo
                .body("products.size()", equalTo(30));// Verifica o limite

    }

    @Test
    public void testBuscarProdutosporID() {

        given()
                .when()
                .get("/products/1")
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("id", greaterThan(0))
                .body("price", greaterThanOrEqualTo(0.0f))
                .body("images", everyItem(startsWith("https://")))
                .body("thumbnail", startsWith("https://"));

    }

    @Test
    public void testBuscarProdutosComIDZero() {

        given()
                .when()
                .get("/products/0")
                .then()
                .assertThat()
                .statusCode(404)
                .body("message", equalTo("Product with id '0' not found"));

    }

    @Test
    public void testTokenInvalido() {

        Login login = LoginDataFactory.fazerLogin();


        given()
                .header("Authorization", "tokenInvalido")
                .when()
                .get("/auth/products")
                .then()
                .assertThat()
                .statusCode(401)
                .body("message", equalTo("Invalid/Expired Token!"));
    }

    @Test
    public void testCriacaoProdutoSucesso() {

        Produto produto = ProdutoDataFactory.criarProduto();

        given()
                .contentType(ContentType.JSON)
                .body(produto)
                .when()
                .post("/products/add")
                .then()
                .assertThat()
                .statusCode(201)
                .body("id", notNullValue()); // Verifica se um ID foi gerado

    }

    //Testes de Contrato
    @Test
    public void testCriacaoProdutoSucessoContrato() {

        Produto produto = ProdutoDataFactory.criarProduto();

        given()
                .contentType(ContentType.JSON)
                .body(produto)
                .when()
                .post("/products/add")
                .then()
                .assertThat()
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath("schemas/postProdutoSucesso.json"));

    }

    @Test
    public void testBuscarUsuarioParaAutenticacaoContrato() {
        // Faz a requisição GET para /users
        given()
                .get("/users")
                .then()
                .assertThat()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/getUsuariosParaAutenticacao.json"));


    }

    @Test
    public void testBuscarProdutosComAutenticacaoContrato() {
        Login login = LoginDataFactory.fazerLogin();

        String token = given()
                .contentType(ContentType.JSON)
                .body(login)
                .when()
                .post("/auth/login")
                .then()
                .extract()
                .path("accessToken");


        given()
                .header("Authorization", token)
                .when()
                .get("/auth/products")
                .then()
                .assertThat()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/getProdutosComAutenticacao.json"));

    }
    @Test
    public void testBuscarProdutosIDContrato() {
        given()
                .when()
                .get("/products/1")
                .then()
                .assertThat()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/getProdutoPorID.json"));


    }

}


package com.diegoaquino.rest.tests;

import com.diegoaquino.rest.api.aplicacaoApi.ProdutoApi;
import com.diegoaquino.rest.models.Produto;
import com.diegoaquino.rest.utils.Utils;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.diegoaquino.rest.api.BaseTest;
import java.util.Map;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Epic("Desafio QA Sicredi")
@Feature("Gerenciamento de produtos eletrônicos API")
public class RestApiTest extends BaseTest {

    @Test
    @DisplayName("Deve consultar status")
    public void consultarStatus() {

        Response response = ProdutoApi.get(STATUS_ENDPOINT);
        JsonPath jsonPath = new JsonPath(response.body().asString());

        assertThat(response.statusCode(), equalTo(200));
        assertThat(jsonPath.getString("status"), equalTo("ok"));
        assertThat(jsonPath.getString("method"), equalTo("GET"));

    }

    @Test
    @DisplayName("Deve consultar usuário para autenticação")
    public void validarUsuario() {

        Response response = ProdutoApi.get(USER_ENDPOINT);
        JsonPath jsonPath = new JsonPath(response.body().asString());

        assertThat(response.statusCode(), equalTo(200));
        assertThat(jsonPath.getString("users[0].username"), equalTo(USERNAME));
        assertThat(jsonPath.getString("users[0].password"), equalTo(PASSWORD));

    }

    @Test
    @DisplayName("Deve criar token para autenticação")
    public void criarToken() {
        Map<String, String> credenciais = Utils.gerarCredenciais(USERNAME, PASSWORD);

        Response response = ProdutoApi.post(LOGIN_ENDPOINT, credenciais);
        JsonPath jsonPath = new JsonPath(response.body().asString());

        assertThat(response.statusCode(), equalTo(200));
        assertThat(jsonPath.getString("accessToken"), notNullValue());
        assertThat(jsonPath.getString("username"), equalTo(USERNAME));
        assertThat(jsonPath.getString("email"), equalTo("emily.johnson@x.dummyjson.com"));
    }

    @Test
    @DisplayName("Não deve criar token com username inválido")
    public void criarTokenUsernameInvalido() {
        Map<String, String> credenciais = Utils.gerarCredenciais("emilyss", PASSWORD);

        Response response = ProdutoApi.post(LOGIN_ENDPOINT, credenciais);
        JsonPath jsonPath = new JsonPath(response.body().asString());

        assertThat(response.statusCode(), equalTo(400));
        assertThat(jsonPath.getString("message"), equalTo("Invalid credentials"));
    }

    @Test
    @DisplayName("Não deve criar token com password inválido")
    public void criarTokenPasswordInvalido() {
        Map<String, String> credenciais = Utils.gerarCredenciais(USERNAME, "emilyssps");

        Response response = ProdutoApi.post(LOGIN_ENDPOINT, credenciais);
        JsonPath jsonPath = new JsonPath(response.body().asString());

        assertThat(response.statusCode(), equalTo(400));
        assertThat(jsonPath.getString("message"), equalTo("Invalid credentials"));
    }

    @Test
    @DisplayName("Não deve criar token sem informar um username")
    public void criarTokenSemUsername() {
        Map<String, String> credenciais = Utils.gerarCredenciais(null, PASSWORD);

        Response response = ProdutoApi.post(LOGIN_ENDPOINT, credenciais);
        JsonPath jsonPath = new JsonPath(response.body().asString());

        assertThat(response.statusCode(), equalTo(400));
        assertThat(jsonPath.getString("message"), equalTo("Username and password required"));
    }

    @Test
    @DisplayName("Não deve criar token sem informar um password")
    public void criarTokenSemPassword() {
        Map<String, String> credenciais = Utils.gerarCredenciais(USERNAME, null);

        Response response = ProdutoApi.post(LOGIN_ENDPOINT, credenciais);
        JsonPath jsonPath = new JsonPath(response.body().asString());

        assertThat(response.statusCode(), equalTo(400));
        assertThat(jsonPath.getString("message"), equalTo("Username and password required"));
    }

    @Test
    @DisplayName("Deve consultar produtos autenticado")
    public void consultarProdutosAutenticado() {

        Response response = ProdutoApi.getAuth(AUTH_PRODUCTS_ENDPOINT, accessToken);
        JsonPath jsonPath = new JsonPath(response.body().asString());

        assertThat(response.statusCode(), equalTo(200));
        assertThat(jsonPath.getInt("products[0].id"), equalTo(1));
        assertThat(jsonPath.getString("products[0].title"), equalTo("Essence Mascara Lash Princess"));
        assertThat(jsonPath.getFloat("products[0].price"), equalTo(9.99f));
        assertThat(jsonPath.getString("products[0].category"), equalTo("beauty"));
        assertThat(jsonPath.getFloat("products[0].rating"), equalTo(4.94f));
        assertThat(jsonPath.getString("products[0].availabilityStatus"), equalTo("Low Stock"));

    }

    @Test
    @DisplayName("Não deve consultar produtos sem autenticação")
    public void buscarProdutosTokenInvalido() {
        String tokenInvalido = "abcde12345";

        Response response = ProdutoApi.getAuth(AUTH_PRODUCTS_ENDPOINT, tokenInvalido);
        JsonPath jsonPath = new JsonPath(response.body().asString());

        assertThat(response.statusCode(), equalTo(401));
        assertThat(jsonPath.getString("message"), equalTo("Invalid/Expired Token!"));
    }

    @Test
    @DisplayName("Deve criar produto")
    public void adicionarProduto() {
        Produto novoProduto = Utils.criarProduto();

        Response response = ProdutoApi.post(ADD_PRODUCT_ENDPOINT, novoProduto);
        JsonPath jsonPath = new JsonPath(response.body().asString());

        assertThat(response.statusCode(), equalTo(201));
        assertThat(jsonPath.getString("title"), equalTo("Perfume Oil"));
        assertThat(jsonPath.getString("description"), equalTo("Mega Discount, Impression of A..."));
        assertThat(jsonPath.getFloat("price"), equalTo(13f));
        assertThat(jsonPath.getFloat("discountPercentage"), equalTo(8.4f));
        assertThat(jsonPath.getFloat("rating"), equalTo(4.26f));
        assertThat(jsonPath.getInt("stock"), equalTo(65));
        assertThat(jsonPath.getString("brand"), equalTo("Impression of Acqua Di Gio"));
        assertThat(jsonPath.getString("category"), equalTo("fragrances"));
        assertThat(jsonPath.getString("thumbnail"), equalTo("https://i.dummyjson.com/data/products/11/thumnail.jpg"));

    }

    @Test
    @DisplayName("Os campos de valores devem ser númericos")
    public void adicionarProdutoValoresNumericos() {
        Map<String, Object> produtoInvalidoMap = Utils.criarProdutoComValoresInvalidos();

        Response response = ProdutoApi.post(ADD_PRODUCT_ENDPOINT, produtoInvalidoMap);
        JsonPath jsonPath = new JsonPath(response.body().asString());

        assertThat(response.statusCode(), equalTo(400));
        assertThat(jsonPath.getString("errorMessage[0]"), containsString("O campo price deve ser númerico"));
        assertThat(jsonPath.getString("errorMessage[1]"), containsString("O campo discountPercentage deve ser númerico"));
        assertThat(jsonPath.getString("errorMessage[2]"), containsString("O campo rating deve ser númerico"));
        assertThat(jsonPath.getString("errorMessage[3]"), containsString("O campo stock deve ser númerico"));
    }

    @Test
    @DisplayName("Não deve aceitar descontos maiores que 100%")
    public void naoDeveAceitarDescontoMaiorQue100() {
        Produto produtoInvalido = Utils.criarProdutoComDescontoInvalido();

        Response response = ProdutoApi.post(ADD_PRODUCT_ENDPOINT, produtoInvalido);
        JsonPath jsonPath = new JsonPath(response.body().asString());

        assertThat(response.statusCode(), equalTo(400));
        assertThat(jsonPath.getString("errorMessage"), containsString("discountPercentage deve ser menor ou igual a 100"));
    }


    @Test
    @DisplayName("Deve consultar lista de produtos")
    public void consultarProdutos() {
        Response response = ProdutoApi.get(PRODUCTS_ENDPOINT);
        JsonPath jsonPath = new JsonPath(response.body().asString());

        assertThat(response.statusCode(), equalTo(200));
        assertThat(jsonPath.getInt("products[1].id"), equalTo(2));
        assertThat(jsonPath.getString("products[1].title"), equalTo("Eyeshadow Palette with Mirror"));
        assertThat(jsonPath.getFloat("products[1].price"), equalTo(19.99f));
        assertThat(jsonPath.getString("products[1].category"), equalTo("beauty"));
        assertThat(jsonPath.getFloat("products[1].rating"), equalTo(3.28f));
        assertThat(jsonPath.getString("products[1].availabilityStatus"), equalTo("In Stock"));

    }

    @Test
    @DisplayName("Deve consultar produtos por id")
    public void consultarProdutosPorId() {
        Response response = ProdutoApi.get(PRODUCTS_ENDPOINT + "/3");
        JsonPath jsonPath = new JsonPath(response.body().asString());

        assertThat(response.statusCode(), equalTo(200));
        assertThat(jsonPath.getInt("id"), equalTo(3));
        assertThat(jsonPath.getString("title"), equalTo("Powder Canister"));
        assertThat(jsonPath.getFloat("price"), equalTo(14.99f));
        assertThat(jsonPath.getString("category"), equalTo("beauty"));
        assertThat(jsonPath.getFloat("rating"), equalTo(3.82f));
        assertThat(jsonPath.getString("availabilityStatus"), equalTo("In Stock"));

    }

    @Test
    @DisplayName("Não deve consultar produtos não cadastrados")
    public void consultarProdutosPorIdInvalido() {
        Response response = ProdutoApi.get(PRODUCTS_ENDPOINT + "/1005");
        JsonPath jsonPath = new JsonPath(response.body().asString());

        assertThat(response.statusCode(), equalTo(404));
        assertThat(jsonPath.getString("message"), equalTo("Product with id '1005' not found"));
    }
}

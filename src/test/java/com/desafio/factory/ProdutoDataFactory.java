package com.desafio.factory;

import com.desafio.pojo.Login;
import com.desafio.pojo.Produto;

public class ProdutoDataFactory {

    public static Produto criarProduto(){
        Produto produto = new Produto();

        produto.setTitle("Perfume CH");
        produto.setDescription("perfume bom");
        produto.setPrice("400");
        produto.setDiscountPercentage("8.4");
        produto.setRating("4.26");
        produto.setStock("50");
        produto.setBrand("Floral");
        produto.setCategory("fragrances");
        produto.setThumbnail("https://i.dummyjson.com/data/products/11/thumnail.jpg");
        return produto;
    }

}

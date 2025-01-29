package com.diegoaquino.rest.models;

public class Produto {
    private String title;
    private String description;
    private Float price;
    private Float discountPercentage;
    private Float rating;
    private Integer stock;
    private String brand;
    private String category;
    private String thumbnail;

    public String getTitle() {
        return title;
    }

    public Produto setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Produto setDescription(String description) {
        this.description = description;
        return this;
    }

    public Float getPrice() {
        return price;
    }

    public Produto setPrice(Float price) {
        this.price = price;
        return this;
    }

    public Float getDiscountPercentage() {
        return discountPercentage;
    }

    public Produto setDiscountPercentage(Float discountPercentage) {
        this.discountPercentage = discountPercentage;
        return this;
    }

    public Float getRating() {
        return rating;
    }

    public Produto setRating(Float rating) {
        this.rating = rating;
        return this;
    }

    public Integer getStock() {
        return stock;
    }

    public Produto setStock(Integer stock) {
        this.stock = stock;
        return this;
    }

    public String getBrand() {
        return brand;
    }

    public Produto setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public Produto setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public Produto setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }
}

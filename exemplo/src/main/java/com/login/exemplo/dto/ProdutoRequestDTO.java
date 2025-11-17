package com.login.exemplo.dto;

import com.login.exemplo.entity.Produto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProdutoRequestDTO {

    @NotBlank
    private String name;
    @NotNull
    private double price;
    @NotNull
    private int quantity;

    public ProdutoRequestDTO(){

    }

    public ProdutoRequestDTO(Produto produto) {
        this.name = produto.getName();
        this.price = produto.getPrice();
        this.quantity = produto.getQuantity();
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public int getQuantity() {
        return quantity;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}

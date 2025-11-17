package com.login.exemplo.dto;

import com.login.exemplo.entity.Produto;

public class ProdutoResponseDTO {

    private String name;
    private double price;
    private int quantity;
    private double subTotal;

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double price() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int quantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double subTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public ProdutoResponseDTO(Produto produto) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.subTotal = produto.getQuantity() * produto.getPrice();
    }
}

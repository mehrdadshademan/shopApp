package com.energize.shop.shopApp.exception;

public class ProductNotValidException extends RuntimeException{
    public ProductNotValidException(Long id) {
        super(" not valid product " + id);
    }
}

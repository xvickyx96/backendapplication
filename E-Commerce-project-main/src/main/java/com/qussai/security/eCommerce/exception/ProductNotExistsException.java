package com.qussai.security.eCommerce.exception;

public class ProductNotExistsException extends IllegalArgumentException{

    public ProductNotExistsException(String msg) {
        super(msg);
    }
}

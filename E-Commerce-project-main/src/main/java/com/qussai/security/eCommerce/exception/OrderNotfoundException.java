package com.qussai.security.eCommerce.exception;

public class OrderNotfoundException extends RuntimeException{

	public OrderNotfoundException() {
		
	}
public OrderNotfoundException(String msg) {
		super(msg);
	}
}

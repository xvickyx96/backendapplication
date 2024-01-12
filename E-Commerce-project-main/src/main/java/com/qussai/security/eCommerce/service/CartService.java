package com.qussai.security.eCommerce.service;

import java.util.List;


import com.qussai.security.eCommerce.model.Cart;
import org.springframework.stereotype.Service;


@Service
public interface CartService {
	
	public Cart AddProduct(Cart cart, Integer Productid, Integer customerId,Integer quantity);
	
	public String deleteProductfromCart(Integer id);
	
	public void deleteAllCart();

	public List<Cart> ViewAllCart();
	
}

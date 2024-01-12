package com.qussai.security.eCommerce.service;

import java.util.List;
import java.util.Optional;

import com.qussai.security.eCommerce.exception.CustomerNotFoundException;
import com.qussai.security.eCommerce.exception.ProductNotFoundException;
import com.qussai.security.eCommerce.model.Cart;
import com.qussai.security.eCommerce.model.CartItem;
import com.qussai.security.eCommerce.model.Products;
import com.qussai.security.eCommerce.repository.*;
import com.qussai.security.webSecurity.user.User;
import com.qussai.security.webSecurity.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartServiceImpl implements CartService{
	
	@Autowired
	private ProductsDao pDao;
	
	@Autowired
	private UserRepository userDao;
	
	@Autowired
	private CartDao cartDao;
	
//	@Autowired
//	private AddressDao addressDao;

	private ProductsService productsService;

//	@Autowired
//	private CurrentUserSessionDao currentUserSessionDao;


	//Method to view the cart

	@Override
	public List<Cart> ViewAllCart() {
		// TODO Auto-generated method stub
		List<Cart> list =cartDao.findAll();

		return cartDao.findAll();
	}
	
	
	//Method to update the Product from cart
//	@Override
//	public Cart UpdateCartProduct(Cart cart) throws ProductNotFoundException {
//		// TODO Auto-generated method stub
//		
//		Optional<Cart> opt = cartDao.findById(cart.getCartItemId());
//
//		if (opt.isPresent()) {
//			opt.get();
//			Cart crt = cartDao.save(cart);
//			return crt;
//		} else
//			throw new ProductNotFoundException("Product not found with given id");
//		
//	}
//	




	//Method to add the Product and customer in cart
	@Override
	public Cart AddProduct(Cart cart, Integer Productid, Integer customerId, Integer quantity) {
		Optional<Products> opt = pDao.findById(Productid);
		Optional<User> customer = userDao.findById(customerId);

		if (customer.isEmpty()) {
			throw new CustomerNotFoundException("Customer not found");
		}

		Optional<User> user = userDao.findByEmail(customer.get().getEmail());
		if (user == null) {
			throw new CustomerNotFoundException("Customer not logged in");
		}

		if (opt.isPresent()) {
			Products prod = opt.get();
			User cust = customer.get();

//			// Skapa en ny CartItem med kvantitet och koppla den till produkten
//			CartItem cartItem = new CartItem();
//			cartItem.setProductId(Long.valueOf(prod.getProductId()));
//			cartItem.setProductName(prod.getProductName());
//			cartItem.setPrice(prod.getPrice());
//			cartItem.setColor(prod.getColor());
//			cartItem.setQuantity(quantity);

			// Lägg till CartItem och kund i kundvagnen
			cart.setCartItem(prod);
			cart.setCustomerlist(cust);

			return cartDao.save(cart);
		} else {
			throw new ProductNotFoundException("Product not available");
		}
	}



	//Method to delete the product from cart
	@Override
	public String deleteProductfromCart(Integer id)throws ProductNotFoundException {
		Optional<Cart> opt = cartDao.findById(id);
		
		if (opt.isPresent()) {
			Cart cart = opt.get();
//			System.out.println(prod);
			cartDao.delete(cart);
			return "CartProduct is deleted from Cart";
			
		} else
			throw new ProductNotFoundException("Product not found with given id");
		
		
	}

	private Cart cart;
	@Override
	@Transactional
	public void deleteAllCart() {
		// TODO Auto-generated method stub
		cartDao.DeleteAll();
//		return "Cart is empty";
	}




}

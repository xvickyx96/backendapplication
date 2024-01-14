package com.qussai.security.eCommerce.service;


import com.qussai.security.eCommerce.model.Products;
import com.qussai.security.eCommerce.model.ProductsDTO;

import java.util.List;


public interface ProductsService {

	public Products addProducts(Products products);
	
	public List<Products> getAllProducts();

	
	public Products getProductsFromCatalogById(Integer id);

	
	public String deleteProductFromCatalog(Integer id);
	
	public Products updateProductIncatalog(Products product);

	Products findById(Integer productId);
}
	
	
	
	
	
	
	
	
	
	
	
	
package com.qussai.security.eCommerce.service;

import java.util.List;
import java.util.Optional;

import com.qussai.security.eCommerce.exception.ProductNotExistsException;
import com.qussai.security.eCommerce.exception.ProductNotFoundException;
import com.qussai.security.eCommerce.model.Products;
import com.qussai.security.eCommerce.model.ProductsDTO;
import com.qussai.security.eCommerce.repository.ProductsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductsServiceImpl implements ProductsService{
	
	@Autowired
	private ProductsDao psDao;
	
	@Override
	public Products addProducts(Products products) {
		// Check if there is another clothing item with the same name
		if (psDao.findByProductName(products.getProductName()) == null) {
			throw new ProductNotFoundException("Another record with the same name exists");
		}
		// Save the new clothing item
		return psDao.save(products);
	}

	public Products findById(Integer productId)  {
		Optional<Products> optionalProduct = psDao.findById(productId);
		if (optionalProduct.isEmpty()) {
			throw new ProductNotExistsException("product id is invalid: " + productId);
		}
		return optionalProduct.get();
	}

	@Override
	public List<Products> getAllProducts() {
		// TODO Auto-generated method stub
		List<Products> list = psDao.findAll();
		
		if (list.size() > 0) {
			return list;
		} else
			throw new ProductNotFoundException("No products in catalog");
	}

	@Override
	public Products getProductsFromCatalogById(Integer id) {
		Optional<Products> opt = psDao.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		}

		else
			throw new ProductNotFoundException("Product not found with given id");
	}


	@Override
	public String deleteProductFromCatalog(Integer id) throws ProductNotFoundException {
		
		Optional<Products> opt=	psDao.findById(id);
			
		if(opt.isPresent()) {
				Products prod = opt.get();
				psDao.delete(prod);
				return "Product deleted from catalog";
			} else
				throw new ProductNotFoundException("Product not found with given id");
	}

	@Override
	public Products updateProductIncatalog(Products product) throws ProductNotFoundException {

		Optional<Products> opt = psDao.findById(product.getProductId());

		if (opt.isPresent()) {
			opt.get();
			Products prod1 = psDao.save(product);
			return prod1;
		} else
			throw new ProductNotFoundException("Product not found with given id");
	}

	
}
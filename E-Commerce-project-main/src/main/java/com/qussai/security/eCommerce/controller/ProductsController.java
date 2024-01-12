package com.qussai.security.eCommerce.controller;

import java.util.List;


import com.qussai.security.eCommerce.model.Products;
import com.qussai.security.eCommerce.service.ProductsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ProductsController {
	@Autowired
	private ProductsService ppService;
	
	
	@PostMapping("/addnewproducts")
	public Products saveProductsHandler(@Valid @RequestBody Products product) {
		// Save the new clothing item using the ClothingItemService
		Products result = ppService.addProducts(product);
		return new ResponseEntity<>(result, HttpStatus.CREATED).getBody();
	}
	
	/////////////////////////////////////////////////////////////////
	
	@GetMapping("/allproducts")
	public List<Products> getAllProducts(){
		// Retrieve all clothing items from the ClothingItemService
		List<Products> result = ppService.getAllProducts();
		return new ResponseEntity<>(result, HttpStatus.OK).getBody();
	}
	/////////////////////////////////////////////////////////////////
	
	@GetMapping("/product/{id}")
	public ResponseEntity<Products> getProductFromCatalogByIdHandler(@PathVariable("id") Integer id) {
		// Retrieve a clothing item by its ID from the ClothingItemService
		Products result = ppService.getProductsFromCatalogById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);

	}
	
	//////////////////////////////////////////////////////////////////
	@DeleteMapping("/deleteproduct/{id}")
	public ResponseEntity<String> deleteProductFromCatalogHandler(@PathVariable("id") Integer id) {
		
		String res = ppService.deleteProductFromCatalog(id);
		return new ResponseEntity<String>(res, HttpStatus.OK);
	}
	
	///////////////////////////////////////////////////////////////////
	@PutMapping("/updateproducts")
	public ResponseEntity<Products> updateProductInCatalogHandler(@Valid @RequestBody Products prod) {

		Products prod1 = ppService.updateProductIncatalog(prod);

		return new ResponseEntity<Products>(prod1, HttpStatus.OK);
	}
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	@GetMapping("/products/{id}")
//	public Products getProductsById(@PathVariable("id") Integer id) {
//		
//		return ppService.getProductFromCatalogById(id);
//	}
//	
//	/////////////////////////////////////////////////////////////////////
//	
//	@GetMapping("/test/{id}")
//	public Products getproductsById(@PathVariable("id") Integer id) {
//		
//		return ppService.getproductsById(id);
//	}
//	
//	///////////////////////////////////////////////////////
//	
//	@GetMapping("/category/{cat}")
//	public List<ProductsDTO> getfunctions(@PathVariable("cat") CategoryEnum cat) {
//		
//		return ppService.funCategory(cat);
//	}
//	
//	/////////////////////////////////////////////////////////////
//	@PostMapping("/addcategorywise/{cat}")
//	public Products addProductsByCategory(@RequestBody @PathVariable("cat") CategoryEnum cat, Products products) {
//		
//		return ppService.addProductsByCategory(cat, products);
//		
//	}
	

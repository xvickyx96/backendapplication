package com.qussai.security.eCommerce.repository;

import java.util.List;

import com.qussai.security.eCommerce.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface ProductsDao extends JpaRepository<Products, Integer> {
	
	public List<Products>  findByProductName(String productName);
	
//	@Query("select new com.project.model.ProductsDTO(p.productName, p.price) from Products p where p.productId=?1")
//	public Products getproductsById(int id);
	
//	@Query("select new com.project.model.ProductsDTO(p.productName, p.price) from Products p where p.category=?1")
//	public List<ProductsDTO> getCategoryWiseProducts(CategoryEnum cat);

}

package com.docker.junkstarter.service;

import java.util.List;

import com.docker.junkstarter.model.Product;

public interface ProductService {
	
	Product findByName(String name);
		
	List<Product> findAllProducts();

	Product findById(Long productId);
	

}

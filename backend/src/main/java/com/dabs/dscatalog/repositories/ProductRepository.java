package com.dabs.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dabs.dscatalog.entities.Product;

@Repository//registrar o componente como injetável pelo Spring
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	

}

package com.dabs.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dabs.dscatalog.entities.Category;

@Repository//registrar o componente como injetável pelo Spring
public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	

}

package com.dabs.dscatalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dabs.dscatalog.entities.Category;
import com.dabs.dscatalog.repositories.CategoryRepository;

@Service//vai resgistrar essa classe como um componente que vai participar do sistema de in jeção de dependência automatizado do spring, mecanismo de injeção de dependência automatizado
public class CategoryService {

	@Autowired//instância gerenciada pelo Spring
	private CategoryRepository repository;
	
	public List<Category> findAll(){
		return repository.findAll();
		
	}
}
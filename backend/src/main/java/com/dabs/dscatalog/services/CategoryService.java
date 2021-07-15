package com.dabs.dscatalog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dabs.dscatalog.dto.CategoryDTO;
import com.dabs.dscatalog.entities.Category;
import com.dabs.dscatalog.repositories.CategoryRepository;

@Service//vai resgistrar essa classe como um componente que vai participar do sistema de in jeção de dependência automatizado do spring, mecanismo de injeção de dependência automatizado
public class CategoryService {

	@Autowired//instância gerenciada pelo Spring
	private CategoryRepository repository;
	
	//garantir a integridade da transação
	@Transactional(readOnly = true)//para operações de somente leitura
	public List<CategoryDTO> findAll(){
		List<Category> list =  repository.findAll();
		return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());//pegando cada elemento da lista original, aplicando essa função lambda, transformando a lista que era Category em uma lista CategoryDto. O resultado será um stream que tem que ser transformado, com a função collect, de volta em lista
			
	}
	
}

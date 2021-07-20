package com.dabs.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dabs.dscatalog.dto.CategoryDTO;
import com.dabs.dscatalog.entities.Category;
import com.dabs.dscatalog.repositories.CategoryRepository;
import com.dabs.dscatalog.services.exceptions.EntityNotFoundException;

@Service//vai resgistrar essa classe como um componente que vai participar do sistema de in jeção de dependência automatizado do spring, mecanismo de injeção de dependência automatizado
public class CategoryService {

	@Autowired//instância gerenciada pelo Spring
	private CategoryRepository repository;//objeto responsável por acessar o banco de dados
	
	//garantir a integridade da transação
	@Transactional(readOnly = true)//para operações de somente leitura
	public List<CategoryDTO> findAll(){
		List<Category> list =  repository.findAll();
		return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());//pegando cada elemento da lista original, aplicando essa função lambda, transformando a lista que era Category em uma lista CategoryDto. O resultado será um stream que tem que ser transformado, com a função collect, de volta em lista
			
	}
	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> obj = repository.findById(id);//o optional é uma abordagem desde o java 8 para evitar trabalhar com o valor nulo. O spring data jpa implementou esse método com o retorno de um Optional
		Category entity = obj.orElseThrow(()-> new EntityNotFoundException("Entity not found"));//obtém o objeto que estava dentro do optional ou lança uma exceção
		return new CategoryDTO(entity);
	}
	
}

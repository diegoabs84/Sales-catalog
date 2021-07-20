package com.dabs.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dabs.dscatalog.dto.CategoryDTO;
import com.dabs.dscatalog.entities.Category;
import com.dabs.dscatalog.repositories.CategoryRepository;
import com.dabs.dscatalog.services.exceptions.DatabaseException;
import com.dabs.dscatalog.services.exceptions.ResourceNotFoundException;

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
		Category entity = obj.orElseThrow(()-> new ResourceNotFoundException("Entity not found"));//obtém o objeto que estava dentro do optional ou lança uma exceção
		return new CategoryDTO(entity);
	}
	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();//converter o DTO para entity
		entity.setName(dto.getName());
		entity = repository.save(entity);//referência atualizada
		return new CategoryDTO(entity);//retornando novamente para um DTO
	}
	@Transactional
	public CategoryDTO update(Long id,CategoryDTO dto) {
		try {//se chamar o getOne pra um id que n existe, entra no catch
		Category entity = repository.getOne(id);//primeiro instancia uma category, este getOne não toca o banco de dados, ele vai instanciar um objeto provisório, e depois que manda salvar aí sim vai no banco de dados
		entity.setName(dto.getName());
		entity = repository.save(entity);//agora sim salva no banco de dados
		return new CategoryDTO(entity);
		} catch( EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found" + id);
			
		}
		
	}
	public void delete(Long id) {
		try {
			repository.deleteById(id);
			
		} catch( EmptyResultDataAccessException e) {//caso não tenha o id a ser deletado
			throw new ResourceNotFoundException("Id not found" + id);
		} catch( DataIntegrityViolationException e) {//caso dê um problema de integridade referencial no banco
			throw new DatabaseException("Integrity Violation");
			
		}
		
	}
	
	
	
	
}

package com.dabs.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dabs.dscatalog.dto.CategoryDTO;
import com.dabs.dscatalog.dto.ProductDTO;
import com.dabs.dscatalog.entities.Category;
import com.dabs.dscatalog.entities.Product;
import com.dabs.dscatalog.repositories.CategoryRepository;
import com.dabs.dscatalog.repositories.ProductRepository;
import com.dabs.dscatalog.services.exceptions.DatabaseException;
import com.dabs.dscatalog.services.exceptions.ResourceNotFoundException;

@Service//vai resgistrar essa classe como um componente que vai participar do sistema de in jeção de dependência automatizado do spring, mecanismo de injeção de dependência automatizado
public class ProductService {

	@Autowired//instância gerenciada pelo Spring
	private ProductRepository repository;//objeto responsável por acessar o banco de dados
	
	@Autowired//instância gerenciada pelo Spring
	private CategoryRepository categoryRepository;//objeto responsável por acessar o banco de dados
	
	//garantir a integridade da transação
	@Transactional(readOnly = true)//para operações de somente leitura
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest){
		Page<Product> list =  repository.findAll(pageRequest);
		return list.map(x -> new ProductDTO(x));//pegando cada elemento da lista original, aplicando essa função lambda, transformando a lista que era Product em uma lista ProductDto.
			
	}
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> obj = repository.findById(id);//o optional é uma abordagem desde o java 8 para evitar trabalhar com o valor nulo. O spring data jpa implementou esse método com o retorno de um Optional
		Product entity = obj.orElseThrow(()-> new ResourceNotFoundException("Entity not found"));//obtém o objeto que estava dentro do optional ou lança uma exceção
		return new ProductDTO(entity, entity.getCategories());
	}
	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();//converter o DTO para entity
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);//referência atualizada
		return new ProductDTO(entity);//retornando novamente para um DTO
	}
	
	@Transactional
	public ProductDTO update(Long id,ProductDTO dto) {
		try {//se chamar o getOne pra um id que n existe, entra no catch
		Product entity = repository.getOne(id);//primeiro instancia uma category, este getOne não toca o banco de dados, ele vai instanciar um objeto provisório, e depois que manda salvar aí sim vai no banco de dados
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);//agora sim salva no banco de dados
		return new ProductDTO(entity);
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
	private void copyDtoToEntity(ProductDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setDate(dto.getDate());
		entity.setImgUrl(dto.getImgUrl());
		entity.setPrice(dto.getPrice());
		
		entity.getCategories().clear();//limpando qualquer categoria que porventura houvesse na entity
		for (CategoryDTO catDto : dto.getCategories()) {//percorrendo a lista de categorias do dto
			Category category = categoryRepository.getOne(catDto.getId());//pega o id da categoria dentro da lista de categorias do dto para instanciar um objeto category sem tocar no banco de dados ainda
			entity.getCategories().add(category);//acessa a lista de categorias da entidade e adiciona a categoria instanciada
			
		}
		
		
	}
	
	
	
}

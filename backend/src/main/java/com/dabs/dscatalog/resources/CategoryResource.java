package com.dabs.dscatalog.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dabs.dscatalog.dto.CategoryDTO;
import com.dabs.dscatalog.services.CategoryService;


@RestController//implementa o controlador REST que responde as requisições
@RequestMapping(value = "/categories")// rota REST do recurso, dentro dela haverá vários endpoints
public class CategoryResource {
	
	@Autowired
	private CategoryService service;//criando uma dependência com o service

	@GetMapping
	public ResponseEntity<List<CategoryDTO>> findAll(){
		List<CategoryDTO> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	
}

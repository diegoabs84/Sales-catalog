package com.dabs.dscatalog.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dabs.dscatalog.dto.CategoryDTO;
import com.dabs.dscatalog.services.CategoryService;


@RestController//implementa o controlador REST que responde as requisições
@RequestMapping(value = "/categories")// rota REST do recurso, dentro dela haverá vários endpoints
public class CategoryResource {
	
	@Autowired
	private CategoryService service;//criando uma dependência com o service

	@GetMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> findById(@PathVariable Long id){
		CategoryDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping
	public ResponseEntity<CategoryDTO> insert(@RequestBody CategoryDTO dto){//para que reconheça o objeto enviado e "case" com o categoryDTO, usa o requestbody
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
		
	}
	
	
	
}

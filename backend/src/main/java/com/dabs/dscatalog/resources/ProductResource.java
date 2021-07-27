package com.dabs.dscatalog.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dabs.dscatalog.dto.ProductDTO;
import com.dabs.dscatalog.services.ProductService;


@RestController//implementa o controlador REST que responde as requisições
@RequestMapping(value = "/products")// rota REST do recurso, dentro dela haverá vários endpoints
public class ProductResource {
	
	@Autowired
	private ProductService service;//criando uma dependência com o service
	
	@GetMapping
	public ResponseEntity<Page<ProductDTO>> findAll(Pageable pageable){
		Page<ProductDTO> list = service.findAllPaged(pageable);
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> findById(@PathVariable Long id){
		ProductDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping
	public ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO dto){//para que reconheça o objeto enviado e "case" com o categoryDTO, usa o requestbody
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
		
	}
	
	@PutMapping(value = "/{id}")//atualizar
	public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductDTO dto){//para que reconheça o objeto enviado e "case" com o categoryDTO, usa o requestbody
		dto = service.update(id,dto);
		return ResponseEntity.ok().body(dto);
	}
	
	@DeleteMapping(value = "/{id}")//atualizar
	public ResponseEntity<Void> update(@PathVariable Long id ){//void pois o corpo da resposta será vazio
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	
}

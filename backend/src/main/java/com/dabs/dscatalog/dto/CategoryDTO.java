package com.dabs.dscatalog.dto;

import java.io.Serializable;

import com.dabs.dscatalog.entities.Category;

public class CategoryDTO implements Serializable {//padrão Java para que o objeto possa ser convertido em bytes, para que possa ser gravado em arquivos, passar nas redes...é utilizado hoje por ser uma boa medida
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	
	public CategoryDTO() {
		
	}

	public CategoryDTO(Long id, String name) {
		
		this.id = id;
		this.name = name;
	}
	
	public CategoryDTO (Category entity) {//construtor que já irá povoar o DTO passando a entidade como argumento
		this.id = entity.getId();
		this.name = entity.getName();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	

}

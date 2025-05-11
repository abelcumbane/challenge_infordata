package com.desafio.demo.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.desafio.demo.entitys.Categorias;

public interface CategoriaRepository extends JpaRepository<Categorias,Long> {
	
	//JPQL to find all the Categorias that includes the substring the user defined.
			@Query("select c from Categorias c where lower(c.nome)  like lower(concat('%', :substring, '%'))")
			List<Categorias> findCategorias(String substring);


}

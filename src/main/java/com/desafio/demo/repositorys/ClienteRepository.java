package com.desafio.demo.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.desafio.demo.entitys.Clientes;


public interface ClienteRepository extends JpaRepository<Clientes,Long> {
	
	//JPQL to find all the Clientes that includes the substring the user defined.
			@Query("select c from Clientes c where lower(c.nome)  like lower(concat('%', :substring, '%'))")
			List<Clientes> findClientes(String substring);


}

package com.desafio.demo.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.desafio.demo.entitys.Facturacao;

public interface FacturacaoRepository extends JpaRepository<Facturacao,Long> {
	//JPQL to find all the Clientes that includes the substring the user defined.
	@Query("select f from Facturacao f where lower(f.numero)  like lower(concat('%', :substring, '%'))")
	List<Facturacao> findFacturas(String substring);


}

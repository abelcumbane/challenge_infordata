package com.desafio.demo.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.desafio.demo.entitys.Produtos;


public interface ProdutoRepository extends JpaRepository<Produtos,Long> {
	//JPQL to find all the Produtos that includes the substring the user defined.
		@Query("select p from Produtos p where lower(p.nome)  like lower(concat('%', :substring, '%'))")
		List<Produtos> findProdutos(String substring);

}

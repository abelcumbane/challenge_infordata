package com.desafio.demo.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desafio.demo.entitys.FaturaProduto;

public interface FaturaProdutoRepository extends JpaRepository<FaturaProduto,Long>{

}

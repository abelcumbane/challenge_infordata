package com.desafio.demo.services;

import java.util.List;

import com.desafio.demo.entitys.FaturaProduto;

public interface FaturaProdutoService {
	
	public void save(FaturaProduto faturaproduto);
	public void update(FaturaProduto faturaproduto);
	public void remove(FaturaProduto faturaproduto);
	public List<FaturaProduto> findAll();
	public List<FaturaProduto> find(String substring);
	public FaturaProduto findById(Integer faturaprodutoId);
	public long countFaturaProduto();
	public FaturaProduto create(FaturaProduto faturaproduto);
	public void delete(Integer id);
	List<FaturaProduto> findByFacturacaoId(Long facturacaoId);

}

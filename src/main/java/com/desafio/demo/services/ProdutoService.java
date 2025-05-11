	package com.desafio.demo.services;
	
	import java.util.List;
	
	import com.desafio.demo.entitys.Produtos;
	
	
	public interface ProdutoService {
	
		public void save(Produtos produto);
		public void update(Produtos produto);
		public void remove(Produtos produto);
		public List<Produtos> findAll();
		public List<Produtos> find(String substring);
		public Produtos findById(Integer produtoId);
		public long countProdutoss();
		public Produtos create(Produtos produto);
		public void delete(Integer id);
	}

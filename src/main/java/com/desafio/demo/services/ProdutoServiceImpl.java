	package com.desafio.demo.services;
	
	import java.util.List;
	
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Service;
	
	import com.desafio.demo.entitys.Produtos;
	import com.desafio.demo.repositorys.ProdutoRepository;
	
	@Service
	public class ProdutoServiceImpl implements ProdutoService {
		
		@Autowired
		private ProdutoRepository produtoRepository;
	
		@Override
		public void save(Produtos produto) {
			produtoRepository.save(produto);
			
		}
	
		@Override
		public void update(Produtos produto) {
			if(produtoRepository.existsById(produto.getId())) {
				produtoRepository.save(produto);
			}else {
				throw new RuntimeException("Produto nao encontrado");
			}
					
		}
	
		@Override
		public void remove(Produtos produto) {
			// TODO Ainda por implementar
			
		}
	
		@Override
		public List<Produtos> findAll() {
			return produtoRepository.findAll();
		}
	
		@Override
		public List<Produtos> find(String substring) {
			return produtoRepository.findProdutos(substring);
		}
	
		@Override
		public Produtos findById(Integer produtoId) {
			// TODO Ainda por implementar
			return null;
		}
	
		@Override
		public long countProdutoss() {
			// TODO Ainda por implementar
			return 0;
		}
	
		@Override
		public Produtos create(Produtos produto) {
			// TODO Ainda por implementar
			return null;
		}
	
		@Override
		public void delete(Integer id) {
			// TODO Ainda por implementar
			
		}
	
	}

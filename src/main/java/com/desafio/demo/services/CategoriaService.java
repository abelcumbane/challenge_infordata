	package com.desafio.demo.services;
	
	import java.util.List;
	
	import com.desafio.demo.entitys.Categorias;
	
	public interface CategoriaService {
		public void save(Categorias categoria);
		public void update(Categorias categoria);
		public void remove(Categorias categoria);
		public List<Categorias> findAll();
		public List<Categorias> find(String substring);
		public Categorias findById(Integer categoriaId);
		public long countCategoriass();
		public Categorias create(Categorias categoria);
		public void delete(Integer id);
	
	}

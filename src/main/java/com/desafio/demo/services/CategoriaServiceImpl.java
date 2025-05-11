	package com.desafio.demo.services;
	
	import java.util.List;
	
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Service;
	
	import com.desafio.demo.entitys.Categorias;
	import com.desafio.demo.repositorys.CategoriaRepository;
	
	@Service
	public class CategoriaServiceImpl implements CategoriaService {
	
		@Autowired
		private CategoriaRepository categoriaRepository;
		
		@Override
		public void save(Categorias categoria) {
			categoriaRepository.save(categoria);
			
		}
	
		@Override
		public void update(Categorias categoria) {
			if(categoriaRepository.existsById(categoria.getId())) {
				categoriaRepository.save(categoria);
			}else {
				throw new RuntimeException("Categoria nao encontrada");
			}
			
		}
	
		@Override
		public void remove(Categorias categoria) {
			// TODO Ainda por implementar
			
		}
	
		@Override
		public List<Categorias> findAll() {
			return categoriaRepository.findAll();
		}
	
		@Override
		public List<Categorias> find(String substring) {
			return categoriaRepository.findCategorias(substring);
		}
	
		@Override
		public Categorias findById(Integer categoriaId) {
			// TODO Ainda por implementar
			return null;
		}
	
		@Override
		public long countCategoriass() {
			// TODO Ainda por implementar
			return 0;
		}
	
		@Override
		public Categorias create(Categorias categoria) {
			// TODO Ainda por implementar
			return null;
		}
	
		@Override
		public void delete(Integer id) {
			// TODO Ainda por implementar
			
		}
	
	}

	package com.desafio.demo.entitys;
	
	import jakarta.persistence.Column;
	import jakarta.persistence.Entity;
	import jakarta.persistence.GeneratedValue;
	import jakarta.persistence.GenerationType;
	import jakarta.persistence.Id;
	
	@Entity
	public class Produtos {
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	
		@Column
	    private String codigo;
		
		@Column
	    private String descricao;
		
		@Column
		private Double preco;
		
		@Column
		private Integer quantidade;
		
	    
		
	    
	    public Produtos() {
			
		}
	
	
		public Long getId() {
			return id;
		}
	
	
		public void setId(Long id) {
			this.id = id;
		}
	
	
		public String getDescricao() {
			return descricao;
		}
	
	
		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}
	
	
		public Double  getPreco() {
			return preco;
		}


		public void setPreco(Double preco) {
			this.preco = preco;
		}


		public Integer getQuantidade() {
			return quantidade;
		}


		public void setQuantidade(Integer quantidade) {
			this.quantidade = quantidade;
		}


		public String getCodigo() {
			return codigo;
		}
	
	
		public void setCodigo(String codigo) {
			this.codigo = codigo;
		}
	        
	}

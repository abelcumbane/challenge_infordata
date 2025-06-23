package com.desafio.demo.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class FaturaProduto {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "produto_id")
	private Produtos produto;
	
	@Column
	private int quantidade;
	
	@Column
	private Double precoUnitario;
	
	@Column 
	private Double subtotal;
	
	@ManyToOne
	@JoinColumn(name = "facturacao_id")
	private Facturacao fatura; 


	public FaturaProduto() {
		// TODO Auto-generated constructor stub
	}

	
	

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Produtos getProduto() {
		return produto;
	}


	public void setProduto(Produtos produto) {
		this.produto = produto;
	}


	public int getQuantidade() {
		return quantidade;
	}


	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}


	public Double getPrecoUnitario() {
		return precoUnitario;
	}


	public void setPrecoUnitario(Double precoUnitario) {
		this.precoUnitario = precoUnitario;
	}


	public Double getSubtotal() {
		return subtotal;
	}


	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}


	public Facturacao getFatura() {
		return fatura;
	}


	public void setFatura(Facturacao fatura) {
		this.fatura = fatura;
	}
	
	

}

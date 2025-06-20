package com.desafio.demo.entitys;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Recibo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Clientes cliente;
	
	@Column
	private LocalDate dataPagamento;
	
	@Column
	private BigDecimal valorPago;
	
	@ManyToMany
	@JoinTable(name = "recibo_facturacao", joinColumns = @JoinColumn(name = "recibo_id"),
				inverseJoinColumns = @JoinColumn(name = "facturacao_id"))
	private List<Facturacao> faturasPagas;
	
	
	private String formaPagamento;

	
	public Recibo() {
		// TODO Auto-generated constructor stub
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Clientes getCliente() {
		return cliente;
	}


	public void setCliente(Clientes cliente) {
		this.cliente = cliente;
	}


	public LocalDate getDataPagamento() {
		return dataPagamento;
	}


	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}


	public BigDecimal getValorPago() {
		return valorPago;
	}


	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}


	public List<Facturacao> getFaturasPagas() {
		return faturasPagas;
	}


	public void setFaturasPagas(List<Facturacao> faturasPagas) {
		this.faturasPagas = faturasPagas;
	}


	public String getFormaPagamento() {
		return formaPagamento;
	}


	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	
	

}

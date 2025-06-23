package com.desafio.demo.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Clientes {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String nome;
	
	@Column
	private String nuit;
	
	@Column
	private String telfone;
	
	@Column
	private String email;
	
	@Column
	private String endereco;
	
	@Column
	private String estado;
	
	
	public Clientes() {
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id; 
	} 

	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getNuit() {
		return nuit;
	}


	public void setNuit(String nuit) {
		this.nuit = nuit;
	}


	public String getTelfone() {
		return telfone;
	}


	public void setTelfone(String telfone) {
		this.telfone = telfone;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getEndereco() {
		return endereco;
	}


	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	

}

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
	private int id;
	
	@Column
	private String nome;
	
	@Column
	private Integer nuit;
	
	@Column
	private String telfone;
	
	@Column
	private String email;
	
	@Column
	private String endereco;
	
	@Column
	private boolean estado;
	
	
	public Clientes() {
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public Integer getNuit() {
		return nuit;
	}


	public void setNuit(Integer nuit) {
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


	public boolean isEstado() {
		return estado;
	}


	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	
	

}

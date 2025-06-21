package com.desafio.demo.services;

import java.util.List;

import com.desafio.demo.entitys.Clientes;

public interface ClienteService {
	
	public void save(Clientes cliente);
	public void update(Clientes cliente);
	public void remove(Clientes cliente);
	public List<Clientes> findAll();
	public List<Clientes> find(String substring);
	public Clientes findById(Integer clienteId);
	public long countClientes();
	public Clientes create(Clientes cliente);
	public void delete(Integer id);

}

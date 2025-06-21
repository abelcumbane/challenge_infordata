package com.desafio.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desafio.demo.entitys.Clientes;
import com.desafio.demo.repositorys.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	@Override
	
	public void save(Clientes cliente) {
		clienteRepository.save(cliente);	
	}

	@Override
	public void update(Clientes cliente) {
		if(clienteRepository.existsById(cliente.getId())) {
			clienteRepository.save(cliente);
		}else {
			throw new RuntimeException("Cliente nao encontrado");
		}
		
	}

	@Override
	public void remove(Clientes cliente) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Clientes> findAll() {
		return clienteRepository.findAll();
	}

	@Override
	public List<Clientes> find(String substring) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Clientes findById(Integer clienteId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long countClientes() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Clientes create(Clientes cliente) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

}

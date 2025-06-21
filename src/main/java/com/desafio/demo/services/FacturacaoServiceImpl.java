package com.desafio.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desafio.demo.entitys.Facturacao;
import com.desafio.demo.repositorys.FacturacaoRepository;

@Service
public class FacturacaoServiceImpl implements FacturacaoService{

	@Autowired
	private FacturacaoRepository facturacaoRepository;
	
	@Override
	public void save(Facturacao factura) {
		facturacaoRepository.save(factura);
	}

	@Override
	public void update(Facturacao factura) {
		if(facturacaoRepository.existsById(factura.getId())) {
			facturacaoRepository.save(factura);
		}else {
			throw new RuntimeException("Factura nao encontrada");
		}
		
	}

	@Override
	public void remove(Facturacao factura) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Facturacao> findAll() {
		return facturacaoRepository.findAll();
	}

	@Override
	public List<Facturacao> find(String substring) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Facturacao findById(Integer facturaId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long countFacturacao() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Facturacao create(Facturacao factura) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

}

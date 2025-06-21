package com.desafio.demo.services;

import java.util.List;

import com.desafio.demo.entitys.Facturacao;

public interface FacturacaoService {

	public void save(Facturacao factura);
	public void update(Facturacao factura);
	public void remove(Facturacao factura);
	public List<Facturacao> findAll();
	public List<Facturacao> find(String substring);
	public Facturacao findById(Integer facturaId);
	public long countFacturacao();
	public Facturacao create(Facturacao factura);
	public void delete(Integer id);
}

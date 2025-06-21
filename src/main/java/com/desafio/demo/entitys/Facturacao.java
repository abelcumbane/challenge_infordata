package com.desafio.demo.entitys;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.desafio.demo.entitys.enums.EstadoFatura;

@Entity
public class Facturacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String numero;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Clientes cliente;

    @Column
    private LocalDateTime dataEmissao;

    @OneToMany(mappedBy = "fatura", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FaturaProduto> itens;

    @Column
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    private EstadoFatura estado;

    @Column
    private String observacoes;
    
    public Facturacao() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Clientes getCliente() {
		return cliente;
	}

	public void setCliente(Clientes cliente) {
		this.cliente = cliente;
	}

	public LocalDateTime getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(LocalDateTime dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public List<FaturaProduto> getItens() {
		return itens;
	}

	public void setItens(List<FaturaProduto> itens) {
		this.itens = itens;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public EstadoFatura getEstado() {
		return estado;
	}

	public void setEstado(EstadoFatura estado) {
		this.estado = estado;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
    
    

}

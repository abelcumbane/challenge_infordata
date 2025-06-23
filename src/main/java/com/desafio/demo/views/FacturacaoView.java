package com.desafio.demo.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import com.desafio.demo.entitys.Clientes;
import com.desafio.demo.entitys.Facturacao;
import com.desafio.demo.entitys.FaturaProduto;
import com.desafio.demo.entitys.Produtos;
import com.desafio.demo.entitys.enums.EstadoFatura;
import com.desafio.demo.services.ClienteService;
import com.desafio.demo.services.FacturacaoService;
import com.desafio.demo.services.ProdutoService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Route(value = "facturacao", layout = MainView.class)
public class FacturacaoView extends VerticalLayout {

    private final FacturacaoService facturacaoService;
    private final ClienteService clienteService;
    private final ProdutoService produtoService;

    private ComboBox<Clientes> clienteCombo;
    private ComboBox<Produtos> produtoCombo;
    private IntegerField quantidadeField;
    private NumberField totalField;
    private TextField numeroFaturaField;
    private ComboBox<EstadoFatura> estadoCombo;
    private TextArea observacoesField;
    private Grid<FaturaProduto> grid;

    private List<FaturaProduto> itens = new ArrayList<>();

    public FacturacaoView(FacturacaoService facturacaoService,
                          ClienteService clienteService,
                          ProdutoService produtoService) {
        this.facturacaoService = facturacaoService;
        this.clienteService = clienteService;
        this.produtoService = produtoService;

        configureGrid();
        configureComponents();
        buildLayout();
    }

    private void configureComponents() {
        clienteCombo = new ComboBox<>("Cliente");
        clienteCombo.setItems(clienteService.findAll());
        clienteCombo.setItemLabelGenerator(Clientes::getNome);

        produtoCombo = new ComboBox<>("Produto");
        produtoCombo.setItems(produtoService.findAll());
        produtoCombo.setItemLabelGenerator(Produtos::getDescricao);

        quantidadeField = new IntegerField("Quantidade");
        quantidadeField.setMin(1);
        
        numeroFaturaField = new TextField("Número da Fatura");
        numeroFaturaField.setReadOnly(true); // será gerado automaticamente
        numeroFaturaField.setValue(gerarNumeroFatura());

        estadoCombo = new ComboBox<>("Estado");
        estadoCombo.setItems(EstadoFatura.values());
        estadoCombo.setValue(EstadoFatura.EMITIDA); // valor padrão

        observacoesField = new TextArea("Observações");
        observacoesField.setPlaceholder("Digite observações da fatura (opcional)");


        totalField = new NumberField("Total");
        totalField.setReadOnly(true);

        Button adicionarItemBtn = new Button("Adicionar Produto", e -> adicionarItem());
        Button salvarBtn = new Button("Salvar Fatura", e -> salvarFatura());

        HorizontalLayout form = new HorizontalLayout(produtoCombo, quantidadeField, adicionarItemBtn);
        VerticalLayout faturaMeta = new VerticalLayout(numeroFaturaField, estadoCombo, observacoesField);
        add(clienteCombo,faturaMeta, form, grid, totalField, salvarBtn);
    }

    private String gerarNumeroFatura() {
    	long count = facturacaoService.countFacturacao() + 1;
        return String.format("F%03d", count);
	}

	private void configureGrid() {
        grid = new Grid<>(FaturaProduto.class, false);
        grid.addColumn(i -> i.getProduto().getDescricao()).setHeader("Produto");
        grid.addColumn(FaturaProduto::getQuantidade).setHeader("Qtd");
        grid.addColumn(FaturaProduto::getPrecoUnitario).setHeader("Preço Unitario");
        grid.addColumn(FaturaProduto::getSubtotal).setHeader("Subtotal");
    }

    private void buildLayout() {
        setSizeFull();
        setSpacing(true);
        setPadding(true);
    }

    private void adicionarItem() {
        Produtos produto = produtoCombo.getValue();
        Integer quantidade = quantidadeField.getValue();

        if (produto == null || quantidade == null || quantidade <= 0) {
            Notification.show("Selecione um produto e defina a quantidade.");
            return;
        }

        FaturaProduto item = new FaturaProduto();
        item.setProduto(produto);
        item.setQuantidade(quantidade);
        item.setPrecoUnitario(produto.getPreco());
        item.setSubtotal(produto.getPreco() * quantidade);
        itens.add(item);

        grid.setItems(itens);
        atualizarTotal();

        produtoCombo.clear();
        quantidadeField.clear();
    }

    private void atualizarTotal() {
        double total = itens.stream().mapToDouble(FaturaProduto::getSubtotal).sum();
        totalField.setValue(total);
    }

    private void salvarFatura() {
        if (clienteCombo.getValue() == null || itens.isEmpty()) {
            Notification.show("Selecione um cliente e adicione pelo menos 1 produto.");
            return;
        }

        Facturacao fatura = new Facturacao();
        fatura.setCliente(clienteCombo.getValue());
        fatura.setDataEmissao(LocalDate.now());
        fatura.setItens(itens); // precisa estar com cascade na entidade
        fatura.setTotal(totalField.getValue());
        
        fatura.setNumero(numeroFaturaField.getValue());
        fatura.setEstado(estadoCombo.getValue());
        fatura.setObservacoes(observacoesField.getValue());

        for (FaturaProduto item : itens) {
            item.setFatura(fatura); // liga o item à fatura
        }

        facturacaoService.save(fatura);
        Notification.show("Fatura salva com sucesso!");
        limparFormulario();
    }

    private void limparFormulario() {
        clienteCombo.clear();
        produtoCombo.clear();
        quantidadeField.clear();
        totalField.clear();
        itens.clear();
        grid.setItems(itens);
    }
}


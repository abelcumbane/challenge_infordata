	package com.desafio.demo.views;
	
	import java.util.List;
	import java.util.Optional;
	
	import org.springframework.beans.factory.annotation.Autowired;
	
	import com.desafio.demo.entitys.Produtos;
	import com.desafio.demo.services.ProdutoService;
	import com.vaadin.flow.component.Component;
	import com.vaadin.flow.component.UI;
	import com.vaadin.flow.component.button.Button;
	import com.vaadin.flow.component.button.ButtonVariant;
	import com.vaadin.flow.component.dialog.Dialog;
	import com.vaadin.flow.component.grid.Grid;
	import com.vaadin.flow.component.html.H1;
	import com.vaadin.flow.component.icon.Icon;
	import com.vaadin.flow.component.icon.VaadinIcon;
	import com.vaadin.flow.component.notification.Notification;
	import com.vaadin.flow.component.notification.NotificationVariant;
	import com.vaadin.flow.component.notification.Notification.Position;
	import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
	import com.vaadin.flow.component.orderedlayout.VerticalLayout;
	import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
	import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
	import com.vaadin.flow.component.textfield.TextField;
	import com.vaadin.flow.data.binder.Binder;
	import com.vaadin.flow.data.value.ValueChangeMode;
	import com.vaadin.flow.router.Route;
	
	@Route(value = "produtos", layout = MainView.class)
	public class ProdutoView extends VerticalLayout  {
		
	
		private ProdutoService produtoService;
		
		
		private Grid<Produtos> grid;
		private TextField filterField;
		private Produtos produto; 
		
		// new Button("Voltar", e -> UI.getCurrent().navigate(""))
		
		private Binder<Produtos> binder;
		
		public ProdutoView (ProdutoService produtoService) {
			this.produtoService = produtoService;
	        
			setSizeFull();
	        setAlignItems(Alignment.CENTER); 
	        
	        createFieldVariables();
	        configuredGrid();
	        
	        add(createToolbar(),grid);
	        loadProdutos();	
	    }
		
		private void createFieldVariables() {
			this.grid = new Grid<>(Produtos.class);
			this.filterField = new TextField();
			
		}
		
		private void configuredGrid() {
			grid.setSizeFull();
			grid.setColumns("nome","codigo");
			
		}
		
		private Component createToolbar() {
			filterField.setPlaceholder("Pesquisar pelo nome");
			filterField.setClearButtonVisible(true);
			filterField.setValueChangeMode(ValueChangeMode.LAZY); 
			filterField.addValueChangeListener(e -> updateProdutos());
			
			Button addProdutoButton = new Button(new Icon(VaadinIcon.PLUS));
			Button editProdutoButton = new Button(new Icon(VaadinIcon.EDIT));
			Button removeProdutoButton = new Button(new Icon(VaadinIcon.TRASH));
			
			addProdutoButton.addClickListener(event ->openDialogAddProdutos());
			
			removeProdutoButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("remover_produto")));
			
			editProdutoButton.setEnabled(false);
			grid.addSelectionListener(event -> {
				editProdutoButton.setEnabled(event.getFirstSelectedItem().isPresent());
			} );
			
			editProdutoButton.addClickListener(e -> {
				Optional<Produtos> selectedProduto = grid.getSelectedItems().stream().findFirst();
				
				selectedProduto.ifPresentOrElse(
						produto -> openDialogEditProdutos(produto), 
				        () -> Notification.show("Nenhuma faculdade selecionada!")
				    );
			});
			
			return new HorizontalLayout(filterField,addProdutoButton,editProdutoButton,removeProdutoButton);
		}
		
	private void openDialogAddProdutos() {
			
			Dialog dialog = new Dialog();
			dialog.setWidth("500px");
			dialog.setHeight("300px");
			dialog.setHeaderTitle("Novos Produtos");
			
			// Impede que o usuário feche o dialog clicando fora ou pressionando ESC
		    dialog.setCloseOnOutsideClick(false);
		    dialog.setCloseOnEsc(false);
		    
			Produtos novaProdutos = new Produtos();
			TextField nomeField = new TextField("Nome do produto");
			TextField codigoField = new TextField("Codigo");
	
			nomeField.setWidth("90%");
			nomeField.setHeight("50px"); 
			
			binder = new Binder<>(Produtos.class);
			binder.forField(nomeField).asRequired("Nome é obrigatório ").bind(Produtos::getNome, Produtos::setNome);
			binder.forField(codigoField).bind(Produtos::getCodigo, Produtos::setCodigo);
			
			Button saveButton = new Button("Salvar", event -> {
				if (binder.writeBeanIfValid(novaProdutos)) {
					produtoService.save(novaProdutos); // Salva no banco de dados
	                updateGrid();
	                dialog.close();
	                Notification notification = Notification.show("Produto salvo com sucesso!");
					notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
					notification.setPosition(Position.TOP_CENTER);
	            }
			});
			
			saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
			
			Button cancelButton = new Button("Cancelar", event -> dialog.close());
			cancelButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
			
			HorizontalLayout buttonLayout = new HorizontalLayout(saveButton, cancelButton);
		    buttonLayout.setJustifyContentMode(JustifyContentMode.CENTER);
		    buttonLayout.setWidthFull();
			
			dialog.add(new VerticalLayout(nomeField,codigoField,buttonLayout));
			dialog.open();
			
		}
	
	private void openDialogEditProdutos(Produtos produto) {
		Dialog dialog = new Dialog();
		dialog.setWidth("500px");
		dialog.setHeight("300px");
	    dialog.setHeaderTitle("Editar Produtos");
	    
	 // Impede que o usuário feche o dialog clicando fora ou pressionando ESC
	    dialog.setCloseOnOutsideClick(false);
	    dialog.setCloseOnEsc(false);
	
	    TextField nomeField = new TextField("Nome do Produto.");
	    TextField codigoField = new TextField("Codigo do produto.");
	    nomeField.setValue(produto.getNome()!= null ? produto.getNome(): ""); // Preenche com o nome atual
	    codigoField.setValue(produto.getCodigo()!= null ? produto.getCodigo(): "");
	    nomeField.setWidth("90%");
	    nomeField.setHeight("50px"); 
	
	    
	    binder = new Binder<>(Produtos.class);
	    binder.forField(nomeField)
	          .asRequired("Nome é obrigatório")
	          .bind(Produtos::getNome, Produtos::setNome);
	    
	    binder.forField(codigoField).bind(Produtos::getCodigo, Produtos::setCodigo);

	
	    binder.readBean(produto); // Preenche os campos com os dados atuais
	
	    Button saveButton = new Button("Salvar", event -> {
	        if (binder.writeBeanIfValid(produto)) { 
	        	produtoService.update(produto); // Atualiza no banco de dados
	            updateGrid();
	            dialog.close();
	            Notification notification = Notification.show("Produto salvo com sucesso!");
				notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
				notification.setPosition(Position.TOP_CENTER);
	        }
	    });
	    saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
	
	    Button cancelButton = new Button("Cancelar", event -> dialog.close());
	    cancelButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
	
	    HorizontalLayout buttonLayout = new HorizontalLayout(saveButton, cancelButton);
	    buttonLayout.setJustifyContentMode(JustifyContentMode.CENTER);
	    buttonLayout.setWidthFull();
	
	    dialog.add(new VerticalLayout(nomeField, codigoField, buttonLayout));
	    dialog.open(); 
		
	}
	
	
	private void updateGrid() {
	    List<Produtos> produto = produtoService.findAll();
	    grid.setItems(produto);
	}
	
	private void loadProdutos() { 
		grid.setItems(produtoService.findAll());
		
	}
	
	private void updateProdutos() {
		grid.setItems(produtoService.find(filterField.getValue()));
	}			
	
		
	
	}

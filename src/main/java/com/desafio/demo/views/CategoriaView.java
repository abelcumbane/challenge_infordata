	package com.desafio.demo.views;
	
	import java.util.List;
	import java.util.Optional;
	
	import com.desafio.demo.entitys.Categorias;
	import com.desafio.demo.services.CategoriaService;
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
	
	@Route(value = "categorias", layout = MainView.class)
	public class CategoriaView extends VerticalLayout {
		
		private CategoriaService categoriaService;
		
		
		private Grid<Categorias> grid;
		private TextField filterField;
		private Categorias categoria; 
		
		// new Button("Voltar", e -> UI.getCurrent().navigate(""))
		
		private Binder<Categorias> binder;
		
		public CategoriaView (CategoriaService categoriaService) {
			this.categoriaService = categoriaService;
	        
			setSizeFull();
	        setAlignItems(Alignment.CENTER); 
	        
	        createFieldVariables();
	        configuredGrid();
	        
	        add(createToolbar(),grid);
	        loadCategorias();	
	    }
		
		private void createFieldVariables() {
			this.grid = new Grid<>(Categorias.class);
			this.filterField = new TextField();
			
		}
		
		private void configuredGrid() {
			grid.setSizeFull();
			grid.setColumns("nome","descricao");
			
		}
		
		private Component createToolbar() {
			filterField.setPlaceholder("Pesquisar pelo nome");
			filterField.setClearButtonVisible(true);
			filterField.setValueChangeMode(ValueChangeMode.LAZY); 
			filterField.addValueChangeListener(e -> updateCategorias());
			
			Button addCategoriaButton = new Button(new Icon(VaadinIcon.PLUS));
			Button editCategoriaButton = new Button(new Icon(VaadinIcon.EDIT));
			Button removeCategoriaButton = new Button(new Icon(VaadinIcon.TRASH));
			
			addCategoriaButton.addClickListener(event ->openDialogAddCategorias());
			
			removeCategoriaButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("remover_categoria")));
			
			editCategoriaButton.setEnabled(false);
			grid.addSelectionListener(event -> {
				editCategoriaButton.setEnabled(event.getFirstSelectedItem().isPresent());
			} );
			
			editCategoriaButton.addClickListener(e -> {
				Optional<Categorias> selectedProduto = grid.getSelectedItems().stream().findFirst();
				
				selectedProduto.ifPresentOrElse(
						categoria -> openDialogEditCategorias(categoria), 
				        () -> Notification.show("Nenhuma faculdade selecionada!")
				    );
			});
			
			return new HorizontalLayout(filterField,addCategoriaButton,editCategoriaButton,removeCategoriaButton);
		}
		
	private void openDialogAddCategorias() {
			
			Dialog dialog = new Dialog();
			dialog.setWidth("500px");
			dialog.setHeight("300px");
			dialog.setHeaderTitle("Novas Categorias");
			
			// Impede que o usuário feche o dialog clicando fora ou pressionando ESC
		    dialog.setCloseOnOutsideClick(false);
		    dialog.setCloseOnEsc(false);
		    
			Categorias novaCategorias = new Categorias();
			TextField nomeField = new TextField("Nome do categoria");
			TextField descrcaoField = new TextField("Descricao");
	
			nomeField.setWidth("90%");
			nomeField.setHeight("50px"); 
			
			binder = new Binder<>(Categorias.class);
			binder.forField(nomeField).asRequired("Nome é obrigatório ").bind(Categorias::getNome, Categorias::setNome);
			binder.forField(descrcaoField).bind(Categorias::getDescricao, Categorias::setDescricao);
			
			Button saveButton = new Button("Salvar", event -> {
				if (binder.writeBeanIfValid(novaCategorias)) {
					categoriaService.save(novaCategorias); // Salva no banco de dados
	                updateGrid();
	                dialog.close();
	                Notification notification = Notification.show("Categoria salva com sucesso!");
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
			
			dialog.add(new VerticalLayout(nomeField,descrcaoField,buttonLayout));
			dialog.open();
			
		}
	
	private void openDialogEditCategorias(Categorias categoria) {
		Dialog dialog = new Dialog();
		dialog.setWidth("500px");
		dialog.setHeight("300px");
	    dialog.setHeaderTitle("Editar Categorias");
	    
	 // Impede que o usuário feche o dialog clicando fora ou pressionando ESC
	    dialog.setCloseOnOutsideClick(false);
	    dialog.setCloseOnEsc(false);
	
	    TextField nomeField = new TextField("Nome da Categoria.");
	    TextField descrcaoField = new TextField("Nome da Categoria.");
	    nomeField.setValue(categoria.getNome()!= null ? categoria.getNome(): "");  // Preenche com o nome atual
	    descrcaoField.setValue(categoria.getDescricao()!= null ? categoria.getDescricao(): "");
	    nomeField.setWidth("90%");
	    nomeField.setHeight("50px"); 
	
	    
	    binder = new Binder<>(Categorias.class);
	    binder.forField(nomeField)
	          .asRequired("Nome é obrigatório")
	          .bind(Categorias::getNome, Categorias::setNome);
	    
	    binder.forField(descrcaoField).bind(Categorias::getDescricao, Categorias::setDescricao);
	
	
	    binder.readBean(categoria); // Preenche os campos com os dados atuais
	
	    Button saveButton = new Button("Salvar", event -> {
	        if (binder.writeBeanIfValid(categoria)) { 
	        	categoriaService.update(categoria); // Atualiza no banco de dados
	            updateGrid();
	            dialog.close();
	            Notification notification = Notification.show("Categorias salva com sucesso!");
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
	
	    dialog.add(new VerticalLayout(nomeField, descrcaoField, buttonLayout));
	    dialog.open(); 
		
	}
	
	
	private void updateGrid() {
	    List<Categorias> categoria = categoriaService.findAll();
	    grid.setItems(categoria);
	}
	
	private void loadCategorias() { 
		grid.setItems(categoriaService.findAll());
		
	}
	
	private void updateCategorias() {
		grid.setItems(categoriaService.find(filterField.getValue()));
	}			
	
		
	
	}

	package com.desafio.demo.views;
	
	import java.util.List;
	import java.util.Optional;
	
	import com.desafio.demo.entitys.Clientes;
	import com.desafio.demo.services.ClienteService;
	import com.vaadin.flow.component.Component;
	import com.vaadin.flow.component.UI;
	import com.vaadin.flow.component.button.Button;
	import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
	import com.vaadin.flow.component.grid.Grid;
	import com.vaadin.flow.component.html.H1;
	import com.vaadin.flow.component.html.Span;
	import com.vaadin.flow.component.icon.Icon;
	import com.vaadin.flow.component.icon.VaadinIcon;
	import com.vaadin.flow.component.notification.Notification;
	import com.vaadin.flow.component.notification.NotificationVariant;
	import com.vaadin.flow.component.notification.Notification.Position;
	import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
	import com.vaadin.flow.component.orderedlayout.VerticalLayout;
	import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
	import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.IntegerField;
	import com.vaadin.flow.component.textfield.NumberField;
	import com.vaadin.flow.component.textfield.TextField;
	import com.vaadin.flow.data.binder.Binder;
	import com.vaadin.flow.data.value.ValueChangeMode;
	import com.vaadin.flow.router.Route;
	
	
	@Route(value = "clientes", layout = MainView.class)
	public class ClientesView extends VerticalLayout {
		
		private ClienteService clienteService;
		
		
		private Grid<Clientes> grid;
		private TextField filterField;
		private Clientes cliente; 
		
		// new Button("Voltar", e -> UI.getCurrent().navigate(""))
		
		private Binder<Clientes> binder;
		
		public ClientesView (ClienteService clienteService) {
			this.clienteService = clienteService;
	        
			setSizeFull();
	        setAlignItems(Alignment.START); 
	        
	        createFieldVariables();
	        configuredGrid();
	        
	        add(createToolbar(),grid);
	        loadClientes();	
	    }
		
		private void createFieldVariables() {
			this.grid = new Grid<>(Clientes.class);
			this.filterField = new TextField();
			
		}
		
		private void configuredGrid() {
			grid.setSizeFull();
			grid.setColumns("nome","nuit","telfone");
			
			grid.addComponentColumn(this::createDetailsButton).setHeader("Ações").setAutoWidth(true);
			
		}
		
		private void showClienteDetailsDialog(Clientes cliente) {
		    Dialog dialog = new Dialog();
		    dialog.setHeaderTitle("Detalhes do Cliente: ");
		    dialog.setCloseOnOutsideClick(false);
		    dialog.setCloseOnEsc(false);
	
		    VerticalLayout layout = new VerticalLayout();
		    layout.add(
		        new Span("Código: " + cliente.getId()),
		        new Span("Nome: " + cliente.getNome()),
		        new Span("Nuit: " + cliente.getNuit()),
		        new Span("Telfone: " + cliente.getTelfone()),
		        new Span("Email: " + cliente.getEmail()),
		        new Span("Endereco: " + cliente.getEndereco()),
		        new Span("Estado: " + cliente.getEstado())


		    );
	
		    Button fechar = new Button("Fechar", e -> dialog.close());
		    layout.add(fechar);
		    
		 // Botão Editar
		    Button editar = new Button("Editar", e -> {
		        //dialog.close(); // fecha detalhes
		        openDialogEditClientes(cliente); // chama o dialog de edição
		    });
		    editar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

		 // Botão Apagar com confirmação
		    Button apagar = new Button("Apagar", e -> {
		        dialog.close();
		        Dialog confirmDialog = new Dialog();
		        confirmDialog.setHeaderTitle("Confirmação");

		        confirmDialog.add(new Span("Tem certeza que deseja remover este cliente?"));

		        Button sim = new Button("Sim", ev -> {
		            clienteService.remove(cliente);
		            updateGrid(); // atualiza a grid
		            confirmDialog.close();
		            Notification.show("Cliente removido com sucesso.").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
		        });

		        Button nao = new Button("Não", ev -> confirmDialog.close());
		        nao.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

		        HorizontalLayout confirmButtons = new HorizontalLayout(sim, nao);
		        confirmDialog.add(confirmButtons);
		        confirmDialog.open();
		    });
		    apagar.addThemeVariants(ButtonVariant.LUMO_ERROR);

		    
		    // Layout de botões
		    HorizontalLayout buttons = new HorizontalLayout(editar, apagar, fechar);
		    layout.add(buttons);
	
		    dialog.add(layout);
		    dialog.open();
		}
		
		private Button createDetailsButton(Clientes cliente) {
		    Button detalhesBtn = new Button("Detalhes");
		    detalhesBtn.addClickListener(e -> showClienteDetailsDialog(cliente));
		    return detalhesBtn;
		}
	
		
		
		private Component createToolbar() {
			filterField.setPlaceholder("Pesquisar pelo nome");
			filterField.setClearButtonVisible(true);
			filterField.setValueChangeMode(ValueChangeMode.LAZY); 
			filterField.addValueChangeListener(e -> updateClientes());
			
			Button addProdutoButton = new Button(new Icon(VaadinIcon.PLUS));
			Button editProdutoButton = new Button(new Icon(VaadinIcon.EDIT));
			Button removeProdutoButton = new Button(new Icon(VaadinIcon.TRASH));
			
			addProdutoButton.getStyle().set("background-color", "#28a745"); // Verde
			addProdutoButton.getStyle().set("color", "white");
			editProdutoButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);  // Cinza
			removeProdutoButton.addThemeVariants(ButtonVariant.LUMO_ERROR);   // Vermelho
			
			addProdutoButton.addClickListener(event ->openDialogAddClientes());
			
			removeProdutoButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("remover_clientes")));
			
			editProdutoButton.setEnabled(false);
			grid.addSelectionListener(event -> {
				editProdutoButton.setEnabled(event.getFirstSelectedItem().isPresent());
			} );
			
			editProdutoButton.addClickListener(e -> {
				Optional<Clientes> selectedProduto = grid.getSelectedItems().stream().findFirst();
				
				selectedProduto.ifPresentOrElse(
						cliente -> openDialogEditClientes(cliente), 
				        () -> Notification.show("Nenhum producto selecionado!")
				    );
			});
			
			return new HorizontalLayout(filterField,addProdutoButton,editProdutoButton,removeProdutoButton);
		}
		
	private void openDialogAddClientes() {
			
			Dialog dialog = new Dialog();
			dialog.setWidth("500px");
			dialog.setHeight("300px");
			dialog.setHeaderTitle("Novos Clientes");
			
			// Impede que o usuário feche o dialog clicando fora ou pressionando ESC
		    dialog.setCloseOnOutsideClick(false);
		    dialog.setCloseOnEsc(false);
		    
			Clientes novoClientes = new Clientes();
			TextField nome = new TextField("Nome do Cliente");
			TextField nuit = new TextField("Nuit");
			TextField telfone = new TextField("Telfone");
			EmailField email = new EmailField("Email");
			TextField endereco = new TextField("Endereço");
			
			
			ComboBox<String> estado = new ComboBox<>("Estado");
			estado.setItems("Activo","Inactivo");
			estado.setPlaceholder("Selecionar o Estado");
		   
			//nomeField.setWidth("90%");
			//nomeField.setHeight("50px"); 
			
			binder = new Binder<>(Clientes.class);
			binder.forField(nome).asRequired("Nome é obrigatório ").bind(Clientes::getNome, Clientes::setNome);
			binder.forField(nuit).bind(Clientes::getNuit, Clientes::setNuit);
			binder.forField(telfone).bind(Clientes::getTelfone, Clientes::setTelfone);
			binder.forField(email).bind(Clientes::getEmail, Clientes::setEmail);
			binder.forField(endereco).bind(Clientes::getEndereco, Clientes::setEndereco);
			binder.forField(estado).bind(Clientes::getEstado, Clientes::setEstado);
			
			// Layout em duas colunas
		    VerticalLayout coluna1 = new VerticalLayout(nome, telfone, endereco);
		    VerticalLayout coluna2 = new VerticalLayout(nuit, email, estado);
		    coluna1.setPadding(false);
		    coluna2.setPadding(false);
		    
		    HorizontalLayout formLayout = new HorizontalLayout(coluna1, coluna2);
		    formLayout.setWidthFull();
		    formLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);

			Button saveButton = new Button("Salvar", event -> {
				if (binder.writeBeanIfValid(novoClientes)) {
					clienteService.save(novoClientes); // Salva no banco de dados
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
			
			dialog.add(new VerticalLayout(formLayout,buttonLayout));
			dialog.open();
			
		}
	
	
	private void openDialogEditClientes(Clientes cliente) {
	
		Dialog dialog = new Dialog();
		dialog.setWidth("500px");
		dialog.setHeight("300px");
	    dialog.setHeaderTitle("Editar Clientes");
	    
	 // Impede que o usuário feche o dialog clicando fora ou pressionando ESC
	    dialog.setCloseOnOutsideClick(false);
	    dialog.setCloseOnEsc(false);
	
		TextField nome = new TextField("Nome do Cliente");
		TextField nuit = new TextField("Nuit");
		TextField telfone = new TextField("Telfone");
		EmailField email = new EmailField("Email");
		TextField endereco = new TextField("Endereço");
		
		
		ComboBox<String> estado = new ComboBox<>("Estado");
		estado.setItems("Activo","Inactivo");
		estado.setPlaceholder("Selecionar o Estado");
	    
		nome.setValue(cliente.getNome()!= null ? cliente.getNome(): ""); // Preenche com o nome atual
		nuit.setValue(cliente.getNuit()!= null ? cliente.getNuit(): "");
		telfone.setValue(cliente.getTelfone()!= null ? cliente.getTelfone(): "");
		email.setValue(cliente.getEmail()!= null ? cliente.getEmail(): "");
		endereco.setValue(cliente.getEndereco()!= null ? cliente.getEndereco(): "");
		estado.setValue(cliente.getEstado()!= null ? cliente.getEstado(): "");
	    
	
	    
		binder = new Binder<>(Clientes.class);
		binder.forField(nome).asRequired("Nome é obrigatório ").bind(Clientes::getNome, Clientes::setNome);
		binder.forField(nuit).bind(Clientes::getNuit, Clientes::setNuit);
		binder.forField(telfone).bind(Clientes::getTelfone, Clientes::setTelfone);
		binder.forField(email).bind(Clientes::getEmail, Clientes::setEmail);
		binder.forField(endereco).bind(Clientes::getEndereco, Clientes::setEndereco);
		binder.forField(estado).bind(Clientes::getEstado, Clientes::setEstado);
	
	    binder.readBean(cliente); // Preenche os campos com os dados atuais
	    
		// Layout em duas colunas
	    VerticalLayout coluna1 = new VerticalLayout(nome, telfone, endereco);
	    VerticalLayout coluna2 = new VerticalLayout(nuit, email, estado);
	    coluna1.setPadding(false);
	    coluna2.setPadding(false);
	    
	    HorizontalLayout formLayout = new HorizontalLayout(coluna1, coluna2);
	    formLayout.setWidthFull();
	    formLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
	
	    Button saveButton = new Button("Salvar", event -> {
	        if (binder.writeBeanIfValid(cliente)) { 
	        	clienteService.update(cliente); // Atualiza no banco de dados
	            updateGrid();
	            dialog.close();
	            Notification notification = Notification.show("Cliente salvo com sucesso!");
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
	
	    dialog.add(new VerticalLayout(formLayout, buttonLayout));
	    dialog.open(); 
		
	}
	
	
	private void updateGrid() {
	    List<Clientes> cliente = clienteService.findAll();
	    grid.setItems(cliente);
	}
	
	private void loadClientes() { 
		grid.setItems(clienteService.findAll());
		
	}
	
	private void updateClientes() {
		grid.setItems(clienteService.find(filterField.getValue()));
	}			
	
		
		
		
		
		
		/*---------------------------------------------------------------------------------------------------
		 * 
		 * add(
			            new H1("Página de Clientes"),
			            new Button("Criar Cliente", e -> Notification.show("Cliente criado!")),
			            new Button("Voltar", e -> UI.getCurrent().navigate(""))
			        );	
		 */
	
	}

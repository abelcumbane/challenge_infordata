package com.desafio.demo.views;

import java.util.Set;

import com.desafio.demo.entitys.Categorias;
import com.desafio.demo.services.CategoriaService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.data.selection.SelectionEvent;
import com.vaadin.flow.data.selection.SelectionListener;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@PageTitle("Remover Categoria")
@Route("remover_categoria")
public class RemoveCategoryView extends VerticalLayout implements SelectionListener<Grid<Categorias>, Categorias> {
	
	private Grid<Categorias> grid;
	private final CategoriaService categoriaService;
	private Button remove;
	private Button cancel;
	private Set<Categorias> selected;
	
	
	public RemoveCategoryView(CategoriaService categoriaService) {
		this.categoriaService = categoriaService;
		
		setSizeFull();
		setAlignItems(Alignment.CENTER);
		
		createFieldVariables();
		confiGuredGrid();
		
		add(grid,creatButtonLayout());
		
		loadCategoria();
		
	}
	
	private void createFieldVariables() {
		grid = new Grid<>(Categorias.class);
		remove = new Button("Remover");
		cancel = new Button("Cancelar");
		
	}

	private void confiGuredGrid() {
		grid.setSizeFull();
		grid.setColumns("nome");
		grid.getColumns().forEach(col -> col.setAutoWidth(true));			
	}

	private Component creatButtonLayout() {
		remove.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		
		cancel.addClickListener(e -> closeView());
		remove.addClickListener(e -> removeSelected());
		
		return new HorizontalLayout(remove,cancel);
	}
	

	private void removeSelected() {
		selected.forEach(categoriaService::remove);
		Notification notification = Notification.show("Categoria removida com sucesso...");
		notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
		notification.setPosition(Position.TOP_CENTER);
		
		//Remove the Categorias from the grid (update the grid)
		grid.setItems(categoriaService.findAll());
		
	}
	
	private void closeView() {
		getUI().ifPresent(ui -> ui.navigate("categorias"));
	}


	private void loadCategoria() {
		grid.setItems(categoriaService.findAll());	
		grid.setSelectionMode(SelectionMode.MULTI);
		grid.addSelectionListener(this);
		
	}

	@Override
	public void selectionChange(SelectionEvent<Grid<Categorias>, Categorias> event) {
		selected = event.getAllSelectedItems();		
	}

}

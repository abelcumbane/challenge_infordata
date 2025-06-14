package com.desafio.demo.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "fornecedores", layout = MainView.class)
public class FornecedorView extends VerticalLayout {
	
	public FornecedorView() {
		 add(
		          new H1("Página de Fornecedores"),
		            new Button("Atualizar Dados", e -> Notification.show("Dados atualizados!")),
		            new Button("Voltar", e -> UI.getCurrent().navigate(""))
		        );
	}

}

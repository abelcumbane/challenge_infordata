package com.desafio.demo.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "compras", layout = MainView.class)
public class CompraView extends VerticalLayout{
	
	public CompraView() {
		 add(
		            new H1("Página Compras"),
		            new Button("Criar Compra", e -> Notification.show("Compra criado!")),
		            new Button("Voltar", e -> UI.getCurrent().navigate(""))
		        );
	}

}

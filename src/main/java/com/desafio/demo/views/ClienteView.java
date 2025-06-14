package com.desafio.demo.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;


@Route(value = "clientes", layout = MainView.class)
public class ClienteView extends VerticalLayout {
	
	public ClienteView() {
		 add(
		            new H1("Página de Clientes"),
		            new Button("Criar Cliente", e -> Notification.show("Cliente criado!")),
		            new Button("Voltar", e -> UI.getCurrent().navigate(""))
		        );	
		 }

}

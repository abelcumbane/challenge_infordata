package com.desafio.demo.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "encomendas", layout = MainView.class)
public class EncomendaView extends VerticalLayout {

	public EncomendaView() {
		 add(
		            new H1("Página Encomendas"),
		            new Button("Criar Projecto", e -> Notification.show("Encomenda criada!")),
		            new Button("Voltar", e -> UI.getCurrent().navigate(""))
		        );	}
}

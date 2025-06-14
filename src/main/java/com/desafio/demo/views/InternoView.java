package com.desafio.demo.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "internos", layout = MainView.class)
public class InternoView extends VerticalLayout{
	
	public InternoView() {
		 add(
		            new H1("Página de Internos"),
		            new Button("Criar Interno", e -> Notification.show("Interno criado!")),
		            new Button("Voltar", e -> UI.getCurrent().navigate(""))
		        );	}

}

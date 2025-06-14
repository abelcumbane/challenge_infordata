package com.desafio.demo.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "relatórios", layout = MainView.class)
public class RelatórioView extends VerticalLayout {
	
	public RelatórioView() {
		 add(
		            new H1("Página Relatório"),
		            new Button("Criar Relatório", e -> Notification.show("Relatório criado!")),
		            new Button("Voltar", e -> UI.getCurrent().navigate(""))
		        );	}

}

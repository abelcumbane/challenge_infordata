package com.desafio.demo.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "projectos", layout = MainView.class)
public class ProjectosView extends VerticalLayout{
	
	public ProjectosView() {
        add(
            new H1("Página Projectos"),
            new Button("Criar Projecto", e -> Notification.show("Projecto criado!")),
            new Button("Voltar", e -> UI.getCurrent().navigate(""))
        );
    }

}

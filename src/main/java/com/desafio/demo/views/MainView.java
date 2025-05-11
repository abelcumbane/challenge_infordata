	package com.desafio.demo.views;
	
	import com.vaadin.flow.component.UI;
	import com.vaadin.flow.component.applayout.AppLayout;
	import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
	import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
	import com.vaadin.flow.component.orderedlayout.VerticalLayout;
	import com.vaadin.flow.router.Route;
	import com.vaadin.flow.router.RouteConfiguration;
	import com.vaadin.flow.router.RouterLink;
	
	@Route("")
	@CssImport("./styles/styles.css")
	public class MainView extends AppLayout {
	
	    private final HorizontalLayout dynamicHeader = new HorizontalLayout();
	    private Button gestaoBtn;
	    private Button projectoBtn;
	
	    public MainView() {
	        setPrimarySection(Section.DRAWER);
	        createHeader();
	        createDrawer();
	    }
	
	    private void createHeader() {
	        dynamicHeader.setSpacing(true);
	        dynamicHeader.setPadding(true);
	        dynamicHeader.setWidthFull();
	        addToNavbar(dynamicHeader);
	    }
	
	    private void createDrawer() {
	        VerticalLayout menu = new VerticalLayout();
	        menu.setPadding(true);
	

	        Image logo = new Image("images/logo.png", "Logo");
	        logo.setWidth("100px");
	        menu.add(logo);
	
	        gestaoBtn = new Button("Gestão", e -> {
	            setSelectedButton(gestaoBtn);
	            showGestaoLinks();
	        });
	        projectoBtn = new Button("Projecto", e -> {
	            setSelectedButton(projectoBtn);
	            showProjectoLinks();
	        });
	
	        menu.add(gestaoBtn, projectoBtn);
	        addToDrawer(menu);
	    }
	
	    private void setSelectedButton(Button selected) {
	        gestaoBtn.removeClassName("selected-button");
	        projectoBtn.removeClassName("selected-button");
	        selected.addClassName("selected-button");
	    }
	
	    private void showGestaoLinks() {
	        dynamicHeader.removeAll();
	        dynamicHeader.add(
	            createDynamicLink("Produto", ProdutoView.class),
	            createDynamicLink("Categoria", CategoriaView.class)
	        );
	    }
	
	    private void showProjectoLinks() {
	        dynamicHeader.removeAll();
	        dynamicHeader.add(
	            createDynamicLink("Projectos", ProjectosView.class),
	            createDynamicLink("Projectos Activos", ProjectosActivosView.class)
	        );
	    }
	
	    private RouterLink createDynamicLink(String text, Class<? extends com.vaadin.flow.component.Component> navigationTarget) {
	        RouterLink link = new RouterLink(text, navigationTarget);
	        String currentRoute = UI.getCurrent().getInternals().getActiveViewLocation().getPath();
	        String targetRoute = RouteConfiguration.forApplicationScope().getUrl(navigationTarget);
	
	        if (currentRoute.equals(targetRoute)) {
	            link.addClassName("active-link");
	        }
	
	        return link;
	    }
	}

package com.desafio.demo.views;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.desafio.demo.entitys.Facturacao;
import com.desafio.demo.services.FacturacaoService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

@Route(value = "facturas", layout = MainView.class)
public class FacturacaoListView extends VerticalLayout {

    private final FacturacaoService facturacaoService;
    private final Grid<Facturacao> grid = new Grid<>(Facturacao.class, false);

    public FacturacaoListView(FacturacaoService facturacaoService) {
        this.facturacaoService = facturacaoService;

        setSizeFull();
        configureGrid();
        add(grid);
        loadData();
    }

    private void configureGrid() {
        grid.addColumn(Facturacao::getNumero).setHeader("Nº Fatura");
        grid.addColumn(f -> f.getCliente().getNome()).setHeader("Cliente");
        grid.addColumn(f -> f.getEstado().name()).setHeader("Estado");
        grid.addColumn(Facturacao::getTotal).setHeader("Total");
        //grid.addColumn(Facturacao::getDataEmissao).setHeader("Data");

        grid.addComponentColumn(this::criarBotoesAcao).setHeader("Ações");
    }

    private Component criarBotoesAcao(Facturacao fatura) {
        Button verBtn = new Button(new Icon(VaadinIcon.EYE));
        verBtn.getElement().setProperty("title", "Ver Detalhes");
        verBtn.addClickListener(e -> verDetalhes(fatura));
        verBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        Button editarBtn = new Button(new Icon(VaadinIcon.EDIT));
        editarBtn.getElement().setProperty("title", "Editar");
        editarBtn.addClickListener(e -> editarFatura(fatura));
        editarBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button apagarBtn = new Button(new Icon(VaadinIcon.TRASH));
        apagarBtn.getElement().setProperty("title", "Eliminar");
        apagarBtn.addClickListener(e -> eliminarFatura(fatura));
        apagarBtn.addThemeVariants(ButtonVariant.LUMO_ERROR);

        Button imprimirBtn = new Button(new Icon(VaadinIcon.PRINT));
        imprimirBtn.getElement().setProperty("title", "Imprimir");
        imprimirBtn.addClickListener(e -> gerarPdf(fatura));
        imprimirBtn.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        HorizontalLayout layout = new HorizontalLayout(verBtn, editarBtn, apagarBtn, imprimirBtn);
        layout.setSpacing(false);
        layout.getStyle().set("gap", "5px");

        return layout;
    }


    private void gerarPdf(Facturacao fatura) {
        try {
            String filename = "Fatura_" + fatura.getNumero() + ".pdf";
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            // Criar o PDF
            com.lowagie.text.Document document = new com.lowagie.text.Document();
            com.lowagie.text.pdf.PdfWriter.getInstance(document, baos);
            document.open();

            document.add(new com.lowagie.text.Paragraph("Fatura Nº: " + fatura.getNumero()));
            document.add(new com.lowagie.text.Paragraph("Cliente: " + fatura.getCliente().getNome()));
            document.add(new com.lowagie.text.Paragraph("Data: " + fatura.getDataEmissao()));
            document.add(new com.lowagie.text.Paragraph("Total: " + fatura.getTotal()));
            document.add(new com.lowagie.text.Paragraph("Estado: " + (fatura.getEstado() != null ? fatura.getEstado().name() : "N/D")));
            document.add(new com.lowagie.text.Paragraph("Observações: " + fatura.getObservacoes()));

            document.close();

            // Criar o recurso de download
            StreamResource resource = new StreamResource(
                filename,
                () -> new ByteArrayInputStream(baos.toByteArray())
            );

            Anchor downloadLink = new Anchor(resource, "");
            downloadLink.getElement().setAttribute("download", true);

            Button downloadBtn = new Button("Baixar PDF", new Icon(VaadinIcon.DOWNLOAD_ALT));
            downloadBtn.getStyle().set("color", "white");
            downloadBtn.getStyle().set("background-color", "#007bff");
            downloadLink.add(downloadBtn);

            // Exibir o link temporariamente em um dialog
            Dialog dialog = new Dialog();
            dialog.setHeaderTitle("Download da Fatura");
            dialog.add(downloadLink);
            dialog.setWidth("300px");

            Button fecharBtn = new Button("Fechar", e -> dialog.close());
            fecharBtn.addThemeVariants(ButtonVariant.LUMO_ERROR);

            dialog.add(new HorizontalLayout(fecharBtn));
            dialog.open();

        } catch (Exception e) {
            Notification.show("Erro ao gerar PDF: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
        }
    }



	private void verDetalhes(Facturacao fatura) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Detalhes da Fatura");

        VerticalLayout content = new VerticalLayout(
            new Span("Número: " + fatura.getNumero()),
            new Span("Cliente: " + fatura.getCliente().getNome()),
            new Span("Estado: " + fatura.getEstado()),
            new Span("Total: " + fatura.getTotal()),
            new Span("Data: " + fatura.getDataEmissao()),
            new Span("Observações: " + fatura.getObservacoes())
        );

        Button fechar = new Button("Fechar", e -> dialog.close());
        content.add(fechar);
        dialog.add(content);
        dialog.open();
    }

    private void editarFatura(Facturacao fatura) {
        Notification.show("Funcionalidade de edição em desenvolvimento");
        // Ou navegar para uma tela de edição: 
        // UI.getCurrent().navigate("editar-factura/" + fatura.getId());
    }

    private void eliminarFatura(Facturacao fatura) {
        facturacaoService.remove(fatura);
        Notification.show("Fatura eliminada!");
        loadData();
    }

    private void loadData() {
        grid.setItems(facturacaoService.findAll());
    }
}

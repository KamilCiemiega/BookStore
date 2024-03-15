package app.views.UserPanel.Category;

import app.service.CategoryService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class CategoryOverflow extends Dialog {

    public CategoryOverflow(CategoryService categoryService) {

        CategoryTreeView categoryTreeView = new CategoryTreeView(categoryService);

        setHeight("500px");
        setHeight("450px");
        HorizontalLayout header = new HorizontalLayout();
        header.addClassName("header");
        Button backButton = new Button("Back", e -> close());
        backButton.addClassName("overflowBackButton");
        Span mainTitle = new Span("To choose, click on the assortment");
        mainTitle.addClassName("mainTitle");

        Div line = new Div();
        line.addClassName("line");

        Span chooseInformation = new Span("Actual choose:");
        chooseInformation.addClassName("chooseInformation");
        header.add(backButton, mainTitle);

        add(header, line, chooseInformation, categoryTreeView.displayTreeView());
    }
}

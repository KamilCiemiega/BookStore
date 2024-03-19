package app.views.UserPanel.Category;

import app.service.CategoryService;
import app.views.UserPanel.Category.AddCategory.AddCategoryTreeView;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("categoryOverflow")
public class CategoryOverflow extends Dialog {
    private final Span chooseElement;

    public CategoryOverflow(CategoryService categoryService, TextField mainCategory) {
        AddCategoryTreeView addCategoryTreeView = new AddCategoryTreeView(categoryService, mainCategory, this);

        setHeight("450px");
        HorizontalLayout header = new HorizontalLayout();
        header.addClassName("header");
        header.setSpacing(false);
        Span backButton = new Span("Back");
        backButton.addClickListener(e -> close());
        backButton.addClassName("overflowBackButton");
        Span confirmButton = new Span("Confirm");
        confirmButton.addClickListener(e -> close());
        confirmButton.addClassName("overflowConfirmButton");

        Span mainTitle = new Span("To choose, click on the assortment");
        mainTitle.addClassName("mainTitle");

        Div line = new Div();
        line.addClassName("line");

        HorizontalLayout chooseInformationContainer = new HorizontalLayout();
        chooseInformationContainer.addClassName("chooseInformationContainer");
        chooseInformationContainer.setSpacing(false);

        Span chooseInformation = new Span("Actual choose: ");
        chooseInformation.addClassName("chooseInformation");
        chooseElement = new Span();
        chooseElement.addClassName("chooseElement");

        chooseInformationContainer.add(chooseInformation, chooseElement);

        header.add(backButton, confirmButton, mainTitle);

        add(header, line, chooseInformationContainer, addCategoryTreeView.displayTreeView());
    }

    public void updateChooseInformation(String categoryName) {
        chooseElement.setText(categoryName);
    }
}

package app.views.UserPanel.Books.BookCreator;

import app.views.UserPanel.UserPanel;
import app.views.ViewConfigurator;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.awt.*;


@Route(value = "BookCreator", layout = UserPanel.class)
public class BookCreator extends VerticalLayout implements ViewConfigurator {
    public BookCreator() {
        configureView();
        add(bookCreatorContainer());
    }

    @Override
    public void configureView() {
        setSizeFull();
        setClassName("BookCreator");
    }

    private VerticalLayout bookCreatorContainer() {
        VerticalLayout container = new VerticalLayout();

        Button save = new Button();

        TabSheet tabSheet = new TabSheet();
        tabSheet.add("Book Parameters", bookParameters());
        tabSheet.add("Book Price", bookPrice());

        container.add(tabSheet);
        return container;
    }

    private FormLayout bookParameters() {
        FormLayout bookFormLayout = new FormLayout();

        TextField code = new TextField();
        code.setRequired(true);
        bookFormLayout.addFormItem(code, "Code");

        TextField hiddenCode = new TextField();
        hiddenCode.setVisible(false);
        bookFormLayout.addFormItem(hiddenCode, "");

        TextField name = new TextField();
        name.setRequired(true);
        bookFormLayout.addFormItem(name, "Name");

        TextField hiddenName = new TextField();
        hiddenName.setVisible(false);
        bookFormLayout.addFormItem(hiddenName, "");


        TextField assortment = new TextField();
        bookFormLayout.addFormItem(assortment, "Assortment");

        return bookFormLayout;
    }

    private FormLayout bookPrice() {
        FormLayout priceFormLayout = new FormLayout();
        priceFormLayout.addClassName("priceFormLayout");

        TextField price = new TextField();
        price.addClassName("priceTextField");
        priceFormLayout.addFormItem(price, "Price");

        TextField hiddenPrice = new TextField();
        hiddenPrice.setVisible(false);
        priceFormLayout.addFormItem(hiddenPrice, "");

        ComboBox<String> discountComboBox = new ComboBox<>();
        discountComboBox.setItems("amount discount", "percentage discount");
        priceFormLayout.addFormItem(discountComboBox, "Choose discount");

        return priceFormLayout;
    }
}

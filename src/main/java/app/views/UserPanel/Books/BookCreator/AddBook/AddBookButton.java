package app.views.UserPanel.Books.BookCreator.AddBook;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.button.Button;


public class AddBookButton{
    public static Button addBookButton(){
        Button addButton = new Button(new Icon(VaadinIcon.PLUS));
        addButton.addClickListener(event -> UI.getCurrent().navigate(BookCreator.class));
        addButton.addClassName("addButton");
        addButton.getIcon().addClassName("plusIcon");

        return addButton;
    }
}

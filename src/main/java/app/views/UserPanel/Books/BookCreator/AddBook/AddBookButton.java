package app.views.UserPanel.Books.BookCreator.AddBook;

import app.views.UserPanel.Utils.AddButton;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;


public class AddBookButton extends AddButton {

    @Override
    public Button addButton() {
        Button addButton = super.addButton();
        addButton.addClickListener(event -> UI.getCurrent().navigate(BookCreator.class));
        return addButton;
    }
}

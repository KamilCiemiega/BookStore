package app.views.UserPanel.Category.DeleteCategory;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;


public class DeleteCategoryOverflow extends Dialog {

    public DeleteCategoryOverflow() {
        setHeight("120px");
        setCloseOnEsc(false);
        setCloseOnOutsideClick(false);
        VerticalLayout container = new VerticalLayout();
        container.addClassName("container");
        Span informationText = new Span("You cannot delete a category that has books");
        Button confirmButton = new Button("Ok");
        confirmButton.addClassName("confirmButton");
        confirmButton.addClickListener(e -> close());

        container.add(informationText, confirmButton);

        add(container);
    }
}

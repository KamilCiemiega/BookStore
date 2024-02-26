package app.views.UserPanel.Books.BookCreator;

import app.service.BookService;
import app.views.UserPanel.UserPanel;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route(value = "EditBook", layout = UserPanel.class)
public class EditBook extends BookCreator{
    private List<Object[]> bookDataList = new ArrayList<>();

    public EditBook(BookService bookService, List<Object[]> bookDataList) {
        super(bookService);
        this.bookDataList = bookDataList;
        UpdateBook updateBook = new UpdateBook(bookService);
    }

    @Override
    protected FormLayout bookParameters() {
        FormLayout bookFormLayout = super.bookParameters();
        if (bookDataList != null && !bookDataList.isEmpty()) {
            String bookName = bookDataList.get(0)[0].toString();
            String code = bookDataList.get(0)[1].toString();
            String price = bookDataList.get(0)[2].toString();

            // Przypisanie wartości do pól tekstowych
            TextField codeTextField = (TextField) bookFormLayout.getChildren().findFirst().get();
            codeTextField.setValue("asddsa");

            TextField nameTextField = (TextField) bookFormLayout.getChildren().toArray()[1];
            nameTextField.setValue(bookName);

            // Dodanie nowego pola z wartością ceny
            TextField priceTextField = new TextField();
            priceTextField.setValue(price);
            bookFormLayout.addFormItem(priceTextField, "Price");
        }

        return bookFormLayout;
    }

//    private void assignBookData() {
//        System.out.println(bookDataList);
//        codeField.setValue("123");
////        if (!bookDataList.isEmpty()) {
////            String bookName = bookDataList.get(0)[0].toString();
////            String code = bookDataList.get(0)[1].toString();
////            String price = bookDataList.get(0)[2].toString();
////
////
////            codeField.setValue(code);
////            nameField.setValue(bookName);
////            priceField.setValue(price);
////        }
//    }

}

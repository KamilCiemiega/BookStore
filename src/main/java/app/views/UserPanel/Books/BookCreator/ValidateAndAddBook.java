package app.views.UserPanel.Books.BookCreator;

import app.service.BookService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ValidateAndAddBook {
    private final BookService bookService;
    private final Map<String, String> errors;
    private boolean dataBaseStatus;
    public ValidateAndAddBook(BookService bookService) {
        this.bookService = bookService;
        this.errors = new HashMap<>();

    }

    public void validateBookData(String codeValue, String nameValue, String assortmentValue,
                                 BigDecimal priceValue, String discountValue ){
        if(codeValue.isEmpty()){
            errors.put("code", "Code field cannot be empty");
        }
        if(nameValue.isEmpty()){
            errors.put("name", "Book name field cannot be empty");
        }
        if(bookService.bookExistsByCode(codeValue)){
            errors.put("codeDuplicate", "Book with that code already exist");
        }
        if(bookService.bookExistsByName(nameValue)){
            errors.put("nameDuplicate", "Book with that name already exist");
        }
        if(errors.isEmpty()){
            try {
                sendDataToDatabase(codeValue, nameValue,priceValue);
                this.dataBaseStatus = true;
            }catch (Exception e){
                errors.put("databaseError", "Error while trying to add data to database");
                this.dataBaseStatus = false;
            }

        }
    }

    private void sendDataToDatabase(String codeValue, String nameValue,
                                    BigDecimal priceValue){
            bookService.insertBook(nameValue,Integer.parseInt(codeValue), priceValue);
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public boolean getDataBaseStatus() {
        return dataBaseStatus;
    }
}

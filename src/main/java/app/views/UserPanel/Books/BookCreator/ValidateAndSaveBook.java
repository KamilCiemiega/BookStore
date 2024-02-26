package app.views.UserPanel.Books.BookCreator;

import app.service.BookService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public abstract class ValidateAndSaveBook {
    protected final BookService bookService;
    protected final Map<String, String> errors;
    protected boolean dataBaseStatus;
    public ValidateAndSaveBook(BookService bookService) {
        this.bookService = bookService;
        this.errors = new HashMap<>();

    }

    public void validateBookData(String codeValue, String nameValue, String assortmentValue,
                                 String priceValue){
        BigDecimal priceBigDecimal = validatePrice(priceValue);
        if(codeValue.isEmpty()){
            errors.put("code", "Code field cannot be empty");
        }
        if(nameValue.isEmpty()){
            errors.put("name", "Book name field cannot be empty");
        }
        if(!priceValue.isEmpty()) {
            if (priceBigDecimal == null) {
                errors.put("priceNumber", "Price must be a number");
            } else if (priceBigDecimal.compareTo(BigDecimal.ZERO) < 0) {
                errors.put("pricePositiveNumber", "Price must be a positive number");
            }
        }
        if(bookService.bookExistsByCode(codeValue)){
            errors.put("codeDuplicate", "Book with that code already exist");
        }
        if(bookService.bookExistsByName(nameValue)){
            errors.put("nameDuplicate", "Book with that name already exist");
        }
    }
    abstract protected void sendDataToDatabase(String codeValue, String nameValue, BigDecimal priceValue);

    public void clearErrors(){
        errors.clear();
    }
    public BigDecimal validatePrice(String priceValue) {
            try {
                return new BigDecimal(priceValue);
            } catch (NumberFormatException e) {
                return null;
            }
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public boolean getDataBaseStatus() {
        return dataBaseStatus;
    }
}

package app.views.UserPanel.Books.BookCreator;

import app.service.BookService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

abstract public class ValidateBook {
    protected final Map<String, String> errors;
    protected BigDecimal priceBigDecimal;
    public ValidateBook() {
        this.errors = new HashMap<>();
    }

    public void validateBookData(String codeValue, String nameValue, String assortmentValue,
                                 String priceValue){
        priceBigDecimal = validatePrice(priceValue);
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

    }
    public void clearErrors(){
        errors.clear();
    }
    private BigDecimal validatePrice(String priceValue) {
            try {
                return new BigDecimal(priceValue);
            } catch (NumberFormatException e) {
                return null;
            }
    }

    public Map<String, String> getErrors() {
        return errors;
    }


}

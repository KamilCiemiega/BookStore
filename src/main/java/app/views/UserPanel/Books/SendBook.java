package app.views.UserPanel.Books;

import java.math.BigDecimal;

public interface SendBook {
    void sendBookDataToDatabase(String codeValue, String nameValue, BigDecimal priceValue);

}

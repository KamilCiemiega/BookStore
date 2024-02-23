package app.views.UserPanel.Books.BookCreator;

public interface BookFieldsData {
    boolean validatePriceData(String Price, String Discount);
    void validateBookParametersData(Integer Code, String Name, String Assortment);

}

package app.views.UserPanel.Category;

import app.views.UserPanel.Category.AddCategory.AddCategoryButton;
import app.views.ViewConfigurator;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class Category extends VerticalLayout implements ViewConfigurator {

    private final AddCategoryButton addCategoryButton;
    public Category() {
        this.addCategoryButton = new AddCategoryButton();
        configureView();
        add(header(), CategoryList());
    }

    @Override
    public void configureView() {
        setSizeFull();
        setClassName("Category");
    }
    private VerticalLayout CategoryList(){
        VerticalLayout listContainer = new VerticalLayout();
        H2 categoryTitle = new H2("All category");
        categoryTitle.addClassName("categoryTitle");

        listContainer.add(categoryTitle);

        return  listContainer;
    }

    private HorizontalLayout header(){
        HorizontalLayout headerContainer = new HorizontalLayout();
        headerContainer.addClassName("categoryContainer");

        headerContainer.add(addCategoryButton.addButton());

        return headerContainer;
    }

}
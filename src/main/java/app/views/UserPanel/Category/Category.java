package app.views.UserPanel.Category;

import app.service.CategoryService;
import app.views.UserPanel.Category.AddCategory.AddCategoryButton;
import app.views.ViewConfigurator;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class Category extends VerticalLayout implements ViewConfigurator {

    private final AddCategoryButton addCategoryButton;
    private final CategoryTreeView categoryTreeView;
    private final CategoryService categoryService;

    public Category(CategoryService categoryService) {
        this.categoryService = categoryService;
        this.categoryTreeView = new CategoryTreeView(categoryService);
        this.addCategoryButton = new AddCategoryButton();
        configureView();
        add(header(), CategoryList());
    }

    @Override
    public void configureView() {
        setSizeFull();
        setPadding(false);
        setClassName("Category");
    }
    private VerticalLayout CategoryList(){
        VerticalLayout listContainer = new VerticalLayout();
        listContainer.addClassName("listContainer");
        H2 categoryTitle = new H2("All category: " + categoryService.getAllCategory().size());
        categoryTitle.addClassName("categoryTitle");

        listContainer.add(categoryTitle, categoryTreeView.displayTreeView());

        return  listContainer;
    }

    private HorizontalLayout header(){
        HorizontalLayout headerContainer = new HorizontalLayout();
        headerContainer.addClassName("categoryContainer");

        headerContainer.add(addCategoryButton.addButton());

        return headerContainer;
    }

}

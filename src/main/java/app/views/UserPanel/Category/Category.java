package app.views.UserPanel.Category;

import app.service.CategoryService;
import app.views.UserPanel.Category.AddCategory.AddCategoryButton;
import app.views.UserPanel.Category.DeleteCategory.DeleteCategoryButton;
import app.views.UserPanel.Category.EditCategory.EditCategoryButton;
import app.views.ViewConfigurator;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class Category extends VerticalLayout implements ViewConfigurator {

    private final AddCategoryButton addCategoryButton;
    private final EditCategoryButton editCategoryButton;
    private final DeleteCategoryButton deleteCategoryButton;
    private final CategoryTreeView categoryTreeView;
    private final CategoryService categoryService;

    public Category(CategoryService categoryService ) {
        this.categoryService = categoryService;
        this.categoryTreeView = new CategoryTreeView(categoryService);
        this.addCategoryButton = new AddCategoryButton();
        this.editCategoryButton = new EditCategoryButton();
        this.deleteCategoryButton = new DeleteCategoryButton(categoryService);

        deleteCategoryButton.addCategoryDeletedListener(categoryTreeView);
        configureView();


        add(header(), categoryList());

    }

    @Override
    public void configureView() {
        setSizeFull();
        setPadding(false);
        setClassName("Category");
    }
    private HorizontalLayout header(){
        HorizontalLayout headerContainer = new HorizontalLayout();
        headerContainer.addClassName("categoryContainer");


        headerContainer.add(addCategoryButton.addButton(), editCategoryButton.editButton(), deleteCategoryButton.deleteButton());

        return headerContainer;
    }


    public VerticalLayout categoryList(){
        VerticalLayout listContainer = new VerticalLayout();
        listContainer.addClassName("listContainer");
        H2 categoryTitle = new H2("All category: " + categoryService.getAllCategory().size());
        categoryTitle.addClassName("categoryTitle");
        BookListByCategory.allCategoryListener(categoryTitle);

        listContainer.add(categoryTitle, categoryTreeView.displayTreeView());

        return  listContainer;
    }



}

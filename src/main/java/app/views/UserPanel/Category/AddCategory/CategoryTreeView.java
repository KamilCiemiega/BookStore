package app.views.UserPanel.Category.AddCategory;

import app.service.CategoryService;
import app.views.UserPanel.Category.CategoryData;
import com.sun.source.tree.Tree;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.provider.hierarchy.TreeData;
import com.vaadin.flow.data.provider.hierarchy.TreeDataProvider;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class CategoryTreeView {

    private final CategoryService categoryService;
    public CategoryTreeView(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public VerticalLayout displayTreeView() {
        List<CategoryData> allCategoriesData = categoryService.getAllCategory();
        List<CategoryData> roots = findRootCategories(allCategoriesData);
        VerticalLayout treeView = new VerticalLayout();

        // Dodanie tytułu
        H2 categoryTitle = new H2("All categories");
        treeView.add(categoryTitle);

        // Dodanie kategorii nadrzędnych (korzeni)
        for (CategoryData root : roots) {
            displayCategory(root, allCategoriesData, treeView);
        }

        return treeView;
    }

    private void displayCategory(CategoryData category, List<CategoryData> allCategories, VerticalLayout treeView) {
        VerticalLayout categoryLayout = new VerticalLayout();
        categoryLayout.add(new H2(category.name()));

        // Rekurencyjne dodanie podkategorii
        List<CategoryData> children = findChildren(category, allCategories);
        for (CategoryData child : children) {
            displayCategory(child, allCategories, categoryLayout);
        }

        treeView.add(categoryLayout);
    }

    private List<CategoryData> findRootCategories(List<CategoryData> categories) {
        List<CategoryData> roots = new ArrayList<>();
        for (CategoryData category : categories) {
            if (category.parentId() == 0) {
                roots.add(category);
            }
        }
        return roots;
    }

    private List<CategoryData> findChildren(CategoryData parent, List<CategoryData> categories) {
        List<CategoryData> children = new ArrayList<>();
        for (CategoryData category : categories) {
            if (category.parentId() == parent.categoryId()) {
                children.add(category);
            }
        }
        return children;
    }
}

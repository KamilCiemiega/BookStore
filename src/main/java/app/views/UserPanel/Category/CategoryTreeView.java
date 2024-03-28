package app.views.UserPanel.Category;

import app.service.CategoryService;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryTreeView extends VerticalLayout {

    protected final Map<CategoryData, VerticalLayout> childrenLayoutMap;
    private final CategoryService categoryService;
    protected final Map<CategoryData, Span> toggleButtons;
    private CategoryData selectedCategory;

    public CategoryTreeView(CategoryService categoryService) {
        this.categoryService = categoryService;
        this.childrenLayoutMap = new HashMap<>();
        this.toggleButtons = new HashMap<>();
    }


    public VerticalLayout displayTreeView() {
        List<CategoryData> allCategoriesData = categoryService.getAllCategory();
        List<CategoryData> roots = findRootCategories(allCategoriesData);
        VerticalLayout treeView = new VerticalLayout();
        treeView.addClassName("treeView");

        for (CategoryData root : roots) {
            displayCategory(root, allCategoriesData, treeView);
        }
        return treeView;
    }

    protected void displayCategory(CategoryData category, List<CategoryData> allCategories, VerticalLayout treeView) {
        HorizontalLayout layout = new HorizontalLayout();
//        layout.addClassName("categoryItem");
//        layout.getElement().setAttribute("tabindex", "0");

        System.out.println(category);
        System.out.println(selectedCategory);
        if (category.equals(selectedCategory)) {
            layout.getStyle().set("background-color", "lightgrey");
        }

        List<CategoryData> children = findChildren(category, allCategories);
        if (!children.isEmpty()) {
            Span toggleButton = new Span("▶ ");
            toggleButton.addClassName("toggleButton");
            toggleButton.addClickListener(e -> {
                toggleChildrenVisibility(category);
            });
            toggleButtons.put(category, toggleButton);
            layout.add(toggleButton);
        } else {
            layout.add(new Span("  "));
        }
        Span categoryName = new Span(category.name());
        categoryName.setId("category-" + category.categoryId());
        categoryName.addClickListener(e -> handleCategoryClick(category));

        layout.add(categoryName);
        treeView.add(layout);

        VerticalLayout childrenLayout = createChildrenLayout(children, allCategories, treeView);
        childrenLayoutMap.put(category, childrenLayout);
        treeView.add(childrenLayout);
        childrenLayout.setVisible(false);
    }

    protected void handleCategoryClick(CategoryData category){
//        System.out.println(category);
        selectedCategory = category;
    }


    protected VerticalLayout createChildrenLayout(List<CategoryData> children, List<CategoryData> allCategories, VerticalLayout treeView) {
        VerticalLayout childrenLayout = new VerticalLayout();
        childrenLayout.addClassName("childrenLayout");

        for (CategoryData child : children) {
            displayCategory(child, allCategories, childrenLayout);
        }

        return childrenLayout;
    }

    protected void toggleChildrenVisibility(CategoryData parent) {
        VerticalLayout childrenLayout = childrenLayoutMap.get(parent);
        Span toggleButton = toggleButtons.get(parent);

        if (childrenLayout != null && toggleButton != null) {
            childrenLayout.setVisible(!childrenLayout.isVisible());
            if (childrenLayout.isVisible()) {
                toggleButton.setText("▼ ");
            } else {
                toggleButton.setText("▶ ");
            }
        }
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

    protected List<CategoryData> findChildren(CategoryData parent, List<CategoryData> categories) {
        List<CategoryData> children = new ArrayList<>();
        for (CategoryData category : categories) {
            if (category.parentId() == parent.categoryId()) {
                children.add(category);
            }
        }
        return children;
    }
}

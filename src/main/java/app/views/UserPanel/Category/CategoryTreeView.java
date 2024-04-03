package app.views.UserPanel.Category;

import app.service.CategoryService;
import app.views.UserPanel.Category.EditCategory.EditCategory;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.util.ArrayList;
import java.util.List;


public class CategoryTreeView extends AbstractCategoryTreeView {

    private HorizontalLayout currentlyHighlightedLayout = null;
    private final List<CategoryData> listOfClickedCategorise = new ArrayList<>();
    private final EditCategory editCategory;

    public CategoryTreeView(CategoryService categoryService) {
        super(categoryService);

        this.editCategory = new EditCategory(categoryService);

    }

    protected void handleCategoryClick(CategoryData category, HorizontalLayout layout){
        if (layout != null) {
            if (currentlyHighlightedLayout != null && !layout.equals(currentlyHighlightedLayout)) {
                removeHighlightFromCategory(currentlyHighlightedLayout);
            }
            if (!listOfClickedCategorise.contains(category)) {
                listOfClickedCategorise.add(category);
                highlightSelectedCategory(layout);
                currentlyHighlightedLayout = layout;
            } else {
                listOfClickedCategorise.remove(category);
                if (!listOfClickedCategorise.contains(category)) {
                    listOfClickedCategorise.add(category);
                    highlightSelectedCategory(layout);
                    currentlyHighlightedLayout = layout;
                }
                else {
                    highlightSelectedCategory(layout);
                    currentlyHighlightedLayout = layout;
                }
            }
//            editCategory.getCategoryData();
        }
    }
    private void highlightSelectedCategory(HorizontalLayout layout) {
        if (layout != null) {
            layout.getStyle().set("background-color", "#b0b2b5");
        }
    }

    private void removeHighlightFromCategory(HorizontalLayout layout) {
        layout.getStyle().remove("background-color");
    }



}

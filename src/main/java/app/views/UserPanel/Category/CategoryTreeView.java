package app.views.UserPanel.Category;

import app.service.CategoryService;
import app.views.UserPanel.Category.DeleteCategory.CategoryDeletedListener;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.ArrayList;
import java.util.List;


public class CategoryTreeView extends AbstractCategoryTreeView implements CategoryDeletedListener {

    private HorizontalLayout currentlyHighlightedLayout = null;
    public static List<CategoryData> listOfClickedCategorise = new ArrayList<>();

    public CategoryTreeView(CategoryService categoryService) {
        super(categoryService);
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

    public static List<CategoryData> getListOfClickedCategorise() {
        return listOfClickedCategorise;
    }

    @Override
    public void categoryDeleted(CategoryData deletedCategory) {
//        System.out.println("categoryDeleded" + deletedCategory);
        removeCategoryFromTreeView(deletedCategory);
        displayTreeView();
    }

    public void removeCategoryFromTreeView(CategoryData deletedCategory) {
        VerticalLayout childrenLayout = childrenLayoutMap.remove(deletedCategory);
//        System.out.println("childrenleyout" + childrenLayoutMap);
        if (childrenLayout != null) {
            treeView.remove(childrenLayout);
//            System.out.println("treeView" + treeView);
        }
    }

}

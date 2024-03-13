package app.views.UserPanel.Category.AddCategory;

import app.service.CategoryService;
import app.views.UserPanel.Category.CategoryData;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.provider.hierarchy.TreeDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryTreeView {
    public CategoryTreeView() {
    }

    public void displayTreeView(){
//        System.out.println(CategoryService.getCategoryList());
//        List<CategoryData> allCategoriesData = categoryService.getAllCategory();
//        System.out.println(allCategoriesData);

        // Konwertuj listę kategorii na hierarchiczną strukturę
//        List<CategoryData> rootCategories = convertToHierarchy(allCategoriesData);

        // Utwórz dostawcę danych drzewa
//        TreeDataProvider<CategoryData> treeDataProvider = new TreeDataProvider<>(rootCategories);
//
//        // Utwórz TreeGrid
//        TreeGrid<CategoryData> treeGrid = new TreeGrid<>();
//        treeGrid.setDataProvider(treeDataProvider);
//        treeGrid.addHierarchyColumn(CategoryData::name).setHeader("Category Name");
    }
//    private List<CategoryData> convertToHierarchy(List<CategoryData> categoriesData) {
//
//    }
}

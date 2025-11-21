package com.example.womenshop.controller.base;

import com.example.womenshop.controller.IRefreshableController;
import com.example.womenshop.controller.ISceneAwareController;
import com.example.womenshop.model.Category;
import com.example.womenshop.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import com.example.womenshop.service.CategoryService;
import com.example.womenshop.service.ProductService;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

import java.util.List;

abstract public class ModuleController extends BaseController implements IRefreshableController {

    protected ProductService productService;
    protected CategoryService categoryService;
    public abstract void initData();

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @FXML
    public void onExit() {
        sceneManager.show("Menu");
    }

    protected void fetchProducts(ListView<Product> lvProducts) {
        List<Product> products = productService.listAllProducts();
        if (products != null) {
            lvProducts.setItems(FXCollections.observableArrayList(products));
        }
    }

    protected void loadCategories(ComboBox<Category> cmbCategory) {
        ObservableList<Category> categories = FXCollections.observableArrayList(categoryService.listAllCategories());
        cmbCategory.setItems(categories);
    }


}

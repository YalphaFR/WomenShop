package com.example.womenshop.controller.base;

import com.example.womenshop.controller.IRefreshableController;
import com.example.womenshop.model.base.Product;
import com.example.womenshop.service.ShopService;
import com.example.womenshop.service.TransactionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import com.example.womenshop.service.CategoryService;
import com.example.womenshop.service.ProductService;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

import java.util.List;

abstract public class ModuleController<T> extends BaseController implements IRefreshableController {

    protected ProductService productService;
    protected CategoryService categoryService;
    protected TransactionService transactionService;
    protected ShopService shopService;

    public abstract void initData();

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public void setShopService(ShopService shopService) {
        this.shopService = shopService;
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

    protected void loadComboBox(ComboBox<T> cmb, List<T> listOfValues) {
        ObservableList<T> observableList = FXCollections.observableArrayList(listOfValues);
        cmb.setItems(observableList);
    }


}

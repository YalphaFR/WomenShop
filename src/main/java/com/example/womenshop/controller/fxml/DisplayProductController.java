package com.example.womenshop.controller.fxml;

import com.example.womenshop.SceneManager;
import com.example.womenshop.controller.IRefreshableController;
import com.example.womenshop.controller.ISceneAwareController;
import com.example.womenshop.controller.base.ModuleController;
import com.example.womenshop.model.Category;
import com.example.womenshop.model.Product;
import com.example.womenshop.service.CategoryService;
import com.example.womenshop.service.ProductService;
import com.example.womenshop.util.UIUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class DisplayProductController extends ModuleController {
    @FXML
    private Button btnExit, btnReset, btnSearch;

    @FXML
    private ComboBox<Category> cmbCategory;

    @FXML
    private ListView<Product> lvProducts;

    @FXML
    private TextField txtID,  txtName;

    private ProductService productService;
    private CategoryService categoryService;



    // EVENTS

    @FXML
    void onReset(ActionEvent event) {
        lvProducts.getSelectionModel().clearSelection();
        txtID.clear();
        txtName.clear();
        cmbCategory.setValue(null);

        fetchProducts();
    }

    @FXML
    void onSearch() {
        Category category = cmbCategory.getValue();
        List<Product> filtered = new ArrayList<>();
        if (!txtID.getText().isEmpty()) {
            Product p = productService.findProductById(Integer.parseInt(txtID.getText()));

            if (p !=null) {
                filtered.add(p);
            }

        } else if (!txtName.getText().isEmpty()) {
           Product p = productService.findProductByName(txtName.getText());
           if (p != null) {
               filtered.add(p);
           }

        } else if (cmbCategory.getSelectionModel().getSelectedItem() != null) {
            filtered = productService.filterByCategory(category);
        }
        lvProducts.setItems(FXCollections.observableArrayList(filtered));
    }

    // UTILS

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    private void displayProductDetails(Product p) {
        if (p != null) {
            txtID.setText(String.valueOf(p.getId()));
            txtName.setText(p.getName());
            cmbCategory.setValue(p.getCategory());
        }
    }

    private void loadCategories() {
        ObservableList<Category> categories = FXCollections.observableArrayList(categoryService.listAllCategories());
        cmbCategory.setItems(categories);
    }

    private void setupListeners() {
        // Events listener
        lvProducts.getSelectionModel().selectedItemProperty().addListener(
                e -> displayProductDetails(lvProducts.getSelectionModel().getSelectedItem())
        );
    }

    private void fetchProducts() {
        List<Product> products = productService.listAllProducts();
        if (products != null) {
            lvProducts.setItems(FXCollections.observableArrayList(products));
        }
    }

    @Override
    public void initData() {
        loadCategories();

        UIUtils.setupComboBoxDisplay(cmbCategory, Category::getName); // on change l'affichage du comboxBox pour les catégories | on affiche uniquement le nom
        UIUtils.setupListViewDisplay(lvProducts, p -> p.getName() + " (" + p.getCategory().getName() + ")");

        fetchProducts();
        setupListeners();

    }


}

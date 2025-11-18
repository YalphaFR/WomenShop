package com.example.womenshop.controller;

import com.example.womenshop.SceneManager;
import com.example.womenshop.model.Category;
import com.example.womenshop.model.Product;

import com.example.womenshop.service.CategoryService;
import com.example.womenshop.service.ProductService;
import com.example.womenshop.util.UIUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;

public class ManageProductController implements Initializable, SceneAwareController, TypicalController {

    @FXML private ListView<Product> lvProducts;
    @FXML private TextField txtName, txtPrice, txtStock;
    @FXML private ComboBox<Category> cmbCategory;
    @FXML private Button btnSave, btnDelete, btnFilter, btnReset, btnExit;

    private SceneManager sceneManager;
    private ProductService productService;
    private CategoryService categoryService;


    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    private void displayProductDetails(Product p) {
        if (p != null) {
            txtName.setText(p.getName());
            cmbCategory.setValue(p.getCategory());
            txtPrice.setText(String.valueOf(p.getPurchasePrice()));
            txtStock.setText(String.valueOf(p.getStock()));
        }
    }

    private void fetchProducts() {
        List<Product> products = productService.listAllProducts();
        if (products != null) {
            lvProducts.setItems(FXCollections.observableArrayList(products));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    @FXML
    private void onDelete() {
        Product selected = lvProducts.getSelectionModel().getSelectedItem();
        if (selected != null) {
            productService.removeProduct(selected.getId());
            fetchProducts();
        }
    }

    @FXML
    private void onFilter() {
        Product selected = lvProducts.getSelectionModel().getSelectedItem();
        Category category = cmbCategory.getValue();

        if (selected == null && category != null) {
            List<Product> products = productService.listAllProducts();
            if (products != null) {
                List<Product> filtered = productService.filterByCategory(category);

                lvProducts.setItems(FXCollections.observableArrayList(filtered));
            }
        }
    }

    @FXML
    private void onSave() {
        try {
            Product selected = lvProducts.getSelectionModel().getSelectedItem();
            String name = txtName.getText();
            Category category = cmbCategory.getValue();
            double price = Double.parseDouble(txtPrice.getText());
            int stock = Integer.parseInt(txtStock.getText());

            if (selected == null) {
                Product p = new Product(category,name, price, 0, false, stock);
                productService.registerProduct(p);
            } else {
                selected.setName(name);
                selected.setCategory(category);
                selected.setPurchasePrice(price);
                selected.setStock(stock);
                productService.updateProductDetails(selected);
            }
            onReset();
        } catch (NumberFormatException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    @FXML
    private void onReset() {
        lvProducts.getSelectionModel().clearSelection();
        txtName.clear();
        cmbCategory.setValue(null);
        txtPrice.clear();
        txtStock.clear();

        fetchProducts();
    }

    @FXML
    private void onExit() {
        sceneManager.show("Menu");
    }

    @Override
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @Override
    public void initData() {
        loadCategories();

        UIUtils.setupComboBoxDisplay(cmbCategory, Category::getName); // on change l'affichage du comboxBox pour les catégories | on affiche uniquement le nom
        UIUtils.setupListViewDisplay(lvProducts, p -> p.getName() + " (" + p.getCategory().getName() + ")");

        fetchProducts();

        setupListeners();

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
}

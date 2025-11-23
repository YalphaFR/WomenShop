package com.example.womenshop.controller.fxml;

import com.example.womenshop.controller.base.ModuleController;
import com.example.womenshop.model.Category;
import com.example.womenshop.model.Product;

import com.example.womenshop.util.UIUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;


import java.util.ArrayList;
import java.util.List;

public class ManageProductController extends ModuleController {

    @FXML private Label lblID;
    @FXML private ListView<Product> lvProducts;
    @FXML private TextField txtName, txtSalePrice, txtPurchasePrice, txtStock;
    @FXML private ComboBox<Category> cmbCategory;
    @FXML private ComboBox<Boolean> cmbIsDiscounted;
    @FXML private Button btnSave, btnDelete, btnFilter, btnReset, btnExit;

    private void displayProductDetails(Product p) {
        if (p != null) {
            lblID.setText(String.valueOf(p.getId()));
            txtName.setText(p.getName());
            cmbCategory.setValue(p.getCategory());
            txtSalePrice.setText(String.valueOf(p.getSalePrice()));
            txtPurchasePrice.setText(String.valueOf(p.getPurchasePrice()));
            txtStock.setText(String.valueOf(p.getStock()));
            cmbIsDiscounted.setValue(p.isDiscounted());
        }
    }

    @FXML
    private void onDelete() {
        Product selected = lvProducts.getSelectionModel().getSelectedItem();
        if (selected != null) {
            productService.removeProduct(selected.getId());
            fetchProducts(lvProducts);
            showAlert("Success :", "Deleted product");
        }
    }

    @FXML
    private void onFilter() {
        List<Product> filtered = new ArrayList<>();
        if (!lblID.getText().isEmpty()) {
            Product p = productService.findProductById(Integer.parseInt(lblID.getText()));

            if (p !=null) {
                filtered.add(p);
            }

        } else if (!txtName.getText().isEmpty()) {
            Product p = productService.findProductByName(txtName.getText());
            if (p != null) {
                filtered.add(p);
            }

        } else if (cmbCategory.getSelectionModel().getSelectedItem() != null) {
            filtered = productService.filterByCategory(cmbCategory.getSelectionModel().getSelectedItem());
        }
        lvProducts.setItems(FXCollections.observableArrayList(filtered));
    }

    @FXML
    private void onSave() {
        try {
            Product selected = lvProducts.getSelectionModel().getSelectedItem();
            String name = txtName.getText();
            Category category = cmbCategory.getValue();
            double salePrice = Double.parseDouble(txtSalePrice.getText());
            double purchasePrice = Double.parseDouble(txtPurchasePrice.getText());
            int stock = Integer.parseInt(txtStock.getText());
            Boolean isDiscounted = cmbIsDiscounted.getValue();

            if (selected == null) {
                Product p = new Product(category,name, purchasePrice, salePrice, isDiscounted, stock);
                productService.registerProduct(p);
                showAlert("Success :", "Registered product");
            } else {
                selected.setName(name);
                selected.setCategory(category);
                selected.setSalePrice(salePrice);
                selected.setPurchasePrice(purchasePrice);
                selected.setStock(stock);
                selected.setDiscounted(isDiscounted);
                productService.updateProductDetails(selected);
                showAlert("Success :", "Modified product");
            }
            onReset();
        } catch (Exception e) {
            showAlert("Error", "An error has occurred");
            System.err.println("An error has occurred: " + e.getMessage());
        }
    }

    @FXML
    private void onReset() {
        lvProducts.getSelectionModel().clearSelection();
        lblID.setText("");
        txtName.clear();
        cmbCategory.setValue(null);
        txtSalePrice.clear();
        txtPurchasePrice.clear();
        txtStock.clear();
        cmbIsDiscounted.setValue(null);

        fetchProducts(lvProducts);
    }

    @Override
    public void initData() {
        UIUtils.setupComboBoxDisplay(cmbCategory, Category::getName); // on change l'affichage du comboxBox pour les catégories | on affiche uniquement le nom
        UIUtils.setupListViewDisplay(lvProducts, p -> p.getName() + " (" + p.getCategory().getName() + ")");

        loadComboBox(cmbCategory, categoryService.listAllCategories());
        loadComboBox(cmbIsDiscounted, List.of(true, false));

        fetchProducts(lvProducts);

        setupListeners();

    }

    private void setupListeners() {
        // Events listener
        lvProducts.getSelectionModel().selectedItemProperty().addListener(
                e -> displayProductDetails(lvProducts.getSelectionModel().getSelectedItem())
        );
    }
}

package com.example.womenshop.controller.fxml;

import com.example.womenshop.SceneManager;
import com.example.womenshop.controller.base.ModuleController;
import com.example.womenshop.model.Category;
import com.example.womenshop.model.Product;
import com.example.womenshop.util.UIUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class SellProductController extends ModuleController {

    @FXML
    private Button btnExit, btnReset, btnSell, btnSearch;

    @FXML
    private ListView<Product> lvProducts;

    @FXML
    private Spinner<Integer> spinStockToSell;

    @FXML private ComboBox<Category> cmbCategory;

    @FXML
    private TextField txtID, txtName;

    @FXML
    private Label lblPrice, lblStockAvailable;

    @FXML
    void onReset() {
        lvProducts.getSelectionModel().clearSelection();
        txtID.clear();
        txtName.clear();
        cmbCategory.setValue(null);
        lblPrice.setText("");
        lblStockAvailable.setText("");
        spinStockToSell.setValueFactory(null);
        spinStockToSell.getEditor().clear();

        fetchProducts(lvProducts);
    }

    @FXML
    void onSell() {

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


    @Override
    public void initData() {
        UIUtils.setupListViewDisplay(lvProducts, p -> p.getName() + " (" + p.getCategory().getName() + ")");
        UIUtils.setupComboBoxDisplay(cmbCategory, Category::getName);

        loadCategories(cmbCategory);
        fetchProducts(lvProducts);

        setupListeners();
    }

    private void setupListeners() {
        // Events listener
        lvProducts.getSelectionModel().selectedItemProperty().addListener(
                e -> displayProductDetails(lvProducts.getSelectionModel().getSelectedItem())
        );
    }

    private void displayProductDetails(Product p) {
        if (p != null) {
            txtID.setText(String.valueOf(p.getId()));
            txtName.setText(p.getName());
            cmbCategory.setValue(p.getCategory());
            lblPrice.setText(String.valueOf(p.getSalePrice()));
            lblStockAvailable.setText(String.valueOf(p.getStock()));
            spinStockToSell.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, p.getStock()));
        }
    }
}

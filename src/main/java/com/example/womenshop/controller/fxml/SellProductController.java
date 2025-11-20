package com.example.womenshop.controller.fxml;

import com.example.womenshop.SceneManager;
import com.example.womenshop.controller.base.ModuleController;
import com.example.womenshop.model.Category;
import com.example.womenshop.model.Product;
import com.example.womenshop.util.UIUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.awt.event.ActionEvent;

public class SellProductController extends ModuleController {

    @FXML
    private Button btnExit, btnReset, btnSave, btnSearch;

    @FXML
    private ListView<Product> lvProducts;

    @FXML
    private Spinner<Product> spinStockToSell;

    @FXML
    private TextField txtID, txtName;

    @FXML
    private Label lblPrice, lblStockAvailable, lblCategory;

    @FXML
    void onReset() {
        lvProducts.getSelectionModel().clearSelection();
        txtID.clear();
        txtName.clear();
        lblCategory.setText("");
        lblPrice.setText("");
        lblStockAvailable.setText("");
        //spinStockToSell.setValueFactory(0);

        fetchProducts(lvProducts);
    }

    @FXML
    void onSave() {

    }

    @FXML
    void onSearch() {

    }


    @Override
    public void initData() {
        UIUtils.setupListViewDisplay(lvProducts, p -> p.getName() + " (" + p.getCategory().getName() + ")");

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
            lblCategory.setText(p.getCategory().getName());
            lblPrice.setText(String.valueOf(p.getSalePrice()));
            lblStockAvailable.setText(String.valueOf(p.getStock()));
        }
    }
}

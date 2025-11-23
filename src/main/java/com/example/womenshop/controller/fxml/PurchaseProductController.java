package com.example.womenshop.controller.fxml;

import com.example.womenshop.controller.base.ModuleController;
import com.example.womenshop.model.Category;
import com.example.womenshop.model.Product;
import com.example.womenshop.model.Transaction;
import com.example.womenshop.util.UIUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;

public class PurchaseProductController extends ModuleController {

    @FXML private Button btnExit, btnReset, btnPurchase, btnSearch;
    @FXML private ListView<Product> lvProducts;
    @FXML private TextField txtName, txtPurchasePrice, txtID;
    @FXML private ComboBox<Category> cmbCategory;
    @FXML private Spinner<Integer> spinQuantity;
    @FXML private Label lblTotalCost;

    @FXML
    void onReset() {
        txtID.clear();
        lvProducts.getSelectionModel().clearSelection();
        txtName.clear();
        cmbCategory.setValue(null);
        txtPurchasePrice.clear();
        spinQuantity.getValueFactory().setValue(1);
        lblTotalCost.setText("Total cost: 0.00 €");
    }

    @FXML
    void onPurchase() {
        try {
            Product product = lvProducts.getSelectionModel().getSelectedItem();

            if (product == null) {
                showAlert("Error", "Please select a product");
                return;
            };

            int quantity = spinQuantity.getValue();
            double amount = product.getPurchasePrice() * quantity;

            product.setStock(product.getStock() + quantity);
            productService.updateProductDetails(product);

            Transaction transaction = new Transaction(product, Transaction.TransactionType.PURCHASE, quantity, amount);
            transactionService.registerTransaction(transaction);
            showAlert("Success", quantity + " Units added to stock!\nThe transaction was successfully completed.");

            this.shopService.addToCapital(-amount);
            onReset();
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid price or quantity");
        }
    }

    @FXML
    void onSearch() {
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
            filtered = productService.filterByCategory(cmbCategory.getSelectionModel().getSelectedItem());
        }
        lvProducts.setItems(FXCollections.observableArrayList(filtered));
    }

    private void displayProductDetails(Product p) {
        if (p != null) {
            txtID.setText(String.valueOf(p.getId()));
            txtName.setText(p.getName());
            cmbCategory.setValue(p.getCategory());
            txtPurchasePrice.setText(String.valueOf(p.getPurchasePrice()));
            updateTotalCost();
        }
    }

    private void updateTotalCost() {
        try {
            double price = Double.parseDouble(txtPurchasePrice.getText());
            int quantity = spinQuantity.getValue();
            lblTotalCost.setText(String.format("Total cost: %.2f €", price * quantity));
        } catch (NumberFormatException e) {
            lblTotalCost.setText("Total cost: 0.00 €");
        }
    }

    @Override
    public void initData() {
        UIUtils.setupComboBoxDisplay(cmbCategory, Category::getName);
        UIUtils.setupListViewDisplay(lvProducts, p -> p.getName() + " (" + p.getCategory().getName() + ") - Stock: " + p.getStock());

        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1);
        spinQuantity.setValueFactory(valueFactory);

        loadComboBox(cmbCategory, categoryService.listAllCategories());
        fetchProducts(lvProducts);
        setupListeners();
    }

    private void setupListeners() {
        lvProducts.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> displayProductDetails(newVal)
        );

        txtPurchasePrice.textProperty().addListener((obs, oldVal, newVal) -> updateTotalCost());
        spinQuantity.valueProperty().addListener((obs, oldVal, newVal) -> updateTotalCost());
    }
}

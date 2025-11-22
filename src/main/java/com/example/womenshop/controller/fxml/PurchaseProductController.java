package com.example.womenshop.controller.fxml;

import com.example.womenshop.controller.base.ModuleController;
import com.example.womenshop.model.Category;
import com.example.womenshop.model.Product;
import com.example.womenshop.util.UIUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PurchaseProductController extends ModuleController {

    @FXML private Button btnExit, btnReset, btnPurchase;
    @FXML private ListView<Product> lvProducts;
    @FXML private TextField txtName, txtPurchasePrice, txtSalePrice;
    @FXML private ComboBox<Category> cmbCategory;
    @FXML private Spinner<Integer> spinQuantity;
    @FXML private Label lblTotalCost;

    @FXML
    void onReset() {
        lvProducts.getSelectionModel().clearSelection();
        txtName.clear();
        cmbCategory.setValue(null);
        txtPurchasePrice.clear();
        txtSalePrice.clear();
        spinQuantity.getValueFactory().setValue(1);
        lblTotalCost.setText("Coût total: 0.00 €");
    }

    @FXML
    void onPurchase() {
        try {
            Product selected = lvProducts.getSelectionModel().getSelectedItem();
            String name = txtName.getText();
            Category category = cmbCategory.getValue();
            double purchasePrice = Double.parseDouble(txtPurchasePrice.getText());
            double salePrice = Double.parseDouble(txtSalePrice.getText());
            int quantity = spinQuantity.getValue();

            if (name.isEmpty() || category == null) {
                showAlert("Erreur", "Veuillez remplir tous les champs");
                return;
            }

            if (selected == null) {
                Product p = new Product(category, name, purchasePrice, salePrice, false, quantity);
                productService.registerProduct(p);
                showAlert("Succès", "Produit acheté et ajouté au stock!");
            } else {
                selected.setStock(selected.getStock() + quantity);
                productService.updateProductDetails(selected);
                showAlert("Succès", quantity + " unités ajoutées au stock!");
            }

            onReset();
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Prix ou quantité invalide");
        }
    }

    private void displayProductDetails(Product p) {
        if (p != null) {
            txtName.setText(p.getName());
            cmbCategory.setValue(p.getCategory());
            txtPurchasePrice.setText(String.valueOf(p.getPurchasePrice()));
            txtSalePrice.setText(String.valueOf(p.getSalePrice()));
            updateTotalCost();
        }
    }

    private void updateTotalCost() {
        try {
            double price = Double.parseDouble(txtPurchasePrice.getText());
            int quantity = spinQuantity.getValue();
            lblTotalCost.setText(String.format("Coût total: %.2f €", price * quantity));
        } catch (NumberFormatException e) {
            lblTotalCost.setText("Coût total: 0.00 €");
        }
    }

    @Override
    public void initData() {
        loadCategories(cmbCategory);
        UIUtils.setupComboBoxDisplay(cmbCategory, Category::getName);
        UIUtils.setupListViewDisplay(lvProducts, p -> p.getName() + " (" + p.getCategory().getName() + ") - Stock: " + p.getStock());

        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1);
        spinQuantity.setValueFactory(valueFactory);

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

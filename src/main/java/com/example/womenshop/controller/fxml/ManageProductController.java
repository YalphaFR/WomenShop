package com.example.womenshop.controller.fxml;

import com.example.womenshop.controller.base.ModuleController;
import com.example.womenshop.model.Accessory;
import com.example.womenshop.model.Category;
import com.example.womenshop.model.Clothing;
import com.example.womenshop.model.Shoes;
import com.example.womenshop.model.base.Product;

import com.example.womenshop.util.ParserUtil;
import com.example.womenshop.util.UIUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;


import java.util.ArrayList;
import java.util.List;

public class ManageProductController extends ModuleController {

    @FXML private Label lblID;
    @FXML private ListView<Product> lvProducts;
    @FXML private TextField txtName, txtSalePrice, txtPurchasePrice, txtStock, txtSaleDiscountedPrice, txtSize;
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
            txtSaleDiscountedPrice.setText(String.valueOf(p.getSalePriceDiscounted()));
            txtStock.setText(String.valueOf(p.getStock()));
            cmbIsDiscounted.setValue(p.isDiscounted());

            if (p instanceof Clothing c) {
                txtSize.setText(String.valueOf(c.getSize()));
            } else if (p instanceof Shoes sh) {
                txtSize.setText(String.valueOf(sh.getSize()));
            } else {
                txtSize.setText(String.valueOf(0));
            }
        }
    }

    @FXML
    private void onDelete() {
        Product selected = lvProducts.getSelectionModel().getSelectedItem();

        if (selected == null) return;

        if (selected.getStock() > 0) {
            showAlert("Error", "Product cannot be deleted. Stock still available");
            return;
        }
        productService.removeProduct(selected.getId());
        fetchProducts(lvProducts);
        showAlert("Success :", "Deleted product");
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

            Double salePrice = ParserUtil.parseDoubleOrNull(txtSalePrice.getText());
            if (salePrice < 0) {
                showAlert("Error", "Sale price must be a positive number");
            }

            Double purchasePrice = ParserUtil.parseDoubleOrNull(txtPurchasePrice.getText());
            if (purchasePrice < 0) {
                showAlert("Error", "Purchase price must be a positive number");
            }

            if (purchasePrice > salePrice) {
                showAlert("Error", "Purchase price cannot be greater than sale");
            }

            Double saleDiscountedPrice = ParserUtil.parseDoubleOrNull(txtSaleDiscountedPrice.getText());
            boolean isDiscounted = cmbIsDiscounted.getValue();
            Integer size = ParserUtil.parseIntOrNull(txtSize.getText());

            if (selected == null) {
                Product p = null;
                switch (category.getName()) {
                    case "Clothing":
                        if (!(34 <= size && size <= 54)) {
                            showAlert("Error", "Clothing size must be between 34 and 54");
                            return;
                        }

                        p = new Clothing(category,name, purchasePrice, salePrice, saleDiscountedPrice, isDiscounted, 0, size);
                        break;
                    case "Shoes":
                        if (!(36 <= size && size <= 50)) {
                            showAlert("Error", "Clothing size must be between 36 and 50");
                            return;
                        }

                        p = new Shoes(category,name, purchasePrice, salePrice, saleDiscountedPrice, isDiscounted, 0, size);
                        break;
                    case "Accessory":
                        p = new Accessory(category,name, purchasePrice, salePrice, saleDiscountedPrice, isDiscounted, 0);
                        break;

                }
                productService.registerProduct(p);
                showAlert("Success :", "Registered product");
            } else {
                Integer stock = ParserUtil.parseIntOrNull(txtStock.getText());
                selected.setName(name);
                selected.setCategory(category);
                selected.setSalePrice(salePrice);
                selected.setPurchasePrice(purchasePrice);
                selected.setStock(stock);
                selected.setSalePriceDiscounted(saleDiscountedPrice);
                selected.setDiscounted(isDiscounted);

                if (selected instanceof Clothing c) {
                    c.setSize(size);
                } else if (selected instanceof Shoes sh) {
                    sh.setSize(size);
                }

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
        txtSaleDiscountedPrice.clear();
        cmbIsDiscounted.setValue(null);
        txtSize.clear();

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

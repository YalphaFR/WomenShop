package com.example.womenshop.controller.fxml;

import com.example.womenshop.controller.base.ModuleController;
import com.example.womenshop.model.Category;
import com.example.womenshop.model.Product;
import com.example.womenshop.model.Transaction;
import com.example.womenshop.util.UIUtils;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class DisplayProductController extends ModuleController {
    @FXML private Button btnExit, btnReset, btnSearch;
    @FXML private ComboBox<Category> cmbCategory;
    @FXML private TableColumn<Product, Boolean> colDiscounted;
    @FXML private TableColumn<Product, String> colProductsCategoryName;
    @FXML private TableColumn<Product, Double> colProductsFinalPriceDiscounted;
    @FXML private TableColumn<Product, Integer> colProductsId;
    @FXML private TableColumn<Product, String> colProductsName;
    @FXML private TableColumn<Product, Double> colProductsPurchasePrice;
    @FXML private TableColumn<Product, Double> colProductsSalePrice;
    @FXML private TableColumn<Product, Integer> colProductsStock;
    @FXML private TableView<Product> tvProducts;
    @FXML private TextField txtID;
    @FXML private TextField txtName;


    // EVENTS

    @FXML
    void onReset(ActionEvent event) {
        txtID.clear();
        txtName.clear();
        cmbCategory.setValue(null);
        setupTableView();
    }

    @FXML
    void onSearch() {
        try {
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
            tvProducts.setItems(FXCollections.observableArrayList(filtered));
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Prix ou quantité invalide");
        }
    }

    private void displayProductDetails(Product p) {
        if (p != null) {
            txtID.setText(String.valueOf(p.getId()));
            txtName.setText(p.getName());
            cmbCategory.setValue(p.getCategory());
        }
    }

    private void setupListeners() {
        // Events listener
        tvProducts.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldProduct, selectedProduct) -> {
                    if (selectedProduct != null) {
                        displayProductDetails(selectedProduct);
                    }
                });

    }

    @Override
    public void initData() {

        UIUtils.setupComboBoxDisplay(cmbCategory, Category::getName); // on change l'affichage du comboxBox pour les catégories | on affiche uniquement le nom

        loadComboBox(cmbCategory, categoryService.listAllCategories());

        setupTableColumns();
        setupTableView();
        setupListeners();

    }
    private void setupTableView() {
        ObservableList<Product> products = FXCollections.observableArrayList(productService.listAllProducts());
        tvProducts.setItems(products);
    }

    private void setupTableColumns() {
        colProductsId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProductsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colProductsPurchasePrice.setCellValueFactory(new PropertyValueFactory<>("purchasePrice"));
        colProductsSalePrice.setCellValueFactory(new PropertyValueFactory<>("salePrice"));
        colProductsStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colDiscounted.setCellValueFactory(new PropertyValueFactory<>("discounted"));

        // Récupérer le nom de la catégorie (objet imbriqué)
        colProductsCategoryName.setCellValueFactory(cellData -> {
            Product p = cellData.getValue();
            return new SimpleStringProperty(
                    p != null && p.getCategory() != null
                            ? p.getCategory().getName()
                            : "N/A"
            );
        });

        colProductsFinalPriceDiscounted.setCellValueFactory(new PropertyValueFactory<>("finalSalePrice"));
    }



}

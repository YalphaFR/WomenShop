package com.example.womenshop.controller.fxml;

import com.example.womenshop.controller.base.ModuleController;
import com.example.womenshop.model.Category;
import com.example.womenshop.model.Product;
import com.example.womenshop.util.UIUtils;
import javafx.collections.FXCollections;
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


    // EVENTS

    @FXML
    void onReset(ActionEvent event) {
        lvProducts.getSelectionModel().clearSelection();
        txtID.clear();
        txtName.clear();
        cmbCategory.setValue(null);

        fetchProducts(lvProducts);
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
        }
    }

    private void setupListeners() {
        // Events listener
        lvProducts.getSelectionModel().selectedItemProperty().addListener(
                e -> displayProductDetails(lvProducts.getSelectionModel().getSelectedItem())
        );
    }

    @Override
    public void initData() {

        UIUtils.setupComboBoxDisplay(cmbCategory, Category::getName); // on change l'affichage du comboxBox pour les catégories | on affiche uniquement le nom
        UIUtils.setupListViewDisplay(lvProducts, p -> p.getName() + " (" + p.getCategory().getName() + ")" + ", Purchase Price : " + p.getPurchasePrice()  + "$ , Sale Price : " + p.getFinalSalePrice() + "$ , Stock : " + p.getStock() + ", Discounted : " + p.isDiscounted());

        loadComboBox(cmbCategory, categoryService.listAllCategories());
        fetchProducts(lvProducts);

        setupListeners();

    }


}

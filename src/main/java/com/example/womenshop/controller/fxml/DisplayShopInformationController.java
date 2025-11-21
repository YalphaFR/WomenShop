package com.example.womenshop.controller.fxml;

import com.example.womenshop.controller.base.ModuleController;
import com.example.womenshop.model.Product;
import com.example.womenshop.model.Transaction;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;
import java.util.List;

public class DisplayShopInformationController extends ModuleController {

    @FXML private Button btnExit, btnRefresh;
    @FXML private Label lblTotalStock, lblStockValue, lblTotalSales, lblTotalPurchases, lblNetProfit;
    @FXML private TableView<Transaction> tvTransactions;
    @FXML private TableColumn<Transaction, Integer> colTransactionId;
    @FXML private TableColumn<Transaction, String> colProductName;
    @FXML private TableColumn<Transaction, String> colType;
    @FXML private TableColumn<Transaction, Integer> colQuantity;
    @FXML private TableColumn<Transaction, Double> colAmount;
    @FXML private TableColumn<Transaction, LocalDateTime> colDate;

    @FXML
    void onRefresh() {
        initData();
    }

    @Override
    public void initData() {
        calculateStatistics();
    }

    private void calculateStatistics() {
        List<Product> products = productService.listAllProducts();

        if (products == null || products.isEmpty()) {
            lblTotalStock.setText("0");
            lblStockValue.setText("0.00 €");
            lblTotalSales.setText("0.00 €");
            lblTotalPurchases.setText("0.00 €");
            lblNetProfit.setText("Bénéfice net: 0.00 €");
            return;
        }

        int totalStock = products.stream()
                .mapToInt(Product::getStock)
                .sum();
        lblTotalStock.setText(String.valueOf(totalStock));

        double stockValue = products.stream()
                .mapToDouble(p -> p.getPurchasePrice() * p.getStock())
                .sum();
        lblStockValue.setText(String.format("%.2f €", stockValue));

        lblTotalSales.setText("0.00 €");
        lblTotalPurchases.setText("0.00 €");
        lblNetProfit.setText("Bénéfice net: 0.00 €");
        lblNetProfit.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2196F3;");
    }

    private void setupTableColumns() {
        colTransactionId.setCellValueFactory(new PropertyValueFactory<>("transactionsId"));
        colType.setCellValueFactory(new PropertyValueFactory<>("transactionsType"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("transactionsQuantity"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("transactionsAmount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("transactionsDate"));

        colProductName.setCellValueFactory(cellData -> {
            int productId = cellData.getValue().getId();
            Product product = productService.findProductById(productId);
            return new javafx.beans.property.SimpleStringProperty(
                    product != null ? product.getName() : "N/A"
            );
        });
    }
}

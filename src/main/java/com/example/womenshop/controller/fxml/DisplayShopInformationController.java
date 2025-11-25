package com.example.womenshop.controller.fxml;

import com.example.womenshop.controller.base.ModuleController;
import com.example.womenshop.model.base.Product;
import com.example.womenshop.model.Transaction;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;
import java.util.List;

public class DisplayShopInformationController extends ModuleController {

    @FXML private Button btnExit, btnRefresh;
    @FXML private Label lblTotalStock, lblStockPurchaseValue, lblStockSellValue, lblTotalSales, lblTotalPurchases, lblNetProfit, lblShopInitialCapital, lblShopCurrentCapital;
    @FXML private TableView<Transaction> tvTransactions;
    @FXML private TableColumn<Transaction, Integer> colTransactionId;
    @FXML private TableColumn<Transaction, String> colProductName;
    @FXML private TableColumn<Transaction, String> colType;
    @FXML private TableColumn<Transaction, Integer> colQuantity;
    @FXML private TableColumn<Transaction, Double> colAmount;
    @FXML private TableColumn<Transaction, LocalDateTime> colDate;

    // optionnel car à chaque fois que la page est montré, on refresh automatiquement
    @FXML
    void onRefresh() {
        calculateStatistics();
        setupTableView();
    }

    @Override
    public void initData() {
        calculateStatistics();

        setupTableColumns();
        setupTableView();
    }

    private void setupTableView() {
        ObservableList<Transaction> transactions = FXCollections.observableArrayList(transactionService.listAllTransactions());
        tvTransactions.setItems(transactions);
    }

    private void calculateStatistics() {
        List<Product> products = productService.listAllProducts();

        if (products == null || products.isEmpty()) {
            lblTotalStock.setText("0");
            lblStockPurchaseValue.setText("0.00 €");
            lblStockSellValue.setText("0.00 €");
            lblTotalSales.setText("0.00 €");
            lblTotalPurchases.setText("0.00 €");
            lblNetProfit.setText("Net profit: 0.00 €");
            return;
        }

        int totalStock = products.stream()
                .mapToInt(Product::getStock)
                .sum();
        lblTotalStock.setText(String.valueOf(totalStock));

        double stockPurchaseValue = products.stream()
                .mapToDouble(p -> p.getPurchasePrice() * p.getStock())
                .sum();
        lblStockPurchaseValue.setText(String.format("%.2f €", stockPurchaseValue));

        double stockSellValue = products.stream()
                .mapToDouble(p -> p.getFinalSalePrice() * p.getStock())
                .sum();
        lblStockSellValue.setText(String.format("%.2f €", stockSellValue));


        List<Transaction> transactions = transactionService.listAllTransactions();

        double totalSales = transactions.stream()
                .filter(t -> t.getType().equals(Transaction.TransactionType.SALE))
                .mapToDouble(Transaction::getAmount)
                .sum();
        lblTotalSales.setText(String.format("%.2f €", totalSales));

        double totalPurchases = transactions.stream()
                .filter(t -> t.getType().equals(Transaction.TransactionType.PURCHASE))
                .mapToDouble(Transaction::getAmount)
                .sum();
        lblTotalPurchases.setText(String.format("%.2f €", totalPurchases));

        lblNetProfit.setText(String.format("Net profit: %.2f €", totalSales - totalPurchases));
        //lblNetProfit.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2196F3;");

        lblShopInitialCapital.setText(String.format("%.2f €", shopService.getInitialCapital()));
        lblShopCurrentCapital.setText(String.format("%.2f €", shopService.getCurrentCapital()));
    }

    private void setupTableColumns() {
        colTransactionId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        colProductName.setCellValueFactory(cellData -> {
            Product product = cellData.getValue().getProduct(); // prends directement le produit
            return new SimpleStringProperty(
                    (product != null && product.getCategory() != null)
                            ? product.getName()
                            : "N/A"
            );
        });
    }
}

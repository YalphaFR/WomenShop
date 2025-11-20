package com.example.womenshop.controller.fxml;

import com.example.womenshop.SceneManager;
import com.example.womenshop.controller.base.ModuleController;
import com.example.womenshop.model.Product;
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
    private TextField txtID, txtName, txtCategory;

    @FXML
    private Label txtPrice, txtStockAvailable;

    @FXML
    void onExit(ActionEvent event) {

    }

    @FXML
    void onReset(ActionEvent event) {

    }

    @FXML
    void onSave(ActionEvent event) {

    }

    @FXML
    void onSearch(ActionEvent event) {

    }


    @Override
    public void initData() {

    }
}

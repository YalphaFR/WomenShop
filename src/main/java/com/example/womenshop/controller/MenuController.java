package com.example.womenshop.controller;

import com.example.womenshop.SceneManager;
import com.example.womenshop.model.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class MenuController implements SceneAwareController {
    @FXML
    private Button btnDisplayProduct, btnDisplayShopInformation, btnManageProduct, btnPurchaseProduct, btnSellProduct;

    private SceneManager sceneManager;

    @Override
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @FXML
    private void onDisplayProduct() {
        sceneManager.show("DisplayProduct");
    }

    @FXML
    private void onManageProduct() {
        sceneManager.show("ManageProduct");
    }

    @FXML
    private void onSellProduct() {
        sceneManager.show("SellProduct");
    }

    @FXML
    private void onPurchaseProduct() {
        sceneManager.show("PurchaseProduct");
    }

    @FXML
    private void onDisplayShopInformation() {
        sceneManager.show("DisplayShopInformation");
    }
}

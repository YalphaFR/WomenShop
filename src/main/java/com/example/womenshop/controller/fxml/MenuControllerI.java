package com.example.womenshop.controller.fxml;

import com.example.womenshop.SceneManager;
import com.example.womenshop.controller.ISceneAwareController;
import com.example.womenshop.controller.base.BaseController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class MenuControllerI extends BaseController {
    @FXML
    private Button btnDisplayProduct, btnDisplayShopInformation, btnManageProduct, btnPurchaseProduct, btnSellProduct;

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

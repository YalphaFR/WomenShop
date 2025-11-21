package com.example.womenshop;

import com.example.womenshop.controller.fxml.*;
import com.example.womenshop.dao.DBManager;
import com.example.womenshop.repository.mysql.MySQLCategoryRepository;
import com.example.womenshop.repository.mysql.MySQLProductRepository;
import com.example.womenshop.service.CategoryService;
import com.example.womenshop.service.ProductService;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneManager sceneManager = new SceneManager(primaryStage);
        DBManager db =  new DBManager();

        sceneManager.loadAndInitScene(
                "DisplayProduct",
                "/com/example/womenshop/DisplayProduct.fxml",
                DisplayProductController.class,
                controller -> {
                    controller.setSceneManager(sceneManager);
                    controller.setCategoryService(new CategoryService(new MySQLCategoryRepository(db)));
                    controller.setProductService(new ProductService(new MySQLProductRepository(db)));
                }
        );

        sceneManager.loadAndInitScene(
                "DisplayShopInformation",
                "/com/example/womenshop/DisplayShopInformation.fxml",
                DisplayShopInformationController.class,
                controller -> {
                    controller.setSceneManager(sceneManager);
                    controller.setCategoryService(new CategoryService(new MySQLCategoryRepository(db)));
                    controller.setProductService(new ProductService(new MySQLProductRepository(db)));
                }
        );

        sceneManager.loadAndInitScene(
                "ManageProduct",
                "/com/example/womenshop/ManageProduct.fxml",
                ManageProductController.class,
                controller -> {
                    controller.setSceneManager(sceneManager);
                    controller.setCategoryService(new CategoryService(new MySQLCategoryRepository(db)));
                    controller.setProductService(new ProductService(new MySQLProductRepository(db)));
                }
        );

        sceneManager.loadAndInitScene(
                "Menu",
                "/com/example/womenshop/Menu.fxml",
                MenuControllerI.class,
                controller -> {
                    controller.setSceneManager(sceneManager);
                }
        );

        sceneManager.loadAndInitScene(
                "PurchaseProduct",
                "/com/example/womenshop/PurchaseProduct.fxml",
                PurchaseProductController.class,
                controller -> {
                    controller.setSceneManager(sceneManager);
                    controller.setCategoryService(new CategoryService(new MySQLCategoryRepository(db)));
                    controller.setProductService(new ProductService(new MySQLProductRepository(db)));
                }
        );

        sceneManager.loadAndInitScene(
                "SellProduct",
                "/com/example/womenshop/SellProduct.fxml",
                SellProductController.class,
                controller -> {
                    controller.setSceneManager(sceneManager);
                    controller.setCategoryService(new CategoryService(new MySQLCategoryRepository(db)));
                    controller.setProductService(new ProductService(new MySQLProductRepository(db)));
                }
        );

        sceneManager.show("Menu");
        primaryStage.setTitle("Women Shop");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
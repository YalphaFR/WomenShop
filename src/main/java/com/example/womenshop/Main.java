package com.example.womenshop;

import com.example.womenshop.controller.fxml.*;

import com.example.womenshop.dao.DBManager;
import com.example.womenshop.repository.mysql.MySQLCategoryRepository;
import com.example.womenshop.repository.mysql.MySQLProductRepository;
import com.example.womenshop.repository.mysql.MySQLShopRepository;
import com.example.womenshop.repository.mysql.MySQLTransactionRepository;
import com.example.womenshop.service.CategoryService;
import com.example.womenshop.service.ProductService;
import com.example.womenshop.service.ShopService;
import com.example.womenshop.service.TransactionService;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneManager sceneManager = new SceneManager(primaryStage);
        DBManager db =  new DBManager();

        // Les repositories
        MySQLCategoryRepository categoryRepository = new MySQLCategoryRepository(db);
        MySQLProductRepository productRepository = new MySQLProductRepository(db);
        MySQLTransactionRepository transactionRepository = new MySQLTransactionRepository(db);
        MySQLShopRepository  shopRepository = new MySQLShopRepository(db);

        // les services
        CategoryService categoryService = new CategoryService(categoryRepository);
        ProductService productService = new ProductService(productRepository);
        TransactionService transactionService = new TransactionService(transactionRepository, productRepository);
        ShopService shopService = new ShopService(shopRepository, transactionRepository);


        sceneManager.loadAndInitScene(
                "DisplayProduct",
                "/com/example/womenshop/DisplayProduct.fxml",
                DisplayProductController.class,
                controller -> {
                    controller.setSceneManager(sceneManager);
                    controller.setCategoryService(categoryService);
                    controller.setProductService(productService);
                    controller.setShopService(shopService);
                }
        );

        sceneManager.loadAndInitScene(
                "DisplayShopInformation",
                "/com/example/womenshop/DisplayShopInformation.fxml",
                DisplayShopInformationController.class,
                controller -> {
                    controller.setSceneManager(sceneManager);
                    controller.setCategoryService(categoryService);
                    controller.setProductService(productService);
                    controller.setTransactionService(transactionService);
                    controller.setShopService(shopService);
                }
        );

        sceneManager.loadAndInitScene(
                "ManageProduct",
                "/com/example/womenshop/ManageProduct.fxml",
                ManageProductController.class,
                controller -> {
                    controller.setSceneManager(sceneManager);
                    controller.setCategoryService(categoryService);
                    controller.setProductService(productService);
                    controller.setShopService(shopService);
                }
        );

        sceneManager.loadAndInitScene(
                "Menu",
                "/com/example/womenshop/Menu.fxml",
                MenuController.class,
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
                    controller.setCategoryService(categoryService);
                    controller.setProductService(productService);
                    controller.setTransactionService(transactionService);
                    controller.setShopService(shopService);
                }
        );

        sceneManager.loadAndInitScene(
                "SellProduct",
                "/com/example/womenshop/SellProduct.fxml",
                SellProductController.class,
                controller -> {
                    controller.setSceneManager(sceneManager);
                    controller.setCategoryService(categoryService);
                    controller.setProductService(productService);
                    controller.setTransactionService(transactionService);
                    controller.setShopService(shopService);
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
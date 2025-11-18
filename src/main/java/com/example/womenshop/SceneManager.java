package com.example.womenshop;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class SceneManager {

    private final Stage primaryStage;
    private final Map<String, FXMLLoader> loaders = new HashMap<>();

    public SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Charge un FXML et le stocke pour réutilisation.
     * @param name clé pour identifier la scène
     * @param fxmlPath chemin du fichier FXML
     * @throws IOException
     */
    public void loadScene(String name, String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        loader.load();  // charge le FXML et initialise le controller
        loaders.put(name, loader);
    }

    /**
     * Encapsule le chargement fichier FXML, chargement des services et initialisation des données
     * @param key Clé pour identifier la scène
     * @param fxmlPath chemin du fichier FXML
     * @param clazz class du controller qui permet de cast le controller récupéré au type correct
     * @param initializer appliquer une fonction sur le controller chargé
     * @return
     * @param <T> le controller en question
     * @throws IOException
     */
    public <T> T loadAndInitScene(String key, String fxmlPath, Class<T> clazz, Consumer<T> initializer) throws IOException {
        loadScene(key, fxmlPath);           // charge le FXML
        T controller = getController(key, clazz); // récupère le controller
        initializer.accept(controller);     // applique les injections
        return controller;
    }



    /**
     * Affiche une scène déjà chargée
     * @param key clé de la scène
     */
    public void show(String key) {
        FXMLLoader loader = loaders.get(key);
        if (loader == null) throw new RuntimeException("Scene " + key + " not loaded!");

        Parent root = loader.getRoot();

        Scene scene = primaryStage.getScene();
        if (scene == null) {
            primaryStage.setScene(new Scene(root)); // première fois
        } else {
            scene.setRoot(root); // réutilise la scène existante
        }

        primaryStage.show();
    }


    /**
     * Récupère le controller pour injection DI
     */
    public <T> T getController(String name, Class<T> clazz) {
        FXMLLoader loader = loaders.get(name);
        if (loader == null) throw new RuntimeException("Scene " + name + " not loaded!");
        return loader.getController();
    }
}

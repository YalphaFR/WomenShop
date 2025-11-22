package com.example.womenshop.controller.base;

import com.example.womenshop.SceneManager;
import com.example.womenshop.controller.IRefreshableController;
import com.example.womenshop.controller.ISceneAwareController;
import javafx.scene.control.Alert;

abstract public class BaseController implements ISceneAwareController {
    protected SceneManager sceneManager;

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    protected void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

package com.example.womenshop.controller.base;

import com.example.womenshop.controller.ISceneAwareController;
import javafx.fxml.FXML;

abstract public class ModuleController extends BaseController implements ISceneAwareController {

    public abstract void initData();

    @FXML
    public void onExit() {
        sceneManager.show("Menu");
    }


}

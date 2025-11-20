package com.example.womenshop.controller.base;

import com.example.womenshop.SceneManager;
import com.example.womenshop.controller.IRefreshableController;
import com.example.womenshop.controller.ISceneAwareController;

abstract public class BaseController implements ISceneAwareController {
    protected SceneManager sceneManager;

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
}

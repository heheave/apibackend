package com.sjtu.pojo;

import java.util.List;
import java.util.Map;

/**
 * Created by xiaoke on 17-11-21.
 */
public class AppScene {

    private App app;

    private Scene scene;

    private List<Pointer> pointer;

    private Map<Integer, Point> dots;

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public List<Pointer> getPointer() {
        return pointer;
    }

    public void setPointer(List<Pointer> pointer) {
        this.pointer = pointer;
    }

    public Map<Integer, Point> getDots() {
        return dots;
    }

    public void setDots(Map<Integer, Point> dots) {
        this.dots = dots;
    }
}

package com.javafx.wkwk;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Belalang extends Enemy {
        
    public Belalang(int x, int y, int lane) {
        super(x, y, 100, 100, 10, 3, lane, 20);
        this.path = new Image("/com/javafx/wkwk/assets/BelalangSheet.png");
        this.kecepatan = (float) 0.05;
    }
}

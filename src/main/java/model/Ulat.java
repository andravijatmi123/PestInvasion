package com.javafx.wkwk;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
//import static java.util.Spliterators.iterator;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Ulat extends Enemy {

    public Ulat(int x, int y, int lane) {
        super(x, y, 100, 100, 5, 1, lane, 10);
        this.path = new Image("/com/javafx/wkwk/assets/UlatSheet.png");
        this.kecepatan = (float) 0.08;
    }
}


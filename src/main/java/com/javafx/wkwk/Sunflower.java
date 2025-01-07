package com.javafx.wkwk;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;


public class Sunflower extends Plant {
    transient private Timeline sunProducer;
    
    public Sunflower(int x, int y, int col, int row) {
        super(x, y, 90, 75, 3, col, row, 50);
        this.path = new Image("/com/javafx/wkwk/assets/SunflowerSheet.png");
    }
    
    @Override
    public void attack(AnchorPane pane){
        produceSun(pane);
    }
    
    public void produceSun(AnchorPane pane){
        //buat timeline agar sunflower bisa menghasilkan matahari per 10 detik
        sunProducer = new Timeline(new KeyFrame(Duration.seconds(10), event -> {
            Bounds bounds = img.localToScene(img.getBoundsInLocal());
            int sunX = (int) bounds.getMinX() ; // Posisi X di dekat Sunflower
            int sunY = (int) bounds.getMinY() + 20; // Posisi Y di dekat Sunflower
//            int sunX = (int) img.getLayoutX() + 20; // Posisi X di dekat Sunflower
//            int sunY = (int) img.getLayoutY() + 20; // Posisi Y di dekat Sunflower
            
            System.out.println("Sunflower menghasilkan matahari di:" + sunX + ", " + sunY);
            
            //buat matahari
            Sun sun = new Sun(sunX,sunY,50,50,false);
            sun.makeImage(pane);
            System.out.println("Sun created at: (" + sunX + ", " + sunY + ")");
        }));
        
        sunProducer.setCycleCount(Timeline.INDEFINITE);//agar berulang terus menerus
        sunProducer.play();
        
        this.sunProducer = sunProducer;
        GamePlayController.animationTimelines.add(sunProducer);
    }
    
    public Timeline getTimeline(){
        return this.sunProducer;
    }
    
    
    public void onDeath() {
        if (hp <= 0) {
            System.out.println("Sunflower at (" + col + ", " + row + ") has died.");
            
            sunProducer.stop(); // Hentikan timeline matahari
            GamePlayController.allPlants.remove(this.sunProducer); // Hapus dari daftar tanaman
            
            if (img != null) {
            img.setVisible(false); // Sembunyikan gambar tanaman
            img.setDisable(true);
            }
            
        }
    }
    
    
}

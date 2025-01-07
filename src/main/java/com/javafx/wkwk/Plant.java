package com.javafx.wkwk;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 *
 * @author Asus
 */
public abstract class Plant extends GameElements {
    protected int hp; // Hit Points (nyawa)
    protected int col;
    protected int row;
    protected int cost; // Biaya dalam matahari
    
    private static final int FRAME_WIDTH = 100;  // Adjust as per your sprite size
    private static final int FRAME_HEIGHT = 100; // Adjust as per your sprite size

    // Starting position of the desired character (e.g., karakter baris ke-5 )
    private static final int CHARACTER_START_X = 0; // X position of the first frame
    private static final int CHARACTER_START_Y = 0; // baris ke 5 berada di koordinat 350

    private static final int FRAME_COUNT = 6; // Number of walking frames, sesuaikan dengan jumlah gerakan
    private static final int ANIMATION_DURATION = 300; // Duration per frame in milliseconds, semakin lama semakin lambat

    
    private static final double MOVEMENT_SPEED = 0; // Untuk mengatur kecepatan maju

    public Plant(int x, int y, int Width, int height, int hp,int col,int row,int cost) {
        super(x, y, Width, height);
        this.hp = hp;
        this.col = col;
        this.row = row;
        this.cost = cost;
    }

    
    //method load image
    public void makeImage(GridPane lawn){
        img = new ImageView(path);
        lawn.add(img,col,row,1,1);
        img.setFitWidth(FRAME_WIDTH);  // Fixed display size
        img.setFitHeight(FRAME_HEIGHT); // Fixed display size
        img.setX(0);  // Keep the character in a fixed X coordinate
        img.setY(0);  // Keep the character in a fixed Y coordinate
        

        // Create a Timeline for animating the frames
        Timeline timeline = new Timeline();
        for (int i = 0; i < FRAME_COUNT; i++) {
            int frameX = CHARACTER_START_X + i * (FRAME_WIDTH + 0); // Add gap offset if necessary
            KeyFrame keyFrame = new KeyFrame(
                Duration.millis(i * ANIMATION_DURATION),
                e -> img.setViewport(new Rectangle2D(frameX, CHARACTER_START_Y, FRAME_WIDTH, FRAME_HEIGHT))
            );
            timeline.getKeyFrames().add(keyFrame);
        }
         // Set the animation to loop
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    
    //method menyerang
    public void attack(AnchorPane p){
        
    }
    
    public void onDeath() {
  
    }

    
    public int getHP(){
        return this.hp;
    }
    
    //consturctor hp, dan logika jika hp plant habis
    public void setHp(int hp){
        this.hp = hp;
        if(this.hp<=0){
            GamePlayController.allPlants.remove(this);
            img.setVisible(false);
            img.setDisable(true);
        }
    }
    
    public void endAnimation(Timeline t){
        t.stop();
    }

    int getHp() {
        return this.hp;
    }

    public ImageView getImg() {
        return img;
    }
    
    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }   
}

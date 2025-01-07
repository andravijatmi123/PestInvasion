package com.javafx.wkwk;

import java.io.Serializable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 *
 * @author Asus
 */
public abstract class GameElements implements Serializable{
    protected int x;
    protected int y;
    protected Image path;
    protected ImageView img;
    protected int width;
    protected int height;
    
    public GameElements(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height; 
    }
    
    //method buat gambar
    public void makeImage(AnchorPane pane) {

        this.img = new ImageView(path);
                if (img == null) {
            System.err.println("Error: ImageView is null. Cannot add to Pane.");
            return;
        }
        img.setFitWidth(width);  // Lebar gambar
        img.setFitHeight(height);
        this.img.setX(x);
        this.img.setY(y);
        pane.getChildren().add(img);
        System.out.println("Image added to Pane at: (" + x + ", " + y + ")");
    }
    
    public int getX(){
        return this.x;
    }
    
    public void setX(int x){
        this.x = x;
        img.setX(x);
    }
    
    public int getY(){
        return this.y;
    }
    
    
    
    public void setY(int y) {
        this.y = y;
        if (img != null) {
            img.setY(y); // Atur posisi Y pada ImageView
//            System.out.println("Sun Y position updated to: " + y);
        }
    }

}

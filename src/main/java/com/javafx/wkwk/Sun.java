package com.javafx.wkwk;

import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class Sun extends GameElements {
    
    private final int sunValue = 50; //nilai 1 buah matahari
    private boolean claimed = false;
    
    private static int DROP_POSITION;
    private static final int DROP_SPEED_MS = 12;

    private final int timeouttime;
    
    private static MediaPlayer musikClaim = new MediaPlayer(new Media(GamePlayController.class.getResource("/com/javafx/wkwk/sound/claim.mp3").toString()));
    
    public Sun(int x, int y, int width, int height, boolean fallingSun) {
        super(x, y, width, height);
        this.path = new Image("/com/javafx/wkwk/assets/sun.png");
        
        if (fallingSun) {
            timeouttime = 14000; // 14 detik untuk matahari jatuh
        } else {
            timeouttime = 5000; // 5 detik untuk matahari tetap
        }

        System.out.println("Sun initialized at: (" + x + ", " + y + ")");
        disappearAfterTime();
    }
    
    private void setupClickEvent(){
        img.setOnMouseClicked((MouseEvent event) -> {
            if(!claimed){
                claimed = true; // Tandai matahari sudah diklaim
                playMusic("claim");// Play the music
                System.out.println("Sun claimed! Adding " + sunValue + " sun points.");
                GamePlayController.updateSunPoints(sunValue); // Tambahkan poin matahari
                img.setVisible(false); // Hilangkan matahari dari layar
                img.setDisable(true); // Nonaktifkan interaksi lebih lanjut
            }
        });
    }
    

    public void disappearAfterTime() {
        // Menggunakan JavaFX Platform untuk memastikan eksekusi pada thread UI
        
//        if (this.img == null) {
//            System.err.println("Error: ImageView is null. Cannot schedule disappearance.");
//            return;
//        }
        
        
        PauseTransition pause = new PauseTransition(Duration.millis(timeouttime));
        pause.setOnFinished(event -> {
            img.setVisible(false);
            img.setDisable(true);
            System.out.println("Sun disappeared after timeout.");
        });
        pause.play();
        
    }

    
    
    public void makeImage(AnchorPane root) {
//        if (root == null) {
//            System.err.println("Error: AnchorPane root is null. Cannot add image.");
//            return;
//        }
//
//        if (this.img != null) {
            
            this.img = new ImageView(path);
            root.getChildren().add(this.img);
            System.out.println("Sun image added to root.");
            this.img.setY(y);
            this.img.setX(x);
            setupClickEvent(); // Tambahkan event handler klik
            System.out.println("Sun initialized at: (" + x + ", " + y + ")");
            
//        } else {
//            System.err.println("Error: ImageView is null. Cannot add to root.");
//        }
    }
    
    


     // Gerakan jatuh matahari (incremental)
//    public void moveSun() {
//        if (this.img != null && getY() <= MAX_DROP_POSITION) {
//            setY(getY() + 1);
//        } else if (this.img == null) {
//            System.err.println("Error: ImageView is null. Cannot move sun.");
//        }
//    }

    // Animasi jatuh matahari
    public void dropSun(Random rand) {
//        if (this.img == null) {
//            System.err.println("Error: ImageView is null. Cannot start animation.");
//            return;
//        }

        System.out.println("Sun tersedia. posisi: (" + getX() + ", " + getY() + ")");
        DROP_POSITION = rand.nextInt(480)+ 100;
        // Animasi Timeline untuk menggerakkan matahari dari atas ke bawah
        Timeline animation = new Timeline(new KeyFrame(Duration.millis(25), e -> {
            if (getY() < DROP_POSITION) {
                setY(getY() + 1); // Menambah posisi Y secara bertahap
            }
        }));

        /// Tentukan jumlah siklus animasi berdasarkan jarak ke MAX_DROP_POSITION
        int totalSteps = (DROP_POSITION - getY()) / 2;
        animation.setCycleCount(totalSteps);

        // Event setelah animasi selesai
        animation.setOnFinished(e -> {
            GamePlayController.animationTimelines.remove(animation);
            System.out.println("Sun has finished dropping.");
        });

        animation.play(); // Memulai animasi
        GamePlayController.animationTimelines.add(animation); // Menyimpan animasi di GamePlayController
    }

    private void playMusic(String tipe){
    if(tipe.equals("claim")){
        musikClaim.seek(Duration.ZERO);
        musikClaim.setAutoPlay(true);  // Memutar musik otomatis
        musikClaim.setCycleCount(1);  // Musik berulang terus menerus
        musikClaim.play();
        }
    }

    
    
    
}

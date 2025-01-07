package com.javafx.wkwk;

import java.net.URL;
import java.util.Iterator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class Pea extends GameElements {
    private int lane; // Jalur tempat Pea bergerak
    private int plantPosition; // Posisi tanaman yang menghasilkan Pea
    private int row; // Baris tempat Pea ditembakkan
    transient private Timeline peaAnimation; // Animasi gerakan Pea
    private boolean flag; // Menandai apakah Pea sudah mengenai enemy // Jalur gambar peluru
    private boolean isFirstMove = true;
    private static MediaPlayer musikAttack = new MediaPlayer(new Media(GamePlayController.class.getResource("/com/javafx/wkwk/sound/splat3.wav").toString()));

    public Pea(int x, int y, int width, int height, int col, int row) {
        super(x, y, width, height); 
        this.path = new Image("/com/javafx/wkwk/assets/pea.png");      
        this.plantPosition = col; // Kolom tanaman
        this.row = row; // Baris tempat peluru bergerak
        this.flag = false;
    }

//    @Override
//    public void makeImage(Pane pane) {
//        if (pane == null) {
//            System.err.println("Error: Pane is null. Cannot add image.");
//            return;
//        }
//
//        if (this.img == null) {
//            // Buat ImageView jika belum ada
//            Image image = new Image(path, width, height, false, false);
//            img = new ImageView(image);
//            img.setLayoutX(getX());
//            img.setLayoutY(getY());
//        }
//
//        // Tambahkan ImageView ke Pane
//        
//        pane.getChildren().add(img);
//        Bounds boundsInScene = img.localToScene(img.getBoundsInLocal());
//        System.out.println("Peluru di posisi X global: " + boundsInScene.getMinX());
//
//        System.out.println("Pea image added to pane at: (" + getX() + ", " + getY() + ")");
//        
//    }

    // Metode untuk memindahkan Pea
    public void movePea(Pea pea) {
        int row = pea.getRow();
        //System.out.println("muncul di baris " + row);
//        int posisi = 0;
//        if(row == 0){
//            posisi = 364;
//        }else if (row == 1){
//            posisi = 442;
//        }else if(row == 2){
//            posisi = 520;
//        }else if (row == 3){
//            posisi = 598;
//        }else if (row == 4){
//            posisi = 676;
//        }else if (row == 5){
//            posisi = 754;
//        }else if (row == 6){
//            posisi = 832;
//        }else if (row == 7){
//            posisi = 910;
//        }else if (row == 8){
//            posisi = 988;
//        }
        
//        if (isFirstMove) { // Jika ini adalah gerakan pertama
//            setX(getX() - posisi ); // Kurangi nilai X sebesar 364
//            isFirstMove = false; // Tandai bahwa gerakan pertama sudah selesai
//            System.out.println("First move: X decreased by 364, current X = " + getX());
//        } else 
        if (getX() < 1024) { // Jika belum mencapai batas layar
            setX(getX() + 2); // Gerakkan peluru ke kanan
            //System.out.println("Pea moving to X: " + getX());
            checkEnemyCollision();
        } else { // Jika sudah mencapai batas
            System.out.println("Pea reached boundary and will be removed.");
            img.setVisible(false);
            img.setDisable(true);
            if (peaAnimation != null) {
                peaAnimation.stop();
                GamePlayController.animationTimelines.remove(peaAnimation);
            }
        }
    }

    // Metode untuk menembakkan Pea
    public void shootPea(Pea pea) {
        System.out.println("Pea shoot animation started.");
        isFirstMove = true; // Reset gerakan pertama setiap kali peluru ditembakkan

        peaAnimation = new Timeline(new KeyFrame(Duration.millis(10), e -> movePea(pea)));
        peaAnimation.setCycleCount(1000); // Durasi total animasi
        peaAnimation.setOnFinished(e -> {
            System.out.println("Pea animation finished or out of bounds.");
        });

        peaAnimation.play();
        GamePlayController.animationTimelines.add(peaAnimation);
    }

    // Periksa tabrakan dengan zombie
    // Periksa tabrakan dengan zombie
    public void checkEnemyCollision() {
        synchronized (GamePlayController.allEnemy) {
            Iterator<Enemy> iterator = GamePlayController.allEnemy.iterator();
            while (iterator.hasNext()) {
                Enemy enemy = iterator.next();

                // Gunakan bounding box untuk deteksi tabrakan
                Bounds peaBounds = img.localToScene(img.getBoundsInLocal());
                Bounds enemyBounds = enemy.img.localToScene(enemy.img.getBoundsInLocal());

                if (peaBounds.intersects(enemyBounds)) {
                    System.out.println("Pea hit enemy at: " + enemy.getX());
                    playMusic("attack");// Play the music

                    
                    // Kurangi nyawa zombie
                    enemy.setHp(enemy.getHp() - 1);
                    if (enemy.getHp() <= 0) {
                        System.out.println("enemy defeated.");
                        
                        GamePlayController.removeEnemy(enemy);
                        GamePlayController.animationTimelines.remove(enemy.enemyAnimation);
                        
                    }
                    // Hentikan animasi dan hapus peluru
                    if (peaAnimation != null) {
                        peaAnimation.stop();
                        GamePlayController.animationTimelines.remove(peaAnimation);
                    }
                    
                    Pane parent = (Pane) img.getParent();
                    if (parent != null) {
                        parent.getChildren().remove(img); // Hapus peluru dari tampilan
                    }
                    

                    return; // Keluar setelah satu tabrakan
                }
            }
        }
    }
    
    public void remove() {
    if (this.img != null) {
        this.img.setVisible(false);  // Menyembunyikan peluru
        this.img.setDisable(true);   // Menonaktifkan peluru
    }
    
//    if (this.peaTimeline != null) {
//        this.peaTimeline.stop();  // Hentikan animasi peluru
//    }
}




    // Getter dan Setter untuk X dan Y
//    @Override
//    public int getX() {
//        return this.x;
//    }

//    @Override
//    public int getY() {
//        return this.y;
//    }

//    @Override
//    public void setX(int x) {
//        this.x = x;
//        if (img != null) {
//           Platform.runLater(() -> img.setLayoutX(x));
//        }
//    }

//    @Override
//    public void setY(int y) {
//        this.y = y;
//        if (img != null) {
//            img.setLayoutY(y);
//        }
//    }

    // Getter untuk row
    public int getRow() {
        return this.row;
    }
    
    private void playMusic (String tipe){
        if(tipe.equals("attack")){
            musikAttack.seek(Duration.ZERO);
            musikAttack.setAutoPlay(true);  // Memutar musik otomatis
            musikAttack.setCycleCount(1);  // Musik berulang terus menerus
            musikAttack.play();
        }
    }
    
    private void stopMusic(String tipe){
        if(tipe.equals("attack")){
                musikAttack.stop(); //hentikan musik
            }
    }
}

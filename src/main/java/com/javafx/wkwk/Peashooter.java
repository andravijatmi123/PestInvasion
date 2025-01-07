package com.javafx.wkwk;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Peashooter extends Plant {

    transient protected Timeline shooterTimeline; // Animasi untuk penembakan
    protected int lane; // Jalur tempat Peashooter berada
    private List<Pea> peas;

    public Peashooter(int x, int y, int col, int row) {
        super(x, y, 90, 75, 5, col, row, 100);
        this.path = new Image ("/com/javafx/wkwk/assets/bungaSepatu.png");
        this.lane = row; // Inisialisasi jalur (lane) berdasarkan baris
        this.peas = new ArrayList<>(); // Inisialisasi daftar peluru
    }

    // Metode untuk memulai serangan
    @Override
    public void attack(AnchorPane pane) {
        if (shooterTimeline != null && shooterTimeline.getStatus() == Timeline.Status.RUNNING) {
            return; // Jika animasi sudah berjalan, jangan memulai animasi baru
        }

        shooterTimeline = new Timeline(new KeyFrame(Duration.seconds(2.5), event -> {
            System.out.println("Peashooter sedang menembak");

            synchronized (GamePlayController.allEnemy) {
                boolean enemyFound = false;
                Iterator<Enemy> iterator = GamePlayController.allEnemy.iterator();

                while (iterator.hasNext()) {
                    Enemy e = iterator.next();

                    // Periksa apakah zombie berada di jalur yang sama
                    if (e.getLane() == this.row && e.getX() > this.col * 100) {
                        enemyFound = true;

                        // Gunakan posisi global dari ImageView
                        Bounds boundsInScene = img.localToScene(img.getBoundsInLocal());
                        if (boundsInScene == null) {
                            System.err.println("Error: Cannot calculate bounds for Peashooter.");
                            return;
                        }

                        int peaX = (int) boundsInScene.getMinX() + 40; // Di depan Peashooter
                        int peaY = (int) boundsInScene.getMinY() + 25; // Di tengah Peashooter

                        // Buat peluru
                        Pea pea = new Pea(peaX, peaY, 20, 20, this.row, this.col);

                        peas.add(pea);
                        pea.makeImage(pane);
                        pea.shootPea(pea);

                        System.out.println("Peashooter menembakkan peluru di: (" + peaX + ", " + peaY + ")");
                        break;
                    }
                }

                if (!enemyFound) {
                    System.out.println("Tidak ada zombie di jalur " + this.row);
                }
            }
        }));

        shooterTimeline.setCycleCount(Timeline.INDEFINITE);
        shooterTimeline.play();

        GamePlayController.animationTimelines.add(shooterTimeline);
    }

    

    // Menghentikan animasi jika diperlukan
    public void stopShooting() {
        if (shooterTimeline != null) {
            shooterTimeline.stop();
            GamePlayController.animationTimelines.remove(shooterTimeline);
        }
    }

    // Mendapatkan timeline animasi penembakan
    public Timeline getShooterTimeline() {
        return this.shooterTimeline;
    }

    // Mendapatkan jalur Peashooter
    public int getShooterLane() {
        return this.lane;
    }
    
    @Override
    public void onDeath() {
    // Hapus semua peluru yang terkait dengan Peashooter
    for (Pea pea : this.peas) {
        pea.remove();  // Menghapus peluru dari tampilan dan menghentikan animasi
    }
    
    // Hapus Peashooter dari tampilan
    if (this.img != null) {
        this.img.setVisible(false);
        this.img.setDisable(true);
    }

    // Hentikan animasi penembakan dan animasi lainnya
    if (this.shooterTimeline != null) {
        this.shooterTimeline.stop();
        GamePlayController.animationTimelines.remove(this.shooterTimeline);
    }

    // Hapus Peashooter dari GamePlayController
    GamePlayController.allPlants.remove(this);
}

}

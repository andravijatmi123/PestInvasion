package com.javafx.wkwk;

import static com.javafx.wkwk.GamePlayController.animationTimelines;
import static com.javafx.wkwk.GamePlayController.scoreCount;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public abstract class Enemy extends GameElements {
    protected int hp;
    protected int attackPower;
    int lane; // Jalur zombie
    protected int dx;
    protected int point;
    protected transient Timeline enemyAnimation;
    protected boolean reachedPlant = false;
    protected boolean isEating = false;
    protected transient Timeline chomping;
    private boolean gameOverTriggered = false; // Flag untuk memastikan Game Lose hanya dicetak sekali
    protected float kecepatan;
    
    private static final int CHARACTER_START_X = 0; // X position of the first frame
    private static final int CHARACTER_START_Y = 0; // baris ke 5 berada di koordinat 350
    private static final int FRAME_COUNT = 6; // Number of walking frames
    private static final int ANIMATION_DURATION = 300; // Duration per frame in milliseconds
    private static final double MOVEMENT_SPEED = 0; // Kecepatan maju
    private static MediaPlayer musikBunuh = new MediaPlayer(new Media(GamePlayController.class.getResource("/com/javafx/wkwk/sound/groan.wav").toString()));
    private static MediaPlayer musikMakan = new MediaPlayer(new Media(GamePlayController.class.getResource("/com/javafx/wkwk/sound/chomp.wav").toString()));
    private static MediaPlayer musikKalah = new MediaPlayer(new Media(GamePlayController.class.getResource("/com/javafx/wkwk/sound/gameover.wav").toString()));

    // Constructor
    public Enemy(int x, int y, int width, int height, int hp, int ap, int lane, int point) {
        super(x, y, width, height);
        this.hp = hp;
        this.attackPower = ap;
        this.lane = lane;
        this.dx = -1; // Zombie bergerak ke kiri
        this.point = point;
        this.chomping = new Timeline();
    }

    public void makeSprite(Pane pane) {
        try {
            img = new ImageView(path);
            pane.getChildren().add(img);
            img.setFitWidth(width);
            img.setFitHeight(height);
            img.setX(this.x);
            img.setY(this.y);

            // Animasi gerakan zombie
            Timeline timeline = new Timeline();
            for (int i = 0; i < FRAME_COUNT; i++) {
                int frameX = CHARACTER_START_X + i * (width + 0); 
                KeyFrame keyFrame = new KeyFrame(Duration.millis(i * ANIMATION_DURATION), e -> img.setViewport(new Rectangle2D(frameX, CHARACTER_START_Y, width, height)));
                timeline.getKeyFrames().add(keyFrame);
            }
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        } catch (Exception e) {
            System.err.println("Error loading enemy image: " + e.getMessage());
            //this.img = new ImageView(); // Placeholder jika gambar tidak ditemukan
        }
    }
    public void moveEnemy() {
        enemyAnimation = new Timeline(
            new KeyFrame(Duration.seconds(kecepatan), event -> {
                if (this.img != null) {
                    // Pengecekan apakah zombie sudah berada di posisi yang tepat untuk berhenti dan makan tanaman
                    if (this.reachedPlant) {
                        stopMoving(); // Hentikan pergerakan
                        eatPlant(); // Makan tanaman
                    } else {
                        if (!gameOverTriggered && !isEating) { // Cek apakah tidak sedang makan
                            this.img.setX(this.img.getX() + this.dx); // Lanjutkan pergerakan
                            checkReachedHouse(); // Cek apakah zombie mencapai rumah
                            eatPlant(); // Cek apakah zombie dekat dengan tanaman
                        }
                    }
                }
            })
        );
        enemyAnimation.setCycleCount(Animation.INDEFINITE);
        enemyAnimation.play();
        GamePlayController.animationTimelines.add(enemyAnimation);
    }
    public void checkReachedHouse() {
        if (this.img != null && this.img.getX() <= 300 && !gameOverTriggered) {
            playMusic("kalah");// Play the music
            System.out.println("Game Lose");
            gameOverTriggered = true; // Set flag ke true untuk mencegah Game Lose dicetak lagi
            stopMoving(); // Hentikan pergerakan enemy
            GamePlayController.stopAllTimelines();

            // Mengakses Stage dari img.getScene().getWindow() hanya jika img sudah berada di dalam scene
            if (this.img.getScene() != null) {
                Stage stage = (Stage) this.img.getScene().getWindow();
                if (stage != null) {
                    // Pindah scene ke GameLose.fxml
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javafx/wkwk/GameLose.fxml"));
                        Parent root = loader.load();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);  // Mengubah scene
                        stage.centerOnScreen(); // Menyelaraskan stage di tengah layar
                        stage.show();  // Menampilkan stage baru
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.err.println("Stage is null! Cannot change scene.");
                }
            } else {
                System.err.println("img is not part of a scene! Cannot change stage.");
            }
        }
    }
    private void stopMoving() {
        // Zombie tidak bergerak lagi
        this.dx = 0;
        // Hentikan pergerakan zombie
        if (this.enemyAnimation != null && this.enemyAnimation.getStatus() == Animation.Status.RUNNING) {
            this.enemyAnimation.stop();  // Hentikan animasi pergerakan zombie
            GamePlayController.animationTimelines.remove(this.enemyAnimation);
        }
        // Pastikan animasi dihentikan
        System.out.println("enemy movement stopped.");
    }
    private void resumeMoving() {
        System.out.println("enemy melanjutkan perjlanan");
        this.dx = -1; // Ulat melanjutkan pergerakan ke kiri
        if (this.enemyAnimation != null && this.enemyAnimation.getStatus() != Animation.Status.RUNNING) {
            this.enemyAnimation.play(); // Mulai animasi pergerakan
        }
    }
    public void eatPlant() {
        synchronized (GamePlayController.allPlants) {
            List<Plant> toRemove = new ArrayList<>();  // Koleksi sementara untuk tanaman yang harus dihapus

            Iterator<Plant> iterator = GamePlayController.allPlants.iterator();
            while (iterator.hasNext()) {
                Plant plant = iterator.next();
                if (plant.row == this.lane && isNearPlant(plant)) {
                    System.out.println("belalang menemukan plant di lane: " + this.lane);
                    startEating(plant, iterator);
                    toRemove.add(plant);  // Menyimpan tanaman yang akan dihapus
                }
            }
        }
    }
    private void startEating(Plant plant, Iterator<Plant> iterator) {
        System.out.println("Memulai makan");
        this.reachedPlant = true;
        setEating(true);  // Ulat mulai makan
        if (this.chomping.getStatus() != Animation.Status.RUNNING) {
            this.chomping = new Timeline(new KeyFrame(Duration.seconds(1), e -> chompPlant(plant, iterator)));
            this.chomping.setCycleCount(Animation.INDEFINITE);
            this.chomping.play();
            GamePlayController.animationTimelines.add(this.chomping);
        }
    }
    private boolean isNearPlant(Plant plant) {
        Bounds enemyBounds = img.localToScene(img.getBoundsInLocal());
        Bounds plantBounds = plant.img.localToScene(plant.img.getBoundsInLocal());
        return enemyBounds.intersects(plantBounds);
    }

    private void startEatingPlant(Plant plant, Iterator<Plant> iterator) {
        this.reachedPlant = true;
        this.isEating = true;  // Zombie mulai makan
        if (this.chomping.getStatus() != Animation.Status.RUNNING) {
            this.chomping = new Timeline(new KeyFrame(Duration.seconds(1), e -> chompPlant(plant, iterator)));
            this.chomping.setCycleCount(Animation.INDEFINITE);
            this.chomping.play();
            GamePlayController.animationTimelines.add(this.chomping);
        }
    }

    private void chompPlant(Plant plant, Iterator<Plant> iterator) {
        playMusic("makan");// Play the music
        System.out.println("Memakan plant. HP plant: " + plant.getHP());
        plant.setHp(plant.getHp() - this.attackPower);
        if (plant.getHp() <= 0) {
            System.out.println("Tanaman habis");
            removePlant(plant); // Jika tanaman mati, hapus dari list
        }
    }
    private void removePlant(Plant plant) {
        plant.setHp(0);
        System.out.println("Menghapus tanaman: " + plant);
        plant.onDeath();
        if (GamePlayController.allPlants.contains(plant)) {
            GamePlayController.allPlants.remove(plant); // Menghapus tanaman dari list
        }
        if (plant.img != null) {
            plant.img.setVisible(false);  // Menyembunyikan gambar tanaman
            plant.img.setDisable(true);   // Menonaktifkan gambar tanaman
        }
        stopEating();  // Ulat berhenti makan setelah tanaman mati
    }

    private void stopEating() {
        stopMusic("makan");
        this.reachedPlant = false;
        this.isEating = false;  // Zombie selesai makan
        if (this.chomping != null) {
            this.chomping.stop(); // Hentikan animasi makan
        }
        resumeMoving();
    }

    public void onDeath() {
        stopMusic("makan");
        playMusic("bunuh");// Play the music
        GamePlayController.numEnemyKilled++;
        if (this.img != null) {
            this.img.setVisible(false);
            this.img.setDisable(true);
        }
        if (this.enemyAnimation != null) {
            this.enemyAnimation.stop();
        }
        if (this.chomping != null) {
            this.chomping.stop();
        }
        GamePlayController.allEnemy.remove(this);
    }

    // Getter dan Setter tambahan
    public int getLane() {
        return this.lane;
    }

    public int getHp() {
        return this.hp;
    }

    public void setLane(int lane) {
        this.y = lane;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
    
    // Metode untuk memeriksa apakah ulat sedang makan
    private boolean isEating() {
        return this.isEating;  // Mengembalikan status apakah ulat sedang makan
    }

    // Metode untuk mengatur status makan
    private void setEating(boolean status) {
        this.isEating = status;  // Set status makan
    }
    
    private void playMusic (String tipe){
        if(tipe.equals("bunuh")){
            musikBunuh.seek(Duration.ZERO);
            musikBunuh.setAutoPlay(true);  // Memutar musik otomatis
            musikBunuh.setCycleCount(1);  // Musik berulang terus menerus
            musikBunuh.play();
        }else if(tipe.equals("makan")){
            musikMakan.seek(Duration.ZERO);
            musikMakan.setAutoPlay(true);  // Memutar musik otomatis
            musikMakan.setCycleCount(1);  // Musik berulang terus menerus
            musikMakan.play();
        }else if(tipe.equals("kalah")){
            musikKalah.seek(Duration.ZERO);
            musikKalah.setAutoPlay(true);  // Memutar musik otomatis
            musikKalah.setCycleCount(1);  // Musik berulang terus menerus
            musikKalah.play();
        }
    }
    
    private void stopMusic(String tipe){
        if(tipe.equals("bunuh")){
                musikBunuh.stop(); //hentikan musik
            }else if(tipe.equals("makan")){
                musikMakan.stop(); //hentikan musik
            }else if(tipe.equals("kalah")){
                musikKalah.stop(); //hentikan musik
            }
    }
    
}

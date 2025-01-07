package com.javafx.wkwk;

import Dao.PlayerDAO;
import Dao.RecordDAO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import model.Player;

public class GamePlayController {
    private static GamePlayController instance; 
    @FXML
    private  AnchorPane root;

    @FXML
    private ImageView imgPeashooter;
    @FXML
    private ImageView imgSunflower;
    @FXML
    private ImageView imgBambu;
    @FXML
    private ImageView imgSekop;
    
    

    @FXML
    private GridPane grid; // Grid tempat elemen permainan ditempatkan

    @FXML
    private Label sunCountLabel; // Label untuk menampilkan jumlah poin matahari
    
    @FXML
    private ImageView iconSun;
    
    @FXML
    private Label killCountLabel;
    
    @FXML
    private Label scoreCountLabel;

    private int sunPoints; // Poin matahari awal

    private static int sunCount = 100;
    public static int scoreCount = 0;
    private static int highScore = 0;
    public static boolean statusTopScore = false;
    public static int jumlahLoopConvety = 0;
 
    private static final int LANE1 = 55;
    private static final int LANE2 = 150;
    private static final int LANE3 = 245;
    private static final int LANE4 = 350;
    private static final int LANE5 = 445;
    private static boolean gameStatus;
    public static Timeline sunTimeline;
    private static Label sunCountDisplay;
    private static Label scoreCountDisplay;
    private static Label killCountDisplay;
    private static double timeElapsed;

    public static List<Enemy> allEnemy;
    public static List<Plant> allPlants;

    public static ArrayList<Enemy> enemyList;

    private static DataTable d;
    public static int wonGame = 0;
    
    private transient Timeline cekSpawnInterval;
    private transient Timeline enemySpawnTimeline;

    private volatile int spawnedEnemy = 0;
    public static int numEnemyKilled = 0;

    public static ArrayList<Timeline> animationTimelines = new ArrayList<>(); // Initialize here to avoid null
    
    private Plant selectedPlant;
    private boolean selectedItem = false;
    private static boolean gameOverShown = false;  // Variabel untuk memeriksa apakah game over pop-up sudah ditampilkan
    
    //private static GamePlayController instance;
    
    private static Player u;
    private static int topScore;
    @FXML
    private  ImageView imgConvety;
    private Timeline timeConvety;
    int jumlahCycle = 0;
    
    // path music
    private static MediaPlayer musikBackground = new MediaPlayer(new Media(GamePlayController.class.getResource("/com/javafx/wkwk/sound/background.wav").toString()));
    private static MediaPlayer musikTopScore = new MediaPlayer(new Media(GamePlayController.class.getResource("/com/javafx/wkwk/sound/game_end.wav").toString()));
    private static MediaPlayer musikPlant = new MediaPlayer(new Media(GamePlayController.class.getResource("/com/javafx/wkwk/sound/plant.wav").toString()));
    private static MediaPlayer musikSekop = new MediaPlayer(new Media(GamePlayController.class.getResource("/com/javafx/wkwk/sound/splat3.wav").toString()));
    private static MediaPlayer musikKalah = new MediaPlayer(new Media(GamePlayController.class.getResource("/com/javafx/wkwk/sound/gameover.wav").toString()));

    
    float intervalEnemy = 15;
    boolean firstSpawn = false;
    boolean interval2 = false;
    boolean interval3 = false;
    boolean interval4 = false;
    boolean interval5 = false;
    boolean interval6 = false;
    @FXML
    private ImageView btnSelesai;
    boolean backCek = false;
    private DropShadow glowEffectBtnBack = new DropShadow(20, Color.YELLOW);

    
    public void initialize() throws Exception {
        // Logika inisialisasi
        
        gameStatus = true;
        sunCount = 100;
        scoreCount = 0;
        numEnemyKilled = 0;
        topScore = RecordDAO.getTopScore();
        System.out.println("top score: " + topScore);
        statusTopScore = false;
        //sekop = new Sekop(); // Membuat objek Sekop

        // Menambahkan gambar sekop ke UI
        //root.getChildren().add(sekop.getSekopImageView());
        System.out.println("sekop " + imgConvety);
        // Setup grid click handler
        setupGridClickHandler();
        
        sunCountLabel.setText(String.valueOf(sunCount));
        sunCountDisplay = sunCountLabel;
        
        scoreCountLabel.setText(String.valueOf(scoreCount));
        scoreCountDisplay = scoreCountLabel;
        
        killCountLabel.setText(String.valueOf(numEnemyKilled));
        killCountDisplay = killCountLabel;

        allPlants = Collections.synchronizedList(new ArrayList<Plant>());
        allEnemy = Collections.synchronizedList(new ArrayList<Enemy>());
        
        //System.out.println("convety disini,ada convety jangan lari");
        
        playMusic("background");
        
        spawnEnemiesPeriodically(root); //memanggil zombie ketika game mulai
        
        fallingSuns(new Random()); //memanggil matahri ketika game start
        
        timeConvety = new Timeline(new KeyFrame(Duration.seconds(0.01), e -> makeConvety()));
        timeConvety.setCycleCount(Timeline.INDEFINITE);
        timeConvety.play();
        //Click Event untuk peashooter
        imgPeashooter.setOnMouseClicked(event -> {
            if (selectedPlant != null){
                selectedItem = false;
                selectedPlant = null;
                stopHighlightSelectedPlant();
            }else{
                selectCard("Peashooter");
                System.out.println("peashooter yeah");
                highlightSelectedPlant(imgPeashooter);
            }
        });
        
        //Click Event untuk sunFlower
        imgSunflower.setOnMouseClicked(event -> {
            if (selectedPlant != null){
                selectedItem = false;
                selectedPlant = null;
                stopHighlightSelectedPlant();
            }else{
                selectCard("Sunflower");
                System.out.println("sunflowerrrr yeah");
                highlightSelectedPlant(imgSunflower);
            }
            
        });
        
        imgBambu.setOnMouseClicked(event -> {
            if (selectedPlant != null){
                selectedItem = false;
                selectedPlant = null;
                stopHighlightSelectedPlant();
            }else{
                selectCard("Bambu");
                System.out.println("bamboooo yeah");
                highlightSelectedPlant(imgBambu);
            }
        });
        
        imgSekop.setOnMouseClicked(event -> {
            if (selectedItem == true){
                selectedItem = false;
                selectedPlant = null;
                stopHighlightSelectedPlant();
            }else{
                selectCard("Sekop");
                System.out.println("sekop yeah");
                highlightSelectedPlant(imgSekop);
            }
        });
        setupGridClickHandler();//setup untuk klik pada grid, buat naro tanaman
        
    }

    public synchronized void updateSpawnZombies() {
        this.spawnedEnemy += 1;
    }

    public static void removeEnemy(Enemy e) {
        updateScorePoints(e.point); // Tambahkan poin matahari
        System.out.println("enemy killed: " + numEnemyKilled);
        e.onDeath();
        updatekillPoints();
        
    }

    //method sun jatuh
    public void fallingSuns(Random rand) {
        Timeline sunDropper = new Timeline(new KeyFrame(Duration.seconds(10), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    System.out.println("Creating a falling sun...");

                    // Menentukan posisi X secara acak
                    int sunPosition = rand.nextInt(400) + 100; // Rentang posisi X
                    Sun sun = new Sun(sunPosition, -50, 50, 50, true);

                    // Validasi untuk memastikan `img` dan `root` valid

                    // Menambahkan gambar matahari ke AnchorPane
                    sun.makeImage(root);

                    // Memulai animasi jatuh
                    sun.dropSun(new Random());

                    // Logging keberhasilan
                    System.out.println("Falling sun created at position: X = " + sunPosition + ", Y = -50");
                } catch (Exception e) {
                    // Logging jika terjadi error
                    System.err.println("Error in fallingSuns: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }));

        // Mengatur siklus animasi jatuh matahari
        sunDropper.setCycleCount(Timeline.INDEFINITE);
        sunDropper.play();
        sunTimeline = sunDropper;
        animationTimelines.add(sunDropper);
    }
    
    
    public static void updateSunPoints(int points) {
        sunCount += points; // Tambahkan poin matahari ke total
        
        sunCountDisplay.setText(String.valueOf(sunCount)); // Perbarui tampilan poin matahari
        System.out.println("Sun points updated: " + sunCount);
        
    }
    public static void updateScorePoints(int points) {
        scoreCount += points; // Tambahkan poin score ke total
        
        scoreCountDisplay.setText(String.valueOf(scoreCount)); // Perbarui tampilan poin score
        System.out.println("Score points updated: " + scoreCount);
        
        if(scoreCount > topScore){
            highScore = scoreCount; //perbarui skor
            System.out.println("udah melewati high score, selamat yawwwwwwww");
            //metod makeConvety
        }
    }
    
    public void makeConvety(){
        //load gambar convety
        if(scoreCount > topScore && !statusTopScore){
            
            statusTopScore = true;
            System.out.println("convety" + imgConvety);
            Image convety = new Image("/com/javafx/wkwk/assets/convety.png");
            imgConvety.setImage(convety);
            imgConvety.setVisible(true);
            imgConvety.setFitWidth(1000);  // Fixed display size
            imgConvety.setFitHeight(600); // Fixed display size
            imgConvety.setLayoutX((root.getWidth() - imgConvety.getFitWidth()) / 2);
            imgConvety.setLayoutY((root.getHeight() - imgConvety.getFitHeight()) / 2);
            
            // Create and play music
            playMusic("topScore");// Play the music
            
            // Create a Timeline for animating the frames
            Timeline timelineConvety = new Timeline();
            for (int i = 0; i < 6; i++) {
                int frameX = 0 + i * (1000 + 0); // Add gap offset if necessary
                KeyFrame keyFrame = new KeyFrame(
                    Duration.millis(i * 200),
                    e -> imgConvety.setViewport(new Rectangle2D(frameX, 0, 1000, 600))
                );
                timelineConvety.getKeyFrames().add(keyFrame);
            }
            
            
            
            timelineConvety.setOnFinished(event -> {
                imgConvety.setVisible(false);  // Sembunyikan gambar setelah 5 siklus
                imgConvety.setImage(null);             // Reset imgConvety ke null setelah animasi selesai
                stopMusic("topScore");
            });
             // Set the animation to loop
            animationTimelines.add(timelineConvety);
            timelineConvety.setCycleCount(5);
            timelineConvety.play();
        }  
    }
    
    
    public static void updatekillPoints() {
        
            killCountDisplay.setText(String.valueOf(numEnemyKilled)); // Perbarui tampilan poin kill
            System.out.println("kill points updated: " + numEnemyKilled);
        
    }


    
    
    //mengontroll spawn zombie
    public void spawnEnemiesPeriodically(AnchorPane root){
         cekSpawnInterval = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            boolean gantiInterval = false;

             //Tentukan interval berdasarkan jumlah musuh yang dikalahkan
            if(numEnemyKilled == 3 && !interval2){
                interval2 = true;
                gantiInterval = true;
                intervalEnemy = (float) 12.4;
            }else if (numEnemyKilled == 7 && !interval3) {
                interval3 = true;
                gantiInterval = true;
                intervalEnemy = (float) 9.8;
            } else if (numEnemyKilled == 11 && !interval4) {
                interval4 = true;
                gantiInterval = true;
                intervalEnemy = (float) 7.2;
            }else if (numEnemyKilled == 15 && !interval5) {
                interval5 = true;
                gantiInterval = true;
                intervalEnemy = (float) 4.6;
            }else if (numEnemyKilled == 19 && !interval6) {
                interval6 = true;
                gantiInterval = true;
                intervalEnemy = 2;
            }
            System.out.println("INTERVAL" + intervalEnemy);
            // Hentikan dan buat ulang `enemySpawnTimeline` dengan interval baru
            if (gantiInterval || !firstSpawn) {
                if(enemySpawnTimeline != null){
                    enemySpawnTimeline.stop();
                    animationTimelines.remove(enemySpawnTimeline);
                }
                System.out.println("NEW TIME LINE INTERVAL: "+intervalEnemy);
                firstSpawn = true;
                gantiInterval = false;
                enemySpawnTimeline = new Timeline(new KeyFrame(Duration.seconds(intervalEnemy), e -> spawnEnemy(root)));
                enemySpawnTimeline.setCycleCount(Timeline.INDEFINITE);
                enemySpawnTimeline.play();
                animationTimelines.add(enemySpawnTimeline);
            }
        }));

        // Jalankan evaluasi interval secara terus-menerus
        cekSpawnInterval.setCycleCount(Timeline.INDEFINITE);
        cekSpawnInterval.play();
        animationTimelines.add(cekSpawnInterval);
        
    }
    
    private void spawnEnemy(AnchorPane root) {
        
        int randomLane = (int) (Math.random() * 5); // ada 5 jalur
        int randomEnemy = (int) (Math.random() * 2); 
        int x = 1020; // Posisi awal enemy
        int y = calculateLaneY(randomLane); // Hitung Y berdasarkan jalur
        if(numEnemyKilled <= 2){
            Ulat newEnemy = new Ulat(x, y, randomLane);
            newEnemy.makeSprite(root); // Tambahkan enemy ke tampilan
            newEnemy.moveEnemy();    // Mulai enemy move
            GamePlayController.allEnemy.add(newEnemy); // Masukkan enemy ke daftar global
        }else{
            if(randomEnemy == 0){
                Ulat newEnemy = new Ulat(x, y,randomLane);
                newEnemy.makeSprite(root); // Tambahkan enemy ke tampilan
                newEnemy.moveEnemy();    // Mulai gerakan enemy
                GamePlayController.allEnemy.add(newEnemy); // Masukkan enemy ke daftar global
            }else{
                Belalang newBelalang = new Belalang(x, y,randomLane);
                newBelalang.makeSprite(root);
                newBelalang.moveEnemy(); // Mulai gerakan enemy
                allEnemy.add(newBelalang); // Menambahkan Belalang ke dalam daftar enemy
            }
            
        }
        System.out.println("Enemy spawned at lane: " + randomLane + ", X: " + x + ", Y: " + y);
        
    }
    
    private int calculateLaneY(int lane) {
        //return 100 + (lane * 80); // Sesuaikan tata letak jalur di sini
        if (lane == 0){
            return LANE1;
        }
        else if (lane == 1){
            return LANE2;
        }
        else if (lane == 2){
            return LANE3;
        }
        else if (lane == 3){
            return LANE4;
        }
        else{
            return LANE5;
        }
    }
       
    //Setup event klik pada setiap sel grid
    private void setupGridClickHandler() {
    grid.setOnMouseClicked(event -> {
        // Mendapatkan koordinat klik
        int col = (int)(event.getX() / (grid.getWidth() / 9)); // grid 9 kolom
        int row = (int)(event.getY() / (grid.getHeight() / 5)); // grid 5 baris

        System.out.println("Klik di kolom " + col + ", baris " + row);

        // Mengecek apakah tanaman sudah dipilih
        if (selectedPlant != null) {
            // Mengecek apakah mataharinya cukup
            System.out.println("Poin matahari saat ini: " + sunCount);
            System.out.println("Biaya tanaman: " + selectedPlant.cost);
            if (sunCount < selectedPlant.cost) {
                System.out.println("Matahari tidak cukup");
                return;
            } else {
                // Pastikan sel tersebut kosong
                if (isCellOccupied(col, row)) {
                    System.out.println("Tempat ini sudah diisi.");
                    return;
                } else {
                    // Tempatkan tanaman
                    placePlant(selectedPlant, col, row);

                    // Kurangi poin matahari
                    sunCount -= selectedPlant.cost;
                    sunCountDisplay.setText(String.valueOf(sunCount)); // Perbarui tampilan poin matahari
                    System.out.println("Poin matahari diperbarui: " + sunCount);
                    
                    selectedPlant = null;  // Reset selectedPlant setelah penanaman
                }
            }
        } else if (selectedItem && isCellOccupied(col, row)) {
            // Jika tidak ada tanaman yang dipilih dan selectedItem aktif, hapus tanaman
            sekopPlant(getPlantAtCell(col, row));
            System.out.println("Tanaman dihapus");
            selectedItem = false;  // Reset selectedItem
        } else {
            // Jika tidak ada tanaman yang dipilih
            System.out.println("Pilih tanaman terlebih dahulu");
            return;
        }
    });
}



    
    private Plant getPlantAtCell(int col, int row) {
    for (Plant plant : GamePlayController.allPlants) {
        if (plant.col == col && plant.row == row) {
            return plant; // Kembalikan objek Plant jika ditemukan
        }
    }
    return null; // Tidak ada Plant di sel yang dimaksud
    }
    
    private void sekopPlant(Plant plant) {
        plant.setHp(0);
        plant.onDeath();
        playMusic("sekop");// Play the music
        if (plant.img != null) {
            plant.img.setVisible(false);
            plant.img.setDisable(true);  // Menyembunyikan gambar tanaman
        }
        stopHighlightSelectedPlant();
    }
    
    
    public void selectPlant(String plantType) {
    if (plantType.equals("Peashooter")) {
        selectedPlant = new Peashooter(0, 0, 0, 0);
        selectedItem = false;
    } else if (plantType.equals("Sunflower")) {
        selectedPlant = new Sunflower(0, 0, 0, 0);
        selectedItem = false;
    } else if (plantType.equals("Bambu")) {
        selectedPlant = new Bambu(0, 0, 0, 0);
        selectedItem = false;
    } else if (plantType.equals("Sekop")) {
        selectedPlant = null; // Tidak ada tanaman yang dipilih
        selectedItem = true;  // Mengaktifkan status untuk sekop
    } else {
        System.out.println("Tanaman tidak bisa dipilih.");
        return;
    }
    System.out.println("Tanaman yang dipilih: " + plantType);
}

    
    
    //memeriksa apakah sel pada gridPane kosong
    private boolean isCellOccupied(int col, int row) {
        for (Plant plant : GamePlayController.allPlants) {
            if (plant.col == col && plant.row == row) {
                return true;
            }
        }
        return false;
    }
    
    

    //metode menempatkan tanaman di sel gridPane
    private void placePlant(Plant plant, int col, int row){
        Plant newPlant;
        
        //membuat instance tanaman berdasarkan tipe tanaman
        if (plant instanceof Peashooter) {
            newPlant = new Peashooter(0, 0, col, row);
        } else if (plant instanceof Sunflower) {
            newPlant = new Sunflower(0, 0, col, row);
        }else if (plant instanceof Bambu) {
            newPlant = new Bambu(0, 0, col, row);
        }else {
            System.out.println("blm memilih plant");
            return;
        }
        
        //tambahkan tanaman ke grid dan daftar tanaman
        newPlant.makeImage(grid);
        newPlant.attack(root); // Mulai animasi (jika ada)
        
        GamePlayController.allPlants.add(newPlant);//menambahkan ke daftar tanaman
        
        stopHighlightSelectedPlant();
        
        playMusic("plant");// Play the music

        System.out.println("tanaman di taro di kolom " + col + ", baris " + row);
        Bounds bounds = newPlant.img.localToScene(newPlant.img.getBoundsInLocal());
        System.out.println("Posisi tanaman di: (" + bounds.getMinX() + ", " + bounds.getMinY() + ")");
        
    }
    
    
    //metode memilih tanaman
    public void selectCard(String cardType){
        if (cardType.equals("Peashooter")) {
            selectedPlant = new Peashooter(0, 0, 0, 0);
            selectedItem = false;
        }else if (cardType.equals("Sunflower")) {
            selectedPlant = new Sunflower(0, 0, 0, 0);
            selectedItem = false;
        }else if (cardType.equals("Bambu")) {
            selectedPlant = new Bambu(0, 0, 0, 0);
            selectedItem = false;
        }else if (cardType.equals("Sekop")) {
            selectedPlant = null;
            selectedItem = true;
        }else {
            System.out.println("tanaman tidak bisa dipilih.");
            return;
        }
      System.out.println("tanaman yang dipilih: " + cardType);
      highlightSelectedPlant(getImageViewByCardType(cardType));
      
      
    }
    
    
    //efek pilihan tanaman
    private void highlightSelectedPlant(ImageView selectedImageView) {
        // Reset semua efek Glow
        imgPeashooter.setEffect(null);
        imgSunflower.setEffect(null);
        imgBambu.setEffect(null);
        imgSekop.setEffect(null);
        // Tambahkan efek Glow ke tanaman yang dipilih
        Glow glow = new Glow();
            glow.setLevel(0.5); // Intensitas efek
            selectedImageView.setEffect(glow);
    }
    
    private void stopHighlightSelectedPlant() {
        // Reset semua efek Glow
        imgPeashooter.setEffect(null);
        imgSunflower.setEffect(null);
        imgBambu.setEffect(null);
        imgSekop.setEffect(null);
        // Tambahkan efek Glow ke tanaman yang dipilih
    }
    
    
    public static void stopAllTimelines() {
    // Hentikan semua animasi yang sedang berjalan
    stopMusic("background");
    saveScoreToDatabase();
        System.out.println("timeline udah berhenti");
    for (Timeline timeline : animationTimelines) {
        if (timeline != null && timeline.getStatus() == Animation.Status.RUNNING) {
            timeline.stop();
        }
    }

    // Cek apakah masih ada animasi yang berjalan dan berhenti
    System.out.println("All timelines stopped.");
}




    
    public static void endAnimations() {
        for (int i = 0; i < animationTimelines.size(); i++) {
            animationTimelines.get(i).stop();
        }
    }

    public int getPlayerId() {
        // Misalnya, kita mengambil ID pemain dari sesi atau data yang telah disimpan
        // Bisa juga disesuaikan jika ada metode login atau pengambilan data pemain lainnya
        return 1; // Gantilah dengan logika yang sesuai untuk mengambil ID pemain
    }
    
    public static void ResetData(){
        sunCount = 100;
        scoreCount = 0;
    }
    public static void saveScoreToDatabase(){
        int id = PlayerDAO.Valdi(u.getUname(), u.getPass());
        System.out.println("id: "+id);
        RecordDAO.savePlayerScore(id, scoreCount);
    }
    public static void setUser(Player p){
        u = p;
    }
    
    private ImageView getImageViewByCardType(String cardType) {
    switch (cardType) {
        case "Peashooter":
            return imgPeashooter;
        case "Sunflower":
            return imgSunflower;
        case "Bambu":
            return imgBambu;
        case "Sekop":
            return imgSekop;
        default:
            return null;
    }
}
    
    private void playMusic(String tipe){
        //membuat objek media\
        if(tipe.equals("background")){
            musikBackground.setAutoPlay(true);  // Memutar musik otomatis
            musikBackground.setCycleCount(MediaPlayer.INDEFINITE);  // Musik berulang terus menerus
            musikBackground.play();

            // Logging untuk memastikan musik diputar
            System.out.println("Music is playing...");
        }else if(tipe.equals("topScore")){
            musikTopScore.setAutoPlay(true);  // Memutar musik otomatis
            musikTopScore.setCycleCount(MediaPlayer.INDEFINITE);  // Musik berulang terus menerus
            musikTopScore.play();
            
        }else if(tipe.equals("plant")){
            musikPlant.seek(Duration.ZERO);
            musikPlant.setAutoPlay(true);  // Memutar musik otomatis
            musikPlant.setCycleCount(1);  // Musik berulang terus menerus
            musikPlant.play();
            
        }else if(tipe.equals("sekop")){
            musikSekop.seek(Duration.ZERO);
            musikSekop.setAutoPlay(true);  // Memutar musik otomatis
            musikSekop.setCycleCount(1);  // Musik berulang terus menerus
            musikSekop.play();
        }else if(tipe.equals("kalah")){
            musikKalah.seek(Duration.ZERO);
            musikKalah.setAutoPlay(true);  // Memutar musik otomatis
            musikKalah.setCycleCount(1);  // Musik berulang terus menerus
            musikKalah.play();
        }
        
        
    }
    
    public static void stopMusic(String tipe){
        if(musikBackground != null){
            if(tipe.equals("background")){
                musikBackground.stop(); //hentikan musik
                System.out.println("Music Berhent");
            }else if(tipe.equals("topScore")){
                musikTopScore.stop(); //hentikan musik
            }else if(tipe.equals("plant")){
                musikTopScore.stop(); //hentikan musik
            }else if(tipe.equals("sekop")){
                musikSekop.stop(); //hentikan musik
            }
        }
    }

    @FXML
    private void goToGameOver(MouseEvent event) throws IOException {
        if (!backCek) {
            // Pertama kali diklik: beri indikasi bahwa tombol telah diklik
            backCek = true;
            btnSelesai.setEffect(glowEffectBtnBack);
            // Setel timeline untuk reset jika tidak diklik lagi dalam dua detik
            Timeline resetTimeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> resetBackButton()));
            resetTimeline.setCycleCount(1); // Hanya sekali
            resetTimeline.play();
        } else {
            // Jika tombol diklik kedua kali dalam waktu 2 detik, lakukan aksi\
            playMusic("kalah");// Play the music
            System.out.println("Game Lose");
            stopAllTimelines();
            Stage stage = (Stage) btnSelesai.getScene().getWindow();
            URL url = new File("src/main/resources/com/javafx/wkwk/GameLose.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root); // Menyimpan user yang login
            stage.setScene(scene);
            stage.centerOnScreen();
        }
    }
    private void resetBackButton(){
        if (backCek) {
            btnSelesai.setEffect(null);
            backCek = false;  // Reset status klik
        }
    }

    
}

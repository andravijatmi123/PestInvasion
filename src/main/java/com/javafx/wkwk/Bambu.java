package com.javafx.wkwk;

import javafx.scene.image.Image;

public class Bambu extends Plant {
    public Bambu(int x, int y, int col, int row) {
        super(x, y, 90, 75, 10, col, row, 75);
        this.path = new Image("/com/javafx/wkwk/assets/BambuSheet.png");
    }
    public void checkHp() {
        if (hp <= 0) {
            System.out.println("bambu at (" + col + ", " + row + ") has died.");
            if (img != null) {
            img.setVisible(false); // Sembunyikan gambar tanaman
            img.setDisable(true);
            }
            GamePlayController.allPlants.remove(this); // Hapus dari daftar tanaman
        }
    }
}

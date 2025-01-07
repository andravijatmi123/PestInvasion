//package com.javafx.wkwk;
//
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.GridPane;
//
//public class Sekop {
//
//    private ImageView sekopImageView;
//    private static boolean isSekopSelected = false;
//
//    public Sekop() {
//        
//        // Menambahkan event klik pada sekop untuk memilih sekop
//        sekopImageView.setOnMouseClicked(event -> toggleSekopSelection());
//    }
//
//    public ImageView getSekopImageView() {
//        return sekopImageView;
//    }
//
//    public boolean isSekopSelected() {
//        return isSekopSelected;
//    }
//
//    private void toggleSekopSelection() {
//        // Mengubah status sekop apakah dipilih atau tidak
//        isSekopSelected = !isSekopSelected;
//        if (isSekopSelected) {
//            System.out.println("Sekop dipilih! Anda dapat menghapus tanaman.");
//        } else {
//            System.out.println("Sekop dibatalkan. Anda tidak bisa menghapus tanaman.");
//        }
//    }
//
//    // Metode untuk menghapus tanaman yang ada di grid
//    public void removePlant(Plant plant, GridPane grid) {
//        if (isSekopSelected) {
//            // Menghapus gambar tanaman dari grid
//            plant.getImg().setVisible(false); // Menyembunyikan gambar tanaman
//            GamePlayController.allPlants.remove(plant); // Menghapus tanaman dari daftar tanaman
//            System.out.println("Tanaman dihapus dari grid.");
//            isSekopSelected = false;  // Membatalkan pemilihan sekop setelah penghapusan tanaman
//        } else {
//            System.out.println("Sekop belum dipilih. Pilih sekop terlebih dahulu.");
//        }
//    }
//}
//

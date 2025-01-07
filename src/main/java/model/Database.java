package com.javafx.wkwk;

import Dao.BaseDAO;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Asus
 */
public class Database implements Serializable{
    private static final long serialVersionUID=42L;
    private int maxLevel;
    private static Database d;
    
    
    private ArrayList<DataTable> databaseFiles = new ArrayList<>();
    
    public static Database getInstance(){
        if (d == null) {
        d = new Database();
        }
        return d;
    }
    
    public int getMaxLevel(){
        return maxLevel;
    }
    
    public void setMaxLevel(int maxLevel) {
    if (maxLevel > this.maxLevel) {
        this.maxLevel = maxLevel;
        } else {
            System.out.println("Level tidak dapat lebih rendah dari level saat ini.");
        }
    }

    
    public void addData(DataTable d){
        databaseFiles.add(d);
    }
    
    public void removeData(DataTable d) {
    if (d != null && databaseFiles.contains(d)) {
        databaseFiles.remove(d);
        }
    }

    
    public static void deleteAllProgress(){
        d = new Database();
    }
    
    public ArrayList<DataTable> getDatabaseFiles(){
        return databaseFiles;
    }
    
    public void savePlayerScore(int playerId, int score) {
        Connection con = BaseDAO.getCon();
        String query = "INSERT INTO player_scores (player_id, score) VALUES (?, ?)";

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, playerId);
            pst.setInt(2, score);
            pst.executeUpdate();
            System.out.println("Skor berhasil disimpan ke database");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Gagal menyimpan skor ke database");
        } finally {
            BaseDAO.closeCon(con);
        }
    }
    
}

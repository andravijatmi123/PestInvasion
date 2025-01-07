package Dao;

import model.Record;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import model.Player;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Asus
 */
public class RecordDAO extends BaseDAO {
    private static PreparedStatement st;
    private static Connection con;
    
    public static void insertEntry(Record s) {
        
        try {
            
//            String query = "insert into record (idPlayer, score ) "
//                    + " values (,'%s') ";
//            query = String.format(query,
//                    s.getOwner().getId(),
//                    s.getScore());
            con = getCon();
            String query = "INSERT INTO record (idPlayer, score) VALUES (?, ?)";
            st = con.prepareStatement(query);
            st.setInt(1, s.getId());
            st.setInt(2,s.getScore());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeCon(con);
        }
    }
    
    public static ObservableList<Record> showLB (){
        ObservableList<Record> r = FXCollections.observableArrayList();
        con = getCon();
        if (con != null) {
            String query = "SELECT MAX(score) AS score, player.uname\n" +
                            "FROM record JOIN player ON idPlayer = player.id\n" +
                            "GROUP BY player.id\n" +
                            "ORDER BY MAX(score) DESC "+
                            "LIMIT 10";
            
            try {
                Statement statement = con.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                int rank =1;
                while (resultSet.next()) {
                    String nama = resultSet.getString("uname");
                    int score = resultSet.getInt("score");
                    r.add(new Record(rank, nama, score));
                    rank++;
                    
                    System.out.println("Nama: " + nama + ", score: " + score);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return r;
    }
    
    public static void savePlayerScore(int playerId, int score) {
        con = getCon();
        String query = "INSERT INTO record (idPlayer, score) VALUES (?, ?)";

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
    
    public static int getTopScore() {
        int i = 0;
        try {
            con = getCon();
            String query = "SELECT score FROM record ORDER BY score DESC LIMIT 1";
            st = con.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                i = rs.getInt("score");
            }else{
                i = 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeCon(con);
        }
        return i;
    }
    
//    public void savePlayerScore(int playerId, int score) {
//        Connection con = BaseDAO.getCon();
//        String query = "INSERT INTO player_scores (player_id, score) VALUES (?, ?)";
//
//        try (PreparedStatement pst = con.prepareStatement(query)) {
//            pst.setInt(1, playerId);
//            pst.setInt(2, score);
//            pst.executeUpdate();
//            System.out.println("Skor berhasil disimpan ke database");
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.err.println("Gagal menyimpan skor ke database");
//        } finally {
//            BaseDAO.closeCon(con);
//        }
//    }
    
}

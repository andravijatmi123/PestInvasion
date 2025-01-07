package Dao;

import static Dao.BaseDAO.closeCon;
import static Dao.BaseDAO.getCon;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import model.Player;

public class PlayerDAO {
    private static PreparedStatement st;
    private static Connection con;
    
    public static int validate(String name) {
        int i = 0;
        try {
            con = getCon();
            String query = "select id from player where uname = '%s'";
            query = String.format(query,
                    name);
            st = con.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                i = 1;
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
    
    
    public static int Valdi(String name, String passwd){
    int id = -1;
    try {
        con = getCon();
        String query = "SELECT id FROM player WHERE uname = ? AND password = ?";
        st = con.prepareStatement(query);
        st.setString(1, name);   // Bind username
        st.setString(2, passwd); // Bind password
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            id = rs.getInt("id");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        closeCon(con);
    }
    return id;
}

    
    
    public static void registerPlayer(Player u) {
        try {
            con = getCon();
//            String query = "insert into player"
//                    + " values ('%s', '%s') ";
//            query = String.format(query,
//                    u.getUname(),
//                    u.getPass());
            String query = "INSERT INTO player (uname, password) VALUES (?, ?)";
            st = con.prepareStatement(query);
            st.setString(1, u.getUname());
            st.setString(2, u.getPass());
            st.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeCon(con);
        }

    }
}

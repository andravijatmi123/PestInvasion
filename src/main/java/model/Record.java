package model;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author AERO
 */

public class Record {
    private int score;
    private String uname;
    private int rank;

    
    private int id;

    public Record(int rank, String name, int score) {
        this.setScore(score);
        this.setUname(name);
        this.setRank(rank);
    
    }
    

//    public Record() {
//    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
    
    
}
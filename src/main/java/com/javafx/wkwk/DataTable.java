package com.javafx.wkwk;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Asus
 */
public class DataTable implements Serializable {
    private static final long serialVersionUID=42L;
    private static int id=0;
    private int gameId;
    private int sunCount;
    private List<Plant> allPlants = Collections.synchronizedList(new ArrayList<Plant>());
    private List<Enemy> allEnemy = Collections.synchronizedList(new ArrayList<Enemy>());
    private ArrayList<Enemy> enemyList = new ArrayList<>();
    private double timeElapsed;
    private String savingTimestamp;
    public static final int LANE1 = 30;
    public static final int LANE2 = 150;
    public static final int LANE3 = 250;
    public static final int LANE4 = 350;
    public static final int LANE5 = 450;
    
    public DataTable(int sunCount, List<Plant>allPlant, List<Enemy>allenemy, double timeElapsed, ArrayList<Enemy> enemyList){
        this.sunCount = 50;
        this.enemyList = new ArrayList<Enemy>();
        this.allEnemy = Collections.synchronizedList(new ArrayList<Enemy>());
        this.allPlants = Collections.synchronizedList(new ArrayList<Plant>());
        
        
        
    }
    
    public void update(int sunCount, List<Plant> allPlants, List<Enemy> allEnemy, double timeElapsed, ArrayList<Enemy> zombieList) {
    this.sunCount = sunCount;
    this.allPlants = allPlants != null ? new ArrayList<>(allPlants) : new ArrayList<>();
    this.allEnemy = allEnemy != null ? new ArrayList<>(allEnemy) : new ArrayList<>();
    this.timeElapsed = timeElapsed;
    this.enemyList = zombieList != null ? new ArrayList<>(zombieList) : new ArrayList<>();
}


    
    public List<Plant> getAllPlants(){
        return allPlants;
    }
    
    public List<Enemy> getAllEnemy(){
        return allEnemy;
    }
    
    public int getSunCount(){
        return sunCount;
    }
    
    public double getTimeElapsed(){
        return this.timeElapsed;
    }
    
    public ArrayList<Enemy> getEnemyList(){
        return this.enemyList;
    }
    
    public enum Lane {
    LANE1(50), LANE2(150), LANE3(250), LANE4(350), LANE5(450);
    
    private final int position;
    
    Lane(int position) {
        this.position = position;
    }
    
    public int getPosition() {
        return position;
    }
}

    
    
}

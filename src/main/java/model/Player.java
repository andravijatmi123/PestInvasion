/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Asus
 */
public class Player {
    private int id;
    private static String uname;
    private static String pass;

    public Player (String uname, String pass) {
        this.setPass(pass);
        this.setUname(uname);
        //this.setId(id);
    }
    public Player (String uname, String pass, int id){
        this.setPass(pass);
        this.setUname(uname);
        this.setId(id);
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public static String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    
    
}

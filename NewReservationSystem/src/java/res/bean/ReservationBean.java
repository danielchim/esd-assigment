/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package res.bean;

import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author erd25
 */
public class ReservationBean extends RecordBean {
    private ArrayList<String> equipsID;
    
    public ArrayList<String> getEquipsID(){ return equipsID; }
    public void setEquipsID(ArrayList<String> equipsID){ this.equipsID = equipsID; }
}

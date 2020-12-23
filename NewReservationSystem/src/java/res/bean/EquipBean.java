/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package res.bean;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author erd25
 */
public class EquipBean implements Serializable{
    private String equipID;
    private String equipName;
    private String description;
    
    public EquipBean(){}
    
    public String getEquipID(){ return equipID; }
    public String getEquipName(){ return equipName; }
    public String getDescription(){ return description; }
    
    public void setEquipID(String equipID){ this.equipID = equipID; }
    public void setEquipName(String equipName){ this.equipName = equipName; }
    public void setDescription(String description){ this.description = description; }
}

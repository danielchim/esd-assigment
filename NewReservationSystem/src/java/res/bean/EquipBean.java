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
    private int equipID;
    private String equipName;
    private String description;
    private int quantity;
    private String status;
    private Date startDate;
    private Date dueDate;
    
    public EquipBean(){}
    
    public int getEquipID(){ return equipID; }
    public String getEquipName(){ return equipName; }
    public String getDescription(){ return description; }
    public int getQuantity(){ return quantity; }
    public String getStatus(){ return status; }
    public Date getStartDate(){ return startDate; }
    public Date getDueDate(){ return dueDate; }
    
    public void setEquipID(int equipID){ this.equipID = equipID; }
    public void setEquipName(String equipName){ this.equipName = equipName; }
    public void setDescription(String description){ this.description = description; }
    public void setQuantity(int quantity){ this.quantity = quantity; }
    public void setStatus(String status){ this.status = status; }
    public void setStartDate(Date startDate){ this.startDate = startDate; }
    public void setDueDate(Date dueDate){ this.dueDate = dueDate; }
}

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
public class RecordBean implements Serializable {
    private String recordID;
    private String userID;
    private String itemName;
    private String status;
    private Date startDate;
    private Date dueDate;
    
    public RecordBean(){}
    
    public String getRecordID(){ return recordID; }
    public Date getStartDate(){ return startDate; }
    public Date getDueDate(){ return dueDate; }
    public String getItemName() {
        return itemName;
    }
    public String getStatus() {
        return status;
    }

    public void setRecordID(String recordID){ this.recordID = recordID; }
    public void setUserID(String userID){ this.userID = userID; }
    public void setStartDate(Date startDate){ this.startDate = startDate; }
    public void setDueDate(Date dueDate){ this.dueDate = dueDate; }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}

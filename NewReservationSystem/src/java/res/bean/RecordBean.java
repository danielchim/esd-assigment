/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package res.bean;

import java.sql.Date;

/**
 *
 * @author erd25
 */
public abstract class RecordBean {
    private String recordID;
    private String userID;
    private Date startDate;
    private Date dueDate;
    
    public RecordBean(){}
    
    public String getRecordID(){ return recordID; }
    public String getUserID(){ return userID; }
    public Date getStartDate(){ return startDate; }
    public Date getDueDate(){ return dueDate; }
    
    public void setRecordID(String recordID){ this.recordID = recordID; }
    public void setUserID(String userID){ this.userID = userID; }
    public void setStartDate(Date startDate){ this.startDate = startDate; }
    public void setDueDate(Date dueDate){ this.dueDate = dueDate; }
}

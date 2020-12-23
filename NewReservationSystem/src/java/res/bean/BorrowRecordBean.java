/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package res.bean;

/**
 *
 * @author erd25
 */
public class BorrowRecordBean extends RecordBean {
    private String equipID;
    
    public BorrowRecordBean(){}
    
    public String getEquipID(){ return equipID; }
    public void setEquipID(String equipID){ this.equipID = equipID; }
}

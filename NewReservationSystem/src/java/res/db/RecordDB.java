package res.db;
import res.bean.RecordBean;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RecordDB extends DBFactory{
    public RecordDB(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    public boolean insertRecord(int reservationID, int reservationItemID, int userID){
        Connection conn = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try{
            conn = getConnection();
            String preQueryStatement = "INSERT INTO record VALUES (?,?,null,null,'reserved',?)"; // all items are reserved
            pStmnt = conn.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, reservationID);
            pStmnt.setInt(2, reservationItemID);
            pStmnt.setInt(3, userID);
            int rowCount = pStmnt.executeUpdate();
            if(rowCount >= 1){
                isSuccess = true;
            }
            pStmnt.close();
            conn.close();
        }catch(SQLException ex){
            while(ex != null){
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
        return isSuccess;
    }
    public ArrayList<RecordBean> fetchRecord(int userID){
        ArrayList<RecordBean> rbList = new ArrayList<>();
        RecordBean rb = null;
        Connection conn = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try{
            conn = getConnection();
            String preQueryStatement = "SELECT * FROM RECORD INNER JOIN equipinfo pu on RECORD.recordItemID = pu.equipID WHERE userID = ? ORDER BY recordID ";
            pStmnt = conn.prepareStatement(preQueryStatement);
            ResultSet rs = null;
            pStmnt.setInt(1, userID);
            rs = pStmnt.executeQuery();
            while(rs.next()){
                rb = new RecordBean();
                rb.setRecordID(rs.getString(1));
                rb.setItemName(rs.getString(9));
                rb.setStartDate(rs.getDate(3));
                rb.setDueDate(rs.getDate(4));
                rb.setStatus(rs.getString(5));
                rb.setUserID(rs.getString(6));
                rbList.add(rb);
            }
            pStmnt.close();
            conn.close();
        }catch(SQLException ex){
            while(ex != null){
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
        return rbList;
    }
    public ArrayList<RecordBean> searchRecord(int userID,String status,String targetEquipment){
        ArrayList<RecordBean> rbList = new ArrayList<>();
        RecordBean rb = null;
        Connection conn = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        String preQueryStatement = "SELECT * FROM RECORD INNER JOIN equipinfo pu on RECORD.recordItemID = pu.equipID WHERE userID = ? AND status = ? AND equipName LIKE ? ORDER BY recordID";
        try{
            conn = getConnection();
            if(status.equals("")){
                preQueryStatement = "SELECT * FROM RECORD INNER JOIN equipinfo pu on RECORD.recordItemID = pu.equipID WHERE userID = ? AND status <> '' AND equipName LIKE ? ORDER BY recordID";
                pStmnt = conn.prepareStatement(preQueryStatement);
                pStmnt.setInt(1, userID);
                pStmnt.setString(2,"%" + targetEquipment + "%");

            }else if(targetEquipment.equals("")){
                preQueryStatement = "SELECT * FROM RECORD INNER JOIN equipinfo pu on RECORD.recordItemID = pu.equipID WHERE userID = ? AND status = ? AND equipName NOT LIKE '' ORDER BY recordID\n";
                pStmnt = conn.prepareStatement(preQueryStatement);
                pStmnt.setInt(1, userID);
                pStmnt.setString(2, status);
            }else{
                pStmnt = conn.prepareStatement(preQueryStatement);
                pStmnt.setInt(1, userID);
                pStmnt.setString(2, status);
                pStmnt.setString(3,"%" + targetEquipment + "%");
            }
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while(rs.next()){
                rb = new RecordBean();
                rb.setRecordID(rs.getString(1));
                rb.setItemName(rs.getString(9));
                rb.setStartDate(rs.getDate(3));
                rb.setQuantity(rs.getInt(7));
                rb.setDueDate(rs.getDate(4));
                rb.setStatus(rs.getString(5));
                rbList.add(rb);
            }
            pStmnt.close();
            conn.close();
        }catch(SQLException ex){
            while(ex != null){
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
        return rbList;
    }
}

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
    
    public ArrayList<RecordBean> fetchAllRecord(){
        ArrayList<RecordBean> rbList = new ArrayList<>();
        RecordBean rb = null;
        Connection conn = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try{
            conn = getConnection();
            String preQueryStatement = "SELECT * FROM RECORD INNER JOIN equipinfo pu on RECORD.recordItemID = pu.equipID ORDER BY recordID ";
            pStmnt = conn.prepareStatement(preQueryStatement);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while(rs.next()){
                rb = new RecordBean();
                rb.setRecordID(rs.getString(1));
                rb.setItemName(rs.getString(9));
                rb.setStartDate(rs.getDate(3));
                rb.setDueDate(rs.getDate(4));
                rb.setStatus(rs.getString(5));
                rb.setUserID(rs.getString(6));
                rb.setQuantity(7);
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
    
    public double getUtilizationRate(int equipID, String period){
        Connection conn = null;
        PreparedStatement pStmnt = null;
        double itemCount = 0.0;
        double totalCount = 0.0;
        double utilizationRate = 0.0;
        int dayRange = -1;
        if(period.equals("Monthly")){
            dayRange = 30;
        }else if(period.equals("Yearly")){
            dayRange = 365;
        }
        
        try{
            conn = getConnection();
            ResultSet rs = null;
            if(dayRange > 0){
                String preQueryStatement = "SELECT COUNT(quantity) FROM RECORD WHERE recordItemID = ? AND borrowDate - GETDATE() <= ?";
                pStmnt = conn.prepareStatement(preQueryStatement);
                pStmnt.setInt(1, equipID);
                pStmnt.setInt(2, dayRange);
                rs = pStmnt.executeQuery();
                if(rs.next()){
                    itemCount += rs.getInt(1);
                }
                preQueryStatement = "SELECT COUNT(quantity) FROM RECORD WHERE borrowDate - GETDATE() <= ?";
                pStmnt = conn.prepareStatement(preQueryStatement);
                pStmnt.setInt(1, dayRange);
                rs = pStmnt.executeQuery();
                if(rs.next()){
                    totalCount += rs.getInt(1);
                }
            }else{
                String preQueryStatement = "SELECT COUNT(quantity) FROM RECORD WHERE recordItemID = ?";
                pStmnt = conn.prepareStatement(preQueryStatement);
                pStmnt.setInt(1, equipID);
                rs = pStmnt.executeQuery();
                if(rs.next()){
                    itemCount += rs.getInt(1);
                }
                preQueryStatement = "SELECT COUNT(quantity) FROM RECORD";
                pStmnt = conn.prepareStatement(preQueryStatement);
                rs = pStmnt.executeQuery();
                if(rs.next()){
                    totalCount += rs.getInt(1);
                }
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
        return itemCount / totalCount;
    }
}

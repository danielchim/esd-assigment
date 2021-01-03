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
}

package res.db;
import res.bean.RecordBean;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.sql.Date;

public class ReservationDB extends DBFactory{
    public ReservationDB(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    public boolean insertReservation(int reservationID, int reservationItemID,String quantity, int userID){
        Connection conn = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try{
            int finalQuantity = Integer.parseInt(quantity);
            conn = getConnection();
            String preQueryStatement = "INSERT INTO record VALUES (?,?,null,null,'reserved',?,?)"; // all items are reserved
            pStmnt = conn.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, reservationID);
            pStmnt.setInt(2, reservationItemID);
            pStmnt.setInt(3, userID);
            pStmnt.setInt(4, finalQuantity);
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
    public ArrayList<RecordBean> fetchReservation(int userID){
        ArrayList<RecordBean> rbList = new ArrayList<>();
        RecordBean rb = null;
        Connection conn = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        String preQueryStatement = "SELECT * FROM RECORD INNER JOIN equipinfo pu on RECORD.recordItemID = pu.equipID WHERE (userID = ?) AND (status = 'reserved' OR status = 'ready') ORDER BY recordID";
        try{
            conn = getConnection();
            pStmnt = conn.prepareStatement(preQueryStatement);
            if(userID != 0){
                pStmnt.setInt(1, userID);
            }else{
                preQueryStatement = "SELECT * FROM RECORD INNER JOIN equipinfo pu on RECORD.recordItemID = pu.equipID WHERE status = 'reserved' ORDER BY recordID";
                pStmnt = conn.prepareStatement(preQueryStatement);
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
    public boolean updateReservation(int reservationID, int reservationItemID, String status, String quantity , int userID){
        Connection conn = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try{
            int finalQuantity = Integer.parseInt(quantity);
            conn = getConnection();
            String preQueryStatement = "UPDATE record SET recordItemID = ?, status = ?, quantity = ? WHERE userID = ? AND recordID = ?"; // all items are reserved
            pStmnt = conn.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, reservationItemID);
            pStmnt.setString(2, status);
            pStmnt.setInt(3, finalQuantity);
            pStmnt.setInt(4, userID);
            pStmnt.setInt(5, reservationID);
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
    public boolean updateReservation(int reservationID, int reservationItemID, String status, String quantity, LocalDate startDate, LocalDate dueDate, int userID){
        Connection conn = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try{
            int finalQuantity = Integer.parseInt(quantity);
            conn = getConnection();
            String preQueryStatement = "UPDATE record SET recordItemID = ?, status = ?, quantity = ?,borrowDate = ?, returnDate = ? WHERE userID = ? AND recordID = ?"; // all items are reserved
            pStmnt = conn.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, reservationItemID);
            pStmnt.setString(2, status);
            pStmnt.setInt(3, finalQuantity);
            pStmnt.setDate(4, Date.valueOf(startDate));
            pStmnt.setDate(5, Date.valueOf(dueDate));
            pStmnt.setInt(6, userID);
            pStmnt.setInt(7, reservationID);
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
    public ArrayList<RecordBean> searchReservation(int userID,String status,String targetEquipment){
        ArrayList<RecordBean> rbList = new ArrayList<>();
        RecordBean rb = null;
        Connection conn = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        String preQueryStatement = "SELECT * FROM RECORD INNER JOIN equipinfo pu on RECORD.recordItemID = pu.equipID WHERE userID = ? AND status = ? AND equipName LIKE ? ORDER BY recordID";
        try{
            conn = getConnection();
            if(status.equals("")){
                preQueryStatement = "SELECT * FROM RECORD INNER JOIN equipinfo pu on RECORD.recordItemID = pu.equipID WHERE userID = ? AND (status = 'ready' OR status = 'reserved') AND equipName LIKE ? ORDER BY recordID";
                pStmnt = conn.prepareStatement(preQueryStatement);
                pStmnt.setInt(1, userID);
                pStmnt.setString(2,"%" + targetEquipment + "%");

            }else if(targetEquipment.equals("")){
                preQueryStatement = "SELECT * FROM RECORD INNER JOIN equipinfo pu on RECORD.recordItemID = pu.equipID WHERE userID = ? AND status = ? AND equipName NOT LIKE '' ORDER BY recordID\n";
                pStmnt = conn.prepareStatement(preQueryStatement);
                pStmnt.setInt(1, userID);
                pStmnt.setString(2, status);
            }else if(userID == 0){
                preQueryStatement = "SELECT * FROM RECORD INNER JOIN equipinfo pu on RECORD.recordItemID = pu.equipID WHERE userID = NOT NULL AND status = ? AND equipName = ? ORDER BY recordID\n";
                pStmnt = conn.prepareStatement(preQueryStatement);
                pStmnt.setString(1, status);
                pStmnt.setString(2, "%" + targetEquipment + "%");
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
    public boolean deleteReservation(int reservationID){
        Connection conn = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try{
            conn = getConnection();
            String preQueryStatement = "DELETE FROM record WHERE recordID = ?"; // all items are reserved
            pStmnt = conn.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, reservationID);
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
}

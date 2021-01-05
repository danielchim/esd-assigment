package res.db;
import res.bean.RecordBean;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import res.bean.EquipBean;

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
    
    public ArrayList<RecordBean> fetchAllOdRecordByPeriod(int period){
        ArrayList<RecordBean> rbList = new ArrayList<>();
        RecordBean rb = null;
        Connection conn = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        if(period >= 0){
            try{
                conn = getConnection();
                String preQueryStatement = "SELECT * FROM RECORD INNER JOIN equipinfo pu on RECORD.recordItemID = pu.equipID WHERE borrowDate - NOW() <= ? AND returnDate < NOW() ORDER BY recordID ";
                pStmnt = conn.prepareStatement(preQueryStatement);
                pStmnt.setInt(1, period);
                ResultSet rs = null;
                rs = pStmnt.executeQuery();
                while(rs.next()){
                    EquipBean eb = new EquipBean();
                    rb = new RecordBean();
                    rb.setRecordID(rs.getString(1));
                    rb.setItemName(rs.getString(9));
                    rb.setStartDate(rs.getDate(3));
                    rb.setDueDate(rs.getDate(4));
                    rb.setStatus(rs.getString(5));
                    rb.setUserID(rs.getString(6));
                    rb.setQuantity(7);

                    eb.setEquipID(rs.getInt(8));
                    eb.setEquipName(rs.getString(9));
                    eb.setDescription(rs.getString(10));
                    preQueryStatement = "SELECT * FROM disaequip WHERE equipID = ?";
                    pStmnt = conn.prepareStatement(preQueryStatement);
                    ResultSet rs2 = null;
                    pStmnt.setInt(1, eb.getEquipID());
                    rs2 = pStmnt.executeQuery();
                    boolean isDisabled = false;
                    if(rs2.next()){
                        isDisabled = true;
                    }
                    if(isDisabled){
                        eb.setStatus("Disabled");
                        isDisabled = false;
                    }else if(eb.getAvaQuantity() > 0){
                        eb.setStatus("Available");
                    }else{
                        eb.setStatus("Unavailable");
                    }
                    rb.setEquipBean(eb);
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
        }else{
            try{
                conn = getConnection();
                String preQueryStatement = "SELECT * FROM RECORD INNER JOIN equipinfo pu on RECORD.recordItemID = pu.equipID WHERE returnDate < NOW() ORDER BY returnDate DESC";
                pStmnt = conn.prepareStatement(preQueryStatement);
                ResultSet rs = null;
                rs = pStmnt.executeQuery();
                while(rs.next()){
                    EquipBean eb = new EquipBean();
                    rb = new RecordBean();
                    rb.setRecordID(rs.getString(1));
                    rb.setItemName(rs.getString(9));
                    rb.setStartDate(rs.getDate(3));
                    rb.setDueDate(rs.getDate(4));
                    rb.setStatus(rs.getString(5));
                    rb.setUserID(rs.getString(6));
                    rb.setQuantity(7);

                    eb.setEquipID(rs.getInt(8));
                    eb.setEquipName(rs.getString(9));
                    eb.setDescription(rs.getString(10));
                    preQueryStatement = "SELECT * FROM disaequip WHERE equipID = ?";
                    pStmnt = conn.prepareStatement(preQueryStatement);
                    ResultSet rs2 = null;
                    pStmnt.setInt(1, eb.getEquipID());
                    rs2 = pStmnt.executeQuery();
                    boolean isDisabled = false;
                    if(rs2.next()){
                        isDisabled = true;
                    }
                    preQueryStatement = "SELECT * FROM inventory WHERE equipID = ?";
                    pStmnt = conn.prepareStatement(preQueryStatement);
                    ResultSet rs3 = null;
                    pStmnt.setInt(1, eb.getEquipID());
                    rs3 = pStmnt.executeQuery();
                    if(rs3.next()){
                        eb.setAvaQuantity(rs3.getInt(2));
                        eb.setQuantity(rs3.getInt(2));
                    }
                    if(isDisabled){
                        eb.setStatus("Disabled");
                        isDisabled = false;
                    }else if(eb.getAvaQuantity() > 0){
                        eb.setStatus("Available");
                    }else{
                        eb.setStatus("Unavailable");
                    }
                    rb.setEquipBean(eb);
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
        }
        return rbList;
    }
    
    public ArrayList<RecordBean> fetchAllRecordByPeriod(int period){
        ArrayList<RecordBean> rbList = new ArrayList<>();
        RecordBean rb = null;
        Connection conn = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try{
            conn = getConnection();
            String preQueryStatement = "SELECT * FROM RECORD INNER JOIN equipinfo pu on RECORD.recordItemID = pu.equipID WHERE borrowDate - NOW() <= ? ORDER BY recordID ";
            pStmnt = conn.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, period);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while(rs.next()){
                EquipBean eb = new EquipBean();
                rb = new RecordBean();
                rb.setRecordID(rs.getString(1));
                rb.setItemName(rs.getString(9));
                rb.setStartDate(rs.getDate(3));
                rb.setDueDate(rs.getDate(4));
                rb.setStatus(rs.getString(5));
                rb.setUserID(rs.getString(6));
                rb.setQuantity(7);
                
                eb.setEquipID(rs.getInt(8));
                eb.setEquipName(rs.getString(9));
                eb.setDescription(rs.getString(10));
                preQueryStatement = "SELECT * FROM disaequip WHERE equipID = ?";
                pStmnt = conn.prepareStatement(preQueryStatement);
                ResultSet rs2 = null;
                pStmnt.setInt(1, eb.getEquipID());
                rs2 = pStmnt.executeQuery();
                boolean isDisabled = false;
                if(rs2.next()){
                    isDisabled = true;
                }
                if(isDisabled){
                    eb.setStatus("Disabled");
                    isDisabled = false;
                }else if(eb.getAvaQuantity() > 0){
                    eb.setStatus("Available");
                }else{
                    eb.setStatus("Unavailable");
                }
                rb.setEquipBean(eb);
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
    
    public ArrayList<RecordBean> searchEquipRecord(String keyword, String status, String period, String studentID, boolean overDue){
        Connection conn = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        boolean hvKeyword = keyword != null && !keyword.equals("");
        boolean hvStatus = status != null && !status.equals("");
        boolean hvPeriod = period != null && !period.equals("");
        boolean hvSid = studentID != null && !studentID.equals("");
        boolean id_only = false;
        if(hvKeyword){
            id_only = keyword.charAt(0) == '#';
        }
        
        int dayRange = -1;
        if(hvPeriod){
            if(period.equals("Monthly")){
                dayRange = 30;
            }else if(period.equals("Yearly")){
                dayRange = 365;
            }
        }
        
        ArrayList<RecordBean> rbList;
        if(overDue){
            rbList = fetchAllOdRecordByPeriod(dayRange);
        }else{
            rbList = fetchAllRecordByPeriod(dayRange);
        }
        
        ArrayList<RecordBean> requireList = new ArrayList<RecordBean>();
        
        for(RecordBean data : rbList){
            if(id_only){
                if(data.getEquipBean().getEquipID() == Integer.parseInt(keyword.substring(1))){
                    requireList.add(data);
                }
            }else if(!hvKeyword && !hvStatus && !hvPeriod && !hvSid){
                requireList.add(data);
            }else{
                if(hvKeyword){
                    if(data.getItemName().contains(keyword)){
                        requireList.add(data);
                    }
                    if(data.getEquipBean().getDescription().contains(keyword)){
                        requireList.add(data);
                    }
                }
                if(hvStatus){
                    if(data.getEquipBean().getStatus().equals(status)){
                        requireList.add(data);
                    }
                }
                if(hvSid){
                    if(data.getUserID().equals(studentID)){
                        requireList.add(data);
                    }
                }
            }
        }
        
        LinkedHashSet<RecordBean> hashSet = new LinkedHashSet<RecordBean>(requireList);
        requireList = new ArrayList<RecordBean>(hashSet);
        return requireList;
    }
}

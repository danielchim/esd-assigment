/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package res.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import res.bean.EquipBean;

/**
 *
 * @author erd25
 */
public class EquipDB {
    private String dbUrl = "";
    private String dbUser = "";
    private String dbPassword = "";
    
    public EquipDB(String dbUrl, String dbUser, String dbPassword){
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }
    
    public Connection getConnection() throws SQLException, IOException{
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }
    
    public boolean insertEquip(int equipID, String equipName, String description){
        Connection conn = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try{
            conn = getConnection();
            String preQueryStatement = "INSERT INTO equipInfo VALUES (?,?,?)";
            pStmnt = conn.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, equipID);
            pStmnt.setString(2, equipName);
            pStmnt.setString(3, description);
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
    
    public boolean insertDisaEquip(int equipID){
        Connection conn = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try{
            conn = getConnection();
            String preQueryStatement = "INSERT INTO disaequip VALUES (?)";
            pStmnt = conn.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, equipID);
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
    
    public boolean insertInventory(int equipID, int quantity){
        Connection conn = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try{
            conn = getConnection();
            String preQueryStatement = "INSERT INTO inventory VALUES (?,?)";
            pStmnt = conn.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, equipID);
            pStmnt.setInt(2, quantity);
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
    
    public boolean updateEquip(EquipBean eb){
        Connection conn = null;
        PreparedStatement pStmnt = null;
        boolean isUpdated = false;
        try{
            conn = getConnection();
            String preQueryStatement = "UPDATE equipinfo SET equipName = ?, description = ? WHERE equipID = ?";
            pStmnt = conn.prepareStatement(preQueryStatement);
            pStmnt.setString(1, eb.getEquipName());
            pStmnt.setString(2, eb.getDescription());
            pStmnt.setInt(3, eb.getEquipID());
            isUpdated = pStmnt.execute();
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
        return isUpdated;
    }
    
    public boolean delEquip(String equipID){
        Connection conn = null;
        PreparedStatement pStmnt = null;
        boolean isDeleted = false;
        try{
            conn = getConnection();
            String preQueryStatement = "DELETE FROM equipinfo WHERE equipID = ?";
            pStmnt = conn.prepareStatement(preQueryStatement);
            pStmnt.setString(1, equipID);
            isDeleted = pStmnt.execute();
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
        return isDeleted;
    }
    
    public ArrayList<EquipBean> queryEquip(){
        Connection conn = null;
        PreparedStatement pStmnt = null;
        EquipBean eb = null;
        ArrayList<EquipBean> ebs = new ArrayList<EquipBean>();
        boolean isDisabled = false;
        boolean isAvailable = true;
        boolean isOccupied = false;
        boolean isOverDue = false;
        try{
            conn = getConnection();
            String preQueryStatement = "SELECT * FROM equipinfo";
            pStmnt = conn.prepareStatement(preQueryStatement);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while(rs.next()){
                eb = new EquipBean();
                eb.setEquipID(rs.getInt(1));
                eb.setEquipName(rs.getString(2));
                eb.setDescription(rs.getString(3));
                ebs.add(eb);
                try{
                    preQueryStatement = "SELECT quantity FROM inventory WHERE equipID = ?";
                    pStmnt = conn.prepareStatement(preQueryStatement);
                    ResultSet rs2 = null;
                    pStmnt.setInt(1, eb.getEquipID());
                    rs2 = pStmnt.executeQuery();
                    while(rs2.next()){
                        eb.setQuantity(rs2.getInt(1));
                    }
                    preQueryStatement = "SELECT * FROM disaequip WHERE equipID = ?";
                    pStmnt = conn.prepareStatement(preQueryStatement);
                    ResultSet rs3 = null;
                    pStmnt.setInt(1, eb.getEquipID());
                    rs3 = pStmnt.executeQuery();
                    while(rs3.next()){
                        isDisabled = true;
                    }
                }catch(SQLException ex){
                    while(ex != null){
                        ex.printStackTrace();
                        ex = ex.getNextException();
                    }
                }
                
                if(isDisabled){
                    eb.setStatus("Disabled");
                }else{
                    eb.setStatus("Available");
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
        
        
        return ebs;
    }
    
    public int getPossibleID(){
        Connection conn = null;
        PreparedStatement pStmnt = null;
        int pID = 0;
        try{
            conn = getConnection();
            String preQueryStatement = "SELECT MAX(equipID) FROM equipinfo";
            pStmnt = conn.prepareStatement(preQueryStatement);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while(rs.next()){
                pID = rs.getInt(1);
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
        return pID + 1;
    }
    
}

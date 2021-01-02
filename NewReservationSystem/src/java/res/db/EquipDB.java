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
public class EquipDB extends DBFactory{
    public EquipDB(String dbUrl, String dbUser, String dbPassword){
        super(dbUrl, dbUser , dbPassword);
    }

    public boolean insertEquip(String equipID, String equipName, String description){
        Connection conn = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try{
            conn = getConnection();
            String preQueryStatement = "INSERT INTO equipInfo VALUES (?,?,?)";
            pStmnt = conn.prepareStatement(preQueryStatement);
            pStmnt.setString(1, equipID);
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
            pStmnt.setString(3, eb.getEquipID());
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
        try{
            conn = getConnection();
            String preQueryStatement = "SELECT * FROM equipinfo";
            pStmnt = conn.prepareStatement(preQueryStatement);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while(rs.next()){
                eb = new EquipBean();
                eb.setEquipID(rs.getString(1));
                eb.setEquipName(rs.getString(2));
                eb.setDescription(rs.getString(3));
                ebs.add(eb);
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
}

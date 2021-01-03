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
        boolean isUpdated2 = false;
        boolean isUpdated3 = false;
        try{
            conn = getConnection();
            String preQueryStatement = "UPDATE equipinfo SET equipName = ?, description = ? WHERE equipID = ?";
            pStmnt = conn.prepareStatement(preQueryStatement);
            pStmnt.setString(1, eb.getEquipName());
            pStmnt.setString(2, eb.getDescription());
            pStmnt.setInt(3, eb.getEquipID());
            isUpdated = pStmnt.executeUpdate() > 0;
            preQueryStatement = "UPDATE inventory SET quantity = ? WHERE equipID = ?";
            pStmnt = conn.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, eb.getQuantity());
            pStmnt.setInt(2, eb.getEquipID());
            isUpdated2 = pStmnt.executeUpdate() > 0;

            preQueryStatement = "SELECT * FROM disaequip WHERE equipID = ?";
            pStmnt = conn.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, eb.getEquipID());
            ResultSet rs = pStmnt.executeQuery();
            boolean isDisabled = rs.next();

            if(eb.getStatus() != null){
                // case eb disabled
                if(isDisabled){
                    // eb is already disabled
                    isUpdated3 = true;
                }else{
                    // eb is not disabled yet
                    preQueryStatement = "INSERT INTO disaequip VALUES (?)";
                    pStmnt = conn.prepareStatement(preQueryStatement);
                    pStmnt.setInt(1, eb.getEquipID());
                    isUpdated3 = pStmnt.executeUpdate() > 0;
                }

            }else{
                // case eb occupied or available
                if(isDisabled){
                    // eb is disabled currently
                    preQueryStatement = "DELETE FROM disaequip WHERE equipID = ?";
                    pStmnt = conn.prepareStatement(preQueryStatement);
                    pStmnt.setInt(1, eb.getEquipID());
                    isUpdated3 = pStmnt.executeUpdate() > 0;
                }else{
                    // eb is not disabled
                    isUpdated3 = true;
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
        return isUpdated && isUpdated2 && isUpdated3;
    }
    
    public boolean delEquip(int equipID){
        Connection conn = null;
        PreparedStatement pStmnt = null;
        boolean isDeleted1 = false;
        boolean isDeleted2 = false;
        boolean isDeleted3 = false;
        try{
            // delete from inventory
            conn = getConnection();
            String preQueryStatement = "DELETE FROM inventory WHERE equipID = ?";
            pStmnt = conn.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, equipID);
            isDeleted1 = pStmnt.executeUpdate() > 0;

            // if the equipment is disabled
            preQueryStatement = "SELECT * FROM disaequip WHERE equipID = ?";
            pStmnt = conn.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, equipID);
            ResultSet rs = pStmnt.executeQuery();
            boolean isDisabled = rs.next();
            if(isDisabled){
                preQueryStatement = "DELETE FROM disaequip WHERE equipID = ?";
                pStmnt = conn.prepareStatement(preQueryStatement);
                pStmnt.setInt(1, equipID);
                isDeleted2 = pStmnt.executeUpdate() > 0;
            }else{
                isDeleted2 = true;
            }

            // delete from equipinfo
            preQueryStatement = "DELETE FROM equipinfo WHERE equipID = ?";
            pStmnt = conn.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, equipID);
            isDeleted3 = pStmnt.executeUpdate() > 0;
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
        return isDeleted1 & isDeleted2 & isDeleted3;
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
                        eb.setAvaQuantity(rs2.getInt(1));
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
                    isDisabled = false;
                }else if(eb.getAvaQuantity() > 0){
                    eb.setStatus("Available");
                }else{
                    eb.setStatus("Unavailable");
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

    public ArrayList<EquipBean> searchEquip(String keyword, String status){
        boolean id_only = keyword.charAt(0) == '#';
        boolean statusSearch = status != null;

        ArrayList<EquipBean> origin = queryEquip();
        ArrayList<EquipBean> requireList = new ArrayList<EquipBean>();

        if(!statusSearch){
            if(id_only){
                for(EquipBean eb : origin){
                    if(eb.getEquipID() == Integer.parseInt(keyword.substring(1))){
                        requireList.add(eb);
                    }
                }
            }else{
                for(EquipBean eb : origin){
                    boolean matchName = eb.getEquipName().contains(keyword);
                    boolean matchDesc = eb.getDescription().contains(keyword);
                    boolean matchStatus = eb.getStatus().equals(keyword);
                    if(matchStatus){
                        requireList.add(eb);
                    }else{
                        if(matchName || matchDesc){
                            requireList.add(eb);
                        }
                    }
                }
            }
        }else{
            if(id_only){
                for(EquipBean eb : origin){
                    if(eb.getEquipID() == Integer.parseInt(keyword.substring(1)) && eb.getStatus().equals(status)){
                        requireList.add(eb);
                    }
                }
            }else{
                for(EquipBean eb : origin){
                    boolean matchName = eb.getEquipName().contains(keyword);
                    boolean matchDesc = eb.getDescription().contains(keyword);
                    boolean matchStatus = eb.getStatus().equals(keyword);
                    if(matchName || matchDesc && matchStatus){
                        requireList.add(eb);
                    }
                }
            }
        }


        return requireList;
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

    public int fetchEquipmentID(String equipName){
        Connection conn = null;
        PreparedStatement pStmnt = null;
        int equipID = 0;
        try{
            conn = getConnection();
            String preQueryStatement = "SELECT equipID FROM equipinfo WHERE equipName = ?";
            pStmnt = conn.prepareStatement(preQueryStatement);
            pStmnt.setString(1, equipName);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while(rs.next()){
                equipID = rs.getInt(1);
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
        return equipID;
    }

}

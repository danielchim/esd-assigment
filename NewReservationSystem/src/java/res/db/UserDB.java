/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package res.db;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashSet;
import res.bean.UserBean;

/**
 *
 * @author erd25
 */
public class UserDB {
    private String dbUrl = "";
    private String dbUser = "";
    private String dbPassword = "";
    
    public UserDB(String dbUrl, String dbUser, String dbPassword){
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
    
    public boolean isValidUser(String email, String pwd){
        Connection conn = null;
        PreparedStatement pStmnt = null;
        boolean isValid = false;
        try{
            conn = getConnection();
            String preQueryStatement = "SELECT * FROM USERINFO WHERE email = ? and password = ?";
            pStmnt = conn.prepareStatement(preQueryStatement);
            pStmnt.setString(1, email);
            pStmnt.setString(2, pwd);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            if(rs.next()){
                isValid = true;
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
        return isValid;
    }
    public void CreateUserInfoTable(){
        Statement stmnt = null;
        Connection conn = null;
        try{
            conn = getConnection();
            stmnt = conn.createStatement();
            String sql 
                    = "CREATE TABLE IF NOT EXISTS userInfo ("
                    + "Id varchar(5) NOT NULL,"
                    + "username varchar(25) NOT NULL,"
                    + "password varchar(25) NOT NULL,"
                    + "PRIMARY KEY (Id)"
                    + ")";
            stmnt.execute(sql);
            stmnt.close();
            conn.close();
            System.out.println("Table is created");
        }catch(SQLException ex){
            while(ex != null){
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    public boolean addUserInfo(UserBean ub){
        Connection conn = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try{
            conn = getConnection();
            String preQueryStatement = "INSERT INTO USERINFO VALUES (?,?,?,?,?,?,?,?,?)";
            pStmnt = conn.prepareStatement(preQueryStatement);
            pStmnt.setString(1, ub.getUserID());
            pStmnt.setString(2, ub.getFirstName());
            pStmnt.setString(3, ub.getLastName());
            pStmnt.setString(4, ub.getPassword());
            pStmnt.setString(5, ub.getEmail());
            pStmnt.setString(6, ub.getTel());
            pStmnt.setString(7, ub.getType());
            pStmnt.setDate(8,  new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            boolean isDisabled = ub.getStatus().equals("Disabled");
            if(isDisabled){
                pStmnt.setInt(9, 1);
            }else{
                pStmnt.setInt(9, 0);
            }
            isSuccess = pStmnt.executeUpdate() > 0;
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
    
    public boolean updateUser(UserBean ub){
        Connection conn = null;
        PreparedStatement pStmnt = null;
        boolean isUpdated = false;
        String ufn = ub.getFirstName();
        String uln = ub.getLastName();
        String utel = ub.getTel();
        try{
            conn = getConnection();
            String preQueryStatement = "UPDATE userinfo SET userId = ?, firstName = ?, lastName = ?, password = ?, tel = ?, type = ?, disabled = ? WHERE email = ?";
            pStmnt = conn.prepareStatement(preQueryStatement);
            pStmnt.setString(1, ub.getUserID());
            if(ufn != null){
                pStmnt.setString(2, ub.getFirstName());
            }else{
                pStmnt.setString(2, "");
            }
            if(uln != null){
                pStmnt.setString(3, ub.getLastName());
            }else{
                pStmnt.setString(3, "");
            }
            if(utel != null){
                pStmnt.setString(5, ub.getTel());
            }else{
                pStmnt.setString(5, "");
            }
            pStmnt.setString(4, ub.getPassword());
            pStmnt.setString(6, ub.getType());
            pStmnt.setString(8, ub.getEmail());
            boolean isDisabled = false;
            if(ub.getStatus() != null){
                isDisabled = ub.getStatus().equals("Disabled");
            }
            
            if(isDisabled){
                pStmnt.setInt(7, 1);
            }else{
                pStmnt.setInt(7, 0);
            }
            isUpdated = pStmnt.executeUpdate() > 0;
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
    
    public boolean delUser(String userID){
        Connection conn = null;
        PreparedStatement pStmnt = null;
        boolean isDeleted = false;
        try{
            // delete from userinfo
            conn = getConnection();
            String preQueryStatement = "DELETE FROM userinfo WHERE userId = ?";
            pStmnt = conn.prepareStatement(preQueryStatement);
            pStmnt.setString(1, userID);
            isDeleted = pStmnt.executeUpdate() > 0;
            
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
    
    public UserBean queryUserByEmail(String email){
        Connection conn = null;
        PreparedStatement pStmnt = null;
        UserBean ub = null;
        try{
            conn = getConnection();
            String preQueryStatement = "SELECT * FROM userinfo WHERE email = ?";
            pStmnt = conn.prepareStatement(preQueryStatement);
            pStmnt.setString(1, email);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            if(rs.next()){
                ub = new UserBean();
                ub.setUserID(rs.getString(1));
                ub.setFirstName(rs.getString(2));
                ub.setLastName(rs.getString(3));
                ub.setEmail(rs.getString(5));
                ub.setTel(rs.getString(6));
                ub.setType(rs.getString(7));
                ub.setRegisterDate(rs.getDate(8));
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
        return ub;
    }
    
    public ArrayList<UserBean> queryUser(){
        Connection conn = null;
        PreparedStatement pStmnt = null;
        UserBean ub = null;
        ArrayList<UserBean> ubs = new ArrayList<UserBean>();
        try{
            conn = getConnection();
            String preQueryStatement = "SELECT * FROM userinfo";
            pStmnt = conn.prepareStatement(preQueryStatement);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while(rs.next()){
                ub = new UserBean();
                ub.setUserID(rs.getString(1));
                ub.setFirstName(rs.getString(2));
                ub.setLastName(rs.getString(3));
                ub.setPassword(rs.getString(4));
                ub.setEmail(rs.getString(5));
                ub.setTel(rs.getString(6));
                ub.setType(rs.getString(7));
                ub.setRegisterDate(rs.getDate(8));
                ubs.add(ub);
                
                boolean isDisabled = rs.getInt(9) == 1;
                
                if(isDisabled){
                    ub.setStatus("Disabled");
                }else{
                    ub.setStatus("Active");
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
        return ubs;
    }
    
    public ArrayList<UserBean> searchUser(String keyword, String status, String type, int odCount0, int odCount1){
        boolean validOdCount = odCount0 >= 0 || odCount1 >= 0;
        boolean id_only = false;
        boolean hvKeyword = keyword != null && !keyword.equals("");
        boolean hvStatus = status != null && !status.equals("");
        boolean hvType = type != null && !type.equals("");
        if(hvKeyword){
            id_only = keyword.charAt(0) == '#';
        }
        
        ArrayList<UserBean> origin = queryUser();
        ArrayList<UserBean> requireList = new ArrayList<UserBean>();
        
        if(hvKeyword){
            if(id_only){
                for(UserBean ub : origin){
                    if(ub.getUserID().contains(keyword.substring(1))){
                        requireList.add(ub);
                    }
                }
            }else{
                for(UserBean ub : origin){
                    String ufn = ub.getFirstName();
                    String uln = ub.getLastName();
                    String utel = ub.getTel();
                    String uEmail = ub.getEmail();
                    boolean matchFName = false;
                    boolean matchLName = false;
                    boolean matchTel = false;
                    boolean matchEmail = false;
                    if(ufn != null){
                        matchFName = ufn.contains(keyword);
                    }
                    if(uln != null){
                        matchLName = uln.contains(keyword);
                    }
                    if(utel != null){
                        matchTel = utel.contains(keyword);
                    }
                    if(uEmail != null){
                        matchEmail = uEmail.contains(keyword);
                    }
                    if(matchFName || matchLName || matchTel || matchEmail){
                        requireList.add(ub);
                    }
                }
            }
        }
        if(hvStatus){
            for(UserBean ub : origin){
                if(ub.getStatus().equals(status)){
                    requireList.add(ub);
                }
            }
        }
        if(hvType){
            for(UserBean ub : origin){
                if(ub.getType().equals(type)){
                    requireList.add(ub);
                }
            }
        }
        if(validOdCount){
            
        }
        
        LinkedHashSet<UserBean> hashSet = new LinkedHashSet<UserBean>(requireList);
         
        ArrayList<UserBean> listWithoutDuplicates = new ArrayList<>(hashSet);
        
        return listWithoutDuplicates;
    }
}

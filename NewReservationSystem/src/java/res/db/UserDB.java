/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package res.db;

import java.io.IOException;
import java.sql.*;
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
    public boolean addUserInfo(String id, String user, String pwd){
        Connection conn = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try{
            conn = getConnection();
            String preQueryStatement = "INSERT INTO USERINFO VALUES (?,?,?)";
            pStmnt = conn.prepareStatement(preQueryStatement);
            pStmnt.setString(1, id);
            pStmnt.setString(2, user);
            pStmnt.setString(3, pwd);
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
                ub.setUserID(rs.getInt(1));
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
}

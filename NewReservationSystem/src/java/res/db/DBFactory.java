package res.db;

import res.bean.RecordBean;

import java.io.IOException;
import java.sql.*;

public abstract class DBFactory {
    private String dbUrl = "";
    private String dbUser = "";
    private String dbPassword = "";

    public DBFactory(String dbUrl, String dbUser, String dbPassword){
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public Connection getConnection() throws SQLException, IOException {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }
    public int fetchIdLength(String column, String table){
        Connection conn = null;
        PreparedStatement pStmnt = null;
        int length = 0;
        try{
            conn = getConnection();
            String preQueryStatement = "SELECT MAX(" + column + ") FROM " + table;
            pStmnt = conn.prepareStatement(preQueryStatement);
            ResultSet rs = pStmnt.executeQuery();
            while(rs.next()){
                length = rs.getInt(1);
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
        return length;
    }
}

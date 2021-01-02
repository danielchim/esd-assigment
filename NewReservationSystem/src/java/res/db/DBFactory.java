package res.db;

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
            Class.forName("com.mysql.jdbc.Driver");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }
    public boolean fetchIdLength(String column, String table){
        Connection conn = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try{
            conn = getConnection();
            String preQueryStatement = "SELECT sum(char_length(?)) FROM ?";
            pStmnt = conn.prepareStatement(preQueryStatement);
            pStmnt.setString(1, column);
            pStmnt.setString(2, table);
            ResultSet rs = pStmnt.executeQuery();
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

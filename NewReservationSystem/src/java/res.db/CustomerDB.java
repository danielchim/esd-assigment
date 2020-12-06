package db;

import bean.CustomerBean;

import java.sql.*;
import java.io.IOException;
import java.util.ArrayList;

public class CustomerDB {
    private String url ="";
    private String username="";
    private String password ="";
    boolean isSucc;

    public CustomerDB(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Connection getConnection() throws SQLException, IOException{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(url,username,password);
    }

    public boolean createCustTable(){
        String CustId;
        String Name;
        String Tel;
        int Age;
        Statement stmnt = null;
        Connection conn = null;
        try{
            conn = getConnection();
            stmnt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS customer (" + "custID VARCHAR(5) NOT NULL," + "name VARCHAR(25) NOT NULL," + "tel VARCHAR(10) NOT NULL," + "age int(11) NOT NULL," + "PRIMARY KEY(custId)"+")";
            stmnt.execute(sql);
            stmnt.close();
            conn.close();
            isSucc = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            isSucc = false;
        } catch (IOException e) {
            e.printStackTrace();
            isSucc = false;
        }
        return  isSucc;
    }

    public boolean addRecord(String CustId, String Name, String Tel,int Age){
        Connection conn = null;
        PreparedStatement pStmnt = null;
        isSucc = false;
        try{
            conn = getConnection();
            String prequeryStatement = "INSERT INTO CUSTOMER VALUES (?,?,?,?)";
            pStmnt = conn.prepareStatement(prequeryStatement);
            pStmnt.setString(1,CustId);
            pStmnt.setString(2,Name);
            pStmnt.setString(3,Tel);
            pStmnt.setInt(4,Age);
            int rowCount = pStmnt.executeUpdate(); //notify affected rows
            if(rowCount >=1){
                isSucc = true;
            };
            //close connection
            pStmnt.close();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSucc;
    }
    //request query
    public CustomerBean getRecord(String CustId){
        CustomerBean cb = null;
        Connection conn = null;
        PreparedStatement pStmnt = null;
        try {
            conn = getConnection();
            String prequeryStatement = "SELECT * FROM CUSTOMER WHERE CUSTID=?";
            pStmnt = conn.prepareStatement(prequeryStatement);
            pStmnt.setString(1,CustId);
            ResultSet rs = pStmnt.executeQuery();
            //ArrayList pets = new ArrayList();
            if(rs.next()){
                cb = new CustomerBean();
                cb.setCustid(rs.getString("custID"));
                cb.setName(rs.getString("name"));
                cb.setTel(rs.getString("tel"));
                cb.setAge(rs.getInt("age"));
            }
            //close connection
            pStmnt.close();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cb;
    }
    //query by name
    public ArrayList queryCustByName(String name){
        CustomerBean cb = new CustomerBean();
        Connection conn = null;
        boolean issucc = false;
        ArrayList <CustomerBean> queryArr = null;
        PreparedStatement pStmnt = null;
        try {
            conn = getConnection();
            String prequeryStatement = "SELECT * FROM CUSTOMER WHERE NAME=?";
            pStmnt = conn.prepareStatement(prequeryStatement);
            pStmnt.setString(1,name);
            ResultSet rs = pStmnt.executeQuery();
            //ArrayList pets = new ArrayList();
            if(rs.next()){
                queryArr = new ArrayList<>();
                cb.setCustid(rs.getString("custID"));
                cb.setName(rs.getString("name"));
                cb.setTel(rs.getString("tel"));
                cb.setAge(rs.getInt("age"));
                queryArr.add(cb);
            }
            //close connection
            pStmnt.close();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return queryArr;
    }

    //query by name
    public ArrayList queryCustByTel(String tel){
        CustomerBean cb = new CustomerBean();
        Connection conn = null;
        boolean issucc = false;
        ArrayList <CustomerBean> queryArr = null;
        PreparedStatement pStmnt = null;
        try {
            conn = getConnection();
            String prequeryStatement = "SELECT * FROM CUSTOMER WHERE TEL=?";
            pStmnt = conn.prepareStatement(prequeryStatement);
            pStmnt.setString(1,tel);
            ResultSet rs = pStmnt.executeQuery();
            //ArrayList pets = new ArrayList();
            if(rs.next()){
                queryArr = new ArrayList<>();
                cb.setCustid(rs.getString("custID"));
                cb.setName(rs.getString("name"));
                cb.setTel(rs.getString("tel"));
                cb.setAge(rs.getInt("age"));
                queryArr.add(cb);
            }
            //close connection
            pStmnt.close();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return queryArr;
    }

    //query ALL items
    public ArrayList queryCust(){
        CustomerBean cb = null;
        Connection conn = null;
        ArrayList <CustomerBean> queryArr = new ArrayList<>();;
        PreparedStatement pStmnt = null;
        try {
            conn = getConnection();
            String prequeryStatement = "SELECT * FROM CUSTOMER";
            pStmnt = conn.prepareStatement(prequeryStatement);
            ResultSet rs = pStmnt.executeQuery();
            //ArrayList pets = new ArrayList();
            while(rs.next()){
                cb = new CustomerBean();
                cb.setCustid(rs.getString("custID"));
                cb.setName(rs.getString("name"));
                cb.setTel(rs.getString("tel"));
                cb.setAge(rs.getInt("age"));
                queryArr.add(cb);
            }
            //close connection
            pStmnt.close();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return queryArr;
    }

    //delete record
    public boolean delRecord(String custId){
        Connection conn = null;
        PreparedStatement pStmnt = null;
        isSucc = false;
        try{
            conn = getConnection();
            String prequeryStatement = "DELETE FROM CUSTOMER WHERE CUSTID=?";
            pStmnt = conn.prepareStatement(prequeryStatement);
            pStmnt.setString(1,custId);;
            int rowCount = pStmnt.executeUpdate(); //notify affected rows
            if(rowCount >=1){
                isSucc = true;
            };
            //close connection
            pStmnt.close();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSucc;
    }

    //edit records
    public boolean editRecord(CustomerBean cb){
        Connection conn = null;
        PreparedStatement pStmnt = null;
        isSucc = false;
        try{
            conn = getConnection();
            String prequeryStatement = "UPDATE CUSTOMER SET custID= "+ cb.getCustid() + ",name=?,tel=?,age=? WHERE custID=?";
            pStmnt = conn.prepareStatement(prequeryStatement);
            pStmnt.setString(1,cb.getName());
            pStmnt.setString(2,cb.getTel());
            pStmnt.setInt(3,cb.getAge());
            pStmnt.setString(4,cb.getCustid());
            int rowCount = pStmnt.executeUpdate(); //notify affected rows
            if(rowCount >=1){
                isSucc = true;
            };
            //close connection
            pStmnt.close();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSucc;
    }

    //droppin tables
    public void dropCustTable(){
        Connection conn = null;
        PreparedStatement pStmnt = null;
        isSucc = false;
        try{
            conn = getConnection();
            String prequeryStatement = "DROP TABLE IF EXISTS customer";
            pStmnt = conn.prepareStatement(prequeryStatement);
            int rowCount = pStmnt.executeUpdate(); //notify affected rows
            System.out.println("Drop successfully!");
            //close connection
            pStmnt.close();
            conn.close();
        } catch (SQLException throwables) {
            System.out.println(throwables);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

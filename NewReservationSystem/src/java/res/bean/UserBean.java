/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package res.bean;

import java.io.Serializable;
import java.sql.Date;

/**
 * @author erd25
 */
public class UserBean implements Serializable {
    private int userID;
    private String firstName;
    private String lastName;
    private String password;
    private String tel;
    private String email;
    private String status;
    private String type;
    private Date registerDate;
    private int overdueCount;


    public UserBean() {
    }

    public UserBean(int userID, String firstName, String lastName, String password, String tel, String email, String status, String type, Date registerDate, int overdueCount) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.tel = tel;
        this.email = email;
        this.status = status;
        this.type = type;
        this.registerDate = registerDate;
        this.overdueCount = overdueCount;
    }

    public int getUserID() {
        return userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getTel() {
        return tel;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public int getOverdueCount() {
        return overdueCount;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public void setOverdueCount(int overdueCount) {
        this.overdueCount = overdueCount;
    }

}
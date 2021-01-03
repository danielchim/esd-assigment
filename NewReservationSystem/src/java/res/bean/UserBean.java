/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package res.bean;

import java.io.Serializable;
import java.sql.Date;
/**
 *
 * @author erd25
 */
public class UserBean implements Serializable {
    private int userID;
    private String firstName;
    private String lastName;
    private String tel;
    private String email;
    private String type;
    private Date registerDate;
    
    
    
    public UserBean(){}
    public UserBean(int userID, String firstName, String lastName, String tel, String email, String type, Date registerDate){
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.tel = tel;
        this.email = email;
        this.type = type;
        this.registerDate = registerDate;
    }
    
    public int getUserID(){ return userID; }
    public String getFirstName(){ return firstName; }
    public String getLastName(){ return lastName; }
    public String getTel(){ return tel; }
    public String getEmail(){ return email; }
    public String getType(){ return type; }
    public Date getRegisterDate(){ return registerDate; }
    
    public void setUserID(int userID){ this.userID = userID; }
    public void setFirstName(String firstName){ this.firstName = firstName; }
    public void setLastName(String lastName){ this.lastName = lastName; }
    public void setTel(String tel){ this.tel = tel; }
    public void setEmail(String email){ this.email = email; }
    public void setType(String type){ this.type = type; }
    public void setRegisterDate(Date registerDate){ this.registerDate = registerDate; }
    
}

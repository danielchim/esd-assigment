package bean;
public class CustomerBean {
    private String custid;
    private String name;
    private String tel;
    private int age;

    public CustomerBean(){
        this("", "", "", 0);
    }
    public CustomerBean(String custid, String name, String tel, int age){
        this.custid = custid;
        this.name = name;
        this.tel = tel;
        this.age = age;
    }

    public String getCustid(){ return custid; }
    public String getName(){ return name; }
    public String getTel(){ return tel; }
    public int getAge(){ return age; }

    public void setCustid(String custid){ this.custid = custid; }
    public void setName(String name){ this.name = name; }
    public void setTel(String tel){ this.tel = tel; }
    public void setAge(int age){ this.age = age; }

    public String toString(){
        return "ID: " + custid + "\nName: " + name + "\nTel: " + tel + "\nAge: " + age + "\n";
    }
}


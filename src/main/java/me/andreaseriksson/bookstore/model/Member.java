package me.andreaseriksson.bookstore.model;

public class Member {

    private String fname;
    private String lname;
    private String address;
    private String city;
    private int zip;
    private String phone;
    private String email;
    private int userid;
    private String password;

    public String getFname() { return fname; }
    public void setFname(String fname) { this.fname = fname; }

    public String getLname() { return lname; }
    public void setLname(String lname) { this.lname = lname; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public int getZip() { return zip; }
    public void setZip(int zip) { this.zip = zip; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getUserid() { return userid; }
    public void setUserid(int userid) { this.userid = userid; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

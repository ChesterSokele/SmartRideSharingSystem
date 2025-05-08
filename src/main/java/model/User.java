// User.java
package model;

public abstract class User {
    protected int userID;
    protected String name;
    protected String email;
    protected String password;
    protected String phoneNumber;
    protected String role;

    public User(int userID, String name, String email, String password, String phoneNumber, String role) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    // Getters
    public int getUserID() { return userID; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getRole() { return role; }
}


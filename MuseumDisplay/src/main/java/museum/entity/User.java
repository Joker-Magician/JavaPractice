package museum.entity;

import java.time.LocalDateTime;

public class User {
    private int userId;
    private String userName;
    private String password;
    private String email;
    private String role;
    private LocalDateTime createAt;

    public User() {}

    public User(int userId, String userName, String password, String email, String role, LocalDateTime createAt) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.role = role;
        this.createAt = createAt;
    }

    public int getUserId() { return userId; }
    public void setUserId(int userId) {this.userId = userId;}

    public String getUserName() {return userName;}
    public void setUserName(String userName) {this.userName = userName;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getRole() {return role;}
    public void setRole(String role) {this.role = role;}

    public LocalDateTime getCreateAt() {return createAt;}
    public void setCreateAt(LocalDateTime createAt) {this.createAt = createAt;}

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", createAt=" + createAt +
                '}';
    }

    public boolean isAdmin() { return "admin".equalsIgnoreCase(this.role); }
}

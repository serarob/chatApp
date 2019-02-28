package project;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Login {

    private String username;
    private String password;
    private String role;

    private DatabaseAccess db = new DatabaseAccess();
   

    public Login() {

    }

    public boolean loginCredentials() throws SQLException {
        boolean answer;
        Scanner input = new Scanner(System.in);
        System.out.println("Text yout name: ");
        this.username = input.nextLine();
        System.out.println("Text your password:");
        this.password = input.nextLine();

        if (serverLogin()) {
            answer = true;
        } else {
            answer = false;
        }
        return answer;
    }

    public String getUserName() {
        return this.username;
    }

    ;
    
    public String getRole() {
        return this.role;
    }

    ;
    
    public void setRole(String role) {
        this.role = role;
    }

    ;
    
    public String getUserPasswd() {
        return this.password;
    }

    ;
    
    private boolean serverLogin() throws SQLException {

        String userName = null;
        String pass = null;
        String rol = null;
        db.connect();
        String sql = "SELECT * from project.user where username=? and upassword=?";
        PreparedStatement pstmt = db.connect().prepareStatement(sql);
        pstmt.setString(1, username);
        pstmt.setString(2, password);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            userName = rs.getString("username");
            pass = rs.getString("upassword");
            rol = rs.getString("assignrole");
        }

        if (this.username.equals(userName) && this.password.equals(pass)) {
            setRole(rol);
            System.out.println("Sucessful Login");
            db.connect().close();
            return true;
        } else {
            System.out.println("Wrong username or password.");
            db.connect().close();
            return false;
        }
    }
}

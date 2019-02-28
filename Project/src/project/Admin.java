package project;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Admin {

    private DatabaseAccess db = new DatabaseAccess();

    public int createUser(String username, String password, String assignrole) {
        int rows = 0;
        try {
            PreparedStatement pstmt;
            pstmt = db.connect().prepareStatement("SELECT * FROM project.user");
            ResultSet rs = pstmt.executeQuery();
            int iduser;
            rs.last();
            iduser = 1 + rs.getInt("iduser");
            pstmt = db.connect().prepareStatement("INSERT INTO project.user (iduser, username, upassword,assignrole) VALUES (?,?,?,?)");
            pstmt.setInt(1, iduser);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.setString(4, assignrole);

            rows = pstmt.executeUpdate();
            db.connect().close();
        } catch (SQLException e) {

        }
        return rows;
    }

    public void viewUsers() {

        PreparedStatement pstmt;
        String SQL = "SELECT * from project.user";
        try {
            pstmt = db.connect().prepareStatement(SQL);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("iduser" + "|" + "username" + "|" + "role" + "|");
            while (rs.next()) {
                int userId = rs.getInt("iduser");
                String username = rs.getString("username");
                String password = rs.getString("upassword");
                String role = rs.getString("assignrole");
                if (username.equals("admin")) {
                    continue;
                }
                System.out.print(userId + "|" + username + "|" + role + "|");
                System.out.println("");
            }
            db.connect().close();
        } catch (SQLException ex) {

            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int deleteUser(String username) {
        int rows = 0;
        try {
            db.connect();
            PreparedStatement pstmt;
            pstmt = db.connect().prepareStatement("DELETE FROM project.user WHERE username = ? ");
            pstmt.setString(1, username);
            rows = pstmt.executeUpdate();
            db.connect().close();
        } catch (SQLException e) {

            System.out.println("Wrong execute.");
        }
        return rows;
    }

    public int updateUser(String existUser, String newUserName, String newPassword, String newRole) {
        int rows = 0;
        try {
            db.connect();
            PreparedStatement pstmt = null;

            pstmt = db.connect().prepareStatement("UPDATE project.user SET username= ?, upassword= ? , assignrole= ? WHERE username = ? ");

            pstmt.setString(1, newUserName);
            pstmt.setString(2, newPassword);
            pstmt.setString(3, newRole);
            pstmt.setString(4, existUser);
            rows = pstmt.executeUpdate();
            db.connect().close();
        } catch (SQLException e) {

            System.out.println("Wrong execute.");
        }
        return rows;
    }
}

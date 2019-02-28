package project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UserMenuHelperImpl implements UserMenuHelper {

    @Override
    public String editOptions(String role) {
        System.out.println("\nPlease make a selection: ");
        System.out.println("\t\tPress 0 - to view your messages: ");
        System.out.println("\t\tPress 1 - to send a message: ");
        switch (role) {
            case "userall":
                System.out.println("\t\tPress d to delete a message: ");
            case "userviewedit":
                System.out.println("\t\tPress e to edit a message: ");
            case "userview":
                System.out.println("\t\tSelect user to view messages: ");
            default:
                System.out.println("\t\tPress exit - to exit menu: ");
        }
        Scanner sc = new Scanner(System.in);
        String opt = sc.next();
        return opt;
    }

    @Override
    public boolean checkUser(String username) throws SQLException {

        DatabaseAccess db = new DatabaseAccess();
        Connection conn = db.connect();
        ResultSet rs = null;
        PreparedStatement pstmt = null;

        String Sql = "select * from project.user where username = ?";

        try {
            pstmt = conn.prepareStatement(Sql);
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                db.connect().close();
                return true;

            }

        } catch (SQLException e) {
        } finally {
            db.connect().close();
        }
        return false;
    }

}

package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseAccess {

    private static final String urlProject = "jdbc:mysql://localhost:3306/project";
    private static final String user = "root";
    private static final String password = "1234";

    public Connection connect() {
        try {

            Connection conn = DriverManager.getConnection(urlProject, user, password);
            return conn;
        } catch (SQLException e) {

            System.out.println("Problem with connection on database.");
            return null;
        }
    }

    public void closeConnection(Connection conn) {
        try {
            conn.close();
            System.out.println("The connection has been terminated");
        } catch (SQLException e) {
            System.out.println("The connection hasn't been terminated");
        }
    }

}

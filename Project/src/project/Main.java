package project;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) throws SQLException {

        Login l = new Login();
        Menu menu = new Menu();
        AdminMenu adminMenu=new AdminMenu();
        try {

            if (l.loginCredentials()) {
                User user = new User(l.getUserName(), l.getUserPasswd(), l.getRole());
                adminMenu.setLoggedUser(user);
                adminMenu.printMenu();
            }

        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);

        }

    }
}

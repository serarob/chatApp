package project;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminMenu {

    Admin admin = new Admin();
    User LoggedUser;

    public void setLoggedUser(User LoggedUser) {
        this.LoggedUser = LoggedUser;
    }

    public static void adminMenu() {

        System.out.println("\n\t----- WELCOME TO SUPER ADMIN MENU -----");
        System.out.println("\nPlease make a selection:");
        System.out.println("\t\tPress 1 - To create users: ");
        System.out.println("\t\tPress 2 - To view all users: ");
        System.out.println("\t\tPress 3 - To delete a user: ");
        System.out.println("\t\tPress 4 - To update a user: ");
        System.out.println("\t\tPress 5 - To read messages text file");
        System.out.println("\t\tPress 6 - to exit menu: ");

    }

    private void FileToScreen() throws FileNotFoundException, IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("Messages.txt"))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

    public void choiceAdminMenu() throws SQLException, IOException {

        while (true) {
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();

            boolean exit = true;
            switch (choice) {
                case 1:
                    System.out.println("\t\tEnter username: ");
                    String username = sc.next();
                    System.out.println("\t\tEnter password: ");
                    String upasswd = sc.next();
                    System.out.println("\t\tEnter role: ");
                    String assignrole = sc.next();
                    admin.createUser(username, upasswd, assignrole);
                    break;
                case 2:
                    admin.viewUsers();
                    break;
                case 3:
                    System.out.println("\t\tEnter the username you want to delete: ");
                    String user = sc.next();
                    admin.deleteUser(user);
                    break;
                case 4:
                    System.out.println("\t\tUpdate user :");
                    System.out.println("\t\tEnter a username that you want to change: ");
                    String existingUserName = sc.next();
                    System.out.println("\t\tEnter new username: ");
                    String UserName = sc.next();
                    System.out.println("\t\tEnter new password: ");
                    String newPass = sc.next();
                    System.out.println("\t\tEnter new role: ");
                    String Role = sc.next();
                    admin.updateUser(existingUserName, UserName, newPass, Role);
                    break;
                case 5:
                    FileToScreen();
                    break;
                case 6:
                    System.out.println("Exit");
                    exit = true;
                    break;
            }
            System.out.println("Do you want to exit? Y/N");
            Scanner exitsc = new Scanner(System.in);
            String exit_new = exitsc.next();
            if ((exit_new.equals("Y")) || exit_new.equals("y")) {
                break;
            } else {
                adminMenu();
            }
        }
    }

    public void printMenu() throws SQLException {
        if (this.LoggedUser.getUsername().equals("admin") || this.LoggedUser.getPassword().equals("admin")) {
            adminMenu();
            try {
                choiceAdminMenu();
            } catch (IOException ex) {
                Logger.getLogger(AdminMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Menu m = new Menu();
            m.setLoggedUser(this.LoggedUser);
            m.userMenu();
        }

    }
}

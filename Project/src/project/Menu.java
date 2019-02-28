package project;

import java.sql.SQLException;
import java.util.Scanner;

public class Menu {

    User LoggedUser;
    UserMenuHelperImpl userMenuImpl = new UserMenuHelperImpl();

    public static void welcome() {
        System.out.println("+-------------------------+");
        System.out.println("|    Welcome to the       |");
        System.out.println("|   Menu Application      |");
        System.out.println("+-------------------------+");
    }

    public void setLoggedUser(User LoggedUser) {
        this.LoggedUser = LoggedUser;
    }

    public void userMenu() throws SQLException {
        boolean loop = true;
        while (loop) {
            Message m = null;
            String opt = userMenuImpl.editOptions(this.LoggedUser.getRole());
            Scanner sc = new Scanner(System.in);
            switch (opt) {
                case "exit":
                    loop = false;
                    break;
                case "d":
                    System.out.println("\t\tSelect user to delete messages: ");
                    opt = sc.next();
                    m = new DeleteMessage(this.LoggedUser.getUsername());
                    if (isValidUserViewMsg(opt, m)) {
                        if (m.deleteMessage()) {
                            System.out.println("\t\tMessage deleted.");
                        } else {
                            System.out.println("\t\tDelete unsuccessful.");
                        }
                    }
                    break;
                case "e":
                    System.out.println("\t\tSelect user to edit messages: ");
                    opt = sc.next();
                    m = new EditMessage(this.LoggedUser.getUsername());
                    if (isValidUserViewMsg(opt, m)) {
                        if (m.editMessage()) {
                            System.out.println("\t\tMessage has been edited.");
                        } else {
                            System.out.println("\t\tEdit unsuccessful.");
                        }
                    }
                    break;
                case "0":
                case "1":
                    m = new Message(this.LoggedUser.getUsername());
                    m.composeMessage(opt);
                    break;
                default:
                    m = new Message(this.LoggedUser.getUsername());
                    if (!isValidUserViewMsg(opt, m)) {
                        System.out.println("\t\tPlease enter another value.");
                    }
            }
        }
    }

    public boolean isValidUserViewMsg(String opt, Message m) throws SQLException {
        if (userMenuImpl.checkUser(opt)) {
            if (m.viewMess(opt)) {
                return true;
            }
            System.out.println("\t\tNo messages for user: ");
        } else {
            System.out.println("\t\tNot a valid user: ");
        }
        return false;
    }

}

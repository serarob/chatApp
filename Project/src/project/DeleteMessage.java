package project;

import java.sql.SQLException;
import java.util.Scanner;

public class DeleteMessage extends EditMessage {

    public DeleteMessage(String username) {
        super(username);
    }

    @Override
    public boolean deleteMessage() throws SQLException {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Give the id of the message you want to delete : ");
            idmessage = Integer.parseInt(sc.nextLine());
            if (!m_messages.containsKey(idmessage)) {
                System.out.println("\t\tWrong message id.");
                return false;
            }
            Sql = setDeleteMessage(idmessage);

            if (executeTransaction()) {
                return true;
            }

            return false;
        } catch (NumberFormatException e) {
            System.out.println("Wrong, try again.");
            return false;
        }
    }

    public String setDeleteMessage(int idmessage) throws SQLException {

        Sql = "DELETE from project.message "
                + " where idmessage = "
                + idmessage;

        return Sql;

    }

}

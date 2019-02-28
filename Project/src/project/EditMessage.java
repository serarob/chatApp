package project;

import java.util.Scanner;

public class EditMessage extends Message {

    public EditMessage(String username) {
        super(username);
    }

    @Override
    public boolean editMessage() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Give the id of the message you want to edit: ");

            idmessage = Integer.parseInt(sc.nextLine());
            if (!m_messages.containsKey(idmessage)) {
                System.out.println("\t\tWrong message id.");
                return false;
            }

            System.out.println("Write the new message : ");
            String Message = sc.nextLine();
            db = new DatabaseAccess();
            conn = db.connect();
            Sql = setMessage(Message, idmessage);
            if (executeTransaction()) {
                return true;
            }
            return false;
        } catch (NumberFormatException e) {
            System.out.println("Wrong, try again.");
            return false;
        }

    }

    protected String setMessage(String Message, int idmessage) {
        Sql = "UPDATE project.message SET messagedata = '"
                + Message
                + "'"
                + "WHERE idmessage = "
                + Integer.toString(idmessage);
        return Sql;
    }

}

package project;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Message {

    HashMap<Integer, String> m_messages = new HashMap<>();
    public int idmessage;
    PreparedStatement prepStmt = null;
    ResultSet rs = null;
    String Sql = null;
    DatabaseAccess db = new DatabaseAccess();
    Connection conn = db.connect();
    String username = null;
    String msgTxt = null;

    Message(String username) {
        this.username = username;
    }

    ;
   
    private static java.sql.Timestamp getCurrentTimeStamp() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());
    }

    public void sendMessage() throws SQLException {
        String receiver = setReceiver();
        msgTxt = setMessageText();
        if (prepareSqlSendStm(receiver, findIdUser(receiver))) {
            System.out.println("Message sent.\n");
            WriteToFile(receiver);
        } else {
            System.out.println("Message Not sent.\n");
            System.out.println("Message Not written.\n");
        }

    }

    private void WriteToFile(String receiver) {
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter("Messages.txt", true);
            try (PrintWriter printWriter = new PrintWriter(fileWriter)) {
                printWriter.printf("************************************************************************%n");
                printWriter.printf("From:" + username + "%n");
                printWriter.printf("To:" + receiver + "%n");
                printWriter.printf("Date: " + getCurrentTimeStamp().toString() + "%n");
                printWriter.printf(msgTxt + "%n");
                printWriter.printf("************************************************************************%n");
            }

            System.out.println("Message Written to file.\n");
        } catch (IOException ex) {
            System.out.println("Message Not Written to file.\n");
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int findIdUser(String receiver) throws SQLException {
        prepStmt = db.connect().prepareStatement("SELECT u.iduser FROM project.user as u where u.username=" + "'" + receiver + "'");
        rs = prepStmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("iduser");
        }
        return 0;
    }

    private boolean prepareSqlSendStm(String receiver, int iduser) {
        if (iduser == 0) {
            System.out.println("Wrong username.");
            return false;
        }
        try {
            prepStmt = db.connect().prepareStatement("INSERT INTO project.message (idreceiver, idsender, messagedata, datetime, iduser) VALUES (?,?,?,?,?)");
            prepStmt.setString(1, receiver);
            prepStmt.setString(2, username);
            prepStmt.setString(3, msgTxt);
            prepStmt.setTimestamp(4, getCurrentTimeStamp());
            prepStmt.setInt(5, iduser);
            prepStmt.executeUpdate();
            db.connect().close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public boolean viewMess(String username) {

        boolean hasMessages = false;
        Sql = "SELECT m.idmessage , u.username, m.datetime, m.messagedata,m.idsender"
                + " from project.message as m inner join project.user "
                + "as u on (m.idreceiver = u.username)"
                + "where m.idreceiver="
                + "'" + username
                + "'";

        try {
            prepStmt = conn.prepareStatement(Sql);
            rs = prepStmt.executeQuery();
            System.out.println("Id" +"\tText"+"\tdateTime"+"\tsender");
            while (rs.next()) {
                String userMessage = rs.getString("messagedata");
                String sender = rs.getString("idsender");
                int idMessage = rs.getInt("idmessage");
                db.connect().close();
                m_messages.put(idMessage, userMessage);

                System.out.println(idMessage + " " + userMessage+ " "+ getCurrentTimeStamp().toString()+ " "+sender);
                hasMessages = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return hasMessages;
    }

    protected boolean executeTransaction() {
        try {
            prepStmt = conn.prepareStatement(Sql);
            conn.setAutoCommit(false);
            prepStmt.executeUpdate();
            conn.commit();
            db.connect().close();
            conn.setAutoCommit(true);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
            try {
                conn.rollback();
                conn.setAutoCommit(true);
            } catch (SQLException ex1) {
                Logger.getLogger(EditMessage.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return false;
    }

    public boolean editMessage() {
        System.out.println("You don't have edit permission");
        return false;
    }

    public boolean deleteMessage() throws SQLException {
        System.out.println("You don't have delete permission");
        return false;
    }

    public boolean composeMessage(String opt) throws SQLException {
        if (opt.equalsIgnoreCase("1")) {
            sendMessage();
            return true;
        } else if (opt.equalsIgnoreCase("0")) {
            viewMess(this.username);
            return true;
        }
        return false;
    }

    public String setReceiver() {
        Scanner sc;
        String receiver;
        System.out.println("\t\tEnter username of the receiver:");
        sc = new Scanner(System.in);
        receiver = sc.next();
        return receiver;
    }

    public String setMessageText() {
        Scanner sc;
        System.out.println("\t\tEnter message:");
        sc = new Scanner(System.in);
        return sc.nextLine();
    }
}

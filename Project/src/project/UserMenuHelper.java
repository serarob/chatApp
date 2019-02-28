package project;

import java.sql.SQLException;

public interface UserMenuHelper {

    boolean checkUser(String username) throws SQLException;

    public String editOptions(String role);

}

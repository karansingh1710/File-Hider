package dao;

import db.GetConnection;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public static boolean userExists(String email) throws SQLException {
        Connection connection= GetConnection.getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement("Select email from users");
        ResultSet res=preparedStatement.executeQuery();

        while (res.next()){
            String getEmail=res.getString(1);
            if (getEmail.equals(email)) return true;
        }

        return false;
    }

    public static int saveUser(User user) throws SQLException{
        Connection connection=GetConnection.getConnection();
        PreparedStatement pre=connection.prepareStatement("INSERT INTO Users values (default,?,?)");
        pre.setString(1,user.getName());
        pre.setString(2,user.getEmail());

        return pre.executeUpdate();
    }
}

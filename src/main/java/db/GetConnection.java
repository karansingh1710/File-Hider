package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GetConnection {
   public static Connection connection;

   // This method will connect with database and return the connection object
   public static Connection getConnection(){
       try {
           Class.forName("com.mysql.cj.jdbc.Driver");
       } catch (ClassNotFoundException e) {
           e.printStackTrace();
       }
       /*
       Here url is address of database
       user is root
       pass for database
        */
       String url="jdbc:mysql://localhost:3306/FileHider";
       String user="root";
       String pass="RedmiBook12@";
       try {
           connection = DriverManager.getConnection(url,user,pass);
       } catch (SQLException e) {
           e.printStackTrace();
       }
//       System.out.println("Connection is successful");
       return connection;
   }

   // Method which close the resources
    public static void closeResources(){
       if(connection!=null){
           try {
               connection.close();
           } catch (SQLException e) {
               throw new RuntimeException(e);
           }
       }
    }

    public static void main(String[] args) {
    getConnection();
    }
}

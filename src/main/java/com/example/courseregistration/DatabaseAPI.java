package com.example.courseregistration;

import java.sql.*;
import java.util.Properties;

public class DatabaseAPI {
    private static String userName = "roberto" ;
    private static String password = "roberto12372!" ;
    private static String ipAddress = "47.185.87.62";
    private static String portNo = "3306";
    private static String database = "COURSE_REGISTRATION";

    private DatabaseAPI() {}

    public static java.sql.Connection makeConnection() {
        java.sql.Connection connection = null;

        Properties connProps = new Properties();
        connProps.put("user", userName);
        connProps.put("password", password);
        try{
            connection = DriverManager.getConnection(
                    "jdbc:mysql://" + ipAddress + ":" + portNo + "/" + database
                            + "?allowMultiQueries=true&useSSL=false",
                    connProps
            );
        }
        catch(SQLException e) {
            System.out.println("Couldn't make connection." + e.getMessage());
            System.exit(1);
        }

        System.out.println("Connected to database");

        return connection;
    }

    public void executeQuery(Connection connection, String query) throws SQLException {
       Statement stmnt = connection.createStatement();

       ResultSet rs = stmnt.executeQuery(query);

       while(rs.next()) {

       }

       stmnt.close();


    }

    public void executeUpdate(Connection connection, String query) throws SQLException {
        Statement stmnt = connection.createStatement();

        stmnt.executeUpdate(query);

        stmnt.close();

    }


}

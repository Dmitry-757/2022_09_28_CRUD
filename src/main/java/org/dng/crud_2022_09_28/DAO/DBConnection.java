package org.dng.crud_2022_09_28.DAO;

import java.sql.Connection;

public class DBConnection {
    public static Connection getConnection(){
//        return MSSQLConnection.getConnection();
        return mySQLConnection.getConnection();
    }
}

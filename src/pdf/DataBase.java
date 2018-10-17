/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Shirley
 */
public class DataBase {

    static Connection conn = null;
    static Statement statement = null;

    //connect to the database 
    public static void CreateConnection() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:testDB.db");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("connection not established");
        }
    }
    
    //creates the driver table 
    public static void CreateTable() {
        try {
            CreateConnection();
            statement = conn.createStatement();
            statement.execute("CREATE TABLE DRIVER (NAME TEXT, LICENSE_NO INT, CAR_PLATE varchar(6));");
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("did not execute create stmt");
        }

    }

    //inserts data within the driver table 
    public static void InsertData() {
        try {
            CreateConnection();
            statement = conn.createStatement();
            statement.execute("INSERT INTO DRIVER VALUES ('BOB',1234,'TQT765' );");
            statement.execute("INSERT INTO DRIVER VALUES ('CHRIS',5678, 'YEET' )");
            conn.close();
            System.out.println("database created");
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("did not execute insert stmt");
        }

    }

    
    //deletes driver table if it exists 
    public static void DeleteContent() {
        try {
            CreateConnection();
            statement = conn.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS DRIVER");
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("cant drop table");
        }

    }
    
    //Setup database using previously shown methods 
    public static void CreateDB() {
        DeleteContent();
        CreateTable();
        InsertData();
        

    }
}

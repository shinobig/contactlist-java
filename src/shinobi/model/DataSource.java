package shinobi.model;
import java.sql.*;

public class DataSource {

    public static final String DB_NAME = "contacts.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\Shinobi\\Documents\\javaCourse" +
            "\\contactlist-java\\src\\" + DB_NAME;
    public static final String TABLE_CONTACTS = "contacts";
    public static final String COLUMN_FIRST_NAME = "firstName";
    public static final String COLUMN_LAST_NAME = "lastName";
    public static final String COLUMN_NUMBER = "number";
    public static final String COLUMN_NOTES = "notes";

    private Connection conn;

    public boolean open(){
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            Statement statement = conn.createStatement();

            // Setting test parameters
/*
           entryNewLine(statement, "Victor", "Gakiya", 4773942038L, "Hey dude, this is my first app");

           entryNewLine(statement, "Maria", "Castillo", 1234567890L, "neh, ya es muy tarde para pensar");
           entryNewLine(statement, "Keyzo", "Gakiya", 3332221116L, "Mai bueeeeniiii");
           entryNewLine(statement, "Bon", "Jovi", 7778889996L, "Wooooh, we're halfway theeere");

 */
            System.out.println("Succesfully connected to DB");
            return true;
        } catch(SQLException e){
            System.out.println("Couldn't connect to database: " + e.getMessage());
            return false;
        }
    }

    public void close(){
        try{
            if (conn != null){
                conn.close();
            }
        } catch (SQLException e){
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }

    public static void entryNewLine (Statement statement, String firstName, String lastName, long number, String note){
        try {
            statement.execute("INSERT INTO " + TABLE_CONTACTS + " (" +
                    COLUMN_FIRST_NAME + ", " +
                    COLUMN_LAST_NAME + ", " +
                    COLUMN_NUMBER + ", " +
                    COLUMN_NOTES + ") " +
                    "VALUES(\"" + firstName + "\", \"" + lastName + "\", \"" + String.valueOf(number) + "\", \"" + note + "\");");
        } catch (SQLException throwables) {
            System.out.println(throwables);
        }
    }


}

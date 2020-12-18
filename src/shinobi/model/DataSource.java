package shinobi.model;

import java.sql.*;

public class DataSource {

    private static Connection conn;

    private static PreparedStatement addContact;

    public boolean open() {
        try {
            conn = DriverManager.getConnection(Variables.CONNECTION_STRING);
            Statement statement = conn.createStatement();
            addContact = conn.prepareStatement(Variables.INSERT_CONTACT);

            // Setting test parameters
/*
            entryNewContact(statement, "Victor", "Gakiya", 4773942038L, "Hey dude, this is my first app");
            entryNewContact(statement, "Maria", "Castillo", 1234567890L, "neh, ya es muy tarde para pensar");
            entryNewContact(statement, "Keyzo", "Gakiya", 3332221116L, "Mai bueeeeniiii");
            entryNewContact(statement, "Bon", "Jovi", 7778889996L, "Wooooh, we're halfway theeere");
 */
            System.out.println("Succesfully connected to DB");
            return true;
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {
            closeQuery(addContact);

            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }

    private void closeQuery(PreparedStatement preparedStatement) {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }
    /*
    public static void entryNewContact(Statement statement, String firstName, String lastName, long number, String note) {
        try {
            statement.execute("INSERT INTO " + Variables.TABLE_CONTACTS + " (" +
                    Variables.COLUMN_FIRST_NAME + ", " +
                    Variables.COLUMN_LAST_NAME + ", " +
                    Variables.COLUMN_NUMBER + ", " +
                    Variables.COLUMN_NOTES + ") " +
                    "VALUES(\"" + firstName + "\", \"" + lastName + "\", \"" + String.valueOf(number) + "\", \"" + note + "\");");
        } catch (SQLException throwables) {
            System.out.println(throwables);
        }
    }

     */


    public void test(String hola){
        System.out.println(hola);
    }

    public void entryNewContact(String firstName, String lastName, long number, String note) {

       // System.out.println(Variables.INSERT_CONTACT);

        try{
            addContact.setString(1, firstName);
          //  addContact.setString(2, lastName);
          //  addContact.setLong(3, number);
          //  addContact.setString(4, note);

            System.out.println(addContact);

            addContact.execute();

        } catch (SQLException e ){
            System.out.println("Couldn't add new contact: " + e.getMessage());
            System.out.println(addContact.toString());
        }
    }


    public ResultSet queryAllContacts() {
        Statement statement = null;
        try {
            statement = conn.createStatement();
            return statement.executeQuery("SELECT * FROM " + Variables.TABLE_CONTACTS);
        } catch (SQLException e) {
            System.out.println("Couldn't get all the contacts: " + e.getMessage());
            return null;
        }
    }

}

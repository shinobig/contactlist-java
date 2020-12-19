package shinobi.model;

import java.sql.*;

public class DataSource {

    private Connection conn;

    private PreparedStatement addContact;
    private PreparedStatement removeContact;
    private PreparedStatement editContact;

    public boolean open() {
        try {
            conn = DriverManager.getConnection(Variables.CONNECTION_STRING);
            Statement statement = conn.createStatement();
            addContact = conn.prepareStatement(Variables.INSERT_CONTACT);
            removeContact = conn.prepareStatement(Variables.DELETE_CONTACT);
            editContact = conn.prepareStatement(Variables.EDIT_CONTACT);

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
            closeQuery(removeContact);
            closeQuery(editContact);

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

    public void entryNewContact(String firstName, String lastName, long number, String note) {

        try {
            addContact.setString(1, firstName);
            addContact.setString(2, lastName);
            addContact.setLong(3, number);
            addContact.setString(4, note);
            addContact.executeUpdate();
            closeQuery(addContact);
        } catch (SQLException e) {
            System.out.println("Couldn't add new contact: " + e.getMessage());
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

    public void removeContact(String firstNameOfContact){
        try {
            removeContact.setString(1, firstNameOfContact);
            removeContact.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Couldn't delete contact: " + e.getMessage());
        }
    }

    public void editContact(String contactToFind, Contact contactToEdit){

        System.out.println(contactToEdit.getFirstName());

        try{
            editContact.setString(1, contactToEdit.getFirstName());
            editContact.setString(2, contactToEdit.getLastName());
            editContact.setLong(3, Long.parseLong(contactToEdit.getNumber()));
            editContact.setString(4, contactToEdit.getNote());
            editContact.setString(5, contactToFind);

            editContact.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Couldn't save edit contact: " + e.getMessage());
        }

    }
}

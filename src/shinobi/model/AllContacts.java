package shinobi.model;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AllContacts {

    private List<Contact> allContacts = new ArrayList<>();

    public AllContacts() {
    }

    public List<Contact> getAllContacts() {
        return allContacts;
    }

    public void setAllContacts(List<Contact> allContacts) {
        this.allContacts = allContacts;
    }

    public void addNewContact(Contact newContact) {
        allContacts.add(newContact);
    }

    public void showAllContacts() {
        for (Contact contact : allContacts) {
            System.out.println("Name: " + contact.getFirstName());
        }
    }

    public void queryAllContacts(DataSource dataSource) {
        List<Contact> newListOfContacts = new ArrayList<>();

        try {
            ResultSet results = dataSource.queryAllContacts();
            while (results.next()) {
                newListOfContacts.add(new Contact(results.getString(Variables.COLUMN_FIRST_NAME),
                        results.getString(Variables.COLUMN_LAST_NAME),
                        results.getLong(Variables.COLUMN_NUMBER),
                        results.getString(Variables.COLUMN_NOTES)));
            }
            allContacts = newListOfContacts;

        } catch (SQLException e) {
            System.out.println("Couldn't fetch the contacts: " + e.getMessage());
        }
    }

    public boolean isContactRegistered (String nameToFind, String lastNameToFind){
        for (Contact contact : allContacts){
            if (nameToFind.equals(contact.getFirstName()) && lastNameToFind.equals(contact.getLastName())){
                return true;
            }
        }
        return false;
    }

}

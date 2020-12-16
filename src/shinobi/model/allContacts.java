package shinobi.model;

import java.util.ArrayList;
import java.util.List;

public class allContacts {

    private List<Contact> allContacts = new ArrayList<>();

    public allContacts(List<Contact> allContacts) {
        this.allContacts = allContacts;
    }

    public List<Contact> getAllContacts() {
        return allContacts;
    }

    public void setAllContacts(List<Contact> allContacts) {
        this.allContacts = allContacts;
    }

    public void addNewContact(Contact newContact){
        allContacts.add(newContact);
    }

    public void showAllContacts(){
        for(Contact contact : allContacts){
            System.out.println("Name: " + contact.getFirstName());
        }
    }


}

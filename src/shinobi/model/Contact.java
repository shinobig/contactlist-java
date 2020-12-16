package shinobi.model;

import javafx.beans.property.SimpleStringProperty;

public class Contact {

    private SimpleStringProperty firstName;
    private String lastName;
    private int number;
    private String note;

    public Contact(String firstName, String lastName, int number) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = lastName;
        this.number = number;
    }

    public Contact(String firstName, String lastName, int number, String note) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = lastName;
        this.number = number;
        this.note = note;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(SimpleStringProperty firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

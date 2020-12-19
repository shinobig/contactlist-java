package shinobi;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import shinobi.model.Contact;

public class AddContactDialogController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField numberField;
    @FXML
    private TextArea noteArea;

    public Contact processResult(){
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String number = numberField.getText().trim();
        String note = noteArea.getText().trim();
        return new Contact(firstName, lastName, Long.parseLong(number), note);
    }

    public void setEditFields(Contact contactToEdit){
        firstNameField.setText(contactToEdit.getFirstName());
        lastNameField.setText(contactToEdit.getLastName());
        numberField.setText(contactToEdit.getNumber());
        noteArea.setText(contactToEdit.getNote());

    }

}

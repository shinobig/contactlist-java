package shinobi;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import shinobi.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    private List<Contact> allContacts = new ArrayList<>();

    @FXML
    private TableView mainTableView;
    @FXML
    public TableColumn<Contact, String> firstNameColumn;
    @FXML
    public TableColumn lastNameColumn;
    @FXML
    public TableColumn numberColumn;
    @FXML
    public TableColumn noteColumn;

    // Adding observable list
    private ObservableList<Contact> contactsModel = FXCollections.observableArrayList(
            new Contact("Juan", "Last name test" , 1231, "this is my test note")
    );

    public void initialize(){

        ObservableList<Contact> contactsModel = FXCollections.observableArrayList(
                new Contact("Juan", "Last name test" , 1231, "this is my test note")
        );
/*
        Contact testContact = new Contact("Test contact", "Last name test" , 123123123, "this is my test note");

        System.out.println(testContact.getFirstName());
*/
       // System.out.println(contactsModel);
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("firstName"));

        mainTableView.setItems(contactsModel);

    }



}

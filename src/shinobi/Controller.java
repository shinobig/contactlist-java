package shinobi;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import shinobi.model.AllContacts;
import shinobi.model.Contact;
import shinobi.model.DataSource;
import shinobi.model.Variables;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Controller {

    private AllContacts allContacts = new AllContacts();

    @FXML
    private TableView mainTableView;
    @FXML
    private TableColumn<Contact, String> firstNameColumn;
    @FXML
    private TableColumn lastNameColumn;
    @FXML
    private TableColumn numberColumn;
    @FXML
    private TableColumn noteColumn;
    @FXML
    private BorderPane mainBorderPane;

    private DataSource dataSource;

    public void initialize() {

        // Start DB connection
        dataSource = new DataSource();
        if (!dataSource.open()) {
            System.out.println("Can't open datasource");
            return;
        }

        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("lastName"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("number"));
        noteColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("note"));

        refreshAllContacts();

        dataSource.close();
    }

    @FXML
    public void showNewContactDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Add new contact");
        dialog.setHeaderText("Use this dialog to create a new contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("addContactDialog.fxml"));

        Contact newContact;


        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't open new contact dialog: " + e.getMessage());
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();


        if (result.isPresent() && result.get() == ButtonType.OK) {
            AddContactDialogController addContactController = fxmlLoader.getController();

            newContact = addContactController.processResult();
            dataSource.entryNewContact(newContact.getFirstName(), newContact.getLastName(), Long.parseLong(newContact.getNumber()), newContact.getNote());

            System.out.println("Conctact succesfully added");

        }

        //  refreshAllContacts();

    }

    private void refreshAllContacts() {
        // Query all contacts
        allContacts.queryAllContacts(dataSource);
        mainTableView.getItems().setAll(allContacts.getAllContacts());

    }

    private void testdb(String test) {
        System.out.println(test);
    }

}

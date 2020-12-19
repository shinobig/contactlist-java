package shinobi;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
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
    @FXML
    private ContextMenu tableContextMenu;


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

        //  dataSource.close();
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
            if (allContacts.isContactRegistered(newContact.getFirstName(), newContact.getLastName())) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("The contact: " + newContact.getFirstName() + " " + newContact.getLastName() + " already " +
                        "exists");
                Optional<ButtonType> alreadyExistsBtn = alert.showAndWait();

            } else {
                dataSource.entryNewContact(newContact.getFirstName(), newContact.getLastName(), Long.parseLong(newContact.getNumber()), newContact.getNote());

                System.out.println("Contact successfully added");
                mainTableView.getItems().clear();
                refreshAllContacts();
            }
        }
    }

    @FXML
    private void showEditContactDialog(Contact contactToEdit) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Edit contact");
        dialog.setHeaderText("Use this dialog to edit a new contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("addContactDialog.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't open new contact dialog: " + e.getMessage());
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        AddContactDialogController editContactController = fxmlLoader.getController();
        String contactToFind = contactToEdit.getFirstName();
        editContactController.setEditFields(contactToEdit);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Contact editedContact = editContactController.processResult();

            dataSource.editContact(contactToFind, editedContact);

            mainTableView.getItems().clear();
            refreshAllContacts();
        }
    }

    private void refreshAllContacts() {
        // Query all contacts
        allContacts.queryAllContacts(dataSource);
        mainTableView.getItems().setAll(allContacts.getAllContacts());

        // Set context menu
        mainTableView.setRowFactory(new Callback<TableView<Contact>, TableRow<Contact>>() {
            @Override
            public TableRow<Contact> call(TableView<Contact> tableView) {
                final TableRow<Contact> row = new TableRow<>();
                final ContextMenu rowMenu = new ContextMenu();
                ContextMenu tableMenu = tableView.getContextMenu();
                if (tableMenu != null) {
                    rowMenu.getItems().addAll(tableMenu.getItems());
                    rowMenu.getItems().add(new SeparatorMenuItem());
                }
                MenuItem removeItem = new MenuItem("Delete this contact");
                MenuItem editItem = new MenuItem("Edit this contact");
                removeItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        Contact deleteContact = (Contact) mainTableView.getSelectionModel().getSelectedItem();
                        deleteContact(deleteContact);
                    }
                });

                editItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        Contact editContact = (Contact) mainTableView.getSelectionModel().getSelectedItem();
                        showEditContactDialog(editContact);
                    }
                });

                rowMenu.getItems().addAll(removeItem, editItem);
                row.contextMenuProperty().bind(
                        Bindings.when(Bindings.isNotNull(row.itemProperty()))
                                .then(rowMenu)
                                .otherwise((ContextMenu) null));
                return row;
            }
        });
    }

    @FXML
    public void deleteContact(Contact contactToDelete) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Contact");
        alert.setHeaderText("Delete contact: " + contactToDelete.getFirstName());
        alert.setContentText("Are you sure about deleting this contact?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && (result.get() == ButtonType.OK)) {
            String nameToDelete = String.valueOf(contactToDelete.getFirstName());
            dataSource.removeContact(nameToDelete);
            refreshAllContacts();
        }
    }
}

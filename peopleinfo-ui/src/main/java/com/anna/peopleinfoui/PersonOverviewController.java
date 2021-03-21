package com.anna.peopleinfoui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.anna.peopleinfoui.*;

public class PersonOverviewController {
    @FXML
    private TableView<Person> personTableView;

    @FXML
    private TableColumn<Person, String> firstNameColumn;

    @FXML
    private TableColumn<Person, String> lastNameColumn;

    public void setPersonTableView(TableView<Person> personTableView) {
        this.personTableView = personTableView;
    }

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label birthdayLabel;

    @FXML
    private Button newButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;

    private StageInitializer mainApp;
    public PersonOverviewController(){}

    @FXML
    public void initialize(){

        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());

        showPersonDetails(null);
        personTableView.getSelectionModel().selectedItemProperty().addListener(
                (((observableValue, oldValue, newValue) -> showPersonDetails(newValue)))
        );

    }

    @FXML
    private void handleDeletePerson(){
        int selectedIndex = personTableView.getSelectionModel().getSelectedIndex();
        if(selectedIndex>=0){
            personTableView.getItems().remove(selectedIndex);
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getStage());
            alert.setTitle("Error!");
            alert.setHeaderText("Nothing to delete!");
            alert.setContentText("Please select a user to delete");

            alert.showAndWait();
        }
    }

    private void showPersonDetails(Person person){
        if (person != null){
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
            streetLabel.setText(person.getStreet());
            cityLabel.setText(person.getCity());
            postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
            birthdayLabel.setText(DateUtil.format(person.getBirthday()));

        }else{
            firstNameLabel.setText("No data");
            lastNameLabel.setText("No data");
            streetLabel.setText("No data");
            cityLabel.setText("No data");
            postalCodeLabel.setText("No data");
            birthdayLabel.setText("No data");

        }
    }

    @FXML
    private void handleNewPerson(){
        Person tempPerson = new Person();
        boolean isOkClicked = mainApp.showPersonEditPage(tempPerson);
        if (isOkClicked){
            mainApp.getPersonData().add(tempPerson);
        }
    }

    @FXML
    private void handleEditPerson(){
        Person selectedPerson = personTableView.getSelectionModel().getSelectedItem();
        if(selectedPerson != null){
            boolean isOkClicked = mainApp.showPersonEditPage(selectedPerson);
            if(isOkClicked){
                showPersonDetails(selectedPerson);
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getStage());
            alert.setTitle("Error!");
            alert.setHeaderText("Nothing to edit!");
            alert.setContentText("Please select a user to edit!");

            alert.showAndWait();
        }
    }


    public void setMainApp(StageInitializer mainApp) {
        this.mainApp = mainApp;
        personTableView.setItems(mainApp.getPersonData());
    }
}

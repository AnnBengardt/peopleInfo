package com.anna.peopleinfoui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.anna.peopleinfoui.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.json.JsonParser;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class PersonOverviewController {
    private String apiURL = "http://localhost:8080/api/persons";

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
    private void handleDeletePerson() throws IOException {
        int selectedIndex = personTableView.getSelectionModel().getSelectedIndex();
        if(selectedIndex>=0){
            deletePerson(apiURL, personTableView.getSelectionModel().getSelectedItem().getId());
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

    private void deletePerson(String request, Long id) throws IOException {
        URL url = new URL(request + "/" + id.toString());
        System.out.println(url);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("DELETE");
        urlConnection.connect();
        System.out.println(urlConnection.getResponseCode());
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
    private void handleNewPerson() throws IOException {
        Person tempPerson = new Person();
        boolean isOkClicked = mainApp.showPersonEditPage(tempPerson);
        if (isOkClicked){
            mainApp.getPersonData().add(tempPerson);
            createPerson(apiURL, tempPerson);
        }
    }

    private void createPerson(String request, Person tempPerson) throws IOException {
        URL url = new URL(request);
        System.out.println(url);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setRequestProperty("Content-Type", "application/json; utf-8");
        urlConnection.setRequestProperty("Accept", "application/json");
        urlConnection.setDoOutput(true);
        System.out.println(tempPerson.toString());
        JSONObject jsonObject = new JSONObject(tempPerson.toString());
        OutputStream outputStream = urlConnection.getOutputStream();
        outputStream.write(jsonObject.toString().getBytes("UTF-8"));
        outputStream.close();
        System.out.println(urlConnection.getResponseCode());
    }

    @FXML
    private void handleEditPerson() throws IOException {
        Person selectedPerson = personTableView.getSelectionModel().getSelectedItem();
        if(selectedPerson != null){
            boolean isOkClicked = mainApp.showPersonEditPage(selectedPerson);
            if(isOkClicked){
                showPersonDetails(selectedPerson);
                editPerson(apiURL, selectedPerson);
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

    private void editPerson(String request, Person selectedPerson) throws IOException {
        URL url = new URL(request + "/" + selectedPerson.getId());
        System.out.println(url);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("PUT");
        urlConnection.setRequestProperty("Content-Type", "application/json; utf-8");
        urlConnection.setRequestProperty("Accept", "application/json");
        urlConnection.setDoOutput(true);
        System.out.println(selectedPerson.toString());
        JSONObject jsonObject = new JSONObject(selectedPerson.toString());
        OutputStream outputStream = urlConnection.getOutputStream();
        outputStream.write(jsonObject.toString().getBytes("UTF-8"));
        outputStream.close();
        System.out.println(urlConnection.getResponseCode());
    }


    public void setMainApp(StageInitializer mainApp) {
        this.mainApp = mainApp;
        personTableView.setItems(mainApp.getPersonData());
    }
}

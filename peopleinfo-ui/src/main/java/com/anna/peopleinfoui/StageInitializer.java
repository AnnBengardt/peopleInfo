package com.anna.peopleinfoui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import com.anna.peopleinfoui.ClientsApplication.StageReadyEvent;
import com.anna.peopleinfoui.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.Scanner;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent>{
    @Value("classpath:/rootLayout.fxml")
    private Resource rootResource;

    @Value("classpath:/main.fxml")
    private Resource mainResource;

    @Value("classpath:/personEditPage.fxml")
    private Resource personEditPageResource;

    private Stage stage;
    private BorderPane rootLayout;

    private ObservableList<Person> personData = FXCollections.observableArrayList();
    private String applicationTitle;
    private String apiURL = "http://localhost:8080/api/persons";

    public ObservableList<Person> getPersonData() {
        return personData;
    }

    public StageInitializer(@Value("${spring.application.ui.title}") String applicationTitle) throws IOException {
        this.applicationTitle = applicationTitle;
        fetchPeople(apiURL);
    }

    public void fetchPeople(String request) throws IOException {
        URL url = new URL(request);
        Scanner input = new Scanner((InputStream) url.getContent());
        String result = "";
        while (input.hasNext()) {
            result += input.nextLine();
        }
        JSONArray jsonArray = new JSONArray(result);
        for (int i = 0; i < jsonArray.length(); i++){
            JSONObject person = jsonArray.getJSONObject(i);
            Long id = person.getLong("id");
            String firstName = person.getString("firstName");
            String lastName = person.getString("lastName");
            String city = person.getString("city");
            String street = person.getString("street");
            Integer postalCode = person.getInt("postalCode");
            LocalDate birthday = DateUtil.parse(person.getString("birthday"));
            personData.add(new Person(
                    id, firstName, lastName, city, street, postalCode, birthday
            ));
        }
    }


    @Override
    public void onApplicationEvent(StageReadyEvent stageReadyEvent) {
            stage = stageReadyEvent.getStage();
            stage.setTitle(applicationTitle);
            stage.getIcons().add(new Image("file:../../resources/icon.png"));

            initRootLayout();
            showPersonOverview();
    }

    public void initRootLayout() {
        try {
            FXMLLoader rootLoader = new FXMLLoader(rootResource.getURL());
            rootLayout = (BorderPane) rootLoader.load();

            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showPersonOverview(){
        try{
            FXMLLoader mainLoader = new FXMLLoader(mainResource.getURL());
            AnchorPane personOverview = (AnchorPane) mainLoader.load();

            rootLayout.setCenter(personOverview);
            PersonOverviewController PController = mainLoader.getController();
            PController.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean showPersonEditPage(Person person){
        try{
            FXMLLoader personEditPageLoader = new FXMLLoader(personEditPageResource.getURL());
            AnchorPane page = (AnchorPane) personEditPageLoader.load();

            Stage dialogueStage = new Stage();
            dialogueStage.setTitle("EDIT");
            dialogueStage.initOwner(stage);
            dialogueStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogueStage.setScene(scene);

            PersonEditPageController controller = personEditPageLoader.getController();
            controller.setDialogueStage(dialogueStage);
            controller.setPerson(person);
            dialogueStage.showAndWait();
            return controller.isOkClicked();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Stage getStage() {
        return stage;
    }
}

package pamagbalen.application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MainWindowController {

    @FXML
    private AnchorPane mainContainer; 

    @FXML
    private HBox subContainer;

    @FXML
    public void initialize() {
        loadTagalogTextArea();
    }

    private void loadTagalogTextArea() {
        try {
            //search button click
            //ige get niya yung word
            // compare niya yung value ng kapampangan, english and tagalog word na meron tayo
            // tapos kapag meron makikita niya sa baba

            FXMLLoader wordofTheDayLoader = new FXMLLoader(getClass().getResource("/pamagbalen/WordofTheDay.fxml"));
            VBox wordofTheDayContainer = wordofTheDayLoader.load();
            WordofTheDayController wordofTheDayController = wordofTheDayLoader.getController();
            wordofTheDayController.setWordOfTheDay();

            subContainer.getChildren().add(wordofTheDayContainer);

            System.out.println("PASSED TEST 1 - MainWindowController");
        } 
        catch (IOException e) {
            e.printStackTrace(); System.out.println("FAILED TEST 1 - Main Window Controller");
        }
    }
}
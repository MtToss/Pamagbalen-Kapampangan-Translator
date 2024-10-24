package pamagbalen.application;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import pamagbalen.application.tool.mainAbstract;

import java.io.IOException;

public class MainWindowController extends mainAbstract {

    @FXML
    private AnchorPane mainContainer; 

    @FXML
    private HBox subContainer;

    @FXML
    private TextField searchTextField;

    @FXML
    private Button searchButton;

    @FXML
    private AnchorPane bottomPaneContainer;

    @FXML
    private VBox vBoxContainer;

    @FXML
    private Pane browsePane;

    @FXML
    private Pane homePane;

    @FXML
    private Label browseLabel, homeLabel;

    @FXML
    private AnchorPane browseAnchorPane;

    @FXML
    private AnchorPane homeAnchorPane;

    char alphabetContainer = 'b';
    VBox wordofTheDayContainer = null;
    VBox contentContainer = null;
    AnchorPane selectorContainer = null;
    AnchorPane listContentContainer = null;

    PauseTransition pause = new PauseTransition(Duration.seconds(1));



    @FXML
    public void initialize() {
        setYAnimation(browsePane);
        setYAnimation(homePane);
        loadArea();
        wordofTheDayContainer.setTranslateY(75);
        wordofTheDayContainer.setTranslateX(100); 
        
    }

    private void loadArea() {
        try {
            FXMLLoader wordofTheDayLoader = new FXMLLoader(getClass().getResource("/pamagbalen/WordofTheDay.fxml"));
            wordofTheDayContainer = wordofTheDayLoader.load();
            WordofTheDayController wordofTheDayController = wordofTheDayLoader.getController();
            wordofTheDayController.setWordOfTheDay();

            subContainer.getChildren().add(wordofTheDayContainer);
            HBox.setMargin(wordofTheDayContainer, new Insets(50, 50, 0, 50));

            System.out.println("PASSED TEST 1 - MainWindowController");
        } 
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("FAILED TEST 1 - MainWindowController");
        }
    }

    @FXML
    public void onBrowsePaneShow() {
        //animatePane(browsePane, true); 
        browseAnchorPane.setStyle("-fx-background-color: ROYALBLUE;");
        browseLabel.setTextFill(Color.WHITE);
    }

    @FXML
    public void onBrowsePaneHide() {
        //animatePane(browsePane, false); 
        browseAnchorPane.setStyle("-fx-background-color: STEELBLUE;");
        browseLabel.setTextFill(Color.BLACK);
    }

    @FXML
    public void onHomePaneShow() {
        //animatePane(homePane, true);
        homeAnchorPane.setStyle("-fx-background-color: ROYALBLUE;");
        homeLabel.setTextFill(Color.WHITE);
    }

    @FXML
    public void onHomePaneHide() {
        //animatePane(homePane, false);
        homeAnchorPane.setStyle("-fx-background-color: STEELBLUE;");
        homeLabel.setTextFill(Color.BLACK);
    }


    @FXML
    private void buttonClicked() {
        String wordContainer = searchTextField.getText();

        try {
            if (contentContainer == null) {
                FXMLLoader contentContainerLoader = new FXMLLoader(getClass().getResource("/pamagbalen/ContentContainer.fxml"));
                contentContainer = contentContainerLoader.load();
                ContentContainerController contentContainerController = contentContainerLoader.getController();

                contentContainerController.getWordSearched(wordContainer);
                
                if(subContainer.getChildren().contains(wordofTheDayContainer)) {
                    PauseTransition pause = new PauseTransition(Duration.seconds(1));

                    pause.setOnFinished(event -> {
                    
                    contentContainer.setTranslateX(-subContainer.getWidth());
                    animateVBox(contentContainer, true);
                    subContainer.getChildren().add(contentContainer);
                    });
                    pause.play();

                }
                
                contentContainer.getProperties().put("fxController", contentContainerController); // we store the controller to the content container for future purposes
            } 
            else if(subContainer.getChildren().contains(contentContainer) && !subContainer.getChildren().contains(wordofTheDayContainer)) {
                // and we need to update it for another search, we can retrieve to controller and update the word.
                ContentContainerController contentContainerController = (ContentContainerController) contentContainer.getProperties().get("fxController"); // this is what the future purposes means like, we get the controller. Since it is initialized 


                animateExitContentContainer(contentContainer);
                
                PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
                pause.setOnFinished(event -> {
                    contentContainer.setTranslateX(-subContainer.getWidth());
                    contentContainerController.getWordSearched(wordContainer);
                    animateVBox(contentContainer, true);
                });
                pause.play();
                
            }

            if (subContainer.getChildren().contains(wordofTheDayContainer)) {

                animateExitWordofTheDayContainer(wordofTheDayContainer);
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                
                pause.setOnFinished(event -> { 
                    subContainer.getChildren().remove(wordofTheDayContainer); 
                    contentContainer.setTranslateX(-subContainer.getWidth());
                    animateVBox(contentContainer, true);         
                });
                pause.play();
                
            }
              
            pause.setOnFinished(event -> {
                contentContainer.setTranslateX(-subContainer.getWidth());
                addAndAnimateContentContainer(contentContainer, subContainer);
            });
            pause.play();

            
            if(subContainer.getChildren().contains(listContentContainer)) {
                contentContainer.setTranslateX(-subContainer.getWidth());
                System.out.println("HI");
                exitanimateLCC(listContentContainer);
                PauseTransition pause = new PauseTransition(Duration.seconds(0.75));
    
                pause.setOnFinished(event -> {
                    subContainer.getChildren().remove(listContentContainer);
                    
                });
                pause.play();
            }

            if(bottomPaneContainer.getChildren().contains(selectorContainer)) {
                bottomPaneContainer.getChildren().removeAll(selectorContainer);
            }

            HBox.setMargin(contentContainer, new Insets(0, 5, 0, 5));
            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void labelHomeClicked() {


        if(subContainer.getChildren().contains(contentContainer)) 
        {
            
            wordofTheDayContainer.setTranslateX(-8000);
            animateExit(contentContainer);

            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> {
                subContainer.getChildren().remove(contentContainer);
                animateEntrance(wordofTheDayContainer);
            });
            pause.play();
        }

        
        if(!subContainer.getChildren().contains(wordofTheDayContainer)) {  
            if(subContainer.getChildren().contains(listContentContainer)) {
                wordofTheDayContainer.setTranslateX(-8000);
                exitanimateLCC(listContentContainer);
                PauseTransition pause = new PauseTransition(Duration.seconds(0.75));
    
                pause.setOnFinished(event -> {
                    subContainer.getChildren().remove(listContentContainer);
                    animateEntrance(wordofTheDayContainer);
                });
                pause.play();
            }
            System.out.println("PUMAPASOK");
            wordofTheDayContainer.setVisible(true);
            
            subContainer.getChildren().add(wordofTheDayContainer);
        }

        if(bottomPaneContainer.getChildren().contains(selectorContainer)) {
            bottomPaneContainer.getChildren().remove(selectorContainer);
        }
    }

    @FXML
    public void labelBrowseClicked() {
        if(selectorContainer == null) {
            FXMLLoader selectorContainerLoader = new FXMLLoader(getClass().getResource("/pamagbalen/Selector.fxml"));
            try {
                selectorContainer = selectorContainerLoader.load();
            } catch (IOException e) {
                
                e.printStackTrace();
            }
            ListController browseController = selectorContainerLoader.getController();
            browseController.setMainWindowController(this); 

        }

        if(subContainer.getChildren().contains(contentContainer)) {
            subContainer.getChildren().remove(contentContainer);
        }
        
        
        
        if(!bottomPaneContainer.getChildren().contains(selectorContainer)) {

            PauseTransition pause = new PauseTransition(Duration.seconds(1));

            pause.setOnFinished(event -> {
                animateSelectorContainer(selectorContainer);
                bottomPaneContainer.getChildren().add(selectorContainer);
            });
            pause.play();

            
            AnchorPane.setTopAnchor(selectorContainer, 0.0);
            AnchorPane.setBottomAnchor(selectorContainer, 0.0);
            AnchorPane.setLeftAnchor(selectorContainer, null);
            AnchorPane.setRightAnchor(selectorContainer, 0.0);


        }

        if(subContainer.getChildren().contains(wordofTheDayContainer)) {
            System.out.println("GUMAGANA");
            animateExitWordofTheDayContainer(wordofTheDayContainer);
            PauseTransition pause = new PauseTransition(Duration.seconds(0.8));
            pause.setOnFinished(event -> {
                wordofTheDayContainer.setVisible(false);
                subContainer.getChildren().remove(wordofTheDayContainer);
            });
            pause.play();
        }

        if (listContentContainer != null && !subContainer.getChildren().contains(listContentContainer)) {
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> {
                animateLCC(listContentContainer);
                subContainer.getChildren().add(listContentContainer);
            });

        }
    }

    @FXML
    public void loadListContentContainer(char letter) {
        try {
            if(subContainer.getChildren().contains(listContentContainer)) {
                subContainer.getChildren().remove(listContentContainer);
            }
            FXMLLoader listContentContainerLoader = new FXMLLoader(getClass().getResource("/pamagbalen/ListContentContainer.fxml"));
            listContentContainer = listContentContainerLoader.load();
            ListContentContainerController listContentContainerController = listContentContainerLoader.getController();

            listContentContainerController.setAlphabet(letter);
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        

        if (!subContainer.getChildren().contains(listContentContainer)) {
            subContainer.getChildren().add(listContentContainer);
        }
    }

    public char getAlphabet(char alphabet) {
        alphabetContainer = alphabet;
        return alphabetContainer;
    }
}

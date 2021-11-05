package it.polimi.ingsw.client.view.gui.ForCards;

import it.polimi.ingsw.client.view.gui.ViewGUI;
import it.polimi.ingsw.shared.model.CardDetails;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;




/**
 * shows the hand when there are 3 cards
 */

public class ControllerThreePlayersChooseCard implements Initializable {
    @FXML
    private RadioButton firstCard;
    @FXML
    private RadioButton secondCard;
    @FXML
    private RadioButton thirdCard;
    @FXML
    private Button goToBoard;
    @FXML
    private ImageView firstCardImage;
    @FXML
    private ImageView secondCardImage;
    @FXML
    private ImageView thirdCardImage;
    @FXML
    private TextArea firstCardDescription;
    @FXML
    private TextArea secondCardDescription;
    @FXML
    private TextArea thirdCardDescription;

    private ToggleGroup cardFor3GameGroup;
    private ArrayList<CardDetails> cardsToShow;
    private Image cardImage;
    private String cardDescription;
    private int i = 1;
    private static ViewGUI viewGUI;




    /**
     * viewGUI setter
     * @param viewGUI
     */

    public void setViewGUI(ViewGUI viewGUI) {
        ControllerThreePlayersChooseCard.viewGUI = viewGUI;
    }




    /**
     * sets buttons' visibility
     */

    public void clientHasChosen(){
        this.goToBoard.setDisable(false);
    }



    public void goToNext(){
        viewGUI.guiConfirm();
    }




    /**
     * method that shows card image and description
     */

    public void showCardImageAndDescription(){
          for (CardDetails c : cardsToShow ){
              cardImage = new Image("client/images/godCards/" + c.getCardName() + ".jpg");
              cardDescription = c.getCardDescription();
              if (i==1){
                  firstCardImage.setImage(cardImage);
                  firstCardDescription.setText(cardDescription);
                  i++;
              }else if (i==2){
                  secondCardImage.setImage(cardImage);
                  secondCardDescription.setText(cardDescription);
                  i++;
              }else if(i==3){
                  thirdCardImage.setImage(cardImage);
                  thirdCardDescription.setText(cardDescription);
              }
          }
    }



    /**
     * method what sets the card to show
     * @param cardToShow
     */

    public void setCardsToShow(ArrayList<CardDetails> cardToShow){
        this.cardsToShow = cardToShow;
        showCardImageAndDescription();
    }


    /**
     * method which is used to pick the card based on the index
     * @return
     */

    public int indexCard(){
        int index=-1;
        if (this.cardFor3GameGroup.getSelectedToggle().equals(firstCard))
            index =0;
        else if (this.cardFor3GameGroup.getSelectedToggle().equals(secondCard))
            index = 1;
        else if (this.cardFor3GameGroup.getSelectedToggle().equals(thirdCard))
            index =2;

        return index;
    }





    /**
     * initializes the objects before a scene starts
     * @param url
     * @param resourceBundle
     */

    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cardFor3GameGroup = new ToggleGroup();
        this.firstCard.setToggleGroup(cardFor3GameGroup);
        this.secondCard.setToggleGroup(cardFor3GameGroup);
        this.thirdCard.setToggleGroup(cardFor3GameGroup);
        //disable button
        this.goToBoard.setDisable(true);
        //set text color
        firstCardDescription.setStyle("-fx-font-family: \"Comic Sans MS\"; -fx-font-size: 16; -fx-text-fill: #ffa2e9;");
        secondCardDescription.setStyle("-fx-font-family: \"Comic Sans MS\"; -fx-font-size: 16; -fx-text-fill: #ffa2e9;");
        thirdCardDescription.setStyle("-fx-font-family: \"Comic Sans MS\"; -fx-font-size: 16; -fx-text-fill: #ffa2e9;");
    }
}

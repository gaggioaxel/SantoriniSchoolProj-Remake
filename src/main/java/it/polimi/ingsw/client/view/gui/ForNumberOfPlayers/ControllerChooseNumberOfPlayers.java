package it.polimi.ingsw.client.view.gui.ForNumberOfPlayers;

import it.polimi.ingsw.client.view.gui.ViewGUI;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;





/**
 * asks the first connected player the number of players with who he/she wants to have a match with
 */

public class ControllerChooseNumberOfPlayers implements Initializable {
    @FXML
    private RadioButton ThreePlayersButton;
    @FXML
    private RadioButton TwoPlayersButton;
    @FXML
    private Label numberPlayerChosen;
    @FXML
    private Button gotoChooseNickName;

    private ToggleGroup favLangToggleGroup;
    private int selectedNumber;
    private static ViewGUI viewGUI;


    /**
     * viewGUI setter
     * @param viewGUI
     */

    public void setViewGUI(ViewGUI viewGUI) {
        ControllerChooseNumberOfPlayers.viewGUI = viewGUI;
    }




    /**
     * This method will update the radioButtonLabel whenever a different
     * radio button is pushed
     */
    public void radioButtonChanged() {
        if (this.favLangToggleGroup.getSelectedToggle().equals(this.TwoPlayersButton)) {
            selectedNumber = 2;
            numberPlayerChosen.setText("The number of players chosen is " + selectedNumber + ".");
        }

        if (this.favLangToggleGroup.getSelectedToggle().equals(this.ThreePlayersButton)) {
            selectedNumber = 3;
            numberPlayerChosen.setText("The number of players chosen is " + selectedNumber + ".");
        }
    }


    /**
     * setter number of players
     */

    public void setNumberOfPlayers(){
        this.gotoChooseNickName.setDisable(false);
    }


    /**
     * synchronization
     */

    public void chosenNumber(){
        viewGUI.guiConfirm();
    }


    /**
     * getter of selectedNumber
     * @return
     */

    public int getSelectedNumber() {
        return selectedNumber;
    }



    /**
     * initializes the objects before a scene starts
     * @param url
     * @param rb
     */

    public void initialize(URL url, ResourceBundle rb){
        //configuring the number of players selection
        numberPlayerChosen.setText("");
        favLangToggleGroup = new ToggleGroup();
        this.ThreePlayersButton.setToggleGroup(favLangToggleGroup);
        this.TwoPlayersButton.setToggleGroup(favLangToggleGroup);
        //initialize the button for change scene
        this.gotoChooseNickName.setDisable(true);


   }


}

package it.polimi.ingsw.client.view.gui.ForUsername;

import it.polimi.ingsw.client.view.gui.ViewGUI;
import it.polimi.ingsw.shared.utils.CircularList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;





/**
 * this controller asks the username
 */

public class ControllerUsername implements Initializable {


    @FXML
    TextField userNameTextField;
    @FXML
    Label userNameTextLabel;
    @FXML
    Button signInButton;
    @FXML
    Button gotoNext;

    private static ViewGUI viewGUI;
    private String userName;
    private CircularList<String> usernamePlayers;

    public void setViewGUI(ViewGUI viewGUI) {
        ControllerUsername.viewGUI = viewGUI;
    }


    public void goToNextScene(){
        userNameTextLabel.setText(userName);
        viewGUI.guiConfirm();
    }


    /**
     * setters
     */


    public String setUserName(){
        userName = userNameTextField.getText();
        while(userName==null){
            userNameTextLabel.setText("insert a username in the text field above.");
            userName = userNameTextField.getText();
        }
        if(!checkUsername()){
            while (usernamePlayers.contains(userName)|| userName ==null) {
                userNameTextField.clear();
                userNameTextLabel.setText("name already taken, choose another one.");
                userName = userNameTextField.getText();
            }
       }
        gotoNext.setDisable(false);
        return userName;
    }


    public void setUsernamePlayers(CircularList<String> usernamePlayers) {
        this.usernamePlayers = usernamePlayers;
    }




    /**
     * getters
     */

    public String getUsername(){
        return userName;
    }

    public CircularList<String> getUsernamePlayers() {
        return usernamePlayers;
    }

    public boolean checkUsername(){
        return usernamePlayers.isEmpty();
    }





    /**
     * initializes the objects before a scene starts
     * @param url
     * @param resourceBundle
     */


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        userNameTextField.setPromptText("userName");
        gotoNext.setDisable(true);
    }
}

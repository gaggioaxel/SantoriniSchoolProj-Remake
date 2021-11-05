package it.polimi.ingsw.client.view.gui.ForEndGame;

import it.polimi.ingsw.client.view.gui.ViewGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;







public class ControllerEndGame implements Initializable {
    @FXML
    Label loserId;
    @FXML
    Label winnerId;

    private static ViewGUI viewGUI;



    public void setViewGUI(ViewGUI viewGUI) {
        ControllerEndGame.viewGUI = viewGUI;
    }


    public void setWinnerId(){
        this.winnerId.setText("you are the winner");
    }

    public void setLoserId(){
        this.loserId.setText("you lost.");
    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loserId.setText("");
        winnerId.setText("");

    }
}


package main.java.client.view.gui.ForEndGame;

import main.java.client.view.gui.ViewGUI;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

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


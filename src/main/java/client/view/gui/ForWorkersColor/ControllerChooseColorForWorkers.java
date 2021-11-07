package main.java.client.view.gui.ForWorkersColor;

import main.java.client.view.gui.ViewGUI;
import main.java.shared.color.Color;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerChooseColorForWorkers implements Initializable {
    @FXML
    ComboBox chooseColor;
    @FXML
    Label chosenColor;
    @FXML
    Label chosenColor1;
    @FXML
    Button confirmColor;


    private String color;
    private ArrayList<String> colors;
    private static ViewGUI viewGUI;
    private Color colorStyle;

    public void setViewGUI(ViewGUI viewGUI) {
        ControllerChooseColorForWorkers.viewGUI = viewGUI;
    }

    public void goToNext(){
        viewGUI.guiConfirm();
    }

    public void selectedColor(){
        confirmColor.setDisable(false);
        color= chooseColor.getValue().toString();
        this.chosenColor.setText("the color selected is " + color);
    }

    public String getColor(){
        return color;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public void setColors(ArrayList<String> colors) {
        this.colors = colors;
        for (String c: colors)
            chooseColor.getItems().add(c);
        chooseColor.setVisibleRowCount(colors.size());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chooseColor.setEditable(false);
        chosenColor.setText("");
        confirmColor.setText("Next");
        confirmColor.setDisable(true);
    }
}

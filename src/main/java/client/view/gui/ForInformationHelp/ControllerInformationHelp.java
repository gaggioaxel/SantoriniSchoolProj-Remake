package main.java.client.view.gui.ForInformationHelp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 *
 */

public class ControllerInformationHelp implements Initializable {

    @FXML
    Pagination helpPagination;
    @FXML
    Label helpText;


    File filesTxt[];


    public VBox createPage(int index){

        Label helpText = new Label();

        try (BufferedReader reader = new BufferedReader(new FileReader(new File("client/helpText/page" + index + ".txt")))) {
            helpText.setWrapText(true);
            helpText.setStyle("-fx-font-family: \"Comic Sans MS\"; -fx-font-size: 20; -fx-text-fill: white;");
            String line;
            while ((line = reader.readLine()) != null)
                helpText.setText("line");
        } catch (IOException e) {
            e.printStackTrace();
        }

        VBox pageBox = new VBox();
        pageBox.getChildren().add(helpText);
        return pageBox;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        helpPagination.setPageFactory((Integer pageIndex)->createPage(pageIndex));
    }



}

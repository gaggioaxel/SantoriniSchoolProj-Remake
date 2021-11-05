package it.polimi.ingsw.client.view.gui.ForUsersInfoTableView;

import it.polimi.ingsw.client.view.ClientInfo.PlayerInfo;
import it.polimi.ingsw.client.view.gui.ViewGUI;
import it.polimi.ingsw.shared.model.CardDetails;
import it.polimi.ingsw.shared.model.PlayerDetails;
import it.polimi.ingsw.shared.utils.CircularList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;





/**
 * shows the information table view
 */

public class UsersInfoTableView implements Initializable {

    private static ViewGUI viewGUI;

    private CircularList<PlayerDetails> playersInfo;

    private ArrayList<PlayerInfo> userInfoTable;

    private PlayerInfo updatePlayer;

    private CircularList<String> names;

    private ArrayList<CardDetails> cardDetails;

    @FXML
    private TableView<PlayerInfo> usersInfoTableView;
    @FXML
    private TableColumn<PlayerInfo , String> username;
    @FXML
    private TableColumn<PlayerInfo , String> workersColor;
    @FXML
    private TableColumn<PlayerInfo , String> cardName;
    @FXML
    private TableColumn<PlayerInfo , String> cardDescription;
    @FXML
    private Button confirmFirstPlayer;
    @FXML
    private Label chooseFirstPlayerLabel;


    /**
     * viewGUI setter
     * @param viewGUI
     */

    public void setViewGUI(ViewGUI viewGUI) {
        UsersInfoTableView.viewGUI = viewGUI;
    }




    /**
     * setters
     */

    public void setPlayersInfo(CircularList<PlayerDetails> playersInfo,CircularList<String> usernames) {
        this.playersInfo = playersInfo;
        names = usernames;
        userInfoTable = new ArrayList<>();
        if (playersInfo.isEmpty()) {
            for (String name : names){
                updatePlayer = new PlayerInfo(name, null, null, null);
                userInfoTable.add(updatePlayer);
            }
        }else {
            for (PlayerDetails p : playersInfo) {
                if (p.hasAColor()){
                    for (CardDetails c: cardDetails) {
                        if (c.getCardName().equals(p.getCard().getCardName()))
                            updatePlayer = new PlayerInfo(p.getUsername(), p.getColor().getColorName(), c.getCardName(),c.getCardDescription());
                    }
                }else if (p.hasCard()){
                    for (CardDetails c: cardDetails) {
                        if(c.getCardName().equals(p.getCard().getCardName()))
                        updatePlayer = new PlayerInfo(p.getUsername(), null, c.getCardName(), c.getCardDescription());
                    }
                }else{
                    updatePlayer = new PlayerInfo(p.getUsername(), null,null,null);
                }
                userInfoTable.add(updatePlayer);
            }
        }

        populatingTable();
    }

    public void setCardDetails(ArrayList<CardDetails> cardDetails) {
        this.cardDetails = cardDetails;
    }




    /**
     * button's implementation visibility
     */

    public void itemSelected(){
        confirmFirstPlayer.setDisable(false);
    }



    public void goToNext(){
        viewGUI.guiConfirm();
    }


    /**
     * button's implementation visibility
     */

    public void forTheFirstPlayer(){
        chooseFirstPlayerLabel.setVisible(true);
        confirmFirstPlayer.setVisible(true);
        confirmFirstPlayer.setDisable(true);
    }


    /**
     *getters
     */

    public String getFirstPlayerName(){
        PlayerInfo firstPlayer = usersInfoTableView.getSelectionModel().getSelectedItem();
        String firstPlayerName = firstPlayer.getName();
        chooseFirstPlayerLabel.setVisible(false);
        confirmFirstPlayer.setVisible(false);
        return firstPlayerName;
    }

    public CircularList<PlayerDetails> getPlayersInfo() {
        return playersInfo;
    }


    public ObservableList<PlayerInfo> getInfo(){
        ObservableList<PlayerInfo> userInfo = FXCollections.observableArrayList();
        userInfo.addAll(userInfoTable);
        return userInfo;
    }


    public ArrayList<CardDetails> getCardDetails() {
        return cardDetails;
    }






    /**
     * populates table
     */

    /**
     *
     */
    public void populatingTable() {
      //populating the tableView
      username.setCellValueFactory(new PropertyValueFactory<PlayerInfo, String>("name"));
      workersColor.setCellValueFactory(new PropertyValueFactory<PlayerInfo, String>("color"));
      cardName.setCellValueFactory(new PropertyValueFactory<PlayerInfo, String>("cardName"));
      cardDescription.setCellValueFactory(new PropertyValueFactory<PlayerInfo, String>("cardDescription"));
      usersInfoTableView.setItems(getInfo());
    }






    /**
     * initializes the objects before a scene starts
     * @param url
     * @param resourceBundle
     */



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chooseFirstPlayerLabel.setText("Choose the first Player from the list below");
        chooseFirstPlayerLabel.setVisible(false);
        confirmFirstPlayer.setVisible(false);
    }
}

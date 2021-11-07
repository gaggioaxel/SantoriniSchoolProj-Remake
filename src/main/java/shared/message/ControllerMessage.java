package main.java.shared.message;

import main.java.shared.utils.CircularList;



/**this message is sent by the controller(Controller+SetupController+TurnManagingController*/




public class ControllerMessage extends Message {

    private String challenger;

    private int numbPlayers;

    private CircularList<String> playersInGame;






    /**controllerMessage's constructor
     *
     * @param ty represents the type of message
     * @param to represents the event that is currently happening
     * @param send represent the message's sender
     */

    public ControllerMessage(MessageTypes ty, Events to, MessageSender send) {
        super(ty, to, send);
    }






    /**setter*/

    public void setChallenger(String s){this.challenger = s;}

    public void setNumbPlayers(int numbPlayers) {
        this.numbPlayers = numbPlayers;
    }

    public void setPlayersInGame(CircularList<String> playersInGame) {
        this.playersInGame = playersInGame;
    }





    /**getters*/

    public String getChallenger(){return this.challenger;}

    public int getNumbPlayers(){return this.numbPlayers;}

    public CircularList<String> getPlayersInGame() {
        return playersInGame;
    }
}

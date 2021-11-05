package it.polimi.ingsw.shared.message;

import it.polimi.ingsw.shared.model.PlayerDetails;



/**this message contains a player's details*/


public class PlayerDetailsModelMessage extends ModelMessage {

    private PlayerDetails playerDetails;



    /**
     * constructor for the PlayerDetailsModelMessage
     *
     * @param ty   represents the type of the message
     * @param to   represents the event that is currently happening
     * @param send represents which entity sends the message
     */


    public PlayerDetailsModelMessage(MessageTypes ty, Events to, MessageSender send) {
        super(ty, to, send);
    }



    /**setter*/

    public void setPlayerDetails(PlayerDetails p){this.playerDetails=p;}




    /**getter*/

    public PlayerDetails getPlayerDetails(){return playerDetails;}
}

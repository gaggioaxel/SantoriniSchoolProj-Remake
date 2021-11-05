package it.polimi.ingsw.shared.message;

public class ClientMessage extends Message {

    private int card;

    private int[] hand;

    private String color;

    private String username;

    private String firstPlayer;

    private int numbPlayers;

    private int selectedPosition;

    private boolean pass = false;

    private int selectedAction;

    private boolean undo=false;


    public ClientMessage(MessageTypes ty, Events to, MessageSender send) {
        super(ty, to, send);
    }

    public void setCard(int card) {
        this.card = card;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setNumbPlayers(int numbPlayers) {
        this.numbPlayers = numbPlayers;
    }

    public void setHand(int[] hand) {
        this.hand = hand;
    }

    public void setFirstPlayer(String firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public void setPass(){this.pass = true;}

    public void setSelectedAction(int selectedAction) {
        this.selectedAction = selectedAction;
    }


    public int getSelectedAction() {
        return selectedAction;
    }

    public String getUsername() {
        return username;
    }

    public int getCard() {
        return card;
    }

    public String getColor() {
        return color;
    }

    public int getNumbPlayers() {
        return numbPlayers;
    }

    public int[] getHand() {
        return hand;
    }

    public String getFirstPlayer() {
        return firstPlayer;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public boolean getPass() {
        return pass;
    }
}

package main.java.shared.message;

import java.io.Serializable;


/**this class is the extended by the other messages*/


public  class Message implements Serializable {

    private final MessageSender sender;

    private final MessageTypes type;

    private final Events event;

    private String playerUsername;




    /**
     * Message's constructor
     * @param ty type of message
     * @param to occurring event
     * @param send message sender
     */


    public Message(MessageTypes ty, Events to, MessageSender send){
        this.type = ty;
        this.event = to;
        this.sender = send;
    }




    /**setter*/

    public void setPlayerUsername(String playerUsername) {
        this.playerUsername = playerUsername;
    }





    /**getters*/

    public String getPlayerUsername() {
        return playerUsername;
    }

    public MessageTypes getMessageType(){return this.type;}

    public MessageSender getMessageSender() {
        return this.sender;
    }

    public Events getMessageEvent(){return this.event;}

}

package main.java.shared.message;

import main.java.shared.model.BoardDetails;



/**this message contains the board details*/




public class BoardModelMessage extends ModelMessage {

    private BoardDetails boardConfirmed;




    /**
     * BoarModelMessage's constructor
     * @param ty
     * @param to
     * @param send
     */

    public BoardModelMessage(MessageTypes ty, Events to, MessageSender send) {
        super(ty, to, send);
    }



    /**setter*/

    public void setBoardConfirmed(BoardDetails board){
        this.boardConfirmed = board;
    }


    /**getter*/

    public BoardDetails getBoardConfirmed(){return boardConfirmed;}
}

package main.java.shared.message;

import main.java.shared.utils.Point;

import java.util.ArrayList;



/**This message contains the board's available positions*/



public class AvailablePosModelMessage extends ModelMessage{

    private ArrayList<Point> availablePositionConfirmed;




    /**
     *  AvailablePosModelMessage's constructor
     * @param ty
     * @param to
     * @param send
     */

    public AvailablePosModelMessage(MessageTypes ty, Events to, MessageSender send) {
        super(ty, to, send);
    }



    /**setter*/

    public void setAvailablePositionConfirmed(ArrayList<Point> availablePositionConfirmed) {
        this.availablePositionConfirmed = availablePositionConfirmed;
    }



    /**getter*/

    public ArrayList<Point> getAvailablePositionConfirmed(){return availablePositionConfirmed;}
}

package it.polimi.ingsw.server.model.turn;


import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.shared.model.Action;

public interface PassiveEffect {

    boolean isInAPassiveInstance();

    boolean getApplicabilityOnEnemies(Player caster, Action lastAction, Board board);
}

package main.java.server.model.turn;


import main.java.server.model.Board;
import main.java.server.model.Player;
import main.java.shared.model.Action;

public interface PassiveEffect {

    boolean isInAPassiveInstance();

    boolean getApplicabilityOnEnemies(Player caster, Action lastAction, Board board);
}

package main.java.server.model.action;

import main.java.server.model.Player;
import main.java.shared.model.Action;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class ActionsCache {

    private final Map<Player, LinkedList<Action>> actionsPerPlayer;

    public ActionsCache(int size) {
        actionsPerPlayer = new HashMap<>(size);
    }

    /**
     * pushes an action in the stack of latest actions
     * @param player that made an action
     * @param actionMade last action that the player made
     */
    public void pushAction(Player player, Action actionMade) {
        if(!(actionsPerPlayer.containsKey(player)) || actionsPerPlayer.get(player) == null) {
            LinkedList<Action> list = new LinkedList<>();
            list.push(actionMade);
            actionsPerPlayer.put(player, list);
        } else
            actionsPerPlayer.get(player).push(actionMade);
    }

    /**
     * clear all the actions of a player, called when a player ends the turn
     * @param player to clear the cache
     */
    public void clearCache(Player player) {
        actionsPerPlayer.put(player, null);
    }

    /**
     * gets the latest particular action based on action type
     * @param player that made the action
     * @param type of the action to get
     * @return the action or null if not found
     */
    public Action getPlayerAction(Player player, Action.ActionType type) {
        if(actionsPerPlayer.containsKey(player))
            for(Action am : actionsPerPlayer.get(player))
                if(am.getActionType().equals(type))
                    return am;
        return null;
    }

    /**
     * gets the latest generic action done
     * @param player that made the action
     * @return the latest action or null
     */
    public Action getPlayerLastAction(Player player) {
        if(actionsPerPlayer.containsKey(player))
            return actionsPerPlayer.get(player).peek();
        return null;
    }

    /**
     * retrieves and removes the latest action
     * @param player whose action must be retrieved
     * @return the latest action or null if it's the starting turn or the starting of the game
     */
    public Action removeLatestAction(Player player) {
        return actionsPerPlayer.get(player)!=null && !actionsPerPlayer.get(player).isEmpty() ? actionsPerPlayer.get(player).pop() : null;
    }

    /*------------------------------------------ TESTING -------------------------------------------------------------------*/

    public Map<Player, LinkedList<Action>> getDaMap() {
        return actionsPerPlayer;
    }
}

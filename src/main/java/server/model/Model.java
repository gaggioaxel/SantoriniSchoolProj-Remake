package main.java.server.model;


import main.java.server.model.turn.strategy.*;
import main.java.shared.message.*;
import main.java.shared.observer.Observable;
import main.java.server.model.action.ActionsCache;
import main.java.server.model.action.ActionsCreator;
import main.java.server.model.turn.PassiveEffect;
import main.java.server.model.turn.building.StandardBuildStrategy;
import main.java.server.model.turn.building.limiter.BuildingLimiter;
import main.java.server.model.turn.building.limiter.SameWorkerAsPreviousMovedLimiter;
import main.java.server.model.turn.movement.StandardMoveStrategy;
import main.java.server.model.turn.movement.limiter.MovementLimiter;
import main.java.server.model.turn.win.WinCondition;
import main.java.server.model.turn.win.limiter.WinLimiter;
import main.java.shared.color.Color;
import main.java.shared.model.Action;
import main.java.shared.model.BoardDetails;
import main.java.shared.model.CardDetails;
import main.java.shared.model.PlayerDetails;
import main.java.shared.utils.Point;
import main.java.shared.utils.Triplet;
import main.java.shared.utils.Tuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;


public class Model extends Observable<ModelMessage> {


    /*------------ Functions to manage game setup (colors selection, cards selection and workers placement) ----------------*/

    private final Deck deck= new Deck();
    private int numbOfPlayers;
    private final ArrayList<Player> players = new ArrayList<>();
    private ArrayList<CardDetails> hand;
    private final Map<Player, Turn> playersCardTurn = new HashMap<>(3);
    private final Map<Player, Turn> playerStdTurnVariations = new HashMap<>(1);
    private final Map<Player, ArrayList<Triplet<TurnPhase, PassiveEffect, AtomicBoolean>>> playersPassiveEffect = new HashMap<>(1);
    private final Map<Player, ArrayList<WinLimiter>> playersWinLimiter = new HashMap<>(1);
    private final Map<Player, ArrayList<WinCondition>> playersWinConditions = new HashMap<>(3);
    private final ArrayList<Point> workersPlacementAllowedPoses = new ArrayList<>(24);


    /**
     * gets the player with this username
     * @param name of the player
     * @return the player instance whose name is name param
     */
    public Player getPlayer(String name){
        for(Player p: players)
            if(p.getUsername().equals(name))
                return p;
        return null;
    }


    /**
     * sets the username
     * @param us username to set
     */
    public void setUsername(String us){

        Player player = new Player(us);
        PlayerDetails playerDetails = new PlayerDetails(null, null, null, null);
        players.add(player);

        try{
            playerDetails = player.getReadableCopy();
        } catch (Exception e) {
            e.printStackTrace();
        }

        PlayerDetailsModelMessage userMessage = new PlayerDetailsModelMessage(MessageTypes.SETUP_REQUEST, Events.USERNAME, MessageSender.MODEL);
        userMessage.setPlayerDetails(playerDetails);
        notify(userMessage);

    }

    /**
     * sets the number of players
     * @param n number of players to set
     */
    public void setNumPlayer(int n){
        numbOfPlayers = n;
        ModelMessage numMessage = new ModelMessage(MessageTypes.SETUP_REQUEST, Events.NUMB_PLAYERS, MessageSender.MODEL);
        numMessage.setNumberOfPlayersConfirmed(numbOfPlayers);
        notify(numMessage);
    }

    /**
     * sets the color to the player
     * @param p layer to set color to
     * @param color to set
     */
    public void setPlayerColor(Player p, String color) {

        PlayerDetails playerDetails = new PlayerDetails(null, null, null, null);

        try{
            p.setColor(new Color(color));
            playerDetails = p.getReadableCopy();

        } catch (Exception e) {
            e.printStackTrace();
        }


        PlayerDetailsModelMessage colorMessage = new PlayerDetailsModelMessage(MessageTypes.SETUP_REQUEST, Events.COLOR, MessageSender.MODEL);
        colorMessage.setPlayerDetails(playerDetails);
        notify(colorMessage);
    }



    /**
     * create a hand of cards based on param given by the challenger
     * @param selectedCards is the list of cards' index in deck
     * @throws IllegalArgumentException if (see below)
     */
    public void selectCardsFromDeck(int[] selectedCards) {
        hand = new ArrayList<>(selectedCards.length);
        ArrayList<CardDetails> cardsName = deck.getAllCardsDetails();
        for(int num : selectedCards)
            hand.add(cardsName.get(num));

        ModelMessage selectedCardsMessage = new ModelMessage(MessageTypes.SETUP_REQUEST, Events.DECK, MessageSender.MODEL);
        selectedCardsMessage.setHandConfirmed(hand);
        notify(selectedCardsMessage);
    }



    /**
     * removes the card from the hand, builds the full card and sets the turn and sends to player
     * @param p layer that picks a card
     * @param cardSelectedFromHand index of hand arraylist
     */
    public void playerPicksACard(Player p, int cardSelectedFromHand) {

        Card card = deck.getCard(hand.remove(cardSelectedFromHand).getCardName());

        p.setCard(card);
        playersCardTurn.put(p, card.getTurn());
        ArrayList<Tuple<TurnPhase, ActionsCreator>> turnPhases = card.getTurn().getAllPhases();
        ArrayList<Triplet<TurnPhase, PassiveEffect, AtomicBoolean>> passiveEffectsPhases = new ArrayList<>(1);
        PassiveEffect pe;
        for(Tuple<TurnPhase, ActionsCreator> el : turnPhases)
            if((pe = el.y.removePassiveEffect()) != null)
                passiveEffectsPhases.add(new Triplet<>(el.x, pe, new AtomicBoolean(false)));
        if(card.getWinChecker().hasWinLimiter())
            playersWinLimiter.put(p, card.getWinChecker().removeWinLimiter());
        playersWinConditions.put(p, card.getWinChecker().removeWinConditions());

        if(!passiveEffectsPhases.isEmpty())
            playersPassiveEffect.put(p, passiveEffectsPhases);

        PlayerDetails pDetails=p.getReadableCopy();


        PlayerDetailsModelMessage cardMessage = new PlayerDetailsModelMessage(MessageTypes.SETUP_REQUEST, Events.CARD, MessageSender.MODEL);
        cardMessage.setPlayerDetails(pDetails);
        notify(cardMessage);
    }

    public void undoCardSelection(Player p) {
        playersCardTurn.remove(p);
        playersPassiveEffect.remove(p);
        playerChosenTurnStrategy.remove(p);
        playersWinLimiter.remove(p);
        playersWinConditions.remove(p);
        p.setCard(null);

    }

    /**
     * shows the available poses to a player set in controller
     */
    public void showAvailablePlacesForWorkersPos() {
        if(workersPlacementAllowedPoses.isEmpty())
            workersPlacementAllowedPoses.addAll(board.getFreePosesForWorkersPlacement());

        ArrayList<Point> updatedPositions = new ArrayList<>(workersPlacementAllowedPoses);


        AvailablePosModelMessage availablePosMessage = new AvailablePosModelMessage(MessageTypes.SETUP_REQUEST, Events.AVAILABLE_POSITION, MessageSender.MODEL);
        availablePosMessage.setAvailablePositionConfirmed(updatedPositions);
        notify(availablePosMessage);

    }


    public void showUpdatedBoard(String whichController){

        BoardDetails updatedBoardDetails;

        updatedBoardDetails = board.getReadableCopy();
        BoardModelMessage boardMessage = new BoardModelMessage(MessageTypes.SETUP_REQUEST, Events.BOARD, MessageSender.MODEL);
        boardMessage.setBoardConfirmed(updatedBoardDetails);
        notify(boardMessage);

    }


    /**
     *
     * @param p layer that placed workers
     * @param selectedPos index of workersPlacementAllowedPoses arraylist
     */
    public void placeWorker(Player p, int selectedPos) {

        ArrayList<Point> updated=null;

        Worker worker = board.getWorkerPos(p.getWorkers()[0]) != null ? p.getWorkers()[1] : p.getWorkers()[0];
        board.getTile(workersPlacementAllowedPoses.get(selectedPos)).setWorker(worker);


        try {

            workersPlacementAllowedPoses.remove(selectedPos);
            updated = new ArrayList<>(workersPlacementAllowedPoses);

        } catch (Exception e) {
            e.printStackTrace();
        }


        AvailablePosModelMessage placeWorkerMessage = new AvailablePosModelMessage(MessageTypes.SETUP_REQUEST, Events.PLACE_WORKER, MessageSender.MODEL);
        placeWorkerMessage.setAvailablePositionConfirmed(updated);
        notify(placeWorkerMessage);


    }

    /**
     * sends the workerDetails to the client
     * @param p layer to send worker to
     */
    public void sendWorkerDetails(Player p){

        PlayerDetails pDetails = p.getReadableCopy();
        PlayerDetailsModelMessage workerMessage = new PlayerDetailsModelMessage(MessageTypes.SETUP_REQUEST, Events.WORKER_DETAILS, MessageSender.MODEL);
        workerMessage.setPlayerDetails(pDetails);
        notify(workerMessage);

    }


    /**
     * remove worker placed
     * @param p layer that wants to undo placement
     */
    public void undoLastPlacement(Player p) {
        Worker w = board.getWorkerPos(p.getWorkers()[1])!=null ? p.getWorkers()[1] : p.getWorkers()[0];
        Point point = board.getWorkerPos(w);
        board.getTile(point).removeWorker();
        workersPlacementAllowedPoses.add(point);
        //notify(new ServerGameObject(board.getReadableCopy()));
    }

    /*----------------------------------------------------------------------------------------------------------------------*/







    /*------------------------------------------- FOR TESTS PURPOSES -------------------------------------------------------*/
    /*----------------------------------------------------------------------------------------------------------------------*/

    public int getNumbOfPlayers() {
        return numbOfPlayers;
    }

    public Deck getDeck() {
        return deck;
    }

    public ArrayList<CardDetails> getHand() {
        return hand;
    }

    public Map<Player, Turn> getPlayersCardTurn() {
        return playersCardTurn;
    }

    public ArrayList<Point> getWorkersPlacementAllowedPoses() {
        return workersPlacementAllowedPoses;
    }

    public int getIndexFirstStandardTurnAction() {
        return indexFirstStandardTurnAction;
    }

    public Board getBoard() {
        return board;
    }

    public Map<Player, ArrayList<Triplet<TurnPhase, PassiveEffect, AtomicBoolean>>> getPlayersPassiveEffect() {
        return playersPassiveEffect;
    }

    public Map<Player, Turn> getPlayerStdTurnVariations() {
        return playerStdTurnVariations;
    }

    public Map<Player, ArrayList<WinLimiter>> getPlayersWinLimiter() {
        return playersWinLimiter;
    }

    public Map<Player, ArrayList<WinCondition>> getPlayersWinConditions() {
        return playersWinConditions;
    }

    /*----------------------------------------------------------------------------------------------------------------------*/
    /*----------------------------------------------------------------------------------------------------------------------*/









    /*------------------------------- Functions that manage turns rotation in game -----------------------------------------*/


    private ArrayList<Action> latestAllowedActions;
    private int indexFirstStandardTurnAction = -2;
    private final Board board = new Board();
    private final ActionsCache cache = new ActionsCache(3);
    private final Map<Player, TurnStrategy> playerChosenTurnStrategy = new HashMap<>(3);
    private boolean currPlayerHasLost = false;


    /**
     * generates the actions, getting the turn, the phase, limiters,
     * if the phase is optional adds a skipping action
     * if the turn is at his first phase and the turn generated by the card has different phases flow than
     * standard version of turn, available actions of this are added to allow player to not activate
     * the card and play with regular rules
     * (external building limiters are not present in the current implementation so are set to null, but can be easily implemented)
     * @param p layer whose available actions must be showed
     */
    public void showActions(Player p) {
        ArrayList<Action> updatedActions = null;
        Turn currT = getTurn(p);
        Tuple<TurnPhase, ActionsCreator> phaseTup = currT.pollNextPhase();
        ArrayList<MovementLimiter> pe = getLimiters(p, phaseTup.x);
        latestAllowedActions = phaseTup.y.getActions(p, board,
                cache, pe, null);

        if(phaseTup.x.isOptional())
            latestAllowedActions.add(ActionsCreator.getSkippableAction());

        if(currT.isTurnStarting()) {
            if (currT instanceof DifferentActionsTurn) {
                indexFirstStandardTurnAction = latestAllowedActions.size();
                latestAllowedActions.addAll(getStandardTurnVersion(p));
            } else {
                indexFirstStandardTurnAction = -1;
            }
            if(playersPassiveEffect.containsKey(p))
                playersPassiveEffect.get(p).get(0).z.set(false);
        }

        if(!phaseTup.x.isOptional() && latestAllowedActions.isEmpty())
            currPlayerHasLost = true;

        try{
            updatedActions = new ArrayList<>(latestAllowedActions);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*model message*/
        PossibleActionsModelMessage showActionsMessage = new PossibleActionsModelMessage(MessageTypes.TURN_REQUEST, Events.SHOW_POSSIBLE_ACTIONS, MessageSender.MODEL);
        showActionsMessage.setPlayerUsername(p.getUsername());
        showActionsMessage.setPossibleActionConfirmed(updatedActions);
        notify(showActionsMessage);
    }




    /**
     * @param p layer whose turn must be returned
     * @return the actions of the standard turn available for players whose card modifies the normal flow (move->build)
     */
    private ArrayList<Action> getStandardTurnVersion(Player p) {
        Turn t;
        if(playerStdTurnVariations.containsKey(p))
            t = playerStdTurnVariations.get(p);
        else {
            ArrayList<BuildingLimiter> stdBls = new ArrayList<>(1);
            stdBls.add(new SameWorkerAsPreviousMovedLimiter());
            t = new StandardActionsTurn(new ActionsCreator(new StandardMoveStrategy(), null, null, null),
                    new ActionsCreator(null, null, new StandardBuildStrategy(), stdBls));
            playerStdTurnVariations.put(p, t);
        }
        Tuple<TurnPhase, ActionsCreator> pt = t.pollNextPhase();
        return new ArrayList<>(pt.y.getActions(p, board, cache, getLimiters(p, pt.x), null));
    }


    /**
     * @param p layer asking for limiters
     * @param turnPhase tof passive effects (even if there is implemented only one passive effect (Athena))
     * @return all the external limiters applied by passive effects of other players cards
     */
    private ArrayList<MovementLimiter> getLimiters(Player p, TurnPhase turnPhase) {
        ArrayList<MovementLimiter> pe = new ArrayList<>(1);
        for(Player p1 : playersPassiveEffect.keySet())
            if(!p1.equals(p))
                for(Triplet<TurnPhase, PassiveEffect, AtomicBoolean> trip : playersPassiveEffect.get(p1))
                    if(trip.x.equals(turnPhase) && trip.z.get())
                        pe.add((MovementLimiter) trip.y);
        return pe;
    }


    /**
     * @param p layer whose turn must be retrieved
     * @return the chosenTurnStrategy if is set, the CardTurn if not already chosen a strategy
     */
    private Turn getTurn(Player p) {
        if(playerChosenTurnStrategy.containsKey(p) && playerChosenTurnStrategy.get(p)!=null)
            return playerChosenTurnStrategy.get(p).getTurnStrategy();
        else
            return playersCardTurn.get(p);
    }


    /**
     * retrieve the turn strategy, if the turn is starting clears previous turn cache then select the turnStrategy
     * based on the index of the action chosen, and performs the action on the board
     * perform the action chosen by the player in the available
     * and then sets the passive effect if has one compatible with the action
     * @param p player that performs the action
     * @param number index of latestAllowedActions
     */
    public void performAction(Player p, int number) {
        BoardDetails boardUpdated;

        Turn t = getTurn(p);

        if(t.isTurnStarting()) {
            cache.clearCache(p);
            if (indexFirstStandardTurnAction >= 0 && number >= indexFirstStandardTurnAction)
                playerChosenTurnStrategy.put(p, new TurnStrategy(playerStdTurnVariations.get(p)));
            else
                playerChosenTurnStrategy.put(p, new TurnStrategy(t));
        }

        Action actionMade = latestAllowedActions.get(number);
        Action fm, cacheableAction=null;
        if(actionMade.getActionType()!= Action.ActionType.PASS) {
            fm = actionMade.hasForcedMove() ? new Action(board.getTile(actionMade.getForcedMove().getFrom()).getWorker(),
                    actionMade.getActionType(), actionMade.getForcedMove().getFrom(), actionMade.getForcedMove().getTo(), null, null) : null;
            cacheableAction = new Action(board.getTile(actionMade.getFrom()).getWorker(), actionMade.getActionType(),
                    actionMade.getFrom(), actionMade.getTo(), fm, actionMade.getBuildLevel());
        }
        switch (actionMade.getActionType()) {
            case MOVE:
                Worker w = board.getTile(actionMade.getFrom()).removeWorker();
                if(actionMade.hasForcedMove()) {
                    Action forced = actionMade.getForcedMove();
                    Worker w1 = board.getTile(forced.getFrom()).removeWorker();
                    board.getTile(forced.getTo()).setWorker(w1);
                }
                board.getTile(actionMade.getTo()).setWorker(w);

                cache.pushAction(p, cacheableAction);
                break;
            case BUILD:
                board.getTile(actionMade.getTo()).build(actionMade.getBuildLevel());
                cache.pushAction(p, cacheableAction);
                break;
            case PASS:
                while (t.pollNextPhase()!=null);
                break;
        }
        setPassiveEffect(p, cacheableAction);

        boardUpdated = board.getReadableCopy();


        BoardModelMessage performActionMessage = new BoardModelMessage(MessageTypes.TURN_REQUEST, Events.ASK_TO_SELECT_ACTION, MessageSender.MODEL);
        performActionMessage.setPlayerUsername(p.getUsername());
        performActionMessage.setBoardConfirmed(boardUpdated);
        notify(performActionMessage);

    }


    /**
     * sets the passive effect if has one
     * @param p to set
     * @param action made
     */
    private void setPassiveEffect(Player p, Action action) {
        for(Player p1 : playersPassiveEffect.keySet()) {
            if(p1.equals(p)) {
                Triplet<TurnPhase, PassiveEffect, AtomicBoolean> trip = playersPassiveEffect.get(p1).get(0);
                if(trip.x.getType().toString().equals(action.getActionType().toString()))
                    trip.z.set(trip.y.getApplicabilityOnEnemies(p, action, board));
            }
        }
    }

    /**
     * @param p layer whose last action must be checked
     * @return true if not at the starting game or his last action stored is a move, false otherwise
     */
    public boolean hasPlayerJustMoved(Player p) {
        Action lastAct = cache.getPlayerLastAction(p);
        return lastAct!=null && lastAct.getActionType().equals(Action.ActionType.MOVE);
    }


    /**
     * @param p layer that controller wants to check if has won
     * @return true if not meet any win limiter and meet at least one win condition
     */
    public boolean hasPlayerWon(Player p) {
        return (!meetAnyWinLimiter(p) && meetAtLeastOneWinCondition(p));
    }



    /**
     * @param p layer whose win limiter must be excluded between the checked
     * @return true if there's at least one match, false otherwise
     */
    private boolean meetAnyWinLimiter(Player p) {
        for(Player p1 : playersWinLimiter.keySet())
            if (!p1.equals(p))
                for(WinLimiter wl : playersWinLimiter.get(p1))
                    if(wl.meetWinLimiter(cache.getPlayerAction(p, Action.ActionType.MOVE), board))
                        return true;
        return false;
    }


    /**
     * @param p layer whose win conditions must be checked
     * @return true if meet at least one of his available win conditions
     */
    private boolean meetAtLeastOneWinCondition(Player p) {
        AtomicBoolean win = new AtomicBoolean(false);
        playersWinConditions.get(p).forEach(wc -> {
            if(wc.meetWinCondition(cache.getPlayerAction(p, Action.ActionType.MOVE), board))
                win.set(true);
        });
        return win.get();
    }


    /**
     * for controller purposes, to check if turn must be changed
     * @param p player to check if his turn is ended
     * @return true if the player has no more phases
     */
    public boolean isTurnEnded(Player p) {
        return playerChosenTurnStrategy.containsKey(p) && playerChosenTurnStrategy.get(p).getNextPhase()==null;
    }


    /**
     * called by controller to inform model the turn has changed, so it resets the turn phases
     * @param prevPlayer to reset phases
     */
    public void proceedNexTurn(Player prevPlayer) {
        playersCardTurn.get(prevPlayer).reloadTurnPhases();
        if(playerStdTurnVariations.containsKey(prevPlayer) && playerStdTurnVariations.get(prevPlayer)!=null)
            playerStdTurnVariations.get(prevPlayer).reloadTurnPhases();
        playerChosenTurnStrategy.get(prevPlayer).reloadTurnPhases();
        playerChosenTurnStrategy.remove(prevPlayer);
        indexFirstStandardTurnAction = -2;
        currPlayerHasLost = false;
    }


    /**
     * for loss purpose
     * @param player whose actions must be undone
     */
    public void rollbackTurnActions(Player player, boolean hasLost) {

        if(hasLost){
            while(undoAnAction(player)!=null);
        } else
            undoAnAction(player);
        BoardDetails boardAfterLoss = board.getReadableCopy();
        BoardModelMessage rollBackTurnActionsMessage = new BoardModelMessage(MessageTypes.TURN_REQUEST, Events.REMOVE_LOSER, MessageSender.MODEL);
        rollBackTurnActionsMessage.setBoardConfirmed(boardAfterLoss);
        notify(rollBackTurnActionsMessage);


    }


    /**
     * undo-er of single actions for remove player usage only since undo functionality is only client level
     * @param player whose action must be undone
     * @return the undone action or null if is has rolled back the whole turn
     */
    private Action undoAnAction(Player player) {
        Action ac = cache.removeLatestAction(player);
        if (ac == null)
            return null;
        if(ac.getActionType().equals(Action.ActionType.BUILD)) {
            board.getTile(ac.getTo()).removeTopLevel();
        } else if(ac.getActionType().equals(Action.ActionType.MOVE)) {
            board.getTile(ac.getFrom()).setWorker(board.getTile(ac.getTo()).removeWorker());
            if(ac.hasForcedMove()) {
                Action fm = ac.getForcedMove();
                board.getTile(fm.getFrom()).setWorker(board.getTile(fm.getTo()).removeWorker());
            }
        }
        playersCardTurn.get(player).undoPhase();
        return ac;
    }





    /**
     * called if a player cannot move in a binding phase
     * under std circumstances should be called after rollbackTurnActions
     * @param p layer to remove
     */
    public void removePlayer(Player p) {
        playersCardTurn.remove(p);
        playerChosenTurnStrategy.remove(p);
        playerStdTurnVariations.remove(p);
        playersPassiveEffect.remove(p);
        try {
            for (Worker w : p.getWorkers())
                board.getTile(board.getWorkerPos(w)).removeWorker();
        } catch (Exception ignored) {}
    }


    /**
     * checks if currPlayerHasLost is set to true by the getActions(), if so, notifies it to all players
     * @return true if getActions set to true on last query
     */
    public boolean hasPlayerLost(Player p) {
        ModelMessage lossMessage = new ModelMessage(MessageTypes.TURN_REQUEST, Events.LOSS, MessageSender.MODEL);
        lossMessage.setPlayerUsername(p.getUsername());
        notify(lossMessage);

        return currPlayerHasLost;

    }


    /**
     * notifies to all players that the game is finished
     */
    public void endGame() {
        ModelMessage endGameMessage = new ModelMessage(MessageTypes.TURN_REQUEST, Events.END_GAME, MessageSender.MODEL);
        notify(endGameMessage);
    }


    /*----------------------------------------------------------------------------------------------------------------------*/





    /*------------------------------------------- FOR TESTS PURPOSES -------------------------------------------------------*/
    /*----------------------------------------------------------------------------------------------------------------------*/

    public Map<Player, TurnStrategy> getPlayerChosenTurnStrategy() {
        return playerChosenTurnStrategy;
    }

    public ActionsCache getCache() {
        return cache;
    }

    public Turn getGetTurn(Player p) {
        return getTurn(p);
    }

    public boolean getCurrPlayerHasLost() {
        return currPlayerHasLost;
    }

    public void setCurrPlayerHasLost() {
        currPlayerHasLost = true;
    }

    public ArrayList<Action> getLatestAllowedActions(){
        return this.latestAllowedActions;
    }

    /*----------------------------------------------------------------------------------------------------------------------*/


}
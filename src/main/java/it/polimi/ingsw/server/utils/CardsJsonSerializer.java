package it.polimi.ingsw.server.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.server.model.Card;
import it.polimi.ingsw.server.model.action.ActionsCreator;
import it.polimi.ingsw.server.model.turn.PassiveEffect;
import it.polimi.ingsw.server.model.turn.building.Building;
import it.polimi.ingsw.server.model.turn.building.DomeBuilderStrategy;
import it.polimi.ingsw.server.model.turn.building.StandardBuildStrategy;
import it.polimi.ingsw.server.model.turn.building.UnderHimselfBuildStrategy;
import it.polimi.ingsw.server.model.turn.building.limiter.*;
import it.polimi.ingsw.server.model.turn.movement.*;
import it.polimi.ingsw.server.model.turn.movement.limiter.CantMoveUpLimiter;
import it.polimi.ingsw.server.model.turn.movement.limiter.MovementLimiter;
import it.polimi.ingsw.server.model.turn.movement.limiter.SameWorkerBuilderLimiter;
import it.polimi.ingsw.server.model.turn.strategy.*;
import it.polimi.ingsw.server.model.turn.win.StandardWinCondition;
import it.polimi.ingsw.server.model.turn.win.TwoOrMoreLevelsJumpDownCondition;
import it.polimi.ingsw.server.model.turn.win.WinCondition;
import it.polimi.ingsw.server.model.turn.win.WinConditionsStorage;
import it.polimi.ingsw.server.model.turn.win.limiter.PerimeterWinLimiter;
import it.polimi.ingsw.server.model.turn.win.limiter.WinLimiter;
import it.polimi.ingsw.shared.utils.Tuple;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import static it.polimi.ingsw.server.model.turn.strategy.TurnPhase.TurnPhaseType.BUILD;
import static it.polimi.ingsw.server.model.turn.strategy.TurnPhase.TurnPhaseType.MOVE;

public class CardsJsonSerializer {

    /**
     * serializes all the cards with their effect
     * @param args
     * @throws IOException
     */
    public static void main( String[] args ) throws IOException {

        GsonBuilder builder = new GsonBuilder()
                .registerTypeAdapter(Card.class, new CardTypeJsonSerializer())
                .registerTypeAdapter(Turn.class, new InterfaceAdapterJson<Turn>())
                .registerTypeAdapter(PassiveEffect.class, new InterfaceAdapterJson<PassiveEffect>())
                .registerTypeAdapter(Movement.class, new InterfaceAdapterJson<Movement>())
                .registerTypeAdapter(MovementLimiter.class, new InterfaceAdapterJson<MovementLimiter>())
                .registerTypeAdapter(Building.class, new InterfaceAdapterJson<Building>())
                .registerTypeAdapter(BuildingLimiter.class, new InterfaceAdapterJson<BuildingLimiter>())
                .registerTypeAdapter(WinCondition.class, new InterfaceAdapterJson<WinCondition>())
                .registerTypeAdapter(WinLimiter.class, new InterfaceAdapterJson<WinLimiter>())
                .setPrettyPrinting();
        Gson gson = builder.create();
        Card c;
        ArrayList<MovementLimiter> mls = new ArrayList<>();
        ArrayList<BuildingLimiter> blsStandard = new ArrayList<>();
        ArrayList<WinCondition> wcStd = new ArrayList<>(1);
        wcStd.add(new StandardWinCondition());


        //Apollo
        blsStandard.add(new SameWorkerAsPreviousMovedLimiter());

        Turn t = new StandardActionsTurn(new ActionsCreator(new SwapperMoveStrategy(), null, null, null), new ActionsCreator(null, null, new StandardBuildStrategy(), blsStandard));
        WinConditionsStorage wc = new WinConditionsStorage(wcStd, null);
        Tuple<Turn, WinConditionsStorage> tuple = new Tuple<>(t, wc);
        c = new Card("Apollo", tuple);
        BufferedWriter bw = new BufferedWriter(new FileWriter("./src/main/resources/server/" + "Apollo" + "_godCard.json"));
        gson.toJson(c, bw);
        bw.flush();
        bw.close();


        //Artemis
        t=new StandardActionsTurn(new ActionsCreator(new ExtraMoveStrategy(), null, null, null), new ActionsCreator(null, null, new StandardBuildStrategy(), blsStandard));
        wc = new WinConditionsStorage(wcStd, null);
        tuple = new Tuple<>(t, wc);
        c = new Card("Artemis", tuple);
        bw = new BufferedWriter(new FileWriter("./src/main/resources/server/" + "Artemis" + "_godCard.json"));
        gson.toJson(c, bw);
        bw.flush();
        bw.close();



        //Athena
        mls.add(new CantMoveUpLimiter(CantMoveUpLimiter.PassiveEffectTarget.ENEMIES));

        t=new StandardActionsTurn(new ActionsCreator(new StandardMoveStrategy(), mls, null, null), new ActionsCreator(null, null, new StandardBuildStrategy(), blsStandard));
        wc = new WinConditionsStorage(wcStd, null);
        tuple = new Tuple<>(t, wc);
        c = new Card("Athena", tuple);
        bw = new BufferedWriter(new FileWriter("./src/main/resources/server/" + "Athena" + "_godCard.json"));
        gson.toJson(c, bw);
        bw.flush();
        bw.close();


        //Atlas
        t=new StandardActionsTurn(new ActionsCreator(new StandardMoveStrategy(), null, null, null), new ActionsCreator(null, null, new DomeBuilderStrategy(), blsStandard));
        wc = new WinConditionsStorage(wcStd, null);
        tuple = new Tuple<>(t, wc);
        c = new Card("Atlas", tuple);
        bw = new BufferedWriter(new FileWriter("./src/main/resources/server/" + "Atlas" + "_godCard.json"));
        gson.toJson(c, bw);
        bw.flush();
        bw.close();




        //Demeter
        LinkedList<Tuple<TurnPhase, ActionsCreator>> phases = new LinkedList<>();
        Tuple<TurnPhase, ActionsCreator> phase = new Tuple<>(new TurnPhase(MOVE, false), new ActionsCreator(new StandardMoveStrategy(), null, null, null));
        phases.add(phase);
        phase = new Tuple<>(new TurnPhase(BUILD, false), new ActionsCreator(null, null, new StandardBuildStrategy(), blsStandard));
        phases.add(phase);
        ArrayList<BuildingLimiter> bls1 = new ArrayList<>(blsStandard);
        bls1.add(new DifferentPlaceBuildLimiter());
        phase = new Tuple<>(new TurnPhase(BUILD, true), new ActionsCreator(null, null, new StandardBuildStrategy(), bls1));
        phases.add(phase);
        t = new ExtraActionsTurn(phases);
        wc = new WinConditionsStorage(wcStd, null);
        tuple = new Tuple<>(t, wc);
        c = new Card("Demeter", tuple);
        bw = new BufferedWriter(new FileWriter("./src/main/resources/server/" + "Demeter" + "_godCard.json"));
        gson.toJson(c, bw);
        bw.flush();
        bw.close();

        bls1.clear();



        //Hephaestus
        phases = new LinkedList<>();
        phase = new Tuple<>(new TurnPhase(MOVE, false), new ActionsCreator(new StandardMoveStrategy(), null, null, null));
        phases.add(phase);
        phase = new Tuple<>(new TurnPhase(BUILD, false), new ActionsCreator(null, null, new StandardBuildStrategy(), blsStandard));
        phases.add(phase);

        bls1.add(blsStandard.get(0));
        bls1.add(new SamePlaceBuildLimiter());
        bls1.add(new NotDomeBuildLimiter());
        phase = new Tuple<>(new TurnPhase(BUILD, true), new ActionsCreator(null, null, new StandardBuildStrategy(), bls1));
        phases.add(phase);
        t = new ExtraActionsTurn(phases);
        wc = new WinConditionsStorage(wcStd, null);
        tuple = new Tuple<>(t, wc);
        c = new Card("Hephaestus", tuple);
        bw = new BufferedWriter(new FileWriter("./src/main/resources/server/" + "Hephaestus" + "_godCard.json"));
        gson.toJson(c, bw);
        bw.flush();
        bw.close();

        bls1.clear();



        //Minotaur
        t = new StandardActionsTurn(new ActionsCreator(new PusherMoveStrategy(), null, null, null), new ActionsCreator(null, null, new StandardBuildStrategy(), blsStandard));
        wc = new WinConditionsStorage(wcStd, null);
        tuple= new Tuple<>(t, wc);
        c = new Card("Minotaur", tuple);
        bw = new BufferedWriter(new FileWriter("./src/main/resources/server/" + "Minotaur" + "_godCard.json"));
        gson.toJson(c, bw);
        bw.flush();
        bw.close();



        //Pan
        ArrayList<WinCondition> wcEx = new ArrayList<>(wcStd);
        wcEx.add(new TwoOrMoreLevelsJumpDownCondition());
        t = new StandardActionsTurn(new ActionsCreator(new StandardMoveStrategy(), null, null, null), new ActionsCreator(null, null, new StandardBuildStrategy(), blsStandard));
        wc = new WinConditionsStorage(wcEx, null);
        tuple = new Tuple<>(t, wc);
        c = new Card("Pan", tuple);
        bw = new BufferedWriter(new FileWriter("./src/main/resources/server/" + "Pan" + "_godCard.json"));
        gson.toJson(c, bw);
        bw.flush();
        bw.close();



        //Prometheus
        bls1.clear();
        bls1.add(new WontMoveUpLimiter());
        phases = new LinkedList<>();
        phase = new Tuple<>(new TurnPhase(BUILD, false), new ActionsCreator(null, null, new StandardBuildStrategy(), bls1));
        phases.add(phase);
        mls.clear();
        mls.add(new CantMoveUpLimiter(CantMoveUpLimiter.PassiveEffectTarget.SELF));
        mls.add(new SameWorkerBuilderLimiter());
        phase = new Tuple<>(new TurnPhase(MOVE, false), new ActionsCreator(new StandardMoveStrategy(), mls, null, null));
        phases.add(phase);
        phase = new Tuple<>(new TurnPhase(BUILD, false), new ActionsCreator(null, null, new StandardBuildStrategy(), blsStandard));
        phases.add(phase);
        t = new DifferentActionsTurn(phases);
        wc = new WinConditionsStorage(wcStd, null);
        tuple = new Tuple<>(t, wc);
        c = new Card("Prometheus", tuple);
        bw = new BufferedWriter(new FileWriter("./src/main/resources/server/" + "Prometheus" + "_godCard.json"));
        gson.toJson(c, bw);
        bw.flush();
        bw.close();




        //Hera
        t = new StandardActionsTurn(new ActionsCreator(new StandardMoveStrategy(), null, null, null), new ActionsCreator(null, null, new StandardBuildStrategy(), blsStandard));
        wc = new WinConditionsStorage(wcStd, new PerimeterWinLimiter());
        tuple = new Tuple<>(t, wc);
        c = new Card("Hera", tuple);
        bw = new BufferedWriter(new FileWriter("./src/main/resources/server/" + "Hera" + "_godCard.json"));
        gson.toJson(c, bw);
        bw.flush();
        bw.close();



        //Hestia
        phases = new LinkedList<>();
        phase = new Tuple<>(new TurnPhase(MOVE, false), new ActionsCreator(new StandardMoveStrategy(), null, null, null));
        phases.add(phase);
        phase = new Tuple<>(new TurnPhase(BUILD, false), new ActionsCreator(null, null, new StandardBuildStrategy(), blsStandard));
        phases.add(phase);
        bls1.clear();
        bls1.add(blsStandard.get(0));
        bls1.add(new InsidePerimeterLimiter());
        phase = new Tuple<>(new TurnPhase(BUILD, true), new ActionsCreator(null, null, new StandardBuildStrategy(), bls1));
        phases.add(phase);
        t = new ExtraActionsTurn(phases);
        wc = new WinConditionsStorage(wcStd, null);
        tuple = new Tuple<>(t, wc);
        c = new Card("Hestia", tuple);
        bw = new BufferedWriter(new FileWriter("./src/main/resources/server/" + "Hestia" + "_godCard.json"));
        gson.toJson(c, bw);
        bw.flush();
        bw.close();




        //Poseidon
        phases = new LinkedList<>();
        phase = new Tuple<>(new TurnPhase(MOVE, false), new ActionsCreator(new StandardMoveStrategy(), null, null, null));
        phases.add(phase);
        phase = new Tuple<>(new TurnPhase(BUILD, false), new ActionsCreator(null, null, new StandardBuildStrategy(), blsStandard));
        phases.add(phase);
        bls1.clear();
        bls1.add(new WorkerOnGroundLimiter());
        bls1.add(new DifferentWorkerThanMovedPreviousLimiter());
        phase = new Tuple<>(new TurnPhase(BUILD, true), new ActionsCreator(null, null, new StandardBuildStrategy(), bls1));
        phases.add(phase);
        ArrayList<BuildingLimiter> bls2 = new ArrayList<>(bls1);
        bls2.remove(0);
        phase = new Tuple<>(new TurnPhase(BUILD, true), new ActionsCreator(null, null, new StandardBuildStrategy(), bls2));
        phases.add(phase);
        phase = new Tuple<>(new TurnPhase(BUILD, true), new ActionsCreator(null, null, new StandardBuildStrategy(), bls2));
        phases.add(phase);
        t = new ExtraActionsTurn(phases);
        wc = new WinConditionsStorage(wcStd, null);
        tuple = new Tuple<>(t, wc);
        c = new Card("Poseidon", tuple);
        bw = new BufferedWriter(new FileWriter("./src/main/resources/server/" + "Poseidon" + "_godCard.json"));
        gson.toJson(c, bw);
        bw.flush();
        bw.close();




        //Triton
        t = new StandardActionsTurn(new ActionsCreator(new PerimeterMoveStrategy(), null, null, null), new ActionsCreator(null, null, new StandardBuildStrategy(), blsStandard));
        wc = new WinConditionsStorage(wcStd, null);
        tuple = new Tuple<>(t, wc);
        c = new Card("Triton", tuple);
        bw = new BufferedWriter(new FileWriter("./src/main/resources/server/" + "Triton" + "_godCard.json"));
        gson.toJson(c, bw);
        bw.flush();
        bw.close();



        //Zeus
        t = new StandardActionsTurn(new ActionsCreator(new StandardMoveStrategy(), null, null, null), new ActionsCreator(null, null, new UnderHimselfBuildStrategy(), blsStandard));
        wc = new WinConditionsStorage(wcStd, null);
        tuple = new Tuple<>(t, wc);
        c = new Card("Zeus", tuple);
        bw = new BufferedWriter(new FileWriter("./src/main/resources/server/" + "Zeus" + "_godCard.json"));
        gson.toJson(c, bw);
        bw.flush();
        bw.close();


    }

}

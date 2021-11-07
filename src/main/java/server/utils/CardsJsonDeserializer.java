package main.java.server.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.java.server.model.Card;
import main.java.server.model.Deck;
import main.java.server.model.turn.PassiveEffect;
import main.java.server.model.turn.building.Building;
import main.java.server.model.turn.building.limiter.BuildingLimiter;
import main.java.server.model.turn.movement.Movement;
import main.java.server.model.turn.movement.limiter.MovementLimiter;
import main.java.server.model.turn.strategy.Turn;
import main.java.server.model.turn.win.WinCondition;
import main.java.server.model.turn.win.limiter.WinLimiter;
import main.java.shared.model.CardDetails;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class CardsJsonDeserializer {
    /**
     * checks if program is in jar mode or IDE mode then reads all the first part of cards names
     * @return a list of cards details where has been set only the username
     */
    public ArrayList<CardDetails> getDetailedCards() {
        ArrayList<CardDetails> cardsDetails = new ArrayList<>(14);
        final String path = "server";
        final File jarFile = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        if(jarFile.isFile()) {  // Run with JAR file
            try {
                final JarFile jar = new JarFile(jarFile);
                for(Enumeration<JarEntry> entries = jar.entries(); entries.hasMoreElements();) {
                    final String name = entries.nextElement().getName();
                    if (name.contains(path) && name.contains("_godCard")) { //filter according to the path and the name of the card
                        String godName = name.substring(name.lastIndexOf("/") + 1, name.indexOf("_godCard"));
                        cardsDetails.add(new CardDetails(godName, null));
                    }
                }
                jar.close();
            }catch (IOException ignored) {}
        } else { // Run with IDE
            final URL url = CardsJsonDeserializer.class.getResource("/"+path);
            if (url != null) {
                try {
                    final File files = new File(url.toURI());
                    for (File file : files.listFiles()) {
                        String godName = file.getName().substring(0, file.getName().indexOf("_godCard"));
                        cardsDetails.add(new CardDetails(godName, null));
                    }
                } catch (Exception ignored) {}
            }
        }
        return cardsDetails;
    }

    private final GsonBuilder builder = new GsonBuilder()
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
    private final Gson gson = builder.create();


    /**
     * reads fields of file of the card name then gets the card fully built
     * @param cardName that must be retrieved
     * @return full built card (without description)
     */
    public Card buildAndGetCard(String cardName) {
        InputStream godFile = Deck.class.getResourceAsStream("/server/"+cardName+"_godCard.json");
        InputStreamReader reader = new InputStreamReader(godFile);
        return gson.fromJson(reader, Card.class);
    }
}

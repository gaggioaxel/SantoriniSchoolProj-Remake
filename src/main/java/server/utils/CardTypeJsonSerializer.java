package main.java.server.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import main.java.server.model.Card;
import main.java.server.model.turn.strategy.Turn;
import main.java.server.model.turn.win.WinConditionsStorage;

import java.lang.reflect.Type;

public class CardTypeJsonSerializer implements JsonSerializer<Card> {

    /**
     * pretty serializer for cards
     * @param card to serialize
     * @param type not used
     * @param context usage context
     * @return a json element to write
     */
    @Override
    public JsonElement serialize(Card card, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("card_name", context.serialize(card.getCardName(), String.class));
        object.add("turn", context.serialize(card.getTurn(), Turn.class));
        object.add("win", context.serialize(card.getWinChecker(), WinConditionsStorage.class));
        return object;
    }

}

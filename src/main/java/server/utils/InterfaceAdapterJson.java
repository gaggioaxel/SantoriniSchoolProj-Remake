package main.java.server.utils;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class InterfaceAdapterJson<T> implements JsonSerializer<T>, JsonDeserializer<T> {

    /**
     * serialize a generic interface object
     * @param object to serialize
     * @param interfaceType to serialize
     * @param context of serialization
     * @return a json element that's the serialized version of the object
     */
    public JsonElement serialize(T object, Type interfaceType, JsonSerializationContext context) {
        final JsonObject wrapper = new JsonObject();
        wrapper.addProperty("type", object.getClass().getSimpleName());
        if(!context.serialize(object).toString().equals("{}"))
            wrapper.add("attributes", context.serialize(object));
        return wrapper;
    }

    /**
     * deserialize a generic interface object
     * @param elem to deserialize
     * @param interfaceType to deserialize
     * @param context of deserialization
     * @return the object
     */
    public T deserialize(JsonElement elem, Type interfaceType, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject wrapper = (JsonObject) elem;
        final JsonElement typeName = get(wrapper, "type");
        final JsonElement data = get(wrapper, "attributes");
        final Type actualType = typeForName(typeName);
        return data!=null ? context.deserialize(data, actualType) : context.deserialize(JsonNull.INSTANCE, actualType);
    }

    /**
     * gets the type of a json element scanning all packages to find those in the model
     * @param typeElem json element
     * @return the type of that element
     */
    private Type typeForName(final JsonElement typeElem) {
        final ArrayList<Package> packages = new ArrayList<>();
        for (Package p : Package.getPackages())
            if (p.getName().startsWith("it.polimi.ingsw.server.model"))
                packages.add(p);
        final String className = typeElem.getAsString();
        for (Package p : packages) {
            try {
                String packName = p.getName();
                String tentative = packName + "." + className;
                return Class.forName(tentative);
            } catch (ClassNotFoundException ignored) {}
        }
        throw new JsonParseException(new ClassNotFoundException());
    }

    /**
     * gets a json element
     * @param wrapper that contains the object
     * @param memberName the name of the member to find
     * @return the member
     */
    private JsonElement get(final JsonObject wrapper, String memberName) {
        return wrapper.get(memberName)!=null ? wrapper.get(memberName) : new JsonObject();
    }
}


//initial version of this class that works always but was not so pretty


/*package it.polimi.ingsw.server.utils;

import com.google.gson.*;

import java.lang.reflect.Type;

public class InterfaceAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {
    public JsonElement serialize(T object, Type interfaceType, JsonSerializationContext context) {
        final JsonObject wrapper = new JsonObject();
        wrapper.addProperty("type", object.getClass().getName());
        wrapper.add("attributes", context.serialize(object));
        return wrapper;
    }

    public T deserialize(JsonElement elem, Type interfaceType, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject wrapper = (JsonObject) elem;
        final JsonElement typeName = get(wrapper, "type");
        final JsonElement data = get(wrapper, "attributes");
        final Type actualType = typeForName(typeName);
        return context.deserialize(data, actualType);
    }

    private Type typeForName(final JsonElement typeElem) {
        try {
            return Class.forName(typeElem.getAsString());
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e);
        }
    }

    private JsonElement get(final JsonObject wrapper, String memberName) {
        final JsonElement elem = wrapper.get(memberName);
        if (elem == null) throw new JsonParseException("no '" + memberName + "' member found in what was expected to be an interface wrapper");
        return elem;
    }
}*/




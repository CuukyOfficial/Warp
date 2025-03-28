package de.cuuky.warp;

import de.varoplugin.cfw.configuration.serialization.SerializableLocation;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class Warp implements ConfigurationSerializable {

    private final String name;
    private final SerializableLocation location;

    public Warp(String name, Location location) {
        this.name = name;
        this.location = new SerializableLocation(location);
    }

    public String getName() {
        return this.name;
    }

    public Location getLocation() {
        return this.location;
    }


    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", this.name);
        map.put("location", this.location);
        return map;
    }

    public static Warp deserialize(Map<String, Object> args) {
        String name = (String) args.get("name");
        SerializableLocation location = (SerializableLocation) args.get("location");
        return new Warp(name, location);
    }
}

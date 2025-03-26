package de.cuuky.warp;

import de.varoplugin.cfw.configuration.serialization.SerializableLocation;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class Warp implements ConfigurationSerializable {

    private final String name;
    private final SerializableLocation location;
    private final List<Block> signs;

    public Warp(String name, Location location, List<Block> signs) {
        this.name = name;
        this.location = new SerializableLocation(location);
        this.signs = new ArrayList<>(signs);
    }

    public void addSign(Block sign) {
        this.signs.add(sign);
    }

    public void removeSign(Block sign) {
        this.signs.remove(sign);
    }

    public String getName() {
        return this.name;
    }

    public Location getLocation() {
        return this.location;
    }

    public Stream<Block> getSigns() {
        return this.signs.stream();
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = this.location.serialize();
        map.put("name", this.name);
        map.put("location", this.location);
        map.put("signs", this.signs.stream().map(Block::getLocation).map(SerializableLocation::new)
                .collect(Collectors.toList()));
        return map;
    }

    public static Warp deserialize(Map<String, Object> args) {
        String name = (String) args.get("name");
        SerializableLocation location = (SerializableLocation) args.get("location");
        List<?> signs = (List<?>) args.get("signs");
        return new Warp(name, location, signs.stream()
                .map(SerializableLocation.class::cast)
                .map(Location::getBlock)
                .filter(block -> block.getState() instanceof Sign)
                .collect(Collectors.toList()));
    }
}

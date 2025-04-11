package de.cuuky.warp;

import io.github.almightysatan.slams.Context;

public class WarpContext implements Context {

    private final String warp;

    public WarpContext(String warp) {
        this.warp = warp;
    }

    public WarpContext(Warp warp) {
        this(warp.getName());
    }

    public String getWarp() {
        return this.warp;
    }

    @Override
    public String language() {
        return null;
    }
}

package com.clemble.casino.game.lifecycle.management;

import com.clemble.casino.game.lifecycle.management.unit.GameUnit;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

/**
 * Created by mavarazy on 15/03/14.
 */
public class GamePlayerUnit {

    final private List<GameUnit> units;

    @JsonCreator
    public GamePlayerUnit(@JsonProperty("units") List<GameUnit> units) {
        this.units = units == null ? Collections.<GameUnit>emptyList() : units;
    }

    public boolean use(GameUnit unit) {
        return units.remove(unit);
    }

    public List<GameUnit> getUnits() {
        return units;
    }

    public boolean contains(GameUnit unit) {
        return units.contains(unit);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GamePlayerUnit)) return false;

        GamePlayerUnit unit = (GamePlayerUnit) o;

        if (units != null ? !units.equals(unit.units) : unit.units != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return units != null ? units.hashCode() : 0;
    }
}
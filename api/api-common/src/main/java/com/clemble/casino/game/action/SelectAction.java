package com.clemble.casino.game.action;

import com.clemble.casino.game.unit.GameUnit;
import com.clemble.casino.player.PlayerAware;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("select")
public class SelectAction<T extends GameUnit> implements GameAction {

    /**
     * Generated 21/12/13
     */
    private static final long serialVersionUID = -1260530154110392266L;

    final private T select;
    final private String player;

    @JsonCreator
    public SelectAction(@JsonProperty(PlayerAware.JSON_ID) String player, @JsonProperty("select") T select) {
        this.player = player;
        this.select = select;
    }

    @Override
    public String getPlayer() {
        return player;
    }

    public T getSelect() {
        return select;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((select == null) ? 0 : select.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (getClass() != obj.getClass())
            return false;
        SelectAction<?> other = (SelectAction<?>) obj;
        if (select == null) {
            if (other.select != null)
                return false;
        } else if (!select.equals(other.select))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "selectAction:" + getPlayer() + ":" + select;
    }

}
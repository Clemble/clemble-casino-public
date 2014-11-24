package com.clemble.casino.goal.lifecycle.construction.event;

import com.clemble.casino.goal.lifecycle.construction.GoalConstruction;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Created by mavarazy on 10/9/14.
 */
@JsonTypeName(GoalConstructionCompleteEvent.JSON_TYPE)
public class GoalConstructionCompleteEvent implements GoalConstructionEvent {

    final public static String JSON_TYPE = "goal:construction:complete";

    final private String player;
    final private GoalConstruction construction;

    @JsonCreator
    public GoalConstructionCompleteEvent(@JsonProperty(PLAYER) String player, @JsonProperty("body") GoalConstruction construction) {
        this.player = player;
        this.construction = construction;
    }

    @Override
    public String getPlayer() {
        return player;
    }

    @Override
    public GoalConstruction getBody() {
        return construction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GoalConstructionCompleteEvent that = (GoalConstructionCompleteEvent) o;

        if (!construction.equals(that.construction)) return false;
        if (!player.equals(that.player)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = player.hashCode();
        result = 31 * result + construction.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return player + " > " + construction.getGoalKey() + " > " + JSON_TYPE;
    }

}

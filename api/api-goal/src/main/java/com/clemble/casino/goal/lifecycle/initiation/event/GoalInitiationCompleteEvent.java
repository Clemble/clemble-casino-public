package com.clemble.casino.goal.lifecycle.initiation.event;

import com.clemble.casino.goal.lifecycle.initiation.GoalInitiation;
import com.clemble.casino.player.PlayerAware;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Created by mavarazy on 11/16/14.
 */
@JsonTypeName(GoalInitiationCompleteEvent.JSON_TYPE)
public class GoalInitiationCompleteEvent implements GoalInitiationEvent {

    final public static String JSON_TYPE = "goal:initiation:complete";

    final private String player;
    final private GoalInitiation initiation;

    @JsonCreator
    public GoalInitiationCompleteEvent(@JsonProperty(PLAYER) String player, @JsonProperty("body") GoalInitiation initiation) {
        this.player = player;
        this.initiation = initiation;
    }

    @Override
    public String getPlayer() {
        return player;
    }

    @Override
    public GoalInitiation getBody() {
        return initiation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GoalInitiationCompleteEvent that = (GoalInitiationCompleteEvent) o;

        if (!initiation.equals(that.initiation)) return false;
        if (!player.equals(that.player)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return initiation.hashCode();
    }

    @Override
    public String toString() {
        return player + " > " + initiation.getGoalKey() + " > " + JSON_TYPE;
    }

    public static GoalInitiationCompleteEvent create(GoalInitiation initiation) {
        return new GoalInitiationCompleteEvent(initiation.getPlayer(), initiation);
    }

}

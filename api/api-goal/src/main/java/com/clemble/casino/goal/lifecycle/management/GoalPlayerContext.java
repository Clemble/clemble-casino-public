package com.clemble.casino.goal.lifecycle.management;

import com.clemble.casino.lifecycle.configuration.rule.time.PlayerClock;
import com.clemble.casino.lifecycle.management.PlayerContext;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by mavarazy on 10/9/14.
 */
public class GoalPlayerContext implements PlayerContext {

    final private String player;
    final private PlayerClock clock;

    @JsonCreator
    public GoalPlayerContext(
        @JsonProperty(PLAYER) String player,
        @JsonProperty("clock") PlayerClock clock) {
        this.player = player;
        this.clock = clock;
    }

    @Override
    public String getPlayer(){
        return player;
    }

    @Override
    public PlayerClock getClock() {
        return clock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GoalPlayerContext that = (GoalPlayerContext) o;

        if (clock != null ? !clock.equals(that.clock) : that.clock != null) return false;
        if (player != null ? !player.equals(that.player) : that.player != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = player != null ? player.hashCode() : 0;
        result = 31 * result + (clock != null ? clock.hashCode() : 0);
        return result;
    }

}
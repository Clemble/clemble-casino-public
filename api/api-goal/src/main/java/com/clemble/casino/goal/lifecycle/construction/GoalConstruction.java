package com.clemble.casino.goal.lifecycle.construction;

import com.clemble.casino.goal.lifecycle.initiation.GoalInitiation;
import com.clemble.casino.goal.lifecycle.management.GoalRole;
import com.clemble.casino.lifecycle.construction.Construction;
import com.clemble.casino.lifecycle.construction.ConstructionState;
import com.clemble.casino.lifecycle.initiation.InitiationState;
import com.clemble.casino.goal.GoalAware;
import com.clemble.casino.goal.GoalDescriptionAware;
import com.clemble.casino.goal.lifecycle.configuration.GoalConfiguration;
import com.clemble.casino.payment.Bank;
import com.clemble.casino.player.PlayerAware;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.HashSet;

/**
 * Created by mavarazy on 9/7/14.
 */
public class GoalConstruction implements Construction<GoalConfiguration>, GoalAware, GoalDescriptionAware, PlayerAware {

    @Id
    final private String goalKey;
    final private String player;
    final private String goal;
    final private Date startDate;
    final private ConstructionState state;
    final private GoalConfiguration configuration;

    @JsonCreator
    public GoalConstruction(
        @JsonProperty("goalKey") String goalKey,
        @JsonProperty("player") String player,
        @JsonProperty("goal") String goal,
        @JsonProperty("startDate") Date startDate,
        @JsonProperty("configuration") GoalConfiguration configuration,
        @JsonProperty("state") ConstructionState state) {
        this.goalKey = goalKey;
        this.player = player;
        this.goal = goal;
        this.startDate = startDate;
        this.configuration = configuration;
        this.state = state;
    }

    @Override
    public String getPlayer() {
        return player;
    }

    @Override
    public ConstructionState getState() {
        return state;
    }

    @Override
    public GoalConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public String getGoalKey() {
        return goalKey;
    }

    public Date getStartDate() {
        return startDate;
    }

    @Override
    public String getGoal() {
        return goal;
    }

    @Override
    public GoalInitiation toInitiation(){
        // TODO make this more intelligent
        return new GoalInitiation(goalKey, InitiationState.pending, Bank.create(getPlayer(), getConfiguration().getBid()), player, goal, configuration, new HashSet<String>(), new HashSet<String>(), startDate);
    }

    public GoalConstruction clone(ConstructionState state) {
        return new GoalConstruction(goalKey, player, goal, startDate, configuration, state);
    }

    public GoalConstruction clone(String goalKey, ConstructionState state) {
        return new GoalConstruction(goalKey, player, goal, startDate, configuration, state);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GoalConstruction that = (GoalConstruction) o;

        if (configuration != null ? !configuration.equals(that.configuration) : that.configuration != null)
            return false;
        if (goal != null ? !goal.equals(that.goal) : that.goal != null) return false;
        if (goalKey != null ? !goalKey.equals(that.goalKey) : that.goalKey != null) return false;
        if (player != null ? !player.equals(that.player) : that.player != null) return false;
        if (state != that.state) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = goalKey != null ? goalKey.hashCode() : 0;
        result = 31 * result + (player != null ? player.hashCode() : 0);
        result = 31 * result + (goal != null ? goal.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (configuration != null ? configuration.hashCode() : 0);
        return result;
    }
}

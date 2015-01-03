package com.clemble.casino.goal.lifecycle.construction;

import com.clemble.casino.goal.GoalAware;
import com.clemble.casino.goal.GoalDescriptionAware;
import com.clemble.casino.goal.lifecycle.configuration.GoalConfiguration;
import com.clemble.casino.goal.lifecycle.configuration.GoalConfigurationAware;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

/**
 * Created by mavarazy on 1/3/15.
 */
public class GoalSuggestion implements GoalDescriptionAware, GoalAware, GoalConfigurationAware {

    @Id
    final private String goalKey;
    final private String goal;
    final private GoalConfiguration configuration;
    final private GoalSuggestionState state;

    @JsonCreator
    public GoalSuggestion(
        @JsonProperty(GOAL_KEY) String goalKey,
        @JsonProperty("goal") String goal,
        @JsonProperty("configuration") GoalConfiguration configuration,
        @JsonProperty("state") GoalSuggestionState state) {
        this.goalKey = goalKey;
        this.goal = goal;
        this.configuration = configuration;
        this.state = state;
    }

    @Override
    public String getGoalKey() {
        return goalKey;
    }

    @Override
    public String getGoal() {
        return goal;
    }

    @Override
    public GoalConfiguration getConfiguration() {
        return configuration;
    }

    public GoalSuggestionState getState() {
        return  state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GoalSuggestion that = (GoalSuggestion) o;

        if (!configuration.equals(that.configuration)) return false;
        if (!goal.equals(that.goal)) return false;
        if (!goalKey.equals(that.goalKey)) return false;
        if (state != that.state) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = goalKey.hashCode();
        result = 31 * result + goal.hashCode();
        result = 31 * result + configuration.hashCode();
        result = 31 * result + state.hashCode();
        return result;
    }
}

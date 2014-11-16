package com.clemble.casino.goal.event.action;

import com.clemble.casino.goal.GoalStatusAware;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Created by mavarazy on 10/9/14.
 */
@JsonTypeName(GoalStatusUpdateAction.JSON_TYPE)
public class GoalStatusUpdateAction implements PlayerGoalAction, GoalStatusAware {

    final public static String JSON_TYPE = "goal:management:status:update:action";

    final private String status;

    @JsonCreator
    public GoalStatusUpdateAction(@JsonProperty("status") String status) {
        this.status = status;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GoalStatusUpdateAction)) return false;

        GoalStatusUpdateAction that = (GoalStatusUpdateAction) o;

        if (!status.equals(that.status)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return status.hashCode();
    }

    @Override
    public String toString() {
        return JSON_TYPE + " > " + status;
    }

}

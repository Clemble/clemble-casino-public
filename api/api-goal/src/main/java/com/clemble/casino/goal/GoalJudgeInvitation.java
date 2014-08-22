package com.clemble.casino.goal;

import com.clemble.casino.player.PlayerAware;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

import static com.clemble.casino.goal.GoalJudgeInvitationStatus.*;

/**
 * Created by mavarazy on 8/17/14.
 */
public class GoalJudgeInvitation implements PlayerAware, GoalAware, GoalDescriptionAware {

    @Id
    final private String goalKey;
    final private String player;
    final private String judge;
    final private String goal;
    final private GoalJudgeInvitationStatus status;

    @JsonCreator
    public GoalJudgeInvitation(@JsonProperty(PLAYER) String player, @JsonProperty("judge") String judge, @JsonProperty(GOAL_KEY) String goalKey, @JsonProperty("goal") String goal, @JsonProperty("status") GoalJudgeInvitationStatus status) {
        this.player = player;
        this.judge = judge;
        this.goalKey = goalKey;
        this.status = status;
        this.goal = goal;
    }

    @Override
    public String getGoalKey() {
        return goalKey;
    }

    public String getGoal() {
        return goal;
    }

    public GoalJudgeInvitationStatus getStatus() {
        return status;
    }

    public String getJudge() {
        return judge;
    }

    @Override
    public String getPlayer() {
        return player;
    }

    public static GoalJudgeInvitation fromGoal(Goal goal) {
        return new GoalJudgeInvitation(goal.getPlayer(), goal.getJudge(), goal.getGoalKey(), goal.getGoal(), pending);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GoalJudgeInvitation that = (GoalJudgeInvitation) o;

        if (goal != null ? !goal.equals(that.goal) : that.goal != null) return false;
        if (judge != null ? !judge.equals(that.judge) : that.judge != null) return false;
        if (player != null ? !player.equals(that.player) : that.player != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = player != null ? player.hashCode() : 0;
        result = 31 * result + (judge != null ? judge.hashCode() : 0);
        result = 31 * result + (goal != null ? goal.hashCode() : 0);
        return result;
    }
}

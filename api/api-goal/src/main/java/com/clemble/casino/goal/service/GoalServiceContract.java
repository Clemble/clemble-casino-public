package com.clemble.casino.goal.service;

import com.clemble.casino.ClembleService;
import com.clemble.casino.goal.Goal;

import java.util.Collection;

/**
 * Created by mavarazy on 8/4/14.
 */
public interface GoalServiceContract extends ClembleService {

    public Goal getGoal(String player, String id);

    public Collection<Goal> getGoals(String player);

    public Collection<Goal> getPendingGoals(String player);

    public Collection<Goal> getReachedGoals(String player);

    public Collection<Goal> getMissedGoals(String player);

}

package com.clemble.casino.client.goal;

import com.clemble.casino.goal.lifecycle.configuration.service.GoalConfigurationService;
import com.clemble.casino.goal.lifecycle.construction.service.GoalConstructionService;
import com.clemble.casino.goal.lifecycle.initiation.service.GoalInitiationService;
import com.clemble.casino.goal.lifecycle.management.service.GoalActionService;

/**
 * Created by mavarazy on 9/15/14.
 */
public interface GoalOperations {

    GoalConfigurationService configurationService();

    GoalConstructionService constructionService();

    GoalInitiationService initiationService();

    GoalActionService actionService();

}

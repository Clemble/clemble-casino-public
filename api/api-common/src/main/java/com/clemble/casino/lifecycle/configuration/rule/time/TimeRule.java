package com.clemble.casino.lifecycle.configuration.rule.time;

import java.util.Date;

import com.clemble.casino.player.event.PlayerEvent;
import com.clemble.casino.lifecycle.configuration.rule.ConfigurationRule;
import com.clemble.casino.lifecycle.configuration.rule.breach.BreachPunishment;
import com.clemble.casino.lifecycle.configuration.rule.breach.BreachPunishmentAware;

public interface TimeRule extends ConfigurationRule, BreachPunishmentAware {

    public BreachPunishment getPunishment();

    public long getLimit();

    public long timeUntilBreach(long totalTimeSpent);

    public long timeUntilBreach(PlayerClock clock);

    public Date breachDate(PlayerClock clock);

    public PlayerEvent toTimeBreachedEvent(String player);

}
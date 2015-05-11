package com.clemble.casino.lifecycle.configuration.rule.timeout;

import com.clemble.casino.lifecycle.configuration.rule.ConfigurationRule;
import com.clemble.casino.lifecycle.configuration.rule.breach.BreachPunishment;
import com.clemble.casino.lifecycle.configuration.rule.breach.BreachPunishmentAware;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Created by mavarazy on 1/4/15.
 */
@JsonTypeName("rule:timeout:total")
public class TimeoutRule implements ConfigurationRule, BreachPunishmentAware {

    final private BreachPunishment punishment;
    final private TimeoutCalculator timeoutCalculator;

    @JsonCreator
    public TimeoutRule(
        @JsonProperty("punishment") BreachPunishment punishment,
        @JsonProperty("timeoutCalculator") TimeoutCalculator timeoutCalculator) {
        this.punishment = punishment;
        this.timeoutCalculator = timeoutCalculator;
    }

    @Override
    public BreachPunishment getPunishment() {
        return punishment;
    }

    public TimeoutCalculator getTimeoutCalculator() {
        return timeoutCalculator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeoutRule that = (TimeoutRule) o;

        if (!punishment.equals(that.punishment)) return false;
        if (!timeoutCalculator.equals(that.timeoutCalculator)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = punishment.hashCode();
        result = 31 * result + timeoutCalculator.hashCode();
        return result;
    }

}

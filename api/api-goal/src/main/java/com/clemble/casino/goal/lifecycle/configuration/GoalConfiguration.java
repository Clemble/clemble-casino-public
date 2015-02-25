package com.clemble.casino.goal.lifecycle.configuration;

import com.clemble.casino.bet.Bet;
import com.clemble.casino.goal.lifecycle.configuration.rule.reminder.ReminderRule;
import com.clemble.casino.goal.lifecycle.configuration.rule.share.ShareRule;
import com.clemble.casino.lifecycle.configuration.Configuration;
import com.clemble.casino.lifecycle.configuration.rule.ConfigurationRule;
import com.clemble.casino.lifecycle.configuration.rule.timeout.TimeoutRule;
import com.clemble.casino.money.Currency;
import com.clemble.casino.social.SocialProvider;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by mavarazy on 12/13/14.
 */
@JsonTypeName("short")
public class GoalConfiguration implements
    Configuration,
    GoalConfigurationKeyAware {

    final private String configurationKey;
    final private String name;
    final private Bet bet;
    final private ReminderRule emailReminderRule;
    final private ReminderRule phoneReminderRule;
    final private TimeoutRule moveTimeoutRule;
    final private TimeoutRule totalTimeoutRule;
    final private GoalRoleConfiguration supporterConfiguration;
    final private ShareRule shareRule;

    @JsonCreator
    public GoalConfiguration(
        @JsonProperty("configurationKey") String configurationKey,
        @JsonProperty("name") String name,
        @JsonProperty("bid") Bet bet,
        @JsonProperty("emailReminderRule") ReminderRule emailReminderRule,
        @JsonProperty("phoneReminderRule") ReminderRule phoneReminderRule,
        @JsonProperty("moveTimeRule") TimeoutRule moveTimeoutRule,
        @JsonProperty("totalTimeRule") TimeoutRule totalTimeoutRule,
        @JsonProperty("supporterConfiguration") GoalRoleConfiguration supporterConfiguration,
        @JsonProperty("shareRule") ShareRule shareRule
    ) {
        this.configurationKey = configurationKey;
        this.name = name;
        this.bet = bet;
        this.emailReminderRule = emailReminderRule;
        this.phoneReminderRule = phoneReminderRule;
        this.moveTimeoutRule = moveTimeoutRule;
        this.totalTimeoutRule = totalTimeoutRule;
        this.supporterConfiguration = supporterConfiguration;
        this.shareRule = shareRule;
    }

    @Override
    public String getConfigurationKey() {
        return configurationKey;
    }

    public String getName() {
        return name;
    }

    public Bet getBet() {
        return bet;
    }

    public ReminderRule getEmailReminderRule() {
        return emailReminderRule;
    }

    public ReminderRule getPhoneReminderRule() {
        return phoneReminderRule;
    }

    public TimeoutRule getMoveTimeoutRule() {
        return moveTimeoutRule;
    }

    public TimeoutRule getTotalTimeoutRule() {
        return totalTimeoutRule;
    }

    public GoalRoleConfiguration getSupporterConfiguration() {
        return supporterConfiguration;
    }

    public ShareRule getShareRule() {
        return shareRule;
    }

    public GoalConfiguration appendRule(ConfigurationRule rule) {
        if (rule instanceof TimeoutRule) {
            return new GoalConfiguration(
                configurationKey,
                name,
                bet,
                emailReminderRule,
                phoneReminderRule,
                (TimeoutRule) rule,
                totalTimeoutRule,
                supporterConfiguration,
                shareRule
            );
        } else if (rule instanceof ShareRule) {
            Collection<SocialProvider> newShareRule = new HashSet<SocialProvider>(shareRule.getProviders());
            newShareRule.addAll(((ShareRule) rule).getProviders());
            return new GoalConfiguration(
                configurationKey,
                name,
                bet,
                emailReminderRule,
                phoneReminderRule,
                moveTimeoutRule,
                totalTimeoutRule,
                supporterConfiguration,
                new ShareRule(newShareRule)
            );
        } else if (rule instanceof GoalRoleConfiguration){
            return new GoalConfiguration(
                configurationKey,
                name,
                bet,
                emailReminderRule,
                phoneReminderRule,
                moveTimeoutRule,
                totalTimeoutRule,
                (GoalRoleConfiguration) rule,
                shareRule
            );
        }
        throw new UnsupportedOperationException();
    }

    public GoalConfiguration setBet(int amount, int percentage) {
        // Step 1. Generating new bet
        Currency currency = bet.getAmount().getCurrency();
        Bet newBet = Bet.create(currency, amount, percentage);
        // Step 2. Returnign new configuration
        return new GoalConfiguration(
            configurationKey,
            name,
            newBet,
            emailReminderRule,
            phoneReminderRule,
            moveTimeoutRule,
            totalTimeoutRule,
            supporterConfiguration,
            shareRule
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GoalConfiguration that = (GoalConfiguration) o;

        if (!moveTimeoutRule.equals(that.moveTimeoutRule)) return false;
        if (!totalTimeoutRule.equals(that.totalTimeoutRule)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = moveTimeoutRule.hashCode();
        result = 31 * result + totalTimeoutRule.hashCode();
        return result;
    }

}

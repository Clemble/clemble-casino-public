package com.clemble.casino.game.lifecycle.management.event;

import com.clemble.casino.game.lifecycle.management.MatchGameContext;
import com.clemble.casino.game.lifecycle.management.MatchGamePlayerContext;
import com.clemble.casino.game.lifecycle.management.MatchGameState;
import com.clemble.casino.game.lifecycle.management.outcome.GameOutcome;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName(MatchEndedEvent.JSON_TYPE)
public class MatchEndedEvent extends MatchEvent implements GameEndedEvent {

    final public static String JSON_TYPE = "game:management:match:ended";

    /**
     * Generated 01/02/14
     */
    private static final long serialVersionUID = 8084693422808106856L;

    final private GameOutcome outcome;

    @JsonCreator
    public MatchEndedEvent(
        @JsonProperty(SESSION_KEY) String sessionKey,
        @JsonProperty("state") MatchGameState state,
        @JsonProperty("outcome") GameOutcome outcome
    ) {
        super(sessionKey, state);
        this.outcome = outcome;
    }

    @Override
    public GameOutcome getOutcome() {
        return outcome;
    }

    @Override
    public String toString() {
        return getSessionKey() + " > " + JSON_TYPE;
    }

}

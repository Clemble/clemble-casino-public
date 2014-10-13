package com.clemble.casino.game.lifecycle.management;

import java.util.ArrayList;
import java.util.List;

import com.clemble.casino.game.lifecycle.initiation.GameInitiation;
import com.clemble.casino.game.lifecycle.configuration.GameConfiguration;
import com.clemble.casino.lifecycle.configuration.rule.time.PlayerClock;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TournamentGamePlayerContext implements GamePlayerContext {

    /**
     * Generated 15/02/14
     */
    private static final long serialVersionUID = -4672061886105008404L;

    final private String player;
    final private GamePlayerUnit units;
    final private PlayerClock clock;
    final private GamePlayerAccount account;

    @JsonCreator
    public TournamentGamePlayerContext(
        @JsonProperty(PLAYER) String player,
        @JsonProperty("account") GamePlayerAccount account,
        @JsonProperty("clock") PlayerClock clock,
        @JsonProperty("units") GamePlayerUnit units) {
        this.clock = clock;
        this.units = units;
        this.player = player;
        this.account = account;
    }

    @Override
    public String getPlayer() {
        return player;
    }

    @Override
    public PlayerClock getClock() {
        return clock;
    }

    @Override
    public GamePlayerAccount getAccount() {
        return account;
    }

    @Override
    public GamePlayerUnit getUnits() {
        return units;
    }

    public static List<TournamentGamePlayerContext> construct(GameInitiation initiation) {
        GameConfiguration specification = initiation.getConfiguration();
        List<TournamentGamePlayerContext> playerContexts = new ArrayList<TournamentGamePlayerContext>();
        for(String player: initiation.getParticipants()) {
            GamePlayerAccount account = new GamePlayerAccount(specification.getPrice().getAmount(), 0, 0);
            PlayerClock clock = new PlayerClock(0, 0, 0, null);
            GamePlayerUnit unit = new GamePlayerUnit(initiation.getConfiguration().getPlayerUnits());
            playerContexts.add(new TournamentGamePlayerContext(player, account, clock, unit));
        }
        return playerContexts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TournamentGamePlayerContext)) return false;

        TournamentGamePlayerContext that = (TournamentGamePlayerContext) o;

        if (account != null ? !account.equals(that.account) : that.account != null) return false;
        if (clock != null ? !clock.equals(that.clock) : that.clock != null) return false;
        if (player != null ? !player.equals(that.player) : that.player != null) return false;
        if (units != null ? !units.equals(that.units) : that.units != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = player != null ? player.hashCode() : 0;
        result = 31 * result + (units != null ? units.hashCode() : 0);
        result = 31 * result + (clock != null ? clock.hashCode() : 0);
        result = 31 * result + (account != null ? account.hashCode() : 0);
        return result;
    }
}

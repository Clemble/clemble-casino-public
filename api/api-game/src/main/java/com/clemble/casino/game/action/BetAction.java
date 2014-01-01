package com.clemble.casino.game.action;

import java.util.Arrays;
import java.util.Collection;

import com.clemble.casino.player.PlayerAware;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("bet")
public class BetAction extends GameAction {

    /**
     * Generated 10/06/13
     */
    private static final long serialVersionUID = 4761116695040560749L;

    final private long bet;

    @JsonCreator
    public BetAction(@JsonProperty(PlayerAware.JSON_ID) String player, @JsonProperty("bet") long bet) {
        super(player);
        this.bet = bet;
        if (bet < 0)
            throw new IllegalArgumentException("Bet can't be lesser than 0");

    }

    public long getBet() {
        return bet;
    }

    static public String whoBetMore(BetAction[] bets) {
        return whoBetMore(Arrays.asList(bets));
    }

    static public String whoBetMore(Collection<BetAction> bets) {
        if (bets == null || bets.size() == 0)
            return DEFAULT_PLAYER;

        long maxBet = 0;
        String playerWithMaxBet = null;
        for (BetAction bet : bets) {
            if (bet.getBet() > maxBet) {
                maxBet = bet.getBet();
                playerWithMaxBet = bet.getPlayer();
            } else if(bet.getBet() == maxBet) {
                playerWithMaxBet = DEFAULT_PLAYER;
            }
        }

        return playerWithMaxBet;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (bet ^ (bet >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BetAction other = (BetAction) obj;
        if (bet != other.bet)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "betAction:" + getPlayer() + ":" + bet;
    }

}
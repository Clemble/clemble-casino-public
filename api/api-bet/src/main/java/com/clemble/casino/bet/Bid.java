package com.clemble.casino.bet;

import com.clemble.casino.money.Money;
import com.clemble.casino.payment.AmountAware;
import com.clemble.casino.player.WinnerAware;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by mavarazy on 8/10/14.
 */
public class Bid implements AmountAware, WinnerAware {

    final private String winner;
    final private String bidder;
    final private Money amount;

    @JsonCreator
    public Bid(@JsonProperty("winner") String winner, @JsonProperty("bidder") String bidder, @JsonProperty("amount") Money amount) {
        this.winner = winner;
        this.bidder = bidder;
        this.amount = amount;
    }

    @Override
    public String getWinner() {
        return winner;
    }

    public String getBidder() {
        return bidder;
    }

    public Money getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bid bid = (Bid) o;

        if (!amount.equals(bid.amount)) return false;
        if (!bidder.equals(bid.bidder)) return false;
        if (!winner.equals(bid.winner)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = winner.hashCode();
        result = 31 * result + bidder.hashCode();
        result = 31 * result + amount.hashCode();
        return result;
    }
}
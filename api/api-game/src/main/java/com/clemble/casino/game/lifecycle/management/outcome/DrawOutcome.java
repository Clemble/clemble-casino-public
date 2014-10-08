package com.clemble.casino.game.lifecycle.management.outcome;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("draw")
public class DrawOutcome extends GameOutcome {

    /**
     * Generated 
     */
    private static final long serialVersionUID = 1699532347912139595L;

    @Override
    public String toString(){
        return "draw";
    }

}

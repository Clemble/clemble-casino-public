package com.clemble.casino.game.lifecycle.management.event.action.management;

import com.clemble.casino.game.lifecycle.management.event.action.PlayerGameAction;


/**
 * Created by mavarazy on 23/12/13.
 */
public class GameManagementAction implements PlayerGameAction {

    /**
     * Generated 02/01/2012
     */
    private static final long serialVersionUID = -3237359155760508649L;

    final private String player;

    public GameManagementAction(String player) {
        this.player = player;
    }

    @Override
    public String getPlayer() {
        return player;
    }

}
package com.clemble.casino.game.lifecycle.management.service;

import com.clemble.casino.event.Event;
import com.clemble.casino.game.lifecycle.management.GameContext;

import com.clemble.casino.ClembleService;
import com.clemble.casino.game.lifecycle.management.GameState;
import com.clemble.casino.game.lifecycle.management.event.GameManagementEvent;
import com.clemble.casino.lifecycle.management.service.ManagerService;

public interface GameActionService extends ClembleService, ManagerService{

    @Override
    public GameState getState(String sessionKey);

    @Override
    public GameManagementEvent process(String sessionKey, Event move);

    public GameContext<?> getContext(String sessionKey);

}
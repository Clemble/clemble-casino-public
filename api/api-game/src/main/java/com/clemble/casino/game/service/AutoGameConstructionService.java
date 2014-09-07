package com.clemble.casino.game.service;

import com.clemble.casino.game.configuration.GameConfiguration;
import com.clemble.casino.game.construct.AutomaticGameRequest;
import com.clemble.casino.game.construct.GameConstruction;

import java.util.Collection;

public interface AutoGameConstructionService extends GameConstructionService<AutomaticGameRequest> {

    @Override
    GameConstruction construct(final AutomaticGameRequest request);

    @Override
    Collection<GameConstruction> getPending(String player);

}

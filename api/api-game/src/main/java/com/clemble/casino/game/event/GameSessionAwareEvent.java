package com.clemble.casino.game.event;

import com.clemble.casino.event.GameEvent;
import com.clemble.casino.game.GameSessionAware;

public interface GameSessionAwareEvent extends GameEvent, GameSessionAware {
}
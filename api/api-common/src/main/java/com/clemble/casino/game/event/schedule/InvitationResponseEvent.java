package com.clemble.casino.game.event.schedule;

import com.clemble.casino.event.GameConstructionEvent;
import com.clemble.casino.event.PlayerAwareEvent;
import com.fasterxml.jackson.annotation.JsonTypeName;

public interface InvitationResponseEvent extends GameConstructionEvent, PlayerAwareEvent {

}
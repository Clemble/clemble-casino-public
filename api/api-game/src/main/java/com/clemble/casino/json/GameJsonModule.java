package com.clemble.casino.json;

import com.clemble.casino.game.*;
import com.clemble.casino.game.event.server.*;
import com.clemble.casino.game.specification.MatchGameConfiguration;
import com.clemble.casino.game.specification.RoundGameConfiguration;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.module.SimpleModule;

import com.clemble.casino.game.action.BetAction;
import com.clemble.casino.game.action.DefaultGameAction;
import com.clemble.casino.game.action.SelectAction;
import com.clemble.casino.game.action.surrender.GiveUpAction;
import com.clemble.casino.game.action.surrender.MoveTimeoutSurrenderAction;
import com.clemble.casino.game.action.surrender.TotalTimeoutSurrenderAction;
import com.clemble.casino.game.construct.AutomaticGameRequest;
import com.clemble.casino.game.construct.AvailabilityGameRequest;
import com.clemble.casino.game.construct.ManagerGameConstructionRequest;
import com.clemble.casino.game.construct.ScheduledGameRequest;
import com.clemble.casino.game.event.schedule.GameCanceledEvent;
import com.clemble.casino.game.event.schedule.GameConstructedEvent;
import com.clemble.casino.game.event.schedule.InvitationAcceptedEvent;
import com.clemble.casino.game.event.schedule.InvitationDeclinedEvent;
import com.clemble.casino.game.event.schedule.PlayerInvitedEvent;
import com.clemble.casino.game.event.server.RoundEndedEvent;
import com.clemble.casino.game.iterator.SequentialPlayerIterator;
import com.clemble.casino.game.RoundGameContext;
import com.clemble.casino.game.outcome.DrawOutcome;
import com.clemble.casino.game.outcome.NoOutcome;
import com.clemble.casino.game.outcome.PlayerWonOutcome;
import com.clemble.casino.game.rule.bet.BetRule;
import com.clemble.casino.game.rule.bet.FixedBetRule;
import com.clemble.casino.game.rule.bet.FixedChipsBetRule;
import com.clemble.casino.game.rule.bet.LimitedBetRule;
import com.clemble.casino.game.rule.bet.UnlimitedBetRule;
import com.clemble.casino.game.rule.construct.PlayerNumberRule;
import com.clemble.casino.game.rule.construct.PrivacyRule;
import com.clemble.casino.game.rule.giveup.GiveUpRule;
import com.clemble.casino.game.rule.time.MoveTimeRule;
import com.clemble.casino.game.rule.time.TotalTimeRule;
import com.clemble.casino.game.specification.TournamentGameConfiguration;

class GameJsonModule implements ClembleJsonModule {

    @Override
    public Module construct() {
        SimpleModule module = new SimpleModule("Game");
        module.registerSubtypes(new NamedType(BetAction.class, BetAction.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(DefaultGameAction.class, DefaultGameAction.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(SelectAction.class, SelectAction.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(GiveUpAction.class, GiveUpAction.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(MoveTimeoutSurrenderAction.class, MoveTimeoutSurrenderAction.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(TotalTimeoutSurrenderAction.class, TotalTimeoutSurrenderAction.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(AutomaticGameRequest.class, AutomaticGameRequest.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(AvailabilityGameRequest.class, AvailabilityGameRequest.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(ManagerGameConstructionRequest.class, ManagerGameConstructionRequest.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(ScheduledGameRequest.class, ScheduledGameRequest.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(GameCanceledEvent.class, GameCanceledEvent.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(GameConstructedEvent.class, GameConstructedEvent.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(InvitationAcceptedEvent.class, InvitationAcceptedEvent.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(InvitationDeclinedEvent.class, InvitationDeclinedEvent.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(PlayerInvitedEvent.class, PlayerInvitedEvent.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(GameInitiatedEvent.class, GameInitiatedEvent.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(GameInitiationCanceledEvent.class, GameInitiationCanceledEvent.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(GameInitiationConfirmedEvent.class, GameInitiationConfirmedEvent.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(RoundEndedEvent.class, RoundEndedEvent.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(RoundStartedEvent.class, RoundStartedEvent.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(RoundStateChangedEvent.class, RoundStateChangedEvent.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(MatchChangedEvent.class, MatchChangedEvent.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(MatchEndedEvent.class, MatchEndedEvent.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(MatchStartedEvent.class, MatchStartedEvent.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(GameTournamentEndedEvent.class, GameTournamentEndedEvent.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(GameTournamentStartedEvent.class, GameTournamentStartedEvent.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(PlayerMovedEvent.class, PlayerMovedEvent.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(SequentialPlayerIterator.class, SequentialPlayerIterator.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(RoundGameContext.class, RoundGameContext.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(RoundGameRecord.class, RoundGameRecord.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(DrawOutcome.class, DrawOutcome.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(NoOutcome.class, NoOutcome.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(PlayerWonOutcome.class, PlayerWonOutcome.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(MatchGameContext.class, MatchGameContext.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(MatchGameRecord.class, MatchGameRecord.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(BetRule.class, BetRule.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(FixedBetRule.class, FixedBetRule.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(FixedChipsBetRule.class, FixedChipsBetRule.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(LimitedBetRule.class, LimitedBetRule.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(UnlimitedBetRule.class, UnlimitedBetRule.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(PlayerNumberRule.class, PlayerNumberRule.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(PrivacyRule.class, PrivacyRule.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(GiveUpRule.class, GiveUpRule.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(MoveTimeRule.class, MoveTimeRule.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(TotalTimeRule.class, TotalTimeRule.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(RoundGameConfiguration.class, RoundGameConfiguration.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(MatchGameConfiguration.class, MatchGameConfiguration.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(TournamentGameConfiguration.class, TournamentGameConfiguration.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(TournamentGameContext.class, TournamentGameContext.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(TournamentGameRecord.class, TournamentGameRecord.class.getAnnotation(JsonTypeName.class).value()));
        return module;
    }

}

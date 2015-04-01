package com.clemble.casino.client;

import java.io.Closeable;

import com.clemble.casino.client.goal.GoalOperations;
import com.clemble.casino.payment.service.PaymentTransactionOperations;
import com.clemble.casino.payment.service.PlayerAccountService;
import com.clemble.casino.player.service.*;
import com.clemble.casino.registration.service.PlayerPasswordResetService;
import com.clemble.casino.tag.service.PlayerTagService;
import org.springframework.social.ApiBinding;
import org.springframework.web.client.RestTemplate;

import com.clemble.casino.client.event.EventListenerOperations;
import com.clemble.casino.player.PlayerAware;

public interface ClembleCasinoOperations extends ApiBinding, Closeable, PlayerAware {

    PlayerProfileService profileOperations();

    PlayerImageService imageOperations();

    PlayerNotificationService notificationService();

    PlayerFeedService feedService();

    PlayerConnectionService connectionOperations();

    PlayerFriendInvitationService friendInvitationService();

    PlayerAccountService accountService();

    PaymentTransactionOperations paymentOperations();

    EventListenerOperations listenerOperations();

    GoalOperations goalOperations();

    PlayerPasswordResetService passwordResetService();

    PlayerEmailService emailService();

    PlayerTagService tagService();

    // TODO safety concern, since RestTemplate is reused all over the place, make a deep copy of returned rest template
    RestTemplate getRestTemplate();

    void signOut();

    @Override
    void close();

}

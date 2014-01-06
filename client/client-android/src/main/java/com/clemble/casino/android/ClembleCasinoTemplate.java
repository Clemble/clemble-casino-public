package com.clemble.casino.android;

import static com.clemble.casino.utils.Preconditions.checkNotNull;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.social.oauth1.AbstractOAuth1ApiBinding;
import org.springframework.social.oauth1.RSARequestSigner;
import org.springframework.web.client.RestTemplate;

import com.clemble.casino.ServerRegistry;
import com.clemble.casino.SingletonRegistry;
import com.clemble.casino.android.game.service.AndroidGameActionTemplate;
import com.clemble.casino.android.game.service.AndroidGameConstructionService;
import com.clemble.casino.android.game.service.AndroidGameInitiationService;
import com.clemble.casino.android.game.service.AndroidGameSpecificationService;
import com.clemble.casino.android.payment.AndroidPaymentTransactionService;
import com.clemble.casino.android.player.AndroidPlayerConnectionService;
import com.clemble.casino.android.player.AndroidPlayerPresenceService;
import com.clemble.casino.android.player.AndroidPlayerProfileService;
import com.clemble.casino.android.player.AndroidPlayerSessionService;
import com.clemble.casino.client.ClembleCasinoOperations;
import com.clemble.casino.client.error.ClembleCasinoResponseErrorHandler;
import com.clemble.casino.client.event.EventListenerOperations;
import com.clemble.casino.client.event.EventTypeSelector;
import com.clemble.casino.client.event.GameInitiationReadyEventEmulator;
import com.clemble.casino.client.event.PlayerToMoveEventEmulator;
import com.clemble.casino.client.event.RabbitEventListenerTemplate;
import com.clemble.casino.client.game.GameActionOperations;
import com.clemble.casino.client.game.GameActionOperationsFactory;
import com.clemble.casino.client.game.GameActionTemplateFactory;
import com.clemble.casino.client.game.GameConstructionOperations;
import com.clemble.casino.client.game.GameConstructionTemplate;
import com.clemble.casino.client.payment.PaymentOperations;
import com.clemble.casino.client.payment.PaymentTemplate;
import com.clemble.casino.client.player.PlayerConnectionOperations;
import com.clemble.casino.client.player.PlayerConnectionTemplate;
import com.clemble.casino.client.player.PlayerPresenceOperations;
import com.clemble.casino.client.player.PlayerPresenceTemplate;
import com.clemble.casino.client.player.PlayerProfileOperations;
import com.clemble.casino.client.player.PlayerProfileTemplate;
import com.clemble.casino.client.player.PlayerSessionOperations;
import com.clemble.casino.client.player.PlayerSessionTemplate;
import com.clemble.casino.configuration.ResourceLocations;
import com.clemble.casino.configuration.ServerRegistryConfiguration;
import com.clemble.casino.game.Game;
import com.clemble.casino.game.GameSessionKey;
import com.clemble.casino.game.GameState;
import com.clemble.casino.game.event.server.GameInitiatedEvent;
import com.clemble.casino.game.event.server.GameStateManagementEvent;
import com.clemble.casino.game.service.GameActionService;
import com.clemble.casino.game.service.GameConstructionService;
import com.clemble.casino.game.service.GameInitiationService;
import com.clemble.casino.game.service.GameSpecificationService;
import com.clemble.casino.payment.service.PaymentService;
import com.clemble.casino.player.service.PlayerConnectionService;
import com.clemble.casino.player.service.PlayerPresenceService;
import com.clemble.casino.player.service.PlayerProfileService;
import com.clemble.casino.utils.CollectionUtils;

public class ClembleCasinoTemplate extends AbstractOAuth1ApiBinding implements ClembleCasinoOperations {

    /**
     * Generated 19/11/13
     */
    private static final long serialVersionUID = 103204849955372481L;

    final private String player;
    final private EventListenerOperations listenerOperations;
    final private PlayerSessionOperations playerSessionOperations;
    final private PlayerProfileOperations profileOperations;
    final private PlayerConnectionOperations connectionOperations;
    final private PlayerPresenceOperations presenceOperations;
    final private PaymentOperations transactionOperations;
    final private Map<Game, GameConstructionOperations<?>> gameToConstructionOperations;

    @SuppressWarnings({ "rawtypes" })
    public ClembleCasinoTemplate(
            String consumerKey,
            String consumerSecret,
            String accessToken,
            String accessTokenSecret,
            String player,
            String managementUrl)
            throws IOException {
        super(consumerKey, consumerSecret, accessToken, accessTokenSecret, new RSARequestSigner());

        this.player = player;
        this.playerSessionOperations = new PlayerSessionTemplate(player, new AndroidPlayerSessionService(getRestTemplate(), new SingletonRegistry(managementUrl)));
        ResourceLocations resourceLocations = checkNotNull(playerSessionOperations.create().getResourceLocations());
        ServerRegistryConfiguration registryConfiguration = resourceLocations.getServerRegistryConfiguration();

        this.listenerOperations = new RabbitEventListenerTemplate(player, resourceLocations.getNotificationConfiguration(), ClembleCasinoConstants.OBJECT_MAPPER);
        // TODO either make it part of server event or find a nicer way to deal with this
        this.listenerOperations.subscribe(new EventTypeSelector(GameStateManagementEvent.class), new PlayerToMoveEventEmulator(player, listenerOperations));
        // Step 1. Creating PlayerProfile service
        PlayerProfileService playerProfileService = new AndroidPlayerProfileService(getRestTemplate(), registryConfiguration.getPlayerRegistry());
        this.profileOperations = new PlayerProfileTemplate(player, playerProfileService);
        // Step 1.1. Creating Player connections service
        PlayerConnectionService playerConnectionService = new AndroidPlayerConnectionService(getRestTemplate(), registryConfiguration.getPlayerRegistry());
        this.connectionOperations = new PlayerConnectionTemplate(player, playerConnectionService, profileOperations);
        // Step 2. Creating PlayerPresence service
        PlayerPresenceService playerPresenceService = new AndroidPlayerPresenceService(getRestTemplate(), registryConfiguration.getPlayerRegistry());
        this.presenceOperations = new PlayerPresenceTemplate(player, playerPresenceService, listenerOperations);
        // Step 3. Creating PaymentTransaction service
        ServerRegistry paymentServerRegistry = registryConfiguration.getPaymentRegistry();
        PaymentService paymentTransactionService = new AndroidPaymentTransactionService(getRestTemplate(), paymentServerRegistry);
        this.transactionOperations = new PaymentTemplate(player, paymentTransactionService, listenerOperations);
        // Step 4. Creating GameConstruction services
        Map<Game, GameConstructionOperations<?>> gameToConstructor = new EnumMap<Game, GameConstructionOperations<?>>(Game.class);
        for (Game game : resourceLocations.getGames()) {
            ServerRegistry gameRegistry = registryConfiguration.getGameRegistry();
            GameConstructionService constructionService = new AndroidGameConstructionService(getRestTemplate(), gameRegistry);
            GameInitiationService initiationService = new AndroidGameInitiationService(getRestTemplate(), gameRegistry);
            GameSpecificationService specificationService = new AndroidGameSpecificationService(getRestTemplate(), gameRegistry);
            GameActionService<?> actionService = new AndroidGameActionTemplate(gameRegistry, getRestTemplate());
            GameActionOperationsFactory actionOperationsFactory = new GameActionTemplateFactory(player, listenerOperations, actionService);
            GameConstructionOperations<?> constructionOperations = new GameConstructionTemplate(player, game, actionOperationsFactory, constructionService, initiationService, specificationService, listenerOperations);
            gameToConstructor.put(game, constructionOperations);
        }
        this.gameToConstructionOperations = CollectionUtils.immutableMap(gameToConstructor);
        // TODO remove when client, will be doing it automatically
        this.listenerOperations.subscribe(new EventTypeSelector(GameInitiatedEvent.class), new GameInitiationReadyEventEmulator(gameToConstructor));
    }

    @Override
    public PlayerProfileOperations profileOperations() {
        return profileOperations;
    }

    @Override
    public PlayerConnectionOperations connectionOperations(){
        return connectionOperations;
    }

    @Override
    public PlayerPresenceOperations presenceOperations() {
        return presenceOperations;
    }

    @Override
    public PlayerSessionOperations sessionOperations() {
        return playerSessionOperations;
    }

    @Override
    public PaymentOperations paymentOperations() {
        return transactionOperations;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends GameState> GameConstructionOperations<T> gameConstructionOperations(Game game) {
        return (GameConstructionOperations<T>) gameToConstructionOperations.get(game);
    }

    @Override
    public Map<Game, GameConstructionOperations<?>> gameConstructionOperations() {
        return gameToConstructionOperations;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <State extends GameState> GameActionOperations<State> gameActionOperations(GameSessionKey session) {
        // Step 1. Getting associated game construction service
        GameConstructionOperations<State> constructionOperations = (GameConstructionOperations<State>) gameToConstructionOperations.get(session.getGame());
        // Step 2. Constructing appropriate action operations
        return constructionOperations.getActionOperations(session.getSession());
    }

    @Override
    public String getPlayer() {
        return player;
    }

    @Override
    public EventListenerOperations listenerOperations() {
        return listenerOperations;
    }

    /**
     * Returns a {@link MappingJackson2HttpMessageConverter} to be used by the internal {@link RestTemplate}. Override to customize the message converter (for
     * example, to set a custom object mapper or supported media types). To remove/replace this or any of the other message converters that are registered by
     * default, override the getMessageConverters() method instead.
     */
    @Override
    protected MappingJackson2HttpMessageConverter getJsonMessageConverter() {
        MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter();
        jacksonConverter.setObjectMapper(ClembleCasinoConstants.OBJECT_MAPPER);
        return jacksonConverter;
    }

    @Override
    protected void configureRestTemplate(RestTemplate restTemplate) {
        restTemplate.setErrorHandler(new ClembleCasinoResponseErrorHandler(ClembleCasinoConstants.OBJECT_MAPPER));
    }

    @Override
    public void close() {
        if(listenerOperations != null)
            listenerOperations.close();
    }
}

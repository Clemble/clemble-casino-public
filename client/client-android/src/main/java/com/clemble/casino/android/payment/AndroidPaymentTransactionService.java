package com.clemble.casino.android.payment;

import static com.clemble.casino.utils.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.List;

import com.clemble.casino.payment.money.Currency;
import com.clemble.casino.web.payment.PaymentWebMapping;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.clemble.casino.ServerRegistry;
import com.clemble.casino.android.AbstractClembleCasinoOperations;
import com.clemble.casino.payment.PaymentTransaction;
import com.clemble.casino.payment.PlayerAccount;
import com.clemble.casino.payment.service.PaymentService;
import com.clemble.casino.utils.CollectionUtils;
import static com.clemble.casino.web.payment.PaymentWebMapping.*;

public class AndroidPaymentTransactionService extends AbstractClembleCasinoOperations implements PaymentService {

    final private RestTemplate restTemplate;

    public AndroidPaymentTransactionService(RestTemplate restTemplate, ServerRegistry apiBase) {
        super(apiBase);
        this.restTemplate = checkNotNull(restTemplate);
    }

    @Override
    public PaymentTransaction getTransaction(String source, String transactionId) {
        return restTemplate.getForObject(buildUriWith(PAYMENT_TRANSACTIONS_TRANSACTION, source, transactionId), PaymentTransaction.class);
    }

    @Override
    public List<PaymentTransaction> getPlayerTransactions(String player) {
        return CollectionUtils.immutableList(restTemplate.getForObject(buildUriWith(PAYMENT_ACCOUNTS_PLAYER_TRANSACTIONS, player), PaymentTransaction[].class));
    }

    @Override
    public PlayerAccount get(String player) {
        return restTemplate.getForObject(buildUriWith(ACCOUNTS_PLAYER, player), PlayerAccount.class);
    }

    @Override
    public List<String> canAfford(Collection<String> players, Currency currency, Long amount) {
        // Step 1. Generating URL
        String url = buildUriWith(PAYMENT_ACCOUNTS) + "?currency=" + currency + "&amount=" + amount;
        for(String player: players)
            url += "&player=" + player;
        // Step 2. Sending and receiving response
        return CollectionUtils.immutableList(restTemplate.getForObject(url, String[].class));
    }

    @Override
    public List<PaymentTransaction> getPlayerTransactionsWithSource(String player, String source) {
        return CollectionUtils.immutableList(restTemplate.getForObject(buildUriWith(PAYMENT_ACCOUNTS_PLAYER_TRANSACTION_SOURCE, player, source), PaymentTransaction[].class));
    }

}

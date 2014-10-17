package com.clemble.casino.android.goal;

import com.clemble.casino.android.AbstractClembleCasinoOperations;
import com.clemble.casino.goal.lifecycle.record.GoalRecord;
import com.clemble.casino.goal.lifecycle.record.service.GoalRecordService;
import com.clemble.casino.utils.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

import static com.clemble.casino.goal.GoalWebMapping.*;

/**
 * Created by mavarazy on 17/10/14.
 */
public class AndroidGoalRecordService extends AbstractClembleCasinoOperations implements GoalRecordService {

    final private RestTemplate restTemplate;

    public AndroidGoalRecordService(String apiBase, RestTemplate restTemplate) {
        super(apiBase);
        this.restTemplate = restTemplate;
    }

    @Override
    public List<GoalRecord> myRecords() {
        // Step 1. Generating goal construction URI
        URI pendingConstructionUrl = buildUriWith(toGoalConstructionUrl(MY_RECORDS));
        // Step 2. Creating new GoalConstruction
        return CollectionUtils.immutableList(restTemplate.getForObject(pendingConstructionUrl, GoalRecord[].class));
    }

    @Override
    public GoalRecord get(String goalKey) {
        // Step 1. Generating goal construction URI
        URI pendingConstructionUrl = buildUriWith(toGoalConstructionUrl(GOAL_RECORD), goalKey);
        // Step 2. Creating new GoalConstruction
        return restTemplate.getForObject(pendingConstructionUrl, GoalRecord.class);
    }

}
package com.clemble.casino.goal.lifecycle.record.service;

import com.clemble.casino.goal.lifecycle.configuration.GoalConfiguration;
import com.clemble.casino.goal.lifecycle.record.GoalRecord;
import com.clemble.casino.lifecycle.record.Record;
import com.clemble.casino.lifecycle.record.RecordState;
import com.clemble.casino.lifecycle.record.service.RecordService;

import java.util.List;

/**
 * Created by mavarazy on 9/20/14.
 */
public interface GoalRecordService extends RecordService<GoalConfiguration> {

    @Override
    List<GoalRecord> myRecords();

    @Override
    List<GoalRecord> myRecordsWithState(RecordState state);

    @Override
    GoalRecord get(String key);

}

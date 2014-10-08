package com.clemble.casino.game.lifecycle.record.service;

import com.clemble.casino.game.lifecycle.record.GameRecord;
import com.clemble.casino.game.lifecycle.configuration.GameConfiguration;
import com.clemble.casino.lifecycle.record.service.RecordService;

import java.util.List;

public interface GameRecordService extends RecordService<GameConfiguration> {

    @Override
    public List<GameRecord> myRecords();

    @Override
    public GameRecord get(String sessionKey);

}
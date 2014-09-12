package com.clemble.casino.construction;

import com.clemble.casino.configuration.Configuration;
import com.clemble.casino.configuration.ConfigurationAware;

/**
 * Created by mavarazy on 9/7/14.
 */
public interface Construction<T extends Configuration> extends ConfigurationAware<T> {

    ConstructionState getState();

}
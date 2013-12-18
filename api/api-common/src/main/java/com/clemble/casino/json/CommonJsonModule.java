package com.clemble.casino.json;

import com.clemble.casino.DNSBasedServerRegistry;
import com.clemble.casino.base.ExpectedAction;
import com.clemble.casino.player.PlayerPresence;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.module.SimpleModule;

class CommonJsonModule implements ClembleJsonModule {

    @Override
    public Module construct() {
        SimpleModule module = new SimpleModule("Common");
        module.registerSubtypes(new NamedType(ExpectedAction.class, ExpectedAction.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(DNSBasedServerRegistry.class, DNSBasedServerRegistry.class.getAnnotation(JsonTypeName.class).value()));
        module.registerSubtypes(new NamedType(PlayerPresence.class, PlayerPresence.class.getAnnotation(JsonTypeName.class).value()));
        return module;
    }

}

package me.samarthsinha.rugram.core.service;

import me.samarthsinha.rugram.core.entity.LiveRuleVersion;
import org.kie.api.runtime.KieContainer;

public interface RuleContainerWrapper<T> {
    T getRuleContainer(String appId);
    void reloadKieContainer(LiveRuleVersion liveRuleVersion);
}

package me.samarthsinha.rugram.core.service;

import me.samarthsinha.rugram.core.entity.LiveRuleVersion;
import me.samarthsinha.rugram.core.models.RuleTransaction;

public interface RuleExecutionService<R> {
    R execute(String appId,R ruleTransaction);
}

package me.samarthsinha.rugram.core.processors;

import me.samarthsinha.rugram.core.entity.RuleData;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.io.Resource;

public interface RuleProcessor {
    Resource processRuleData(RuleData ruleData, KieServices kieServices);
}

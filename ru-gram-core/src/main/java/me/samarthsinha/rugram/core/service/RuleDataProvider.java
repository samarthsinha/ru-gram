package me.samarthsinha.rugram.core.service;

import me.samarthsinha.rugram.core.entity.LiveRuleVersion;
import me.samarthsinha.rugram.core.entity.RuleData;

import java.util.List;

public interface RuleDataProvider {
     List<LiveRuleVersion> getAllLiveRuleBase();
     List<RuleData> getRuleDetailsLiveRuleBase(List<LiveRuleVersion> liveRuleVersion);
     LiveRuleVersion getLiveRuleBase(String appId);
     void updateLiveVersion(LiveRuleVersion liveRuleVersion);
}

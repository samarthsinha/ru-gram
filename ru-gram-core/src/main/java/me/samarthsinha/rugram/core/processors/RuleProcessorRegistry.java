package me.samarthsinha.rugram.core.processors;

import me.samarthsinha.rugram.core.models.RuleType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class RuleProcessorRegistry {
   private static Map<RuleType, RuleProcessor> ruleProcessors = new HashMap<>();

   protected void register(RuleType ruleType, RuleProcessor ruleProcessor){
       ruleProcessors.put(ruleType,ruleProcessor);
   }

    protected void deRegister(RuleType ruleType){
        ruleProcessors.remove(ruleType);
    }

   public static Optional<RuleProcessor> getRuleProcessor(RuleType ruleType){
       return Optional.of(ruleProcessors.get(ruleType));
   }
}

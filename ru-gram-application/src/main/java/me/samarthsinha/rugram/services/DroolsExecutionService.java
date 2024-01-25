package me.samarthsinha.rugram.services;

import me.samarthsinha.rugram.core.service.RuleContainerWrapper;
import me.samarthsinha.rugram.core.service.RuleExecutionService;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DroolsExecutionService implements RuleExecutionService<Object> {

    private static final Logger logger = LoggerFactory.getLogger(DroolsExecutionService.class);

    private RuleContainerWrapper<KieContainer> droolsKieContainerWrapper;

    public DroolsExecutionService(RuleContainerWrapper<KieContainer> droolsKieContainerWrapper) {
        this.droolsKieContainerWrapper = droolsKieContainerWrapper;
    }

    @Override
    public Object execute(String appId, Object ruleTransaction) {
        KieContainer ruleContainer = droolsKieContainerWrapper.getRuleContainer(appId);
        if(ruleContainer==null) return ruleTransaction;
        KieSession kieSession = null;
        try{
            kieSession = ruleContainer.newKieSession();
            kieSession.insert(ruleTransaction);
            kieSession.fireAllRules();
        }catch (Exception e){
            logger.error("Error in executing rule with rule data : {}",ruleTransaction);
        }finally {
            if(kieSession!=null){
                kieSession.dispose();
            }
        }
        return ruleTransaction;
    }
}

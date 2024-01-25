package me.samarthsinha.rugram.core.service;

import me.samarthsinha.rugram.core.entity.LiveRuleVersion;
import me.samarthsinha.rugram.core.entity.RuleData;
import me.samarthsinha.rugram.core.models.RuleType;
import me.samarthsinha.rugram.core.processors.RuleProcessor;
import me.samarthsinha.rugram.core.processors.RuleProcessorRegistry;
import me.samarthsinha.rugram.core.utils.ApplicationContextProvider;
import org.apache.commons.lang.StringUtils;
import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Wrapper Service to instantiate Drools Kie container from provided RuleDataProvider
 */
@Service("droolsKieContainerWrapperService")
public class DroolsKieContainerWrapperService implements InitializingBean, DisposableBean, RuleContainerWrapper<KieContainer> {
    private static final Logger logger = LoggerFactory.getLogger(DroolsKieContainerWrapperService.class);

    private Map<String,KieContainer> kieContainers;
    private RuleDataProvider ruleDataProvider;

    public DroolsKieContainerWrapperService(RuleDataProvider ruleDataProvider) {
        this.ruleDataProvider = ruleDataProvider;
        this.kieContainers = new ConcurrentHashMap<>();
    }

    @Override
    public KieContainer getRuleContainer(String appId) {
        if(StringUtils.isBlank(appId)){
            return null;
        }
        String containerKey = appId;
        KieContainer kieContainer = kieContainers.get(containerKey);
        if(kieContainer == null){
            synchronized (this){
                if(kieContainer == null){
                    kieContainer = createKieContainer(appId);
                    kieContainers.put(containerKey,kieContainer);
                }
            }
        }
        return kieContainers.get(containerKey);
    }

    @Override
    public void reloadKieContainer(LiveRuleVersion liveRuleVersion) {
        LiveRuleVersion liveRuleBase = ruleDataProvider.getLiveRuleBase(liveRuleVersion.getAppId());
        long currentVersion = liveRuleBase.getVersion();
        long updateVersion = liveRuleVersion.getVersion();
        if(updateVersion>currentVersion){
            //Trigger Update;
            logger.debug("Rule Container for appId: {} having current version: {} is being update to version: {}",
                    liveRuleVersion.getAppId(),currentVersion,updateVersion);
            KieContainer kieContainer = kieContainers.get(liveRuleVersion.getAppId());
            if(kieContainer!=null){
                kieContainer.dispose();
            }
            kieContainers.remove(liveRuleVersion.getAppId());
            ruleDataProvider.updateLiveVersion(liveRuleVersion);
        }
    }


    private KieContainer createKieContainer(String appId){
        LiveRuleVersion liveRuleBase = ruleDataProvider.getLiveRuleBase(appId);
        List<RuleData> ruleDetailsLiveRuleBase = ruleDataProvider.getRuleDetailsLiveRuleBase(Collections.singletonList(liveRuleBase));
        return processLiveRule(ruleDetailsLiveRuleBase);
    }

    /**
     *
     * @param rulesToAdd
     * @return
     */
    private KieContainer processLiveRule(List<RuleData> rulesToAdd){
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        for(RuleData ruleData:rulesToAdd){
            String ruleType = ruleData.getRuleType();
            RuleType rt = RuleType.valueOf(ruleType);
            Optional<RuleProcessor> ruleProcessor = RuleProcessorRegistry.getRuleProcessor(rt);
            RuleProcessor processor = ruleProcessor.get();
            Resource resource = processor.processRuleData(ruleData,kieServices);
            kieFileSystem.write(resource);
        }
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        Results results = kieBuilder.getResults();
        while(results.hasMessages(Message.Level.ERROR)) {
            logger.error("Error in loading rule content {}",results.getMessages(Message.Level.ERROR));
            throw new RuntimeException("Error in building knoweldge base");
        }
        ReleaseId releaseId = kieBuilder.getKieModule().getReleaseId();
        return kieServices.newKieContainer(releaseId);
    }

    public void destroy() throws Exception {
        if(kieContainers != null){
            for(Map.Entry<String,KieContainer> entry: kieContainers.entrySet()){
                if(entry.getValue()!=null) {
                    entry.getValue().dispose();
                }
            }
        }
    }

    public void afterPropertiesSet() throws Exception {
//        buildKieContainer();
    }

    public void buildKieContainer(){
        List<LiveRuleVersion> allLiveRuleBase = ruleDataProvider.getAllLiveRuleBase();
        List<RuleData> ruleDetailsLiveRuleBase = ruleDataProvider.getRuleDetailsLiveRuleBase(allLiveRuleBase);
        Map<String,List<RuleData>> mappedRule = new HashMap<>();
        ruleDetailsLiveRuleBase.forEach(r ->{
            String key = r.getAppId();
            List<RuleData> rd = mappedRule.getOrDefault(key,new ArrayList<>());
            rd.add(r);
            mappedRule.put(key,rd);
        });
        for (Map.Entry<String, List<RuleData>> entry : mappedRule.entrySet()) {
            String k = entry.getKey();
            List<RuleData> v = entry.getValue();
            logger.debug("-----Started loading rules for key: {} ------", k);
            kieContainers.put(k, processLiveRule(v));
            logger.debug("-----Successfully loaded rules for key: {} ------", k);
        }
    }
}

package me.samarthsinha.rugram.core.processors;

import me.samarthsinha.rugram.core.entity.RuleData;
import me.samarthsinha.rugram.core.models.RuleType;
import org.kie.api.KieServices;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service("jsonRuleProcessor")
public class JsonRuleProcessor extends RuleProcessorRegistry implements InitializingBean, DisposableBean, RuleProcessor {
    private static final Logger logger = LoggerFactory.getLogger(JsonRuleProcessor.class);

    @Override
    public Resource processRuleData(RuleData ruleData, KieServices kieServices) {
        String pathUrl = ruleData.getPathUrl();
        logger.error("JSON Rule Content: {}",pathUrl);
        Resource resource = ResourceFactory.newByteArrayResource(ruleData.getRawRules()).setTargetPath(pathUrl).setResourceType(ResourceType.DRL);
        return resource;
    }

    @Override
    public void destroy() throws Exception {
        deRegister(RuleType.JSON_RULE);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        register(RuleType.JSON_RULE,this);
    }
}

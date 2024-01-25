package me.samarthsinha.rugram.core.processors;

import me.samarthsinha.rugram.core.entity.RuleData;
import me.samarthsinha.rugram.core.models.RuleType;
import org.kie.api.KieServices;
import org.kie.api.io.Resource;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service("fileUrlRuleProcessor")
public class FileUrlBasedRuleProcessor extends RuleProcessorRegistry implements InitializingBean, DisposableBean, RuleProcessor {
    private static final Logger logger = LoggerFactory.getLogger(FileUrlBasedRuleProcessor.class);

    @Override
    public Resource processRuleData(RuleData ruleData, KieServices kieServices) {
        String pathUrl = ruleData.getPathUrl();
        logger.error("Url: {}",pathUrl);
        return ResourceFactory.newUrlResource(pathUrl);
    }

    @Override
    public void destroy() throws Exception {
        deRegister(RuleType.FILE_URL);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        register(RuleType.FILE_URL,this);
    }
}

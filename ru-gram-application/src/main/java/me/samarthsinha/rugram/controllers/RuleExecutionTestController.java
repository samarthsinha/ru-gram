package me.samarthsinha.rugram.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.samarthsinha.rugram.core.entity.LiveRuleVersion;
import me.samarthsinha.rugram.core.service.RuleContainerWrapper;
import me.samarthsinha.rugram.core.service.RuleExecutionService;
import org.kie.api.runtime.KieContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

@RestController
@RequestMapping("/v1/rule")
public class RuleExecutionTestController {

    @Autowired
    private RuleExecutionService ruleExecutionService;

    @Autowired
    private RuleContainerWrapper<KieContainer> kieContainerRuleContainerWrapper;

    private String readProperty(String propertyKey) throws IOException {
        FileReader reader= null;
        try {
            ClassPathResource classPathResource = new ClassPathResource("application.properties");
            reader = new FileReader(classPathResource.getFile());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Properties p=new Properties();
        p.load(reader);
        return p.getProperty(propertyKey);
    }

    @Autowired
    ObjectMapper objectMapper;


    @PostMapping("/execute")
    public Object ruleExecution(@RequestHeader String appId, @RequestBody Object o) throws IOException, ClassNotFoundException {
        String s = readProperty("input.object.class");
        Map<String, String> stringStringMap = objectMapper.readValue(s, new TypeReference<Map<String, String>>() {
        });
        String s1 = stringStringMap.get(appId);
        if(s1!=null) {
            Class<?> aClass = Class.forName(s1);
            Object o1 = objectMapper.convertValue(o, aClass);
            return ruleExecutionService.execute(appId,o1);
        }
        return ruleExecutionService.execute(appId,o);
    }

    @PostMapping("/update")
    public Object ruleExecution(@RequestBody LiveRuleVersion liveRuleVersion){
        kieContainerRuleContainerWrapper.reloadKieContainer(liveRuleVersion);
        return "Successfully Update KB";
    }
}

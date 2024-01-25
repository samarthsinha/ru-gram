package me.samarthsinha.rugram.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.samarthsinha.rugram.core.entity.LiveRuleVersion;
import me.samarthsinha.rugram.core.entity.RuleData;
import me.samarthsinha.rugram.core.service.RuleDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DefaultRuleDataProvider implements RuleDataProvider {

    @Autowired
    private ObjectMapper objectMapper;

    private Map<String,LiveRuleVersion> stringLiveRuleVersionMap = new HashMap<>();

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


    @Override
    public List<LiveRuleVersion> getAllLiveRuleBase() {
        try {
            String s = readProperty("live.rule.versions");
            return objectMapper.readValue(s, new TypeReference<List<LiveRuleVersion>>() {
            });
        } catch (IOException e) {
        }
        return new ArrayList<>();
    }

    @Override
    public List<RuleData> getRuleDetailsLiveRuleBase(List<LiveRuleVersion> liveRuleVersion) {
        try {
            String s = readProperty("rule.data.list");
            List<RuleData> ruleData = objectMapper.readValue(s, new TypeReference<List<RuleData>>() {
            });
            Map<String,LiveRuleVersion> stringLiveRuleVersionMap = new HashMap<>();
            liveRuleVersion.forEach(l -> {
                stringLiveRuleVersionMap.put(l.getAppId()+l.getVersion(),l);
            });
            return ruleData.stream().filter(ruleData1 -> stringLiveRuleVersionMap.containsKey(ruleData1.getAppId()+ruleData1.getVersion())).collect(Collectors.toList());
        } catch (IOException e) {
        }
        return new ArrayList<>();
    }

    @Override
    public LiveRuleVersion getLiveRuleBase(String appId) {
        List<LiveRuleVersion> allLiveRuleBase = getAllLiveRuleBase();
        allLiveRuleBase.forEach(l -> {
            stringLiveRuleVersionMap.put(l.getAppId(),l);
        });
        return stringLiveRuleVersionMap.get(appId);
    }

    @Override
    public void updateLiveVersion(LiveRuleVersion liveRuleVersion) {
        stringLiveRuleVersionMap.put(liveRuleVersion.getAppId(),liveRuleVersion);
    }
}

package me.samarthsinha.rugram.core.entity;

import org.kie.api.io.ResourceType;

import java.io.Serializable;
import java.util.Map;

public class RuleData implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String appId;
    private long version;
    private String description;
    private String ruleType;
    private String ruleName;
    private String imports;
    private String packages;
    private ResourceType resourceType;
    private byte[] rawRules;
    private String jsonRules;
    private String pathUrl;
    private Map<String,Object> extraDetails;
    private String updatedBy;
    private String createdAt;

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    private String updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public byte[] getRawRules() {
        return rawRules;
    }

    public void setRawRules(byte[] rawRules) {
        this.rawRules = rawRules;
    }

    public String getJsonRules() {
        return jsonRules;
    }

    public void setJsonRules(String jsonRules) {
        this.jsonRules = jsonRules;
    }

    public String getPathUrl() {
        return pathUrl;
    }

    public void setPathUrl(String pathUrl) {
        this.pathUrl = pathUrl;
    }

    public Map<String, Object> getExtraDetails() {
        return extraDetails;
    }

    public void setExtraDetails(Map<String, Object> extraDetails) {
        this.extraDetails = extraDetails;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getImports() {
        return imports;
    }

    public void setImports(String imports) {
        this.imports = imports;
    }

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }
}

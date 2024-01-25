package me.samarthsinha.rugram.core.entity;

import java.io.Serializable;

public class LiveRuleVersion implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String appId;
    private long version;
    private String transactionClass;
    private String updatedBy;
    private String createdAt;
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

    public long getVersion() {
        return version;
    }

    public void setVersion(long _version) {
        this.version = _version;
    }

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
}

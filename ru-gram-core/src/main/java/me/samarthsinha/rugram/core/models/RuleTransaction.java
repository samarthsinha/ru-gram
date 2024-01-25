package me.samarthsinha.rugram.core.models;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

public class RuleTransaction implements Serializable {
    private static final long serialVersionUID = 1L;
    private String appId;
    private String clientId;
    int years;
    int discount;

    private Collection<Object> inputParams;
    private Map<String,Object> output;

    public RuleTransaction(String appId,String clientId) {
        this.appId = appId;
        this.clientId = clientId;
    }

    public RuleTransaction() {
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Collection<Object> getInputParams() {
        return inputParams;
    }

    public void setInputParams(Collection<Object> inputParams) {
        this.inputParams = inputParams;
    }

    public Map<String, Object> getOutput() {
        return output;
    }

    public void setOutput(Map<String, Object> output) {
        this.output = output;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}

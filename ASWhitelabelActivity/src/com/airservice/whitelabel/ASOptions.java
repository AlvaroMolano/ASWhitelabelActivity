package com.airservice.whitelabel;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by daniel on 13/03/2014.
 */

public class ASOptions implements Serializable
{
    public enum ASEnvironment
    {
        ASEnvironmentQA,
        ASEnvironmentStaging,
        ASEnvironmentProduction
    }

    private ASEnvironment environment;
    private String venueAlias;
    private String appID;
    private String appToken;
    private String brandColor;
    private String filter;
    private Boolean loggingEnabled;

    public ASOptions() {
        setEnvironment(ASEnvironment.ASEnvironmentProduction);
    }

    public ASOptions(String appID, String appToken) {
        setEnvironment(ASEnvironment.ASEnvironmentProduction);
        setLoggingEnabled(false);
        setAppID(appID);
        setAppToken(appToken);
    }

    public ASEnvironment getEnvironment() {
        return environment;
    }

    public void setEnvironment(ASEnvironment environment) {

        if (environment == null)
        {
            environment = ASEnvironment.ASEnvironmentProduction;
        }

        this.environment = environment;
    }

    public String getVenueAlias() {
        return venueAlias;
    }

    public void setVenueAlias(String venueAlias) {

        if (TextUtils.isEmpty(venueAlias))
        {
            throw new IllegalArgumentException("ASOptions venueAlias must be non-null or empty");
        }

        this.venueAlias = venueAlias;
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {

        if (TextUtils.isEmpty(appID))
        {
            throw new IllegalArgumentException("ASOptions appID must be non-null or empty");
        }

        this.appID = appID;
    }

    public String getAppToken() {
        return appToken;
    }

    public void setAppToken(String appToken) {

        if (TextUtils.isEmpty(appToken))
        {
            throw new IllegalArgumentException("ASOptions appToken must be non-null or empty");
        }

        this.appToken = appToken;
    }

    public String getBrandColor() {
        return brandColor;
    }

    public void setBrandColor(String brandColor) {
        this.brandColor = brandColor;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {

        if (TextUtils.isEmpty(filter))
        {
            throw new IllegalArgumentException("ASOptions filter must be non-null or empty");
        }

        this.filter = filter;
    }

    public Boolean getLoggingEnabled() {
        return loggingEnabled;
    }

    public void setLoggingEnabled(Boolean loggingEnabled) {
        this.loggingEnabled = loggingEnabled;
    }
}

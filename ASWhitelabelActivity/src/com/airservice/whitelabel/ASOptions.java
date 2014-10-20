package com.airservice.whitelabel;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.HashMap;

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

    private final static String ASWhitelabelUrlQA = "https://qa.whitelabel.airservice.com";
    private final static String ASWhitelabelUrlStaging = "https://staging.whitelabel.airservice.com";
    private final static String ASWhitelabelUrl = "https://whitelabel.airservice.com";

    private final static String ASAPIURLQA = "https://qa.venues.airservice.co/api/";
    private final static String ASAPIURLStaging = "https://staging.venues.airservice.co/api/";
    private final static String ASAPIURL = "https://venues.airservice.co/api/";
    private final static String ASAPIVersion = "2";

    private ASEnvironment environment;
    private String venueAlias;
    private String appID;
    private String appToken;
    private String brandColor;
    private String filter;
    private String displayName;
    private String appIdentifier;
    private Boolean loggingEnabled;
    private HashMap<String, String> customParameters;

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

    public String getDisplayName() { return displayName; }

    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public String getAppIdentifier() { return appIdentifier; }

    public void setAppIdentifier(String appIdentifier) { this.appIdentifier = appIdentifier; }

    public HashMap<String, String> getCustomParameters() {
        return customParameters;
    }

    public void setCustomParameters(HashMap<String, String> customParameters) {
        this.customParameters = customParameters;
    }

    public String environmentURL()
    {
        switch (this.getEnvironment())
        {
            case ASEnvironmentProduction:
                return ASWhitelabelUrl;
            case ASEnvironmentStaging:
                return ASWhitelabelUrlStaging;
            case ASEnvironmentQA:
                return ASWhitelabelUrlQA;
            default:
                return ASWhitelabelUrl;
        }
    }

    public String apiURL()
    {
        switch (this.getEnvironment())
        {
            case ASEnvironmentProduction:
                return ASAPIURL + ASAPIVersion;
            case ASEnvironmentStaging:
                return ASAPIURLStaging + ASAPIVersion;
            case ASEnvironmentQA:
                return ASAPIURLQA + ASAPIVersion;
            default:
                return ASAPIURL + ASAPIVersion;
        }
    }
}

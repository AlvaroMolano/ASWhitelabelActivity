package com.airservice.whitelabel;

import android.text.TextUtils;

/**
 * Created by daniel on 13/03/2014.
 */

public class ASOptions
{
    public enum ASEnvironment
    {
        ASEnvironmentQA,
        ASEnvironmentStaging,
        ASEnvironmentProduction
    }

    private ASEnvironment environment;
    private String venueSlug;
    private String appID;
    private String appToken;
    private String defaultColor;
    private String collection;

    //default constructor
    public ASOptions() {
        setEnvironment(ASEnvironment.ASEnvironmentProduction);
    }

    //venue specific constructor
    public ASOptions(String appID, String appToken, String venueSlug) {
        setEnvironment(ASEnvironment.ASEnvironmentProduction);
        setAppID(appID);
        setAppToken(appToken);
        setVenueSlug(venueSlug);
    }

    //collection grouped app constructor
    public ASOptions(String appID, String appToken, String collection, String defaultColor) {
        setEnvironment(ASEnvironment.ASEnvironmentProduction);
        setAppID(appID);
        setAppToken(appToken);
        setCollection(collection);
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

    public String getVenueSlug() {
        return venueSlug;
    }

    public void setVenueSlug(String venueSlug) {

        if (TextUtils.isEmpty(venueSlug))
        {
            throw new IllegalArgumentException("ASOptions venueSlug must be non-null or empty");
        }

        this.venueSlug = venueSlug;
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

    public String getDefaultColor() {
        return defaultColor;
    }

    public void setDefaultColor(String defaultColor) {
        this.defaultColor = defaultColor;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {

        if (TextUtils.isEmpty(collection))
        {
            throw new IllegalArgumentException("ASOptions collection must be non-null or empty");
        }

        this.collection = collection;
    }
}

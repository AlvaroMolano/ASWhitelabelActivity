package com.airservice.whitelabel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class ASWhitelabelActivity extends Activity {

    private final static String TAG = "ASWhitelabelActivity";

    public WebView webView;
    public WebViewClient webViewClient;

    public ASOptions asOptions;

    public static void setOptions(Intent intent, ASOptions options)
    {
        intent.putExtra("AS_EXTRA_OPTIONS", options);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.com_airservice_whitelabel_activity_layout);

        validateOptions();

        webView = (WebView)findViewById(R.id.whitelabelWebview);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        webView.setWebChromeClient(new WebChromeClient() {
            public void onGeolocationPermissionsShowPrompt(
                    String origin,
                    GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }
        });

        webViewClient = new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                if (asOptions.getLoggingEnabled())
                {
                    Log.i(TAG, "webview started loading: " + url);
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);

                if (asOptions.getLoggingEnabled())
                {
                    Log.i(TAG, "webview received error: " + description);
                }

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ASWhitelabelActivity.this);
                alertDialog.setMessage(description);
                alertDialog.setTitle("Error");
                alertDialog.setCancelable(false);
                alertDialog.setPositiveButton("Reload", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        webView.reload();
                    }
                });
                alertDialog.create().show();

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                webView.setVisibility(View.VISIBLE);

                if (asOptions.getLoggingEnabled())
                {
                    Log.i(TAG, "webview finished loading: " + url);
                }
            }
        };

        webView.setWebViewClient(webViewClient);
/*
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                if (asOptions.getLoggingEnabled())
                {
                    Log.i(TAG, "webview started loading: " + url);
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);

                if (asOptions.getLoggingEnabled())
                {
                    Log.i(TAG, "webview received error: " + description);
                }

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ASWhitelabelActivity.this);
                alertDialog.setMessage(description);
                alertDialog.setTitle("Error");
                alertDialog.setCancelable(false);
                alertDialog.setPositiveButton("Reload", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        webView.reload();
                    }
                });
                alertDialog.create().show();

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                webView.setVisibility(View.VISIBLE);

                if (asOptions.getLoggingEnabled())
                {
                    Log.i(TAG, "webview finished loading: " + url);
                }
            }
        });
        */

        //loadWhitelabelPage();
    }

    @Override
    public void onStart() {

        super.onStart();

        loadWhitelabelPage();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack())
        {
            webView.goBack();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private String whiteLabelCompleteUrl()
    {
        try {
            URL url = new URL(this.asOptions.environmentURL());

            Uri.Builder builder = new Uri.Builder();
            builder.scheme(url.getProtocol())
                   .authority(url.getAuthority())
                   .appendQueryParameter("app_id", this.asOptions.getAppID())
                   .appendQueryParameter("app_token", this.asOptions.getAppToken());

            if (!TextUtils.isEmpty(this.asOptions.getVenueAlias()))
            {
                builder.appendPath(this.asOptions.getVenueAlias());
                builder.appendQueryParameter("app_type", "venue");
            }

            if (!TextUtils.isEmpty(this.asOptions.getFilter()))
            {
                builder.appendQueryParameter("collection", this.asOptions.getFilter());
            }

            if (!TextUtils.isEmpty(this.asOptions.getBrandColor()))
            {
                builder.appendQueryParameter("default_color", this.asOptions.getBrandColor());
            }

            if (!TextUtils.isEmpty(this.asOptions.getDisplayName()))
            {
                builder.appendQueryParameter("name", this.asOptions.getDisplayName());
            }

            if (!TextUtils.isEmpty(this.asOptions.getAppIdentifier()))
            {
                builder.appendQueryParameter("app_identifier", this.asOptions.getAppIdentifier());
            }

            builder.appendQueryParameter("platform", "android");
            builder.appendQueryParameter("app_store_id", getApplicationContext().getPackageName());

            if (this.asOptions.getCustomParameters() != null) {

                for (Map.Entry<String, String> entry : this.asOptions.getCustomParameters().entrySet()) {
                    builder.appendQueryParameter(entry.getKey(), entry.getValue());
                }
            }

            return builder.build().toString();

        }
        catch (MalformedURLException e) {
            return null;
        }
    }

    private void validateOptions()
    {
        if (getIntent().hasExtra("AS_EXTRA_OPTIONS"))
        {
            this.asOptions = (ASOptions)getIntent().getSerializableExtra("AS_EXTRA_OPTIONS");
        }

        if (this.asOptions == null)
        {
            throw new IllegalArgumentException("Missing ASOptions");
        }

        if (TextUtils.isEmpty(this.asOptions.getAppID()))
        {
            throw new IllegalArgumentException("ASOptions appID must be non-null or empty");
        }

        if (TextUtils.isEmpty(this.asOptions.getAppToken()))
        {
            throw new IllegalArgumentException("ASOptions appToken must be non-null or empty");
        }
    }

    private void loadWhitelabelPage()
    {
        if (isNetworkAvailable())
       {
            webView.loadUrl(whiteLabelCompleteUrl());
        }
        else
        {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(ASWhitelabelActivity.this);
            alertDialog.setMessage("There appears to be a problem with your Internet connection");
            alertDialog.setTitle("Error");
            alertDialog.setCancelable(false);
            alertDialog.setPositiveButton("Reload", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id)
                {
                    loadWhitelabelPage();
                }
            });
            alertDialog.create().show();
        }
    }

    private boolean isNetworkAvailable()
    {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

}
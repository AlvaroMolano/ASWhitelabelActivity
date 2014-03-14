package com.airservice.whitelabel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.*;

import java.net.MalformedURLException;
import java.net.URL;

public class ASWhitelabelActivity extends Activity {

    private final static String ASWhitelabelUrlQA = "https://qa.whitelabel.airservice.com";
    private final static String ASWhitelabelUrlStaging = "https://staging.whitelabel.airservice.com";
    private final static String ASWhitelabelUrl = "https://whitelabel.airservice.com";

    private final static String TAG = "ASWhitelabelActivity";

    WebView webView;

    private ASOptions asOptions;

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

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.i(TAG, "webview started loading: " + url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                Log.i(TAG, "webview received error: " + description);

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
                Log.i(TAG, "webview finished loading: " + url);
            }
        });

        webView.loadUrl(whiteLabelCompleteUrl());
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
            URL url = new URL(environmentURL());

            Uri.Builder builder = new Uri.Builder();
            builder.scheme(url.getProtocol())
                   .authority(url.getAuthority())
                   .appendPath(this.asOptions.getVenueAlias())
                   .appendQueryParameter("app_id", this.asOptions.getAppID())
                   .appendQueryParameter("app_token", this.asOptions.getAppToken())
                   .appendQueryParameter("collection", this.asOptions.getFilter())
                   .appendQueryParameter("default_color", this.asOptions.getBrandColor());

            return builder.build().toString();

        }
        catch (MalformedURLException e) {
            return null;
        }
    }

    private String environmentURL()
    {
        switch (this.asOptions.getEnvironment())
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

        if (TextUtils.isEmpty(this.asOptions.getVenueAlias()) && TextUtils.isEmpty(this.asOptions.getFilter()))
        {
            throw new IllegalArgumentException("ASOptions requires either a venueSlug or a collection");
        }
    }

}
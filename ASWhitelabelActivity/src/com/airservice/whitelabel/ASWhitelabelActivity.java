package com.airservice.whitelabel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.*;

public class ASWhitelabelActivity extends Activity {

    public enum ASEnvironment
    {
        ASEnvironmentQA,
        ASEnvironmentStaging,
        ASEnvironmentProduction
    }

    public final static String AS_EXTRA_ENVIRONMENT = "environment";
    public final static String AS_EXTRA_VENUE_ID = "venue_id";

    private final static String ASWhitelabelUrlQA = "https://qa.whitelabel.airservice.com";
    private final static String ASWhitelabelUrlStaging = "https://staging.whitelabel.airservice.com";
    private final static String ASWhitelabelUrl = "https://whitelabel.airservice.com";

    private final static String TAG = "ASWhitelabelActivity";

    WebView webView;
    private ASEnvironment environment = ASEnvironment.ASEnvironmentProduction;
    private int venueID = 0;


    /*
     * Optional helper methods for settings the parameters instead of manually setting the extras
     */
    public static void populateParameters(Intent intent, int venueID)
    {
        populateParameters(intent, venueID, ASEnvironment.ASEnvironmentProduction);
    }

    public static void populateParameters(Intent intent, int venueID, ASEnvironment environment)
    {
        intent.putExtra(AS_EXTRA_VENUE_ID, venueID);
        intent.putExtra(AS_EXTRA_ENVIRONMENT, environment);
    }



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.com_airservice_whitelabel_activity_layout);

        if(getIntent().hasExtra(AS_EXTRA_VENUE_ID))
        {
            venueID = getIntent().getIntExtra(AS_EXTRA_VENUE_ID, -1);
        }

        if (venueID > 0)
        {
            if(getIntent().hasExtra(AS_EXTRA_ENVIRONMENT))
            {
                environment = (ASEnvironment)getIntent().getSerializableExtra(AS_EXTRA_ENVIRONMENT);
            }

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

            webView.loadUrl(environmentURL());
        }
        else
        {
            Log.e(TAG, "ASWhitelabelActivity - Missing venueID parameter");
            Intent extra = new Intent();
            setResult(RESULT_CANCELED, extra);
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            webView.goBack();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private String environmentURL()
    {
        switch (environment)
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

}
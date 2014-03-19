package com.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.airservice.whitelabel.ASOptions;
import com.airservice.whitelabel.ASWhitelabelActivity;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void startOrdering(View v)
    {
        String appID = "ABC123";
        String appToken = "DEF456";

        ASOptions options = new ASOptions(appID, appToken);
        options.setVenueAlias("airservice-live"); //our demo venue
        options.setLoggingEnabled(true);

        Intent intent = new Intent(this, ASWhitelabelActivity.class);
        ASWhitelabelActivity.setOptions(intent, options);
        startActivity(intent);
    }
}

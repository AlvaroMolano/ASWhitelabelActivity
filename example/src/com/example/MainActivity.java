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
        //Intent intent = new Intent(this, ASWhitelabelActivity.class);

        /* Venue specific example
        String appID = "ABC123";
        String appToken = "DEF456";
        String venueSlug = "venue-name";

        ASOptions options = new ASOptions(appID, appToken, venueSlug);
        */

        /* Collection based example
        String appID = "ABC123";
        String appToken = "DEF456";
        String collection = "XZ99";
        String brandColor = "E06E6E" //hex color

        ASOptions options = new ASOptions(appID, appToken, collection, brandColor);
        */

        String appID = "ABC123";
        String appToken = "DEF456";
        String venueSlug = "hungry-hut";

        ASOptions options = new ASOptions(appID, appToken, venueSlug);
        options.setEnvironment(ASOptions.ASEnvironment.ASEnvironmentQA);

        ASWhitelabelActivity activity = new ASWhitelabelActivity();
        activity.setOptions(options);

        Intent intent = new Intent(this, activity.getClass());

        startActivity(intent);
    }
}

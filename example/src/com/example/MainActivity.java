package com.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
        Intent intent = new Intent(this, ASWhitelabelActivity.class);
        ASWhitelabelActivity.populateParameters(intent, 1);
        startActivity(intent);
    }
}

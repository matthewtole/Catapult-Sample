package com.matthewtole.android.catapult_sample;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {

    private static final String TASKER_BLURB = "com.twofortyfouram.locale.intent.extra.BLURB";
    private static final String TASKER_BUNDLE = "com.twofortyfouram.locale.intent.extra.BUNDLE";
    private static final String TASKER_EDIT = "com.twofortyfouram.locale.intent.action.EDIT_SETTING";
    private static final String TASKER_FIRE = "com.twofortyfouram.locale.intent.action.FIRE_SETTING";

    private static final String CATAPULT_PACKAGE = "com.matthewtole.android.catapult";
    private static final String CATAPULT_EDIT = "com.matthewtole.android.catapult.tasker.ActivityConfigMenu";
    private static final String CATAPULT_FIRE = "com.matthewtole.android.catapult.tasker.FireReceiver";
    private static final String CATAPULT_TYPE_APP = "pebble-app";

    private static final int REQUEST_CATAPULT = 1;

    private Button mBtnLaunch;
    private Bundle mBundle;
    private Button mBtnPick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnPick = (Button) findViewById(R.id.btn_pick);
        mBtnPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TASKER_EDIT);
                intent.setComponent(new ComponentName(CATAPULT_PACKAGE, CATAPULT_EDIT));
                Bundle bundle = new Bundle();
                bundle.putString("type", CATAPULT_TYPE_APP);
                intent.putExtra(TASKER_BUNDLE, bundle);
                startActivityForResult(intent, REQUEST_CATAPULT);
            }
        });

        mBtnLaunch = (Button) findViewById(R.id.btn_launch);
        mBtnLaunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TASKER_FIRE);
                intent.setComponent(new ComponentName(CATAPULT_PACKAGE, CATAPULT_FIRE));
                intent.putExtra(TASKER_BUNDLE, mBundle);
                sendBroadcast(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CATAPULT) {
            String blurb = data.getStringExtra(TASKER_BLURB);
            mBundle = data.getBundleExtra(TASKER_BUNDLE);
            mBtnLaunch.setText(blurb);
            mBtnLaunch.setEnabled(true);
        }
    }
}

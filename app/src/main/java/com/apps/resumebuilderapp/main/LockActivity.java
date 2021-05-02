package com.apps.resumebuilderapp.main;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.apps.resumebuilderapp.R;
import com.apps.resumebuilderapp.utils.Global;
import com.apps.resumebuilderapp.utils.PinEntryEditText;
import com.apps.resumebuilderapp.utils.SessionManager;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;

/**
 * Created by Bhoomika Patel on 06-Nov-16.
 */

public class LockActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    private LinearLayout lnr_main;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_lock);

        setContents();

        facebookBannerAdView();
    }

    private void setContents() {

        sessionManager = new SessionManager(LockActivity.this);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
//        getSupportActionBar().setTitle(getString(R.string.app_name));

        LinearLayout lnr_main = (LinearLayout) findViewById(R.id.lnr_main);
        final PinEntryEditText pin_text = (PinEntryEditText) findViewById(R.id.pin_text);
        if (pin_text != null) {
            pin_text.setAnimateText(true);
            pin_text.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(CharSequence str) {

                    if (str.toString().equals(sessionManager.getPassword())) {
                        startActivity(new Intent(LockActivity.this, MainActivity.class));
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        finish();
                    } else {
                        pin_text.setError(true);
                        Toast.makeText(LockActivity.this, getString(R.string.incorrect_pin), Toast.LENGTH_SHORT).show();
                        pin_text.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                pin_text.setText(null);
                            }
                        }, 1000);
                    }
                }
            });
        }

        Global.setCustomTheme(LockActivity.this, lnr_main);

    }

    private AdView adView;
    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    private void facebookBannerAdView() {
        try {
            adView = new AdView(this, (getString(R.string.banner_facebook)), AdSize.BANNER_HEIGHT_50);
            LinearLayout adContainer = findViewById(R.id.banner_container);
            adContainer.setVisibility(View.VISIBLE);
            adContainer.addView(adView);
            adView.loadAd();
        } catch (Exception e) {
            Log.d("My AD ERROR", "" + e);
        }
    }
}

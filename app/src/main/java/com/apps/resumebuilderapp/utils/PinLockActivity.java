package com.apps.resumebuilderapp.utils;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.apps.resumebuilderapp.R;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;

/**
 * Created by Bhoomika Patel on 06-Nov-16.
 */

public class PinLockActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    private String password = "";

    public static boolean isFromChangePassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_lock);

        setContents();

        facebookBannerAdView();
    }

    private void setContents() {
        sessionManager = new SessionManager(PinLockActivity.this);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(R.string.security));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final PinEntryEditText pin_text = (PinEntryEditText) findViewById(R.id.pin_text);
        if (pin_text != null) {
            pin_text.setAnimateText(true);
            pin_text.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(CharSequence str) {
                    password = str.toString();

                    if (password.length() == 4) {
                        sessionManager.savePassword(password);
                        sessionManager.savePinLock(true);
                        finish();
                    } else {
                        pin_text.setError(true);
                        Global.showToastMessage(PinLockActivity.this, getString(R.string.enter_a_valid_pin));
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

        Global.setCustomTheme(PinLockActivity.this, findViewById(R.id.lnr_main));

//        TextView tv_Save = (TextView) findViewById(R.id.tv_Save);
//        tv_Save.setVisibility(View.VISIBLE);
//
//        tv_Save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (password.length() == 4) {
//                    sessionManager.savePassword(password);
//                    finish();
//                } else {
//                    Global.showSnackBarMessage(PinLockActivity.this, getString(R.string.enter_a_valid_pin));
//                }
//            }
//        });
    }

    /* action menu items click handle */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                if (isFromChangePassword) {
                    isFromChangePassword = false;
                }
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        View view = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(event);

        if (view instanceof EditText) {
            View w = getCurrentFocus();
            int scrcoords[] = new int[2];
            w.getLocationOnScreen(scrcoords);
            float x = event.getRawX() + w.getLeft() - scrcoords[0];
            float y = event.getRawY() + w.getTop() - scrcoords[1];

            if (event.getAction() == MotionEvent.ACTION_UP
                    && (x < w.getLeft() || x >= w.getRight()
                    || y < w.getTop() || y > w.getBottom())) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
            }
        }
        return ret;
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
            Log.d("Hata", "" + e);
        }
    }
}

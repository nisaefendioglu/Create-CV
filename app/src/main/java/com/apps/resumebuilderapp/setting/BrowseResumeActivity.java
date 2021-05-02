package com.apps.resumebuilderapp.setting;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.apps.resumebuilderapp.R;
import com.apps.resumebuilderapp.utils.Global;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;

public class BrowseResumeActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_resume);

        facebookBannerAdView();

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.browserResume);
        if (fragment == null) {
            fragment = new BrowseResumeFragment();
            fm.beginTransaction().add(R.id.browserResume, fragment).commit();
        }
        Global.setCustomTheme(BrowseResumeActivity.this, findViewById(R.id.browserResume));

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
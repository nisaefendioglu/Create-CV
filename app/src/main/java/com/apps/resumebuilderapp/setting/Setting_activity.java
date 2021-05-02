package com.apps.resumebuilderapp.setting;

import com.apps.resumebuilderapp.R;
import com.apps.resumebuilderapp.utils.Global;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

public class Setting_activity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		facebookBannerAdView();

		Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.SettingContainer);
		if (fragment == null) {
			fragment = new SettingFragment();
			fm.beginTransaction().add(R.id.SettingContainer, fragment).commit();
		}
		Global.setCustomTheme(Setting_activity.this, findViewById(R.id.SettingContainer));

	}

	/* action menu items click handle */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
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

	private AdView adView;
	@Override
	protected void onDestroy() {
		if (adView != null) {
			adView.destroy();
		}
		super.onDestroy();
	}
}

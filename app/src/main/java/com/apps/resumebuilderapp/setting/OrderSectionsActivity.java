package com.apps.resumebuilderapp.setting;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.apps.resumebuilderapp.R;
import com.apps.resumebuilderapp.utils.Global;
import com.apps.resumebuilderapp.utils.SessionManager;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.lunger.draglistview.DragListAdapter;
import com.lunger.draglistview.DragListView;

import java.util.ArrayList;

/**
 * Created by Lunger on 8/17 2016 11:21
 */
public class OrderSectionsActivity extends AppCompatActivity {

    private DragListView mDragListView;
    private ArrayList<String> mDatas;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_section);
        findView();

        facebookBannerAdView();
    }

    private void findView() {
        sessionManager = new SessionManager(OrderSectionsActivity.this);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDragListView = (DragListView) findViewById(R.id.lv);
        RelativeLayout rl_main = (RelativeLayout) findViewById(R.id.rl_main);
        initData();
        initDragListView();

        Global.setCustomTheme(OrderSectionsActivity.this, findViewById(R.id.rl_main));

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


    private void initData() {
        mDatas = sessionManager.get_orderSection();
    }

    private void initDragListView() {
        if (mDatas != null && mDatas.size() > 0) {
            final MyAdapter myAdapter = new MyAdapter(this, mDatas);

            mDragListView.setDragListAdapter(myAdapter);
            mDragListView.setDragger(R.id.lnr_main);
//            mDragListView.setDragger(R.id.iv_move);
            mDragListView.setItemFloatColor("#A35151");
            mDragListView.setItemFloatAlpha(0.65f);
            mDragListView.setMyDragListener(new DragListView.MyDragListener() {
                @Override
                public void onDragFinish(int srcPositon, int finalPosition) {
//                    Toast.makeText(OrderSectionsActivity.this, "beginPosition : " + srcPositon + "...endPosition : " + finalPosition, Toast.LENGTH_LONG).show();
                    sessionManager.save_orderSection(myAdapter.getNewListData());
//                    Global.printLog("new List", "===" + myAdapter.getNewListData());
                }
            });
        }
    }

    class MyAdapter extends DragListAdapter {

        public MyAdapter(Context context, ArrayList<String> arrayTitles) {
            super(context, arrayTitles);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view;

            view = LayoutInflater.from(OrderSectionsActivity.this).inflate(
                    R.layout.drag_order_item, null);

            TextView textView = (TextView) view
                    .findViewById(R.id.tv_name);
            textView.setText(mDatas.get(position));
            return view;
        }

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return mDatas.get(position);
        }

        public ArrayList<String> getNewListData() {
            ArrayList<String> strings = new ArrayList<>();

            for (int i = 0; i < mDatas.size(); i++) {
                strings.add(mDatas.get(i));
            }
            return strings;
        }

        @Override
        public long getItemId(int position) {
            return position;
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

    private void facebookBannerAdView() {
        try {
            adView = new com.facebook.ads.AdView(this, (getString(R.string.banner_facebook)), AdSize.BANNER_HEIGHT_50);
            LinearLayout adContainer = findViewById(R.id.banner_container);
            adContainer.setVisibility(View.VISIBLE);
            adContainer.addView(adView);
            adView.loadAd();
        } catch (Exception e) {
            Log.d("Hata", "" + e);
        }
    }
}

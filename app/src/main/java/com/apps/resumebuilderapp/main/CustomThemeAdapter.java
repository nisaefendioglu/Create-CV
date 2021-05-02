package com.apps.resumebuilderapp.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.apps.resumebuilderapp.R;
import com.apps.resumebuilderapp.model.CustomTheme;
import com.apps.resumebuilderapp.setting.SettingFragment;
import com.apps.resumebuilderapp.utils.Global;

import java.util.ArrayList;

import static com.apps.resumebuilderapp.setting.SettingFragment.selectedTheme;


public class CustomThemeAdapter extends BaseAdapter {

    private ArrayList<CustomTheme> dateArrayList;
    private Context context;
    private LayoutInflater mLayoutInflater;
    private CustomTheme custom_date;
    private String name;

    @Override
    public int getCount() {
        return dateArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return dateArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public CustomThemeAdapter(Context context, ArrayList<CustomTheme> dateArrayList, String name) {
        this.context = context;
        this.dateArrayList = dateArrayList;
        this.name = name;

        Global.printLog("dateArrayList==", "====" + dateArrayList.size());
        mLayoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private static class ViewHolder {
        //        RadioGroup radio_group;
        RadioButton rb_theme;
        LinearLayout lnr_main;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row_custom_theme, null);
            holder = new ViewHolder();
            holder.lnr_main = (LinearLayout) convertView.findViewById(R.id.lnr_main);
//            holder.radio_group = (RadioGroup) convertView.findViewById(R.id.radio_group);
            holder.rb_theme = (RadioButton) convertView.findViewById(R.id.rb_theme);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        custom_date = dateArrayList.get(position);

        holder.rb_theme.setText(custom_date.getTheme());

//        holder.rb_theme.setEnabled(false);
        holder.rb_theme.setClickable(false);

        if (custom_date.isSelected()) {
            holder.rb_theme.setChecked(true);
        } else {
            holder.rb_theme.setChecked(false);
        }

        holder.lnr_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.printLog("=======position", "==============" + position);

                if (name.equalsIgnoreCase("font")) {
                    for (int i = 0; i < SettingFragment.custom_fontArrayList.size(); i++) {
                        if (i == position) {
                            Global.printLog("=======i", "==============" + i);
                            dateArrayList.get(i).setSelected(true);
//                        holder.rb_theme.setChecked(true);
                            SettingFragment.custom_fontArrayList.get(i).setSelected(true);
                        } else {
//                        holder.rb_theme.setChecked(false);
                            dateArrayList.get(i).setSelected(false);
                            SettingFragment.custom_fontArrayList.get(i).setSelected(false);
                        }
                    }
                } else if (name.equalsIgnoreCase("color")) {
                    for (int i = 0; i < SettingFragment.custom_colorArrayList.size(); i++) {
                        if (i == position) {
                            Global.printLog("=======i", "==============" + i);
                            dateArrayList.get(i).setSelected(true);
//                        holder.rb_theme.setChecked(true);
                            SettingFragment.custom_colorArrayList.get(i).setSelected(true);
                        } else {
//                        holder.rb_theme.setChecked(false);
                            dateArrayList.get(i).setSelected(false);
                            SettingFragment.custom_colorArrayList.get(i).setSelected(false);
                        }
                    }
                } else {
                    for (int i = 0; i < SettingFragment.custom_themeArrayList.size(); i++) {
                        if (i == position) {
                            Global.printLog("=======i", "==============" + i);
                            dateArrayList.get(i).setSelected(true);
//                        holder.rb_theme.setChecked(true);
                            SettingFragment.custom_themeArrayList.get(i).setSelected(true);
                        } else {
//                        holder.rb_theme.setChecked(false);
                            dateArrayList.get(i).setSelected(false);
                            SettingFragment.custom_themeArrayList.get(i).setSelected(false);
                        }
                    }
                }

                selectedTheme = dateArrayList.get(position).getTheme();
//                SettingActivity.getInstance().setCustomDate(custom_date.getDate());

                Global.printLog("selectedTheme===", "===" + selectedTheme + "=====");
                notifyDataSetChanged();
            }
        });
       /* holder.rb_theme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });*/

        return convertView;

    }

}

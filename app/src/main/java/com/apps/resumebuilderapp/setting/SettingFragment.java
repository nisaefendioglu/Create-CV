package com.apps.resumebuilderapp.setting;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.ListFragment;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.resumebuilderapp.R;
import com.apps.resumebuilderapp.achievements.model.AchievementsData;
import com.apps.resumebuilderapp.achievements.model.AchievementsDataList;
import com.apps.resumebuilderapp.education.model.EducationData;
import com.apps.resumebuilderapp.education.model.EducationDataList;
import com.apps.resumebuilderapp.exeperience.model.ExperienceData;
import com.apps.resumebuilderapp.exeperience.model.ExperienceDataList;
import com.apps.resumebuilderapp.main.CustomThemeAdapter;
import com.apps.resumebuilderapp.model.CustomTheme;
import com.apps.resumebuilderapp.personalInfo.PersonalInfo;
import com.apps.resumebuilderapp.personalInfo.Profile_Info;
import com.apps.resumebuilderapp.personalInfo.dao;
import com.apps.resumebuilderapp.professionalSummary.model.ProfessionalSummary;
import com.apps.resumebuilderapp.professionalSummary.model.ProfessionalSummaryList;
import com.apps.resumebuilderapp.references.model.ReferencesData;
import com.apps.resumebuilderapp.references.model.ReferencesDataList;
import com.apps.resumebuilderapp.skills.model.SkillsData;
import com.apps.resumebuilderapp.skills.model.SkillsDataList;
import com.apps.resumebuilderapp.utils.Global;
import com.apps.resumebuilderapp.utils.PinLockActivity;
import com.apps.resumebuilderapp.utils.SessionManager;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.PdfImageObject;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;

import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.apps.resumebuilderapp.personalInfo.Profile_Info.DOB_FILE;

public class SettingFragment extends ListFragment {

    public final String SETTING_DATE_FORMATE = "Tarih Formatı";
    public final String SETTING_VIEW_RESUME = "Özgeçmişlere Göz At";
    public final String SETTING_RESET_APP = "Tüm Bilgileri Sıfırla";
    public final String SETTING_REORDER_SECTION = "Bölümleri Yeniden Sırala";
    public final String SETTING_FONT_COLORS = "Yazı Tipi/Renk";
    public final String SETTING_SET_PIN = "PinLock'u Ayarla";
    public final String SETTING_CHANGE_PIN = "PIN Sıfırla";
    public final String SETTING_REMOVE_PIN = "PinLock'u Kaldır";
    public static String SETTING_THEME_COLOR = "Tema Rengi : Beyaz";

    public static final int SET_DATE_FORMAT = 1;
    private ArrayList<String> mSettingList;
    private final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;

    private SessionManager sessionManager;
    ArrayAdapter<String> adapter;
    ListView listView;

    public static String custom_theme0 = "Beyaz"; // Default theme white
    public static String custom_theme1 = "Mavi";
    public static String custom_theme2 = "Mor";
    public static String custom_theme3 = "Turuncu";
    public static String custom_theme4 = "Kahverengi";
    public static String custom_theme6 = "Gri";
    public static String custom_theme7 = "Yeşil";
    public static String custom_theme8 = "Teal";
    public static String custom_theme9 = "Kırmızı";
    public static String custom_theme10 = "Indigo";

    final private String header_objective = "Hedef";
    final private String header_experience = "Deneyim";
    final private String header_skills = "Beceriler";
    final private String header_achievement = "Başarılar";
    final private String header_reference = "Referanslar";
    final private String header_education = "Eğitim";
    final private String header_professional = "Profesyonel Özet";

    String[] theme_Name = new String[]{custom_theme0, custom_theme1, custom_theme2, custom_theme3, custom_theme4, custom_theme6
            , custom_theme7, custom_theme8, custom_theme9, custom_theme10};

    String[] font_Name = new String[]{"COURIER", "HELVETICA", "TIMES ROMAN", "CALIBRI"};

    String[] font_color_Name = new String[]{"LIGHT GRAY", "GRAY", "DARK GRAY", "BLACK", "RED", "PINK",
            "ORANGE", "YELLOW", "GREEN", "MAGENTA", "CYAN", "BLUE"};

    public static ArrayList<CustomTheme> custom_themeArrayList = new ArrayList<>();
    public static ArrayList<CustomTheme> custom_fontArrayList = new ArrayList<>();
    public static ArrayList<CustomTheme> custom_colorArrayList = new ArrayList<>();

    public static String selectedTheme = "";
    private CustomThemeAdapter customThemeAdapter;

    private Uri dataUri;
    private String filePath;
    private static final int PICKFILE_RESULT_CODE = 1;
    private PopupWindow popupWindow;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(getContext());

        mSettingList = new ArrayList<String>();
        mSettingList.add(SETTING_DATE_FORMATE);
        mSettingList.add(SETTING_VIEW_RESUME);
        mSettingList.add(SETTING_REORDER_SECTION);
        mSettingList.add(SETTING_FONT_COLORS);

      /*  if (sessionManager.getPinLock()) {
            mSettingList.add(SETTING_CHANGE_PIN);
            mSettingList.add(SETTING_REMOVE_PIN);
        } else {
            mSettingList.add(SETTING_SET_PIN);
        }
*/
        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, mSettingList);
        setListAdapter(adapter);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
        sessionManager = new SessionManager(getContext());
        listView = getListView();
    }

    @Override
    public void onResume() {
        super.onResume();


        selectedTheme = sessionManager.getAppTheme();
        SETTING_THEME_COLOR = "Tema Rengi: " + selectedTheme;

        Global.printLog("selectedTheme===", "==" + selectedTheme);
        setCustomTheme();

        if (!mSettingList.contains(SETTING_THEME_COLOR))
//            mSettingList.add(SETTING_THEME_COLOR);

        if (sessionManager.getPinLock()) {
            if (!mSettingList.contains(SETTING_CHANGE_PIN)) mSettingList.add(SETTING_CHANGE_PIN);
            if (!mSettingList.contains(SETTING_REMOVE_PIN)) mSettingList.add(SETTING_REMOVE_PIN);

            if (mSettingList.contains(SETTING_SET_PIN)) mSettingList.remove(SETTING_SET_PIN);
        } else {
            if (!mSettingList.contains(SETTING_SET_PIN)) mSettingList.add(SETTING_SET_PIN);
            if (mSettingList.contains(SETTING_CHANGE_PIN)) mSettingList.remove(SETTING_CHANGE_PIN);
            if (mSettingList.contains(SETTING_REMOVE_PIN)) mSettingList.remove(SETTING_REMOVE_PIN);
        }

        if (mSettingList.contains(SETTING_RESET_APP)) {
            mSettingList.remove(SETTING_RESET_APP);
        }
        mSettingList.add(SETTING_RESET_APP);

        adapter.notifyDataSetChanged();

//        setNewDataInAdapter();
    }


    public void setNewDataInAdapter() {
        adapter.clear();

        adapter.add(SETTING_DATE_FORMATE);
        adapter.add(SETTING_VIEW_RESUME);
        adapter.add(SETTING_REORDER_SECTION);
        adapter.add(SETTING_FONT_COLORS);

        SETTING_THEME_COLOR = "Tema Rengi: " + selectedTheme;

//        mSettingList.add(SETTING_THEME_COLOR);

        if (sessionManager.getPinLock()) {
            adapter.add(SETTING_CHANGE_PIN);
            adapter.add(SETTING_REMOVE_PIN);

//            adapter.remove(SETTING_SET_PIN);
        } else {
            adapter.add(SETTING_SET_PIN);
//            if (mSettingList.contains(SETTING_CHANGE_PIN)) mSettingList.remove(SETTING_CHANGE_PIN);
//            if (mSettingList.contains(SETTING_REMOVE_PIN)) mSettingList.remove(SETTING_REMOVE_PIN);
        }

        adapter.add(SETTING_RESET_APP);

        adapter.notifyDataSetChanged();
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);
        String selectedItem = (String) getListAdapter().getItem(position);

        if (selectedItem.toLowerCase().contains("Tema")) {
            // Theme Color Clicked..

//            Global.showToastMessage(getActivity(), "Theme clciked");

            custom_themeArrayList.clear();

            for (int i = 0; i < theme_Name.length; i++) {
                CustomTheme custom_theme = new CustomTheme();

                custom_theme.setTheme(theme_Name[i]);
                if (sessionManager.getAppTheme().equalsIgnoreCase(theme_Name[i])) {
                    custom_theme.setSelected(true);
                } else custom_theme.setSelected(false);

                custom_themeArrayList.add(custom_theme);
            }

            showThemeAlert();

        } else if (selectedItem.equals(SETTING_DATE_FORMATE)) {
            // Creating and Building the Dialog

            final CharSequence[] items = {"mm/dd/yyyy", "dd/mm/yyyy", "yyyy/mm/dd"};
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Tarih Formatı Seçin");

//            Global.setCustomThemeInAlert(getContext(),getActivity().getWindow());
//            getActivity().getWindow().setBackgroundDrawableResource(Glo);

            builder.setPositiveButton("Tamam",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences sh;
                            sh = getActivity().getSharedPreferences(
                                    Profile_Info.DATE_FORMATE_FILE,
                                    Context.MODE_PRIVATE);
                            if (sh.getBoolean("dialog", true)) {
                                sh.edit().putInt(Profile_Info.DATE_FORMATE, 1)
                                        .commit();
                            }
                            sh.edit().putBoolean("dialog", true).commit();
                            int choe = sh.getInt(Profile_Info.DATE_FORMATE, 1);
                            ListView lw = ((AlertDialog) dialog).getListView();
                            String checkedItem = lw.getAdapter().getItem(lw.getCheckedItemPosition()).toString();
                            if (checkedItem.equals("mm/dd/yyyy")) {
                                sh.edit().putInt(Profile_Info.DATE_FORMATE, 1)
                                        .commit();
                                choe = 1;
//                                Toast.makeText(
//                                        getActivity().getApplicationContext(),
//                                        "mm/dd/yyyy is Selected",
//                                        Toast.LENGTH_SHORT).show();
                            } else if (checkedItem.equals("dd/mm/yyyy")) {

                                sh.edit().putInt(Profile_Info.DATE_FORMATE, 2)
                                        .commit();
                                choe = 2;
//                                Toast.makeText(
//                                        getActivity().getApplicationContext(),
//                                        "dd/mm/yyyy is Selected",
//                                        Toast.LENGTH_SHORT).show();
                            } else {
                                sh.edit().putInt(Profile_Info.DATE_FORMATE, 3)
                                        .commit();
                                choe = 3;

//                                Toast.makeText(
//                                        getActivity().getApplicationContext(),
//                                        "yyyy/mm/dd is Selected",
//                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                    });

            SharedPreferences sh;
            sh = getActivity().getSharedPreferences(
                    Profile_Info.DATE_FORMATE_FILE,
                    Context.MODE_PRIVATE);
            int cho = sh.getInt(Profile_Info.DATE_FORMATE, 1);

            Global.printLog("which cho===", "===" + cho);

            builder.setSingleChoiceItems(items, (cho - 1),
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub

                            Global.printLog("which===", "===" + which);

                            SharedPreferences sh;
                            sh = getActivity().getSharedPreferences(
                                    Profile_Info.DATE_FORMATE_FILE,
                                    Context.MODE_PRIVATE);
                            sh.edit().putBoolean("dialog", false).commit();

                            if ("mm/dd/yyyy".equals(items[which])) {
                                sh = getActivity().getSharedPreferences(
                                        Profile_Info.DATE_FORMATE_FILE,
                                        Context.MODE_PRIVATE);
                                sh.edit().putInt(Profile_Info.DATE_FORMATE, 1)
                                        .commit();

                            } else if ("dd/mm/yyyy".equals(items[which])) {
                                sh = getActivity().getSharedPreferences(
                                        Profile_Info.DATE_FORMATE_FILE,
                                        Context.MODE_PRIVATE);
                                sh.edit().putInt(Profile_Info.DATE_FORMATE, 2)
                                        .commit();

                            } else {
                                sh = getActivity().getSharedPreferences(
                                        Profile_Info.DATE_FORMATE_FILE,
                                        Context.MODE_PRIVATE);
                                sh.edit().putInt(Profile_Info.DATE_FORMATE, 3)
                                        .commit();
                            }

                        }
                    });
            builder.show();

        } else if (selectedItem.equals(SETTING_FONT_COLORS)) {

            showPopupForFontAndColors();

        } else if (selectedItem.equals(SETTING_REORDER_SECTION)) {
            startActivity(new Intent(getContext(), OrderSectionsActivity.class));
            getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        } else if (selectedItem.equals(SETTING_RESET_APP)) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    getActivity());
            alertDialogBuilder
                    .setMessage("Uygulamadaki Tüm Verileri Silmek İstiyor musunuz?");
            alertDialogBuilder.setPositiveButton("Evet",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            erase();
//                            Toast.makeText(getActivity(), "Data deleted",
//                                    Toast.LENGTH_SHORT).show();
                        }

                    });
            alertDialogBuilder.setNegativeButton("Hayır",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            Toast.makeText(getActivity(), "Your data is saved",
//                                    Toast.LENGTH_SHORT).show();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        } else if (selectedItem.equals(SETTING_VIEW_RESUME)) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(getActivity(), "Bu izni reddettiniz.", Toast.LENGTH_LONG).show();
                } else {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                }
            } else if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                viewResume();
            }
        } else if (selectedItem.equals(SETTING_SET_PIN)) {
            startActivity(new Intent(getContext(), PinLockActivity.class));
            getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        } else if (selectedItem.equals(SETTING_CHANGE_PIN)) {

            if (sessionManager.getPinLock()) {
                PinLockActivity.isFromChangePassword = true;
                startActivity(new Intent(getContext(), PinLockActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            } else {
                Global.showToastMessage(getActivity(), getResources().getString(R.string.pin_lock_must_selected));
            }
        } else if (selectedItem.equals(SETTING_REMOVE_PIN)) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    getActivity());
            alertDialogBuilder
                    .setMessage("PIN'i uygulamadan kaldırmak istiyor musunuz?");
            alertDialogBuilder.setPositiveButton("Evet",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            sessionManager.savePinLock(false);
                            Global.showToastMessage(getActivity(), "PIN Kaldırıldı!");

                            onResume();
                        }

                    });
            alertDialogBuilder.setNegativeButton("Hayır",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }
    }

    public static TextView tv_font, tv_nameColor, tv_titleColor, tv_contentColor;
    EditText edt_nameSize, edt_titleSize, edt_contentSize;
    int titleSize, nameSize, contentSize;
    String titleColor, nameColor, contentColor, selected_font;

    private void showPopupForFontAndColors() {
        //instantiate the popup.xml layout file
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View alertLayout = layoutInflater.inflate(R.layout.popup_font_color, null);

        LinearLayout lnr_main = (LinearLayout) alertLayout.findViewById(R.id.lnr_main);
        Global.setCustomTheme(getActivity(), lnr_main);

        tv_font = alertLayout.findViewById(R.id.tv_font);
        tv_nameColor = alertLayout.findViewById(R.id.tv_nameColor);
        tv_titleColor = alertLayout.findViewById(R.id.tv_titleColor);
        tv_contentColor = alertLayout.findViewById(R.id.tv_contentColor);

        edt_nameSize = alertLayout.findViewById(R.id.edt_nameSize);
        edt_titleSize = alertLayout.findViewById(R.id.edt_titleSize);
        edt_contentSize = alertLayout.findViewById(R.id.edt_contentSize);

        if (!sessionManager.getStringPref(SessionManager.TITLE_SIZE).isEmpty())
            titleSize = Integer.parseInt(sessionManager.getStringPref(SessionManager.TITLE_SIZE));
        else titleSize = 16;

        if (!sessionManager.getStringPref(SessionManager.NAME_SIZE).isEmpty())
            nameSize = Integer.parseInt(sessionManager.getStringPref(SessionManager.NAME_SIZE));
        else nameSize = 14;

        if (!sessionManager.getStringPref(SessionManager.CONTENT_SIZE).isEmpty())
            contentSize = Integer.parseInt(sessionManager.getStringPref(SessionManager.CONTENT_SIZE));
        else contentSize = 12;

        edt_nameSize.setText(nameSize + "");
        edt_titleSize.setText(titleSize + "");
        edt_contentSize.setText(contentSize + "");

        if (!sessionManager.getStringPref(SessionManager.NAME_COLOR).isEmpty())
            titleColor = sessionManager.getStringPref(SessionManager.NAME_COLOR);
        else titleColor = "SİYAH";

        if (!sessionManager.getStringPref(SessionManager.TITLE_COLOR).isEmpty())
            nameColor = sessionManager.getStringPref(SessionManager.TITLE_COLOR);
        else nameColor = "SİYAH";

        if (!sessionManager.getStringPref(SessionManager.CONTENT_COLOR).isEmpty())
            contentColor = sessionManager.getStringPref(SessionManager.CONTENT_COLOR);
        else contentColor = "SİYAH";

        tv_nameColor.setText(nameColor + "");
        tv_titleColor.setText(titleColor + "");
        tv_contentColor.setText(contentColor + "");

        edt_nameSize.setSelection(edt_nameSize.getText().toString().trim().length());

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
//        alert.setTitle("Info");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);

        if (!sessionManager.getStringPref(SessionManager.SELECTED_FONT).isEmpty())
            selected_font = sessionManager.getStringPref(SessionManager.SELECTED_FONT);
        else selected_font = font_Name[0];

        tv_font.setText(selected_font);

        tv_font.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                custom_fontArrayList.clear();

                for (int i = 0; i < font_Name.length; i++) {
                    CustomTheme custom_theme = new CustomTheme();

                    custom_theme.setTheme(font_Name[i]);
                    if (selected_font.equalsIgnoreCase(font_Name[i])) {
                        custom_theme.setSelected(true);
                    } else custom_theme.setSelected(false);

                    custom_fontArrayList.add(custom_theme);
                }

                Global.printLog("custom_fontArrayList---", custom_fontArrayList + "");
                showFontAlert();
            }
        });

        alert.setNegativeButton("İptal", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        alert.setPositiveButton("Kaydet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sessionManager.setStringPref(SessionManager.NAME_SIZE, edt_nameSize.getText().toString().trim());
                sessionManager.setStringPref(SessionManager.TITLE_SIZE, edt_titleSize.getText().toString().trim());
                sessionManager.setStringPref(SessionManager.CONTENT_SIZE, edt_contentSize.getText().toString().trim());

                dialog.dismiss();
            }
        });

        tv_nameColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                custom_colorArrayList.clear();

                if (sessionManager.getStringPref(SessionManager.NAME_COLOR).isEmpty())
                    sessionManager.setStringPref(SessionManager.NAME_COLOR, "Siyah");

                for (int i = 0; i < font_color_Name.length; i++) {
                    CustomTheme custom_theme = new CustomTheme();
                    custom_theme.setTheme(font_color_Name[i]);


                    if (sessionManager.getStringPref(SessionManager.NAME_COLOR).equalsIgnoreCase(font_color_Name[i])) {
                        custom_theme.setSelected(true);
                    } else custom_theme.setSelected(false);

                    custom_colorArrayList.add(custom_theme);
                }
                showColorAlert("isim");
            }
        });

        tv_titleColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                custom_colorArrayList.clear();

                if (sessionManager.getStringPref(SessionManager.TITLE_COLOR).isEmpty())
                    sessionManager.setStringPref(SessionManager.TITLE_COLOR, "Siyah");

                for (int i = 0; i < font_color_Name.length; i++) {
                    CustomTheme custom_theme = new CustomTheme();
                    custom_theme.setTheme(font_color_Name[i]);

                    if (sessionManager.getStringPref(SessionManager.TITLE_COLOR).equalsIgnoreCase(font_color_Name[i])) {
                        custom_theme.setSelected(true);
                    } else custom_theme.setSelected(false);

                    custom_colorArrayList.add(custom_theme);
                }

                showColorAlert("Başlık");
            }
        });

        tv_contentColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                custom_colorArrayList.clear();

                if (sessionManager.getStringPref(SessionManager.CONTENT_COLOR).isEmpty())
                    sessionManager.setStringPref(SessionManager.CONTENT_COLOR, "Siyah");

                for (int i = 0; i < font_color_Name.length; i++) {
                    CustomTheme custom_theme = new CustomTheme();
                    custom_theme.setTheme(font_color_Name[i]);

                    if (sessionManager.getStringPref(SessionManager.CONTENT_COLOR).equalsIgnoreCase(font_color_Name[i])) {
                        custom_theme.setSelected(true);
                    } else custom_theme.setSelected(false);

                    custom_colorArrayList.add(custom_theme);
                }

                showColorAlert("İçerik");
            }
        });

        AlertDialog dialog = alert.create();
        dialog.show();
    }

    private void showColorAlert(final String forWhat) {
        final Dialog dateAlert = new Dialog(getActivity()); //, R.style.DialogStyle
        dateAlert.setContentView(R.layout.dialog_date_formats);
        dateAlert.setCanceledOnTouchOutside(true);
        dateAlert.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ListView lv_upgrades = (ListView) dateAlert.findViewById(R.id.lv_upgrades);

        customThemeAdapter = new CustomThemeAdapter(getActivity(), custom_colorArrayList, "Renk");
        lv_upgrades.setAdapter(customThemeAdapter);

        TextView tv_font_Size = (TextView) dateAlert.findViewById(R.id.tv_font_Size);
        Button btn_ok = (Button) dateAlert.findViewById(R.id.btn_ok);
        Button btn_cancel = (Button) dateAlert.findViewById(R.id.btn_cancel);

        tv_font_Size.setText("Select Font");
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (forWhat.equalsIgnoreCase("isim")) {
                    sessionManager.setStringPref(SessionManager.NAME_COLOR, selectedTheme);
                    tv_nameColor.setText(selectedTheme);
                } else if (forWhat.equalsIgnoreCase("Başlık")) {
                    sessionManager.setStringPref(SessionManager.TITLE_COLOR, selectedTheme);
                    tv_titleColor.setText(selectedTheme);
                } else if (forWhat.equalsIgnoreCase("İçerik")) {
                    sessionManager.setStringPref(SessionManager.CONTENT_COLOR, selectedTheme);
                    tv_contentColor.setText(selectedTheme);
                }
                dateAlert.dismiss();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateAlert.dismiss();
            }
        });

        dateAlert.show();

        doKeepDialog(dateAlert);
    }

    private void showFontAlert() {
        final Dialog dateAlert = new Dialog(getActivity()); //, R.style.DialogStyle
        dateAlert.setContentView(R.layout.dialog_date_formats);
        dateAlert.setCanceledOnTouchOutside(true);
        dateAlert.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ListView lv_upgrades = (ListView) dateAlert.findViewById(R.id.lv_upgrades);

        customThemeAdapter = new CustomThemeAdapter(getActivity(), custom_fontArrayList, "Yazı Tipi");
        lv_upgrades.setAdapter(customThemeAdapter);

        TextView tv_font_Size = (TextView) dateAlert.findViewById(R.id.tv_font_Size);
        Button btn_ok = (Button) dateAlert.findViewById(R.id.btn_ok);
        Button btn_cancel = (Button) dateAlert.findViewById(R.id.btn_cancel);

        tv_font_Size.setText("Select Font");
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.setStringPref(SessionManager.SELECTED_FONT, selectedTheme);
                tv_font.setText(selectedTheme);
                dateAlert.dismiss();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateAlert.dismiss();
            }
        });

        dateAlert.show();

        doKeepDialog(dateAlert);
    }

    String headerParagraph = "";
    String headerKey = "";
    ArrayList<ProfessionalSummary> arrProfessionalSummary = new ArrayList<>();
    ArrayList<String> arrSkills = new ArrayList<>();

    ArrayList<ExperienceData> arrExperience = new ArrayList<>();
    ArrayList<EducationData> arrEducation = new ArrayList<>();
    ArrayList<AchievementsData> arrAchievement = new ArrayList<>();
    ArrayList<ReferencesData> arrReference = new ArrayList<>();

    ExperienceData experienceData;
    EducationData educationData;
    AchievementsData achievementsData;
    ReferencesData referencesData;
    PersonalInfo personalInfo;
    ProfessionalSummary professionalSummary;
    SkillsData skillsData;

    int mCount = 0;
    SharedPreferences sh;

    ProfessionalSummaryList professionalList;
    ExperienceDataList experienceDataList;
    EducationDataList educationDataList;
    SkillsDataList skillsDataList;
    AchievementsDataList achievementsDataList;
    ReferencesDataList referencesDataList;

    private void saveDate(String[] startDateArray) {
        sh = getActivity().getSharedPreferences(
                Profile_Info.DATE_FORMATE_FILE,
                Context.MODE_PRIVATE);

        int choice = sh.getInt(Profile_Info.DATE_FORMATE, 1);
        Global.printLog("date_MONTH_$$$=choice=", choice + "");

        switch (choice) {
            case 1:
//                return month + "/" + day + "/" + year;

                for (int j = 0; j < startDateArray.length; j++) {
                    if (j == 0) {
                        sh.edit().putString(Profile_Info.DOB_MONTH, (Integer.parseInt(startDateArray[j].trim()) - 1) + "").commit();
                    } else if (j == 1) {
                        sh.edit().putString(Profile_Info.DOB_DAY, startDateArray[j].trim()).commit();
                    } else {
                        sh.edit().putString(Profile_Info.DOB_YEAR, startDateArray[j].trim()).commit();
                    }
                }
                break;
            case 2:
//                return day + "/" + month + "/" + year;

                for (int j = 0; j < startDateArray.length; j++) {
                    if (j == 0) {
                        sh.edit().putString(Profile_Info.DOB_DAY, startDateArray[j].trim()).commit();
                    } else if (j == 1) {
                        sh.edit().putString(Profile_Info.DOB_MONTH, (Integer.parseInt(startDateArray[j].trim()) - 1) + "").commit();
                    } else {
                        sh.edit().putString(Profile_Info.DOB_YEAR, startDateArray[j].trim()).commit();
                    }
                }
                break;

            case 3:
//                return year + "/" + month + "/" + day;

                for (int j = 0; j < startDateArray.length; j++) {
                    if (j == 0) {
                        sh.edit().putString(Profile_Info.DOB_YEAR, startDateArray[j].trim()).commit();
                    } else if (j == 1) {
                        sh.edit().putString(Profile_Info.DOB_MONTH, (Integer.parseInt(startDateArray[j].trim()) - 1) + "").commit();
                    } else {
                        sh.edit().putString(Profile_Info.DOB_DAY, startDateArray[j].trim()).commit();
                    }
                }
                break;
        }

        Global.printLog("date_MONTH_$$$==", sh.getString(Profile_Info.DOB_MONTH, "1") + "");

        Date date = new Date(Integer.parseInt(sh.getString(Profile_Info.DOB_YEAR, "1800")),
                Integer.parseInt(sh.getString(Profile_Info.DOB_MONTH, "1")),
                Integer.parseInt(sh.getString(Profile_Info.DOB_DAY, "1")));

        Global.printLog("date$$$==", date + "");

    }

    private void saveDOB(int choice, String[] DOBArray) {
        switch (choice) {
            case 1:
//              return month + "/" + day + "/" + year;
                sh.edit().putString(Profile_Info.DOB_MONTH, (Integer.parseInt(DOBArray[0].trim())) + "").commit();
                sh.edit().putString(Profile_Info.DOB_DAY, DOBArray[1].trim()).commit();
                sh.edit().putString(Profile_Info.DOB_YEAR, DOBArray[2].trim()).commit();
                break;
            case 2:
//                                                return day + "/" + month + "/" + year;
                sh.edit().putString(Profile_Info.DOB_DAY, DOBArray[0].trim()).commit();
                sh.edit().putString(Profile_Info.DOB_MONTH, (Integer.parseInt(DOBArray[1].trim())) + "").commit();
                sh.edit().putString(Profile_Info.DOB_YEAR, DOBArray[2].trim()).commit();
                break;

            case 3:
                sh.edit().putString(Profile_Info.DOB_YEAR, DOBArray[0].trim()).commit();
                sh.edit().putString(Profile_Info.DOB_MONTH, (Integer.parseInt(DOBArray[2].trim())) + "").commit();
                sh.edit().putString(Profile_Info.DOB_DAY, DOBArray[2].trim()).commit();
                break;
        }
    }

    private void showThemeAlert() {
        final Dialog dateAlert = new Dialog(getActivity()); //, R.style.DialogStyle
        dateAlert.setContentView(R.layout.dialog_date_formats);
        dateAlert.setCanceledOnTouchOutside(true);
        dateAlert.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ListView lv_upgrades = (ListView) dateAlert.findViewById(R.id.lv_upgrades);
        LinearLayout lnr_main = (LinearLayout) dateAlert.findViewById(R.id.lnr_main);
        Global.setCustomTheme(getActivity(), lnr_main);

        customThemeAdapter = new CustomThemeAdapter(getActivity(), custom_themeArrayList, "Tema");
        lv_upgrades.setAdapter(customThemeAdapter);

        TextView tv_font_Size = (TextView) dateAlert.findViewById(R.id.tv_font_Size);
        Button btn_ok = (Button) dateAlert.findViewById(R.id.btn_ok);
        Button btn_cancel = (Button) dateAlert.findViewById(R.id.btn_cancel);

        tv_font_Size.setText("Tema Seçiniz");
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

          /*      adapter.remove(SETTING_THEME_COLOR);

                SETTING_THEME_COLOR = "Theme Color: " + selectedTheme;

                adapter.add(SETTING_THEME_COLOR);
                adapter.notifyDataSetChanged();*/

                setNewDataInAdapter();

                sessionManager.saveAppTheme(selectedTheme);

                Global.printLog("selectedTheme==ok=", "==" + selectedTheme);

                setCustomTheme();
                dateAlert.dismiss();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateAlert.dismiss();
            }
        });

        dateAlert.show();

        doKeepDialog(dateAlert);
    }

    // Prevent dialog dismiss when orientation changes
    private static void doKeepDialog(Dialog dialog) {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    viewResume();
                } else {
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void viewResume() {
        File dir = new File(Environment.getExternalStorageDirectory()
                .getPath() + "/" + Global.APP_FOLDER);
        dir.mkdir();
        if (dir.isDirectory()) {
            Intent i = new Intent(getActivity(), BrowseResumeActivity.class);
            startActivity(i);
        } else {
            Toast.makeText(getActivity(), "SD Kart Bulunmuyor.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("WorldReadableFiles")
    private void erase() {
        // TODO Auto-generated method stub
        try {
			/*Bitmap bitmapImage = BitmapFactory.decodeResource(getActivity()
			//		.getResources(), R.drawable.profilepic);
			// fos = new FileOutputStream(mypath);
			@SuppressWarnings("deprecation")
			FileOutputStream fos = getActivity().openFileOutput("photo.jpg",
					Context.MODE_WORLD_READABLE);
			// Use the compress method on the BitMap object to write image to
			// the OutputStream
			bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			fos.close();*/

            File imageFile = new File(Environment.getExternalStorageDirectory()
                    .getPath() + "/" + Global.APP_FOLDER + "/extractedImage.jpg");

            if (imageFile.exists())
                imageFile.delete();

            File file = getActivity().getFileStreamPath("photo.jpg");
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            new dao(getActivity(), Profile_Info.FILE_NAME)
                    .saveData(new PersonalInfo());
            SharedPreferences sh = getActivity().getSharedPreferences(
                    DOB_FILE, Context.MODE_PRIVATE);
            sh.edit().putString(Profile_Info.DOB_DAY, "").commit();
            sh.edit().putString(Profile_Info.DOB_MONTH, "").commit();
            sh.edit().putString(Profile_Info.DOB_YEAR, "").commit();
            sh = getActivity().getSharedPreferences("Objective",
                    Context.MODE_PRIVATE);
            sh.edit().putString("message", "").commit();
            ExperienceDataList.getInstance(getActivity()).DeleteData();
            EducationDataList.getInstance(getActivity()).DeleteData();
            SkillsDataList.getInstance(getActivity()).DeleteData();
            AchievementsDataList.getInstance(getActivity()).DeleteData();
            ReferencesDataList.getInstance(getActivity()).DeleteData();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // Professional summary
        ProfessionalSummaryList.getInstance(getActivity()).DeleteData();
    }

    private void setCustomTheme() {
        listView.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        switch (sessionManager.getAppTheme()) {

          /*public static String custom_theme1 = "Blue";
            public static String custom_theme2 = "Purple";
            public static String custom_theme3 = "Orange";
            public static String custom_theme4 = "Brown";
            public static String custom_theme5 = "Sky"; // Default theme
            public static String custom_theme6 = "Gray";
            public static String custom_theme7 = "Green";
            public static String custom_theme8 = "Teal";
            public static String custom_theme9 = "Red";
            public static String custom_theme10 = "Indigo";*/

            case "Mavi": //bkg1
                listView.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg1));
                break;

            case "Mor": //bkg2
                listView.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg2));
                break;

            case "Turuncu": //bkg3
                listView.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg3));
                break;

            case "Kahverengi": //bkg4
                listView.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg4));
                break;

            case "Beyaz":
            case "Default":
                listView.setBackgroundDrawable(null);
                listView.setBackgroundColor(getResources().getColor(R.color.white));
                break;

            case "Gri": //bkg5
                listView.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg5));
                break;

            case "Yeşil": //bkg6
                listView.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg6));
                break;

            case "Teal": //bkg7
                listView.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg7));
                break;

            case "Kırmızı": //bkg8
                listView.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg8));
                break;

            case "Indigo": //bkg9
                listView.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg9));
                break;
        }
    }

    public String getRealPathFromUri(final Uri uri) {
        // DocumentProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(getContext(), uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(getContext(), contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(getContext(), contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(getContext(), uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    private String getDataColumn(Context context, Uri uri, String selection,
                                 String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    private boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public class MyImageRenderListener implements RenderListener {

        /**
         * The new document to which we've added a border rectangle.
         */
        protected String path = "";

        /**
         * Creates a RenderListener that will look for images.
         */
        public MyImageRenderListener(String path) {
            this.path = path;
        }

        /**
         * @see com.itextpdf.text.pdf.parser.RenderListener#beginTextBlock()
         */
        public void beginTextBlock() {
        }

        /**
         * @see com.itextpdf.text.pdf.parser.RenderListener#endTextBlock()
         */
        public void endTextBlock() {
        }

        /**
         * @see com.itextpdf.text.pdf.parser.RenderListener#renderImage(
         *com.itextpdf.text.pdf.parser.ImageRenderInfo)
         */
        public void renderImage(ImageRenderInfo renderInfo) {
            try {
                String filename;
                FileOutputStream os;
                PdfImageObject image = renderInfo.getImage();
                if (image == null) return;
                filename = String.format(path, renderInfo.getRef().getNumber(), image.getFileType());
                os = new FileOutputStream(filename);
                os.write(image.getImageAsBytes());
                os.flush();
                os.close();

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap bitmap = BitmapFactory.decodeFile(path, options);
                saveToInternalSorage(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        public void renderText(TextRenderInfo renderInfo) {
        }
    }

    @SuppressLint("WorldReadableFiles")
    private void saveToInternalSorage(Bitmap bitmapImage) {
        /*
         * ContextWrapper cw = new ContextWrapper(getApplicationContext()); //
         * path to /data/data/Resume Builder/app_data/imageDir File directory =
         * cw.getDir("imageDir", Context.MODE_PRIVATE); // Create imageDir
         *
         * File mypath = new File(directory, "profile.JPEG");
         *
         * FileOutputStream fos = null;
         */
        try {

            // fos = new FileOutputStream(mypath);
            @SuppressWarnings("deprecation")
//            FileOutputStream fos = this.openFileOutput("photo.jpg",
//                    Context.MODE_WORLD_READABLE);

                    FileOutputStream fos;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                // Do something for Naught and above versions
                fos = getContext().openFileOutput("photo.jpg", Context.MODE_PRIVATE);
            } else {
                // do something for phones running an SDK before Naught
                fos = getContext().openFileOutput("photo.jpg", Context.MODE_WORLD_READABLE);
            }


            // Use the compress method on the BitMap object to write image to
            // the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

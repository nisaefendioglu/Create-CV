package com.apps.resumebuilderapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import androidx.core.content.FileProvider;

import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.apps.resumebuilderapp.BuildConfig;
import com.apps.resumebuilderapp.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created on 12-Sep-17 by Bhoomika Patel .
 */

public class Global {
    public static String displayformatDate = "dd MMMM yyyy"; // default selected
    public static String APP_FOLDER = "Oluşturmaya Devam Et"; // default selected

    public static void printLog(String key, String value) {
//        if (BuildConfig.DEBUG)
//            Log.e(key + "=====", "====" + value+"##");
    }

    public static void showToastMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static byte[] convertBitmapToByte(Bitmap imageBitmap) {
        if (imageBitmap == null) {
            return null;
        } else {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);

            return baos.toByteArray();
        }
    }

    public static String displayformatDate(Context context, String date) {
        String mDate = date;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Date myDate = null;
        try {
            myDate = dateFormat.parse(date);
            //get settings from sharedprefs
            SharedPreferences SP = context.getSharedPreferences("Ayarlar", Activity.MODE_PRIVATE);
            String dateformate = SP.getString("Tarih Formatı", "MM/dd/yyyy").replaceAll("D", "d").replaceAll("Y", "y");

            SimpleDateFormat displayFormat = new SimpleDateFormat(dateformate);
            mDate = displayFormat.format(myDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        return mDate;
    }

    public static String db_changeFormatDate(Context context, String date) {
        String mDate = date;

        printLog("db_change_sdf====", "==date==" + date);
        SharedPreferences SP = context.getSharedPreferences("Ayarlar", Activity.MODE_PRIVATE);
        String dateformate = SP.getString("Tarih Formatı", "MM/dd/yyyy").replaceAll("D", "d").replaceAll("Y", "y");

        printLog("db_change_sdf====", "==dateformate==" + dateformate);
        if (dateformate.equalsIgnoreCase("dd/MM/yyyy")) return date;

        SimpleDateFormat displayFormat = new SimpleDateFormat(dateformate, Locale.US);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Date myDate = null;
        try {
            myDate = displayFormat.parse(date);
            //get settings from sharedprefs

            mDate = dateFormat.format(myDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        return mDate;
    }

    public static Bitmap getResizedBitmap(Bitmap bm) {
        if (bm != null) {
            int width = bm.getWidth();
            int height = bm.getHeight();

            printLog("Original", "width==" + width + "===height===" + height);
            float scaleWidth = ((float) 100) / width;
            float scaleHeight = ((float) 100) / height;
            // CREATE A MATRIX FOR THE MANIPULATION
            Matrix matrix = new Matrix();
            // RESIZE THE BIT MAP
            matrix.postScale(scaleWidth, scaleHeight);

            printLog("scaleWidth", "width==" + width + "===height===" + height);

            // "RECREATE" THE NEW BITMAP
            Bitmap resizedBitmap = Bitmap.createBitmap(
                    bm, 0, 0, width, height, matrix, false);
            bm.recycle();
            return resizedBitmap;
        } else return bm;
    }

    private static Uri getUriFromFile(Context context, File file) {
        Uri mUri = null;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mUri = Uri.fromFile(file);

            Global.printLog("if < N", "===" + mUri);
            return mUri;
//            return Uri.fromFile(file);
        } else {
//            return FileProvider.getUriForFile(context,
//                    context.getPackageName() + ".provider",
//                    file);

            mUri = FileProvider.getUriForFile(context,
                    BuildConfig.APPLICATION_ID + ".provider",
                    file);
            Global.printLog("else > N", "===" + mUri);

            return mUri;
//            FileProvider.getUriForFile(OrderSuccessSummaryActivity.this, BuildConfig.APPLICATION_ID + ".provider", pdfFile);
        }
    }

    public static void openPDF(Context context, File file) {
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setDataAndType(getUriFromFile(location),
//                "application/vnd.android.package-archive");
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        context.startActivity(intent);

        Uri apkURI = FileProvider.getUriForFile(
                context,
                context.getPackageName() + ".provider", file);

        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setDataAndType(getUriFromFile(context, file), "application/vnd.android.package-archive");
        intent.setDataAndType(apkURI, "application/pdf");

        printLog("apkURI", "apkURI===" + apkURI);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//        startActivity(intent);
//        Uri apkURI = FileProvider.getUriForFile(
//                context,
//                context.getPackageName() + ".provider", file);
//        intent.setDataAndType(getUriFromFile(), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(intent);

        /*   try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            } catch (Exception e) {
                try {
                    Uri uri = FileProvider.getUriForFile(Reports.this, BuildConfig.APPLICATION_ID + ".provider", file);

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(uri, "application/pdf");
//            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }*/
    }

    public static void setCustomTheme(Context context, View scroll_main) {
        scroll_main.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));

        SessionManager sessionManager = new SessionManager(context);

        switch (sessionManager.getAppTheme()) {

            case "Mavi": //bkg1
                scroll_main.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bkg1));
                break;

            case "Mor": //bkg2
                scroll_main.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bkg2));
                break;

            case "Turuncu": //bkg3
                scroll_main.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bkg3));
                break;

            case "Kahverengi": //bkg4
                scroll_main.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bkg4));
                break;

            case "Beyaz":
            case "Default":
                scroll_main.setBackgroundDrawable(null);
                scroll_main.setBackgroundColor(context.getResources().getColor(R.color.white));
                break;

            case "Gri": //bkg5
                scroll_main.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bkg5));
                break;

            case "Yeşil": //bkg6
                scroll_main.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bkg6));
                break;

            case "Teal": //bkg7
                scroll_main.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bkg7));
                break;

            case "Kırmızı": //bkg8
                scroll_main.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bkg8));
                break;

            case "Indigo": //bkg9
                scroll_main.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bkg9));
                break;
        }
    }

    public static void setCustomThemeInAlert(Context context, Window scroll_main) {
        scroll_main.setBackgroundDrawableResource(android.R.color.transparent);

        SessionManager sessionManager = new SessionManager(context);

        switch (sessionManager.getAppTheme()) {

            case "Mavi": //bkg1
                scroll_main.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bkg1));
                break;

            case "Mor": //bkg2
                scroll_main.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bkg2));
                break;

            case "Turuncu": //bkg3
                scroll_main.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bkg3));
                break;

            case "Kahverengi": //bkg4
                scroll_main.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bkg4));
                break;

            case "Beyaz":
            case "Default":
                scroll_main.setBackgroundDrawable(null);
                scroll_main.setBackgroundDrawableResource(R.color.white);
                break;

            case "Gri": //bkg5
                scroll_main.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bkg5));
                break;

            case "Yeşil": //bkg6
                scroll_main.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bkg6));
                break;

            case "Teal": //bkg7
                scroll_main.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bkg7));
                break;

            case "Kırmızı": //bkg8
                scroll_main.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bkg8));
                break;

            case "Indigo": //bkg9
                scroll_main.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bkg9));
                break;
        }
    }

    final public static String NAME = "nm";
    final public static String STREET = "strt ";
    final public static String CITY = "cty ";
    final public static String STATE = "ste ";
    final public static String COUNTRY = "cntry ";
    final public static String PIN = "pin ";
    final public static String PROFESSIONAL_SUMMARY = "ps_";
    final public static String EXP_CMPNY_NAME = "ex_cn ";
    final public static String EXP_DESIGNATION = "ex_ds ";
    final public static String EXP_DATE = "ex_dt ";
    final public static String EXP_WHAT_DID_YOU = "ex_wd ";
    final public static String EDU_SCHOOL_NM = "ed_sn ";
    final public static String EDU_DEGREE_NM = "ed_dn ";
    final public static String EDU_DATE = "ed_dt ";
    final public static String SKILL = "skl_";
    final public static String ACHIEVEMENT_NAME = "ac_nm ";
    final public static String ACHIEVEMENT_DATE = "ac_dt ";
    final public static String ACHIEVEMENT_WHAT_DID_YOU = "ac_wd ";
    final public static String REF_NAME = "re_nm ";
    final public static String REF_WHAT_DID_YOU = "re_wd ";
    final public static String REF_CONTACT = "re_ct ";
    final public static String REF_DESIGNATION = "re_ds ";
    final public static String REF_CMPNY_NAME = "re_cn ";
    final public static String REF_EMAIL = "re_em ";

}

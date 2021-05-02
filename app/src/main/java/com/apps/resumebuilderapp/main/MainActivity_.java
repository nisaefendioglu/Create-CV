package com.apps.resumebuilderapp.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.apps.resumebuilderapp.BuildConfig;
import com.apps.resumebuilderapp.R;
import com.apps.resumebuilderapp.achievements.Achievements_Activity;
import com.apps.resumebuilderapp.achievements.dao.AchievementsDao;
import com.apps.resumebuilderapp.achievements.model.AchievementsData;
import com.apps.resumebuilderapp.achievements.model.AchievementsDataList;
import com.apps.resumebuilderapp.education.Education_Activity;
import com.apps.resumebuilderapp.education.dao.EducationDataDao;
import com.apps.resumebuilderapp.education.model.EducationData;
import com.apps.resumebuilderapp.education.model.EducationDataList;
import com.apps.resumebuilderapp.exeperience.Experience_Activity;
import com.apps.resumebuilderapp.exeperience.dao.ExperienceDataDao;
import com.apps.resumebuilderapp.exeperience.model.ExperienceData;
import com.apps.resumebuilderapp.exeperience.model.ExperienceDataList;
import com.apps.resumebuilderapp.objective.Objective;
import com.apps.resumebuilderapp.personalInfo.PersonalInfo;
import com.apps.resumebuilderapp.personalInfo.Profile_Info;
import com.apps.resumebuilderapp.personalInfo.dao;
import com.apps.resumebuilderapp.professionalSummary.Professional_summaryActivity;
import com.apps.resumebuilderapp.professionalSummary.dao.daoProfessionalSummary;
import com.apps.resumebuilderapp.professionalSummary.model.ProfessionalSummary;
import com.apps.resumebuilderapp.professionalSummary.model.ProfessionalSummaryList;
import com.apps.resumebuilderapp.references.ActivityReferences;
import com.apps.resumebuilderapp.references.dao.ReferencesDao;
import com.apps.resumebuilderapp.references.model.ReferencesData;
import com.apps.resumebuilderapp.references.model.ReferencesDataList;
import com.apps.resumebuilderapp.setting.Setting_activity;
import com.apps.resumebuilderapp.skills.Skills_activity;
import com.apps.resumebuilderapp.skills.dao.daoSkills;
import com.apps.resumebuilderapp.skills.model.SkillsData;
import com.apps.resumebuilderapp.skills.model.SkillsDataList;
import com.apps.resumebuilderapp.utils.Global;
import com.apps.resumebuilderapp.utils.SessionManager;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import permission.auron.com.marshmallowpermissionhelper.ActivityManagePermission;
import permission.auron.com.marshmallowpermissionhelper.PermissionResult;
import permission.auron.com.marshmallowpermissionhelper.PermissionUtils;

@SuppressLint("NewApi")
public class MainActivity_ extends ActivityManagePermission {

    private ImageView picture;
    private LinearLayout personalInfo;
    private LinearLayout objective;
    private LinearLayout personalSummary;
    private LinearLayout experience;
    private LinearLayout education;
    private LinearLayout skills;
    private LinearLayout achievements;
    private LinearLayout references;
    private LinearLayout profileImage;
    private LinearLayout coverletter;

    private LinearLayout lnr_main;
    private static int RESULT_LOAD_IMAGE = 1;
    private Bitmap photo;
    private Class activityClass;

    public static final int MENU_SAVE_DOCUMENT = 3;
    private final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE_FOR_SAVE = 1;
    private final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE_FOR_SHARE = 2;
    private SessionManager sessionManager;

    private ArrayList<String> mOrderSections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        facebookBannerAdView();

        sessionManager = new SessionManager(MainActivity_.this);

        lnr_main = (LinearLayout) findViewById(R.id.lnr_main);
        picture = (ImageView) findViewById(R.id.picture);
        personalInfo = (LinearLayout) findViewById(R.id.personalInfo);
        objective = (LinearLayout) findViewById(R.id.objective);
        personalSummary = (LinearLayout) findViewById(R.id.personalSummary);
        experience = (LinearLayout) findViewById(R.id.experience);
        education = (LinearLayout) findViewById(R.id.education);
        skills = (LinearLayout) findViewById(R.id.skills);
        achievements = (LinearLayout) findViewById(R.id.achievements);
        references = (LinearLayout) findViewById(R.id.references);
        profileImage = (LinearLayout) findViewById(R.id.profileImage);
        coverletter = (LinearLayout) findViewById(R.id.coverletter);

        photo = loadImageFromStorage();

        if (photo != null) {
            changePicture = true;
            picture.setImageBitmap(photo);
        }
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolbar_title = (TextView) mToolbar.findViewById(R.id.toolbar_title);

//        mToolbar.setTitle(notesMaster.getNotesTitle());
//        toolbar_title_.setVisibility(View.VISIBLE);
//        toolbar_title_.setText(notesMaster.getNotesTitle());
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        setCustomTheme();

        SharedPreferences sh = this.getSharedPreferences("myfile",
                Context.MODE_PRIVATE);
        if (!(sh.getInt("1", 0) == 1)) {
            try {
                new dao(this, Profile_Info.FILE_NAME)
                        .saveData(new PersonalInfo());
                new daoProfessionalSummary(this, ProfessionalSummaryList.FILE)
                        .saveData(new ArrayList<ProfessionalSummary>());
                new EducationDataDao(this, EducationDataList.FILE)
                        .saveData(new ArrayList<EducationData>());
                new ExperienceDataDao(this, ExperienceDataList.FILE)
                        .saveData(new ArrayList<ExperienceData>());
                new daoSkills(this, SkillsDataList.FILE)
                        .saveData(new ArrayList<SkillsData>());
                new AchievementsDao(this, AchievementsDataList.FILE)
                        .saveData(new ArrayList<AchievementsData>());
                new ReferencesDao(this, ReferencesDataList.FILE)
                        .saveData(new ArrayList<ReferencesData>());

                sh.edit().putInt("1", 1).commit();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        //
        picture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                selectImage();

				/*// TODO Auto-generated method stub
				Intent intent = new Intent();
				// call android default gallery
				intent.setType("image/*");
				photo = null;
				intent.setAction(Intent.ACTION_GET_CONTENT);
				try {
					Intent in = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(in, RESULT_LOAD_IMAGE);

				} catch (ActivityNotFoundException e) {
					// Do nothing for now
				}
*/
            }
        });

        personalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                activityClass = Profile_Info.class;
//                if (interstitial.isLoaded()) {
//                    interstitial.show();
//                } else {
                StartAnotherActivity();
//                }
            }
        });

        objective.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i = new Intent(MainActivity_.this, Objective.class);
                startActivity(i);
            }
        });

        personalSummary.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(MainActivity_.this, Professional_summaryActivity.class);
                startActivity(i);
            }
        });

        experience.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                activityClass = Experience_Activity.class;
//                if (interstitial.isLoaded()) {
//                    interstitial.show();
//                } else {
                StartAnotherActivity();
//                }
            }
        });

        education.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i = new Intent(MainActivity_.this, Education_Activity.class);
                startActivity(i);
            }
        });

        skills.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i = new Intent(MainActivity_.this, Skills_activity.class);
                startActivity(i);
            }
        });

        achievements.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                activityClass = Achievements_Activity.class;
//                if (interstitial.isLoaded()) {
//                    interstitial.show();
//                } else {
                StartAnotherActivity();
//                }
            }
        });

        references.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i = new Intent(MainActivity_.this,
                        ActivityReferences.class);
                startActivity(i);
            }
        });
    }

    static final int REQUEST_CAMERA = 1;
    static final int SELECT_FILE = 2;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    Uri fileUri;

    CharSequence[] items;

    private void selectImage() {

        if (changePicture) {
            items = new CharSequence[]{"Take Photo", "Choose from Library", "Delete", "Cancel"};
        } else {
            items = new CharSequence[]{"Take Photo", "Choose from Library",
                    "Cancel"};
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity_.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    if (ContextCompat.checkSelfPermission(MainActivity_.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity_.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            Toast.makeText(MainActivity_.this, "You have denied this permission.", Toast.LENGTH_LONG).show();
                        } else {
                            ActivityCompat.requestPermissions(MainActivity_.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                        }
                    } else if (ContextCompat.checkSelfPermission(MainActivity_.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {
                      /*  Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, REQUEST_CAMERA);*/
                        cameraIntent();
                    }
                } else if (items[item].equals("Choose from Library")) {
                   /* Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);*/
                    galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                } else if (items[item].equals("Delete")) {
                    File file = getFileStreamPath("photo.jpg");

                    if (file.exists()) {
                        file.delete();
                        picture.setImageBitmap(null);
                    }

                    changePicture = false;
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

                changePicture = true;

                saveToInternalSorage(bm);

                picture.setImageBitmap(bm);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void onCaptureImageResult(Intent data) {
        changePicture = true;


        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        saveToInternalSorage(thumbnail);

        picture.setImageBitmap(thumbnail);
    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * returning image
     */
    private static File getOutputMediaFile(int type) {
        // External sdcard location
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), Global.APP_FOLDER);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(Global.APP_FOLDER, "Oops! Failed create " + Global.APP_FOLDER + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        return mediaFile;
    }

    boolean changePicture = false;
    public static byte[] bytePic = null;
    String selectedImagePath = "";

    /* @Override
     public void onActivityResult(int requestCode, int resultCode, Intent data) {
         super.onActivityResult(requestCode, resultCode, data);

         if (resultCode == Activity.RESULT_OK) {
             if (requestCode == REQUEST_CAMERA) {
                 onCaptureImageResult(data);
             } else if (requestCode == SELECT_FILE) {
                 onSelectFromGalleryResult(data);
             }
         }
     }
 */
    @SuppressLint("NewApi")
    public static String getFilePath(Context context, Uri uri) throws URISyntaxException {
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("image".equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[]{
                        split[1]
                };
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {
                    MediaStore.Images.Media.DATA
            };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private void setBitmapInImage(Bitmap profilePicBitmap) {

        if (profilePicBitmap != null)
            profilePicBitmap = Global.getResizedBitmap(profilePicBitmap);

        picture.setImageBitmap(profilePicBitmap);
    }

//    private void onCaptureImageResult(Intent data) {
//        previewCapturedImage();
//    }

    byte[] img = null;

    private void previewCapturedImage() {

        changePicture = true;

        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            final Bitmap thumbnail = BitmapFactory.decodeFile(fileUri.getPath(), options);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            if (thumbnail != null) {
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 10, bytes);
            }

            img = bytes.toByteArray();

            saveToInternalSorage(thumbnail);

            picture.setImageBitmap(thumbnail);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


  /*  @TargetApi(Build.VERSION_CODES.KITKAT)
    private void onSelectFromGalleryResult(Intent data) {
        //get image and convert into bytearray to store in database
        Uri selectedImageUri = data.getData();
        String[] projection = {MediaStore.MediaColumns.DATA};
        String selectedImagePath;
//        try (Cursor cursor = getContentResolver().query(selectedImageUri, projection, null, null, null)) {
//            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
//            cursor.moveToFirst();
//            selectedImagePath = cursor.getString(column_index);
//        }

        selectedImagePath = data.getData().toString();

        Global.printLog("AddOwner", "========" + selectedImagePath);

        Uri uri = Uri.parse(selectedImagePath);

        try {
            selectedImagePath = getFilePath(MainActivity.this, uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        if (bm != null) {
            bm.compress(Bitmap.CompressFormat.JPEG, 10, bytes);
        }
        img = bytes.toByteArray();

        changePicture = true;

        saveToInternalSorage(bm);

        picture.setImageBitmap(bm);
    }*/

    private void StartAnotherActivity() {
        Intent i = new Intent(MainActivity_.this, activityClass);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Exit?")
                .setMessage("Are you sure you want Exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        photo = loadImageFromStorage();
        if (photo != null) {
            changePicture = true;
            picture.setImageBitmap(photo);
        } else {
            changePicture = false;
            picture.setImageDrawable(getResources().getDrawable(R.drawable.profilepic));
        }
        setCustomTheme();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu_for_main_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save_document:
               /* if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        Toast.makeText(MainActivity.this, "You have denied this permission.", Toast.LENGTH_LONG).show();
                    } else {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE_FOR_SAVE);
                    }
                } else if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    SaveDocument();
                }*/

                askCompactPermissions(new String[]{PermissionUtils.Manifest_WRITE_EXTERNAL_STORAGE,
                        PermissionUtils.Manifest_READ_EXTERNAL_STORAGE}, new PermissionResult() {
                    @Override
                    public void permissionGranted() {
                        //permission granted
                        //replace with your action
                        SaveDocument();
                    }

                    @Override
                    public void permissionDenied() {
                        //permission denied
                        //replace with your action
                    }

                    @Override
                    public void permissionForeverDenied() {
                        // user has check 'never ask again'
                        // you need to open setting manually
                        //  Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        //  Uri uri = Uri.fromParts("package", getPackageName(), null);
                        //   intent.setData(uri);
                        //  startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                    }
                });
                return true;
            case R.id.menu_share:
               /* if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        Toast.makeText(MainActivity.this, "You have denied this permission.", Toast.LENGTH_LONG).show();
                    } else {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE_FOR_SHARE);
                    }
                } else if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    ShareDocument();
                }*/

                askCompactPermissions(new String[]{PermissionUtils.Manifest_WRITE_EXTERNAL_STORAGE,
                        PermissionUtils.Manifest_READ_EXTERNAL_STORAGE}, new PermissionResult() {
                    @Override
                    public void permissionGranted() {
                        //permission granted
                        //replace with your action
                        SaveAndShareDocument();
                    }

                    @Override
                    public void permissionDenied() {
                        //permission denied
                        //replace with your action
                    }

                    @Override
                    public void permissionForeverDenied() {
                        // user has check 'never ask again'
                        // you need to open setting manually
                        //  Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        //  Uri uri = Uri.fromParts("package", getPackageName(), null);
                        //   intent.setData(uri);
                        //  startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                    }
                });

                return true;
            case R.id.menu_setting:
                Intent i = new Intent(MainActivity_.this, Setting_activity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE_FOR_SAVE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SaveDocument();
                } else {
                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE_FOR_SHARE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SaveAndShareDocument();
                } else {
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

/*
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
				&& null != data) {

			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };
			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
			Options op = new Options();
			// op.inDensity=50;
			op.inSampleSize = 4;
			photo = (BitmapFactory.decodeFile(picturePath, op));
			saveToInternalSorage(photo);
			picture.setImageBitmap(loadImageFromStorage());
		} else
			photo = loadImageFromStorage();

		*/
    /*
     * if (requestCode == PICK_FROM_GALLERY && data != null) { Bundle
     * extras2 = data.getExtras(); if (extras2 != null) { photo =
     * extras2.getParcelable("data"); picture.setImageBitmap(photo); File
     * dir = new File(Environment.getExternalStorageDirectory() .getPath() +
     * "/Resume builder"); try { if (dir.mkdir()) {
     * System.out.println("Directory created"); } else {
     * System.out.println("Directory is not created"); } } catch (Exception
     * e) { e.printStackTrace(); }
     *
     * saveToInternalSorage(photo); super.onActivityResult(requestCode,
     * resultCode, data); } }
     *//*

	}
*/

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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                // Do something for Naught and above versions
                fos = this.openFileOutput("photo.jpg", Context.MODE_PRIVATE);
            } else {
                // do something for phones running an SDK before Naught
                fos = this.openFileOutput("photo.jpg", Context.MODE_WORLD_READABLE);
            }


            // Use the compress method on the BitMap object to write image to
            // the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void SaveAndShareDocument() {
        File dir = new File(Environment.getExternalStorageDirectory()
                .getPath() + "/" + Global.APP_FOLDER);
        dir.mkdir();
        try {
            if (dir.isDirectory()) {
                Calendar c = Calendar.getInstance();
                String filePath = Environment.getExternalStorageDirectory()
                        .getPath()
                        + "/" + Global.APP_FOLDER + "/Resume_"
                        + (c.get(Calendar.MONTH) + 1)
                        + "-"
                        + c.get(Calendar.DAY_OF_MONTH)
                        + "-"
                        + c.get(Calendar.YEAR)
                        + " "
                        + c.get(Calendar.HOUR_OF_DAY)
                        + "-"
                        + c.get(Calendar.MINUTE)
                        + "-"
                        + c.get(Calendar.SECOND) + ".pdf";
                SaveToPDF(filePath, true);
            } else {
                Toast.makeText(getApplicationContext(),
                        "No SDcard available", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void SaveDocument() {
        File dir = new File(Environment.getExternalStorageDirectory()
                .getPath() + "/" + Global.APP_FOLDER);
        dir.mkdir();
        try {
            if (dir.isDirectory()) {
                Calendar c = Calendar.getInstance();
                String filePath = Environment.getExternalStorageDirectory()
                        .getPath()
                        + "/" + Global.APP_FOLDER + "/Resume_"
                        + (c.get(Calendar.MONTH) + 1)
                        + "-"
                        + c.get(Calendar.DAY_OF_MONTH)
                        + "-"
                        + c.get(Calendar.YEAR)
                        + " "
                        + c.get(Calendar.HOUR_OF_DAY)
                        + "-"
                        + c.get(Calendar.MINUTE)
                        + "-"
                        + c.get(Calendar.SECOND) + ".pdf";
                SaveToPDF(filePath, false);
            } else {
                Toast.makeText(getApplicationContext(),
                        "No SDcard available", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ShareDocument(File file) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        if (file.exists()) {
            Global.printLog("ShareDocument", "=file=" + file);
            Uri u1 = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", file);
            Global.printLog("ShareDocument", "=u1=" + u1);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("file/pdf");
            //intent.putExtra(android.content.Intent.EXTRA_STREAM, Uri
            //		.parse("file://"
            //				+ SaveToPDF(Environment.getExternalStorageDirectory().getPath() + "/"+Global.APP_FOLDER+"/MyResume.pdf")));
            intent.putExtra(Intent.EXTRA_STREAM, u1);
            intent.putExtra(Intent.EXTRA_SUBJECT, "Resume");

            // For some reason, attaching the file to email is giving
            // an error "Unable to attach file. File size 0 bytes". I believe there
            // might be an issue as the file is in the middle of getting
            // saved and since the process of save is still going on and
            // the file size is 0 when we are trying to immediately
            // attach it to the email.
            // Adding a sleep of 2 seconds gives enough time for the file
            // to be saved before attaching it to an email and the problem
            // is no more seen.

            //...
          /*  try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }*/ //...
            startActivity(intent);
        }
    }

    private Bitmap loadImageFromStorage() {
        try {
            /*
             * ContextWrapper cw = new ContextWrapper(getApplicationContext());
             * File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
             * File mypath = new File(directory, "profile.jpg"); File f = new
             * File(mypath, "profile.JPEG");
             */
            File file = getFileStreamPath("photo.jpg");

            if (file.exists()) {

                Global.printLog("file==getAbsolutePath===", file.getAbsolutePath());
                Bitmap b = BitmapFactory.decodeStream(this
                        .openFileInput("photo.jpg"));
                return b;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }

    BaseColor titleColor = null, nameColor = null, contentColor = null;
    Font headingFont = null, nameFont = null, contentFont = null;
    int titleSize, nameSize, contentSize;

    @SuppressLint("ShowToast")
    private String SaveToPDF(String fP, final boolean wantToShare) {

        final String filePath = fP;
        final ProgressDialog progress = ProgressDialog.show(this, "Saving",
                "Saving Document", true);
        final Toast toast = Toast.makeText(getApplicationContext(),
                "Resume save to "
                        + Environment.getExternalStorageDirectory().getPath()
                        + "/" + Global.APP_FOLDER + "/", Toast.LENGTH_SHORT);

        new Thread(new Runnable() {
            @SuppressWarnings("deprecation")
            @Override
            public void run() {

                Document document = new Document();
                PdfWriter pdfw = new PdfWriter() {
                };
                File file = new File("");
                try {
                    file = new File(filePath);
                    pdfw = PdfWriter.getInstance(document, new FileOutputStream(file));
//                    pdfw.setViewerPreferences(PdfWriter.PageModeUseOC);
//                    pdfw.setPdfVersion(PdfWriter.VERSION_1_5);
                    document.open();

                    if (!sessionManager.getStringPref(SessionManager.TITLE_SIZE).isEmpty())
                        titleSize = Integer.parseInt(sessionManager.getStringPref(SessionManager.TITLE_SIZE));
                    else titleSize = 16;

                    if (!sessionManager.getStringPref(SessionManager.NAME_SIZE).isEmpty())
                        nameSize = Integer.parseInt(sessionManager.getStringPref(SessionManager.NAME_SIZE));
                    else nameSize = 14;

                    if (!sessionManager.getStringPref(SessionManager.CONTENT_SIZE).isEmpty())
                        contentSize = Integer.parseInt(sessionManager.getStringPref(SessionManager.CONTENT_SIZE));
                    else contentSize = 12;

//                    Global.printLog("titleSize===", titleSize + "");
//                    Global.printLog("nameSize===", nameSize + "");
//                    Global.printLog("contentSize===", contentSize + "");

//                  BaseFont urName = BaseFont.createFont("assets/fonts/arial.ttf", "UTF-8", BaseFont.EMBEDDED);


                    String mFontName = sessionManager.getStringPref(SessionManager.SELECTED_FONT);

                    titleColor = getBaseColor(SessionManager.TITLE_COLOR);
                    nameColor = getBaseColor(SessionManager.NAME_COLOR);
                    contentColor = getBaseColor(SessionManager.CONTENT_COLOR);

//                    Global.printLog("titleColor====", "===" + titleColor);
//                    Global.printLog("nameColor====", "===" + nameColor);
//                    Global.printLog("contentColor====", "===" + contentColor);

//                    BaseFont urName = BaseFont.createFont("assets/fonts/Calibri.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
//                    Font arialFontName = new Font(urName, 16);
//                    Font arialFontName = new Font(urName, titleSize, Font.BOLD, titleColor);
//                    Global.printLog("arialFontName====", "===" + arialFontName);

//                    String FONT_Calibri = "resources/Calibri.ttf";
//                    FontFactory.register(FONT_Calibri, "Calibri");
//                    Font arialFontName = FontFactory.getFont("Calibri", "Cp1253", true);

                    if (mFontName.equalsIgnoreCase("COURIER")) {
                        headingFont = new Font(Font.FontFamily.COURIER, titleSize, Font.BOLD, titleColor);
                        nameFont = new Font(Font.FontFamily.COURIER, nameSize, Font.NORMAL, nameColor);
                        contentFont = new Font(Font.FontFamily.COURIER, contentSize, Font.NORMAL, contentColor);

                    } else if (mFontName.equalsIgnoreCase("HELVETICA")) {
                        headingFont = new Font(Font.FontFamily.HELVETICA, titleSize, Font.BOLD, titleColor);
                        nameFont = new Font(Font.FontFamily.HELVETICA, nameSize, Font.NORMAL, nameColor);
                        contentFont = new Font(Font.FontFamily.HELVETICA, contentSize, Font.NORMAL, contentColor);

                    } else if (mFontName.equalsIgnoreCase("TIMES ROMAN")) {
                        headingFont = new Font(Font.FontFamily.TIMES_ROMAN, titleSize, Font.BOLD, titleColor);
                        nameFont = new Font(Font.FontFamily.TIMES_ROMAN, nameSize, Font.NORMAL, nameColor);
                        contentFont = new Font(Font.FontFamily.TIMES_ROMAN, contentSize, Font.NORMAL, contentColor);

                    } else if (mFontName.equalsIgnoreCase("CALIBRI")) {
                        headingFont = new Font(Font.FontFamily.UNDEFINED, titleSize, Font.BOLD, titleColor);
                        nameFont = new Font(Font.FontFamily.UNDEFINED, nameSize, Font.NORMAL, nameColor);
                        contentFont = new Font(Font.FontFamily.UNDEFINED, contentSize, Font.NORMAL, contentColor);
                    } else {
                        headingFont = new Font(Font.FontFamily.HELVETICA, titleSize, Font.BOLD, titleColor);
                        nameFont = new Font(Font.FontFamily.HELVETICA, nameSize, Font.NORMAL, nameColor);
                        contentFont = new Font(Font.FontFamily.HELVETICA, contentSize, Font.NORMAL, contentColor);
                    }

//                    headingFont = arialFontName;
//                    nameFont = arialFontName;
//                    contentFont = arialFontName;

                    // image adding
                    if (photo != null) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        photo.compress(Bitmap.CompressFormat.PNG, 50, stream);
                        Image image = Image.getInstance(stream.toByteArray());
                        image.scaleAbsolute(100.0F, 100.0F);
                        image.setAlignment(1);
                        document.add(image);

                    } else {
                        //Paragraph pT = new Paragraph("Picture \n");
                        Paragraph pT = new Paragraph("\n");
                        pT.setAlignment(1);
                        document.add(pT);
                    }

//                    PdfLayer layer = new PdfLayer("Bhoomi---", pdfw);
//                    layer.setOn(true);
//                    PdfContentByte cb = pdfw.getDirectContent();
////                    cb.beginText();
//                    cb.beginLayer(layer);
//                    cb.showTextAligned(Element.ALIGN_LEFT, "Satnaaam", 50, 766, 0);
//                    cb.endLayer();
//                    cb.endText();

                    // Writing personal info

                    dao d = new dao(getApplicationContext(), "PersonalInfo");
                    PersonalInfo p = d.loadData();
                    SharedPreferences sh = getSharedPreferences(
                            Profile_Info.DOB_FILE, Context.MODE_PRIVATE);
                    String day = sh.getString(Profile_Info.DOB_DAY, "");
                    String Month = sh.getString(Profile_Info.DOB_MONTH, "");
                    String Year = sh.getString(Profile_Info.DOB_YEAR, "");
                    sh = getSharedPreferences(Profile_Info.DATE_FORMATE_FILE,
                            Context.MODE_PRIVATE);
                    int choice = sh.getInt(Profile_Info.DATE_FORMATE, 1);
                    String DOB = PersonalInfo.getDateOfBirthFormate(choice,
                            day, Month, Year);
                    if (!p.getmPersonName().equals("")
                            || !p.getmStreetNo().equals("")
                            || !p.getmCity().equals("")
                            || !p.getmState().equals("")
                            || !p.getmCountry().equals("")
                            || !p.getmContactNumber().equals("")
                            || !p.getmPinOrZip().equals("")
                            || !p.getmEmail().equals("")) {
                        Paragraph pT = new Paragraph(p.getmPersonName(), nameFont);
//                        pT.setFont(headingFont);
                        pT.setAlignment(1);
                        document.add(pT);

                        String mAddress = "";

                        if (p.getmStreetNo() != null && !p.getmStreetNo().isEmpty()) {
                            mAddress = p.getmStreetNo();
                        }

                        if (p.getmCity() != null && !p.getmCity().isEmpty()) {
                            if (!mAddress.isEmpty()) {
                                mAddress += ", ";
                            }
                            mAddress += p.getmCity();
                        }

                        if (p.getmState() != null && !p.getmState().isEmpty()) {
                            if (!mAddress.isEmpty()) {
                                mAddress += ", ";
                            }
                            mAddress += p.getmState();
                        }

                        if (p.getmCountry() != null && !p.getmCountry().isEmpty()) {
                            if (!mAddress.isEmpty()) {
                                mAddress += ", ";
                            }
                            mAddress += p.getmCountry();
                        }

                        if (p.getmPinOrZip() != null && !p.getmPinOrZip().isEmpty()) {
                            if (!mAddress.isEmpty()) {
                                mAddress += ", ";
                            }
                            mAddress += p.getmPinOrZip();
                        }

                        if (mAddress == null || mAddress.trim().isEmpty()) {
                            mAddress = "\n";
                        }
                        pT = new Paragraph(mAddress, contentFont);
                        pT.setAlignment(1);
//                        pT.setFont(contentFont);
                        document.add(pT);
//                        }

                        if (DOB.compareTo("//") != 0) {
                            pT = new Paragraph("Date Of Birth : " + DOB, contentFont);
                            pT.setAlignment(1);
//                            pT.setFont(contentFont);
                            document.add(pT);
                        }

                        if (p.getmContactNumber() != null && !p.getmContactNumber().isEmpty()) {
                            pT = new Paragraph("Contact : " + p.getmContactNumber(), contentFont);
                            pT.setAlignment(1);
//                            pT.setFont(contentFont);
                            document.add(pT);
                        }

                        if (p.getmEmail() != null && !p.getmEmail().isEmpty()) {
                            pT = new Paragraph("Email : " + p.getmEmail(), contentFont);
                            pT.setAlignment(1);
//                            pT.setFont(contentFont);
                            document.add(pT);
                        }
                    }

                    // [Professional Summary, Objective, Experience, Skills, Education, Achievements, References]
                    Paragraph p1;
                    mOrderSections = sessionManager.get_orderSection();
                    Global.printLog("SaveToPDF==mOrderSections=", mOrderSections + "");

                    if (mOrderSections != null) {
                        for (int i = 0; i < mOrderSections.size(); i++) {
                            String mNextHeader = mOrderSections.get(i).trim();
                            Global.printLog("mOrderSections====", "===mNextHeader==" + mNextHeader);

                            switch (mNextHeader) {
                                case "Objective":
                                    // writing Objectives
                                    sh = getSharedPreferences("Objective", Context.MODE_PRIVATE);
                                    Paragraph pObjective = new Paragraph(sh.getString(
                                            "message", ""), contentFont);
                                    p1 = new Paragraph("");
                                    if (sh.getString("message", "") != "") {
                                        p1 = new Paragraph("\nObjective \n", headingFont);
//                                        p1.setFont(headingFont);
                                        document.add(p1);
                                        document.add(pObjective);
                                    }
                                    break;

                                case "Professional Summary":
                                    // writing Professional summary
                                    ArrayList<ProfessionalSummary> Summary = ProfessionalSummaryList
                                            .getInstance(getApplicationContext())
                                            .getPendingJobs();
                                    if (Summary.size() != 0) {
                                        p1 = new Paragraph("\nProfessional Summary ", headingFont);
//                                        p1.setFont(headingFont);
                                        document.add(p1);

                                        for (ProfessionalSummary job : Summary) {
                                            p1 = new Paragraph(job.toString() + "\n", contentFont);
                                            p1.setFont(contentFont);
                                            document.add(p1);
                                        }
                                    }
                                    break;

                                case "Experience":
                                    // writing Experience
                                    ArrayList<ExperienceData> list = ExperienceDataList
                                            .getInstance(getApplicationContext())
                                            .getPendingJobs();
                                    if (list.size() != 0) {
                                        p1 = new Paragraph("\nExperience", headingFont);
                                        p1.setFont(headingFont);
                                        document.add(p1);
                                        for (ExperienceData one : list) {

//                                            if (one.getmCompanyName() != null && !one.getmCompanyName().isEmpty()) {

                                            p1 = new Paragraph(one.getmCompanyName() + "\n", contentFont);
//                                            p1.setFont(contentFont);
                                            document.add(p1);
//                                            }


//                                            if (one.getmDesignation() != null && !one.getmDesignation().isEmpty()) {
                                            p1 = new Paragraph(one.getmDesignation() + "\n", contentFont);
//                                            p1.setFont(contentFont);
                                            document.add(p1);
//                                            }


                                            Date date = one.getmStartDate();
                                            String dateStr2;
                                            String dateStr;
                                            Date d1 = one.getmStartDate();
                                            if (d1.getYear() != 1800) {
                                                dateStr = date.getMonth() + "/" + date.getYear() + "";
                                            } else
                                                dateStr = "";
                                            d1 = one.getmEndDate();
                                            if (d1.getYear() != 1800) {
                                                date = one.getmEndDate();
                                                dateStr2 = date.getMonth() + "/" + date.getYear() + "";
                                            } else if (one.getmIsCurrent())
                                                dateStr2 = "Present";
                                            else
                                                dateStr2 = "";

                                            if (!dateStr.isEmpty() && !dateStr2.isEmpty()) {
                                                p1 = new Paragraph(dateStr + "-" + dateStr2, contentFont);
                                                p1.setFont(contentFont);
                                                document.add(p1);
                                            }

//                                            if (one.getmWhatDidYouOverThere() != null && !one.getmWhatDidYouOverThere().isEmpty()) {
                                            p1 = new Paragraph(one.getmWhatDidYouOverThere(), contentFont);
                                            p1.setFont(contentFont);
                                            document.add(p1);
//                                            }
                                        }// end exp
                                    }
                                    break;

                                case "Education":
                                    // writing Education Details
                                    ArrayList<EducationData> list2 = EducationDataList
                                            .getInstance(getApplicationContext())
                                            .getPendingJobs();
                                    if (list2.size() != 0) {
                                        p1 = new Paragraph("\nEducation", headingFont);
                                        p1.setFont(headingFont);
                                        document.add(p1);

                                        for (EducationData one : list2) {
//                                            if (one.getmSchoolName() != null && !one.getmSchoolName().isEmpty()) {
                                            p1 = new Paragraph(one.getmSchoolName() + "\n", contentFont);
                                            p1.setFont(contentFont);
                                            document.add(p1);
//                                            }

                                            Date date = one.getmStartDate();
                                            String dateStr2;
                                            String dateStr;
                                            Date d1 = one.getmStartDate();
                                            if (d1.getYear() != 1800) {
                                                dateStr = date.getMonth() + "/" + date.getYear() + "";
                                            } else
                                                dateStr = "";
                                            d1 = one.getmEndDate();
                                            if (d1.getYear() != 1800) {
                                                date = one.getmEndDate();
                                                dateStr2 = date.getMonth() + "/" + date.getYear() + "";
                                            } else if (one.ismIsCurrent())
                                                dateStr2 = "Present";
                                            else
                                                dateStr2 = "";

                                            if (!dateStr.isEmpty() && !dateStr2.isEmpty()) {
                                                p1 = new Paragraph(dateStr + "-" + dateStr2, contentFont);
                                                p1.setFont(contentFont);
                                                document.add(p1);
                                            }

//                                            if (one.getmDegreeName() != null && !one.getmDegreeName().isEmpty()) {
                                            p1 = new Paragraph(one.getmDegreeName(), contentFont);
                                            p1.setFont(contentFont);
                                            document.add(p1);
//                                            }
                                        }
                                    }
                                    break;

                                case "Skills":
                                    // skills
                                    ArrayList<SkillsData> SkillsSummary = SkillsDataList
                                            .getInstance(getApplicationContext())
                                            .getPendingJobs();
                                    if (SkillsSummary.size() != 0) {
                                        p1 = new Paragraph("\nSkills \n", headingFont);
                                        p1.setFont(headingFont);
                                        document.add(p1);
                                        for (SkillsData job : SkillsSummary) {
                                            if (job.toString() != null && !job.toString().isEmpty()) {
                                                p1 = new Paragraph(job.toString() + "\n", contentFont);
                                                p1.setFont(contentFont);
                                                document.add(p1);
                                            }
                                        }
                                    }
                                    break;

                                case "Achievements":
                                    // achievements
                                    ArrayList<AchievementsData> Achievements = AchievementsDataList
                                            .getInstance(getApplicationContext())
                                            .getPendingJobs();
                                    if (Achievements.size() != 0) {
                                        p1 = new Paragraph("\nAchievements\n", headingFont);
                                        p1.setFont(headingFont);
                                        document.add(p1);
                                        for (AchievementsData job : Achievements) {

                                            String mAward = "";
                                            String mAwardDate = "";
                                            if (job.getmDateOfAward() != null) {

                                                int a = sh.getInt(Profile_Info.DATE_FORMATE, 1);

                                                mAwardDate = PersonalInfo.getDateOfBirthFormate(a, job
                                                        .getmDateOfAward().getDate() + "", job.getmDateOfAward()
                                                        .getMonth() + "", job.getmDateOfAward().getYear() + "").trim();
                                            }

                                            Global.printLog("mAwardDate====", "=====" + mAwardDate);
                                          /*  if (job.getmNameOfAward() != null && !job.getmNameOfAward().isEmpty()) {
                                                mAward = job.getmNameOfAward();
                                            }
                                            if (job.getmDateOfAward() != null && !job.getmDateOfAward().toString().isEmpty()) {
                                                if (!mAward.isEmpty()) {
                                                    mAward += "\n";
                                                }
                                                mAward += job.getmDateOfAward();
                                            }
                                            if (job.getmWhatDidYouDoToAchieveIt() != null && !job.getmWhatDidYouDoToAchieveIt().isEmpty()) {
                                                if (!mAward.isEmpty()) {
                                                    mAward += "\n";
                                                }
                                                mAward = job.getmWhatDidYouDoToAchieveIt();
                                            }
                                                                                        p1 = new Paragraph(mAward, contentFont);

                                            */

                                            p1 = new Paragraph(job.getmNameOfAward() + "\n"
                                                    + mAwardDate + "\n"
                                                    + job.getmWhatDidYouDoToAchieveIt(), contentFont);
                                            p1.setFont(contentFont);
                                            document.add(p1);
                                        }
                                    }
                                    break;

                                case "References":
                                    // References
                                    ArrayList<ReferencesData> ref = ReferencesDataList
                                            .getInstance(getApplicationContext())
                                            .getPendingJobs();
                                    if (ref.size() != 0) {
                                        p1 = new Paragraph("\nReferences\n", headingFont);
                                        p1.setFont(headingFont);
                                        document.add(p1);
                                        for (ReferencesData job : ref) {

                                            String mReference = "";

                                            if (job.getmNameOfCoWorker() != null && !job.getmNameOfCoWorker().isEmpty()) {
                                                mReference = job.getmNameOfCoWorker();
                                            }

                                            if (job.getmDasignationofCoWorker() != null && !job.getmDasignationofCoWorker().isEmpty()) {
                                                if (!mReference.isEmpty()) {
                                                    mReference += ", ";
                                                }
                                                mReference += job.getmDasignationofCoWorker();
                                            }

                                            if (job.getmCompanyName() != null && !job.getmCompanyName().isEmpty()) {
                                                if (!mReference.isEmpty()) {
                                                    mReference += ", ";
                                                }
                                                mReference += job.getmCompanyName() + "\n";
                                            }

                                            if (mReference == null || mReference.trim().isEmpty()) {
                                                mReference = "\n";
                                            }

                                            p1 = new Paragraph(mReference
                                                    + job.getmEmailOfCoWorker() + "\n"
                                                    + job.getmHowDidYouWorkWithHim() + "\n"
                                                    + job.getmContactOfCoWorker(), contentFont);
                                            p1.setFont(contentFont);
                                            document.add(p1);
                                        }
                                    }
                                    break;
                            }
                        }
                    }
                } catch (DocumentException de) {
                    Log.e("PDFCreator", "DocumentException:" + de);
                } catch (IOException e) {

                    Log.e("PDFCreator", "ioException:" + e);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    document.close();
                    pdfw.close();

                    if (wantToShare && file != null) {
                        ShareDocument(file);
                    }
                    file = null;
                }
                progress.dismiss();
                toast.show();
            }
        }).start();

        return filePath;
    }

    private BaseColor getBaseColor(String title) {

        Global.printLog("getBaseColor====", "==title=" + title);

        String mColorName = sessionManager.getStringPref(title);

//        String[] font_color_Name = new String[]{"LIGHT GRAY", "GRAY", "DARK GRAY", "BLACK", "RED", "PINK",
//                "ORANGE", "YELLOW", "GREEN", "MAGENTA", "CYAN", "BLUE"};

        Global.printLog("mColorName====", "====" + mColorName);
        switch (mColorName) {
            case "LIGHT GRAY":
//                headingFont = new Font(Font.FontFamily.COURIER, titleSize, Font.BOLD, titleColor);
//                nameFont = new Font(Font.FontFamily.COURIER, nameSize, Font.NORMAL, nameColor);
//                contentFont = new Font(Font.FontFamily.COURIER, contentSize, Font.NORMAL, contentColor);

                return BaseColor.LIGHT_GRAY;
//                break;
            case "GRAY":
                return BaseColor.GRAY;
//                break;
            case "DARK GRAY":
                return BaseColor.DARK_GRAY;
//                break;
            case "BLACK":
                return BaseColor.BLACK;
//                break;
            case "RED":
                return BaseColor.RED;
//                break;
            case "PINK":
                return BaseColor.PINK;
//                break;
            case "ORANGE":
                return BaseColor.ORANGE;
//                break;
            case "YELLOW":
                return BaseColor.YELLOW;
//                break;
            case "GREEN":
                return BaseColor.GREEN;
//                break;
            case "MAGENTA":
                return BaseColor.MAGENTA;
//                break;
            case "CYAN":
                return BaseColor.CYAN;
//                break;
            case "BLUE":
                return BaseColor.BLUE;
//                break;
        }
        return BaseColor.BLACK;
    }

    private void setCustomTheme() {
        Global.setCustomTheme(MainActivity_.this, lnr_main);
       /* lnr_main.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        switch (sessionManager.getAppTheme()) {
          *//*public static String custom_theme1 = "Blue";
            public static String custom_theme2 = "Purple";
            public static String custom_theme3 = "Orange";
            public static String custom_theme4 = "Brown";
            public static String custom_theme5 = "Sky"; // Default theme
            public static String custom_theme6 = "Gray";
            public static String custom_theme7 = "Green";
            public static String custom_theme8 = "Teal";
            public static String custom_theme9 = "Red";
            public static String custom_theme10 = "Indigo";*//*

            case "Blue": //bkg1
                lnr_main.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg1));
                break;

            case "Purple": //bkg2
                lnr_main.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg2));
                break;

            case "Orange": //bkg3
                lnr_main.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg3));
                break;

            case "Brown": //bkg4
                lnr_main.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg4));
                break;

            case "Default":
                lnr_main.setBackgroundDrawable(null);
                lnr_main.setBackgroundColor(getResources().getColor(R.color.white));
                break;

            case "Gray": //bkg5
                lnr_main.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg5));
                break;

            case "Green": //bkg6
                lnr_main.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg6));
                break;

            case "Teal": //bkg7
                lnr_main.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg7));
                break;

            case "Red": //bkg8
                lnr_main.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg8));
                break;

            case "Indigo": //bkg9
                lnr_main.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg9));
                break;
        }*/
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

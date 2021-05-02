package com.apps.resumebuilderapp.main;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.apps.resumebuilderapp.R;
import com.apps.resumebuilderapp.utils.SessionManager;

import permission.auron.com.marshmallowpermissionhelper.ActivityManagePermission;
import permission.auron.com.marshmallowpermissionhelper.PermissionResult;
import permission.auron.com.marshmallowpermissionhelper.PermissionUtils;

/**
 * Created by Bhoomika Patel on 06-Nov-16.
 */

public class SplashActivity extends ActivityManagePermission {

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        askForCameraPermission();
    }

    private void askForCameraPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            setContents();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            askCompactPermission(PermissionUtils.Manifest_CAMERA, new PermissionResult() {
                @Override
                public void permissionGranted() {
                    askForGallaryPermission();
                }

                @Override
                public void permissionDenied() {
                    askForGallaryPermission();
                }

                @Override
                public void permissionForeverDenied() {
                    askForGallaryPermission();
                }
            });
        }
    }

    private void askForGallaryPermission() {
        askCompactPermissions(new String[]{PermissionUtils.Manifest_READ_EXTERNAL_STORAGE, PermissionUtils.Manifest_WRITE_EXTERNAL_STORAGE}, new PermissionResult() {
            @Override
            public void permissionGranted() {
                // do something here
                setContents();
            }

            @Override
            public void permissionDenied() {

            }

            @Override
            public void permissionForeverDenied() {

            }
        });
    }


    private void setContents() {
        sessionManager = new SessionManager(SplashActivity.this);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent intent = null;

                if (sessionManager.getPinLock()) {
                    intent = new Intent(SplashActivity.this, LockActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                }

                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        }, 500);

      /*  Thread splesh = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    wait(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Intent intent = null;

                if (sessionManager.getPinLock()) {
                    intent = new Intent(SplashActivity.this, LockActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                }

                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        };
        splesh.start();*/
    }
}

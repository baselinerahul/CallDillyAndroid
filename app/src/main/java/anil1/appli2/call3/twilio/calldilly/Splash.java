package anil1.appli2.call3.twilio.calldilly;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import anil1.appli2.call3.twilio.calldilly.call.VoiceActivity;
import anil1.appli2.call3.twilio.calldilly.sms.Sms;

import static anil1.appli2.call3.twilio.calldilly.MainActivity.RequestPermissionCode;

public class Splash extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                Splash.this,
                Manifest.permission.READ_CONTACTS)) {
            Toast.makeText(Splash.this, "CONTACTS permission allows us to Access CONTACTS app", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(Splash.this, new String[]{
                    Manifest.permission.READ_CONTACTS}, RequestPermissionCode);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(Splash.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }

            }, 3000);

        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Checking the request code of our request
        if (requestCode == 23) {
            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Permission Needed To Run The App", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS) {
            Map<String, Integer> perms = new HashMap<String, Integer>();
            // Initial
            perms.put(Manifest.permission.READ_CONTACTS, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.WRITE_CONTACTS, PackageManager.PERMISSION_GRANTED);
            for (int i = 0; i < permissions.length; i++)
                perms.put(permissions[i], grantResults[i]);
            if (perms.get(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
                    && perms.get(Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(Splash.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }

                }, 2000);

            } else {
                // Permission Denied
                Toast.makeText(Splash.this, "Some Permission is Denied", Toast.LENGTH_SHORT)
                        .show();

                finish();
            }

        }
    }


//    public void EnableRuntimePermission() {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(
//                Splash.this,
//                Manifest.permission.READ_CONTACTS)) {
//            Toast.makeText(Splash.this, "CONTACTS permission allows us to Access CONTACTS app", Toast.LENGTH_LONG).show();
//        } else {
//            ActivityCompat.requestPermissions(Splash.this, new String[]{
//                    Manifest.permission.READ_CONTACTS}, RequestPermissionCode);
//            new Handler().postDelayed(new Runnable() {
//
//                /*
//                 * Showing splash screen with a timer. This will be useful when you
//                 * want to show case your app logo / company
//                 */
//
//                @Override
//                public void run() {
//                    Intent i = new Intent(Splash.this, MainActivity.class);
//                    startActivity(i);
//                    finish();
//                }
//            }, SPLASH_TIME_OUT);
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {
//        switch (RC) {
//            case RequestPermissionCode:
//                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(Splash.this, "Permission Granted, Now your application can access CONTACTS.", Toast.LENGTH_LONG).show();
//                    new Handler().postDelayed(new Runnable() {
//
//                        /*
//                         * Showing splash screen with a timer. This will be useful when you
//                         * want to show case your app logo / company
//                         */
//
//                        @Override
//                        public void run() {
//                            Intent i = new Intent(Splash.this, MainActivity.class);
//                            startActivity(i);
//                            finish();
//                        }
//                    }, SPLASH_TIME_OUT);
//                } else {
//                    Toast.makeText(Splash.this, "Permission Canceled, Now your application cannot access CONTACTS.", Toast.LENGTH_LONG).show();
//                }
//                break;
//        }
//    }

}

package com.example.pocket;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static int SPLASH_SCREEN = 3000;
    private ImageView appIcon;
    private TextView appName;
    private Animation topAnim,bottomAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        appIcon=(ImageView) findViewById(R.id.app_icon);
        appName=(TextView) findViewById(R.id.app_name);
        topAnim=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.top_animation);
        bottomAnim=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bottom_animatio);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                File f=new File("/data/data/"+getPackageName()+"/shared_prefs/UserDetails.xml");
                if(f.exists()){
                    SharedPreferences sharedPreferences=getSharedPreferences("User Details",MODE_PRIVATE);
                    Boolean isLoggedIn=sharedPreferences.getBoolean("isLoggedIn",true);
                    if(isLoggedIn){
                        Intent intent=new Intent(getApplicationContext(),Dashboard.class);
                        startActivity(intent);
                        finish();
                    } else{
                        Boolean fistTimeLoggedIn=sharedPreferences.getBoolean("fistTimeLoggedIn",true);
                        if(fistTimeLoggedIn){
                            Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.CAMERA,Manifest.permission.READ_SMS).withListener(new MultiplePermissionsListener() {
                                @Override
                                public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {

                                }

                                @Override
                                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                                    permissionToken.continuePermissionRequest();
                                }
                            }).check();
                            Intent intent=new Intent(getApplicationContext(),Registration.class);
                            startActivity(intent);
                            finish();
                        } else{
                            Intent intent=new Intent(getApplicationContext(),Login.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                } else{
                    Intent intent=new Intent(getApplicationContext(),onBoarding.class);
                    startActivity(intent);
                    finish();
                }
            }
        },SPLASH_SCREEN);
    }
}
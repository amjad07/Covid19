package com.worklation.covid19;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.worklation.covid19.database.DatabaseHandler;
import com.worklation.covid19.database.ReadDBActivity;
import com.worklation.covid19.heart_monitor.HeartRateFindValue;
import com.worklation.covid19.model.Collector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements SensorEventListener, View.OnClickListener {
    private SensorManager sensorManager;
    double ax,ay,az;   // these are the acceleration in x,y and z axis
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS= 7;

    double val1, val2;
    int breathCount = 0;
    int heartRate = 0;

    AppCompatButton respRateButton, hearRateButton,secondPageButton,uploadSigns,read_db;

    DatabaseHandler db = new DatabaseHandler(this);

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void init(){
        val1 = 0;
        val2 = 0;
        read_db = findViewById(R.id.read_db);
        read_db.setOnClickListener(this::onClick);
        respRateButton = findViewById(R.id.resp_rate);
        respRateButton.setOnClickListener(this::onClick);
        hearRateButton = findViewById(R.id.heart_rate);
        hearRateButton.setOnClickListener(this::onClick);
        uploadSigns = findViewById(R.id.upload_signs);
        uploadSigns.setOnClickListener(this::onClick);
        secondPageButton = findViewById(R.id.second_page);
        secondPageButton.setOnClickListener(this::onClick);

        checkAndroidVersion();


    }
    private void checkAndroidVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkAndRequestPermissions();

        } else {
            // code for lollipop and pre-lollipop devices
        }

    }


    private boolean checkAndRequestPermissions() {
        int camera = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);
        int wtite = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int read = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (wtite != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (read != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }



    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("in fragment on request", "Permission callback called-------");
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Log.d("in fragment on request", "CAMERA & WRITE_EXTERNAL_STORAGE READ_EXTERNAL_STORAGE permission granted");
                        // process the normal flow
                        //else any one or both the permissions are not granted
                    } else {
                        Log.d("in fragment on request", "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            showDialogOK("Camera and Storage Permission required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                      /*      Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                                    .show();*/
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }

    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            ax=event.values[0];
            ay=event.values[1];
            az=event.values[2];

          /*  if (val2 == 0){
                val2 = ay;
            }*/
            if(ay>val2 && ay>1){
                breathCount++;
                System.out.println(breathCount);
            }else if(ay<1){
                val2 = ay;
            }
            String values = az+"";
           // System.out.println(ax);
            System.out.println(ay);
            //System.out.println(az);
            System.out.println(".........");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void respRate(){
        breathCount = 0;
        respRateButton.setEnabled(false);
        hearRateButton.setEnabled(false);
        hearRateButton.setEnabled(false);
        hearRateButton.setEnabled(false);
        uploadSigns.setEnabled(false);
        secondPageButton.setEnabled(false);
        read_db.setEnabled(false);
        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);


        new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                System.out.println("Seconds remaining: " + millisUntilFinished / 1000);
            }



            public void onFinish() {
                System.out.println("Done");
                Toast.makeText(getApplicationContext()
                        ,"Respiratory rate " + breathCount,Toast.LENGTH_SHORT).show();
                System.out.println(breathCount);
                respRateButton.setEnabled(true);
                hearRateButton.setEnabled(true);
                read_db.setEnabled(true);
                uploadSigns.setEnabled(true);
                secondPageButton.setEnabled(true);
                sensorManager.unregisterListener(MainActivity.this);


            }
        }.start();
     }
    private void secondPage(){
        if (!(breathCount <=0)){
            // Inserting symptomss
            Log.d("Insert: ", "Inserting ..");
          //  db.addSigns(new Collector(breathCount+"", "100"));
        }else {
            System.out.println("Enter data properly");
        }
        Intent intent = new Intent(this, SymtomsActivity.class);
        startActivity(intent);
     }
    private void readDbPage(){

        Intent intent = new Intent(this, ReadDBActivity.class);
        startActivity(intent);
     }
    private void hearRateMonitor(){

        Intent intent = new Intent(this, HeartRateFindValue.class);
        startActivity(intent);
     }
    private void uploadSignn(){
        System.out.println(breathCount);
        if (breathCount >0){
            // Inserting symptomss
            Log.d("Insert: ", "data ..");

            db.updateRespRate(new Collector(breathCount+""));
            Log.d("done: ", "Inserting ..");
            Toast.makeText(this,"Signs Data updated",Toast.LENGTH_SHORT).show();

        }else {
            System.out.println("Enter data properly");
        }

     }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.resp_rate:
                respRate();
                break;

            case R.id.heart_rate:
                hearRateMonitor();
                break;
            case R.id.upload_signs:
                uploadSignn();
                break;
            case R.id.second_page:
                secondPage();
                break;
            case R.id.read_db:
                readDbPage();
                break;

            //.... etc
        }
    }
}
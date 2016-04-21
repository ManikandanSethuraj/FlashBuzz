package com.manikandansethuraj.flashbuzz;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    ToggleButton toggleButton;
    Camera camera;
    Camera.Parameters parameters;
    Boolean isFlash = false;
    Boolean isOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toggleButton = (ToggleButton) findViewById(R.id.toggleButton);

        if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
            camera = Camera.open();
            parameters = camera.getParameters();
            isFlash = true;

        }

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFlash){
                if (toggleButton.isChecked()){

                    isOn = toggleButton.isChecked();
                    Log.d("MainActivity ::::: ", String.valueOf(isOn));
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    camera.setParameters(parameters);
                    camera.startPreview();


                }else {

                    isOn = toggleButton.isChecked();
                    Log.d("MainActivity(else)::::", String.valueOf(isOn));
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    camera.setParameters(parameters);
                    camera.stopPreview();


                }
            }else {

                    AlertDialog.Builder localBuilder = new AlertDialog.Builder(MainActivity.this);
                    	localBuilder.setTitle("Error");
                    	localBuilder.setMessage("Your Device don't have Camera Flash Light !!");
                    	localBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();

                            }
                        });
                    	localBuilder.create();
                        localBuilder.show();


                }

            }

        });



    }

    @Override
    protected void onStop() {
        super.onStop();
     if (camera != null){
            camera.release();
            camera = null;
       }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (camera == null){
            camera = Camera.open();
            toggleButton.setChecked(false);
        }
    }
}

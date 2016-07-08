package com.example.krnx.padawan;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by inlab on 06/07/2016.
 */
public class SensorActivity extends BaseActivity {
    SensorManager sensorManager;
    LocationManager lm;
    LocationListener lis;
    List<Address> l;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Log.v("Sensor", String.valueOf(sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)));
        if (sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
            SensorEventListener sensorList = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    DecimalFormat d = new DecimalFormat("000");
                    Double val1 = Double.valueOf(d.format((double) event.values[0]));
                    Double val2 = Double.valueOf(d.format((double) event.values[1]));
                    Double val3 = Double.valueOf(d.format((double) event.values[2]));
                    Toast.makeText(SensorActivity.this, "Resultat: " + val1.toString() + "-" + val2.toString() + "-" + val3.toString(), Toast.LENGTH_LONG).show();
//                    t1.setText("x:" + val1.toString());
//                    t2.setText("y:" + val2.toString());
//                    t3.setText("z:" + val3.toString());
//                    float average = (values[0] + values[1] + values[2]) / 3;
//                    float modValue = Math.abs(average);
//                    mIndicator.setProgress((int) modValue);
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                    // TODO Auto-generated method stub
                }
            };
            sensorManager.registerListener(sensorList, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_FASTEST);
        } else {
            Toast.makeText(SensorActivity.this, "Sensor not available", Toast.LENGTH_SHORT).show();
        }

        lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // TODO Auto-generated method stub
                Geocoder gc = new Geocoder(getApplicationContext());
                try {
                    l = gc.getFromLocation(location.getLatitude(), location.getLongitude(), 5);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < l.size(); ++i) {
                    Log.v("LOG", l.get(i).getAddressLine(0).toString());
                    //TextView t = (TextView) findViewById(R.id.TV);
                    Toast.makeText(SensorActivity.this, ""+l.get(i).getAddressLine(0).toString(), Toast.LENGTH_LONG).show();
//                    if (i == 0) t.setText("");
//                    t.setText(t.getText() + "\n" + l.get(i).getAddressLine(0).toString());
                }
                Log.v("LOG", ((Double) location.getLatitude()).toString());
            }


            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            public void onProviderEnabled(String provider) {

            }

            public void onProviderDisabled(String provider) {

            }
        };
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, lis);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, lis);
    }
}
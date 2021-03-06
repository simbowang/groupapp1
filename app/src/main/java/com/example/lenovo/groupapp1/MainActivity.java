package com.example.lenovo.groupapp1;

/**
 * Created by lenovo on 2016/10/16.
 */

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;



public class MainActivity extends AppCompatActivity implements OnMapReadyCallback
{

    private MapFragment map;
    private GoogleMap myMap;

    private TextView latitude;
    private TextView longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //checkRationale();

        latitude = (TextView) findViewById(R.id.latitude);
        longitude = (TextView) findViewById(R.id.longitude);

        map = (MapFragment) getFragmentManager().findFragmentById(R.id.map_fragment);
        map.getMapAsync(this);

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        LocationListener listener = new LocationListener()
        {

            @Override
            public void onLocationChanged(android.location.Location loc)
            {
                double lat = loc.getLatitude();
                double lon = loc.getLongitude();
                addMapMarker(lat, lon);

                latitude.setText("" + loc.getLatitude());
                longitude.setText("" + loc.getLongitude());
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle)
            {
            }

            @Override
            public void onProviderEnabled(String s)
            {
            }

            @Override
            public void onProviderDisabled(String s)
            {
            }
        };


        /*
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED)
        {

            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED)
            {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                            , 10);
                }
            }
            return;
        }
        */


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            makeRequest();
            return;
        }
        locationManager.requestLocationUpdates("gps", 2000, 0, listener);



    } // End of onCreate






    void addMapMarker(Double latitude, Double longitude)
    {
        myMap.clear();
        LatLng loc = new LatLng(latitude,longitude);
        myMap.addMarker(new MarkerOptions().position(loc).title("current location"));
        myMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
    }







    @Override
    public void onMapReady(GoogleMap myMap)
    {
        this.myMap  = myMap;
    }



    public void checkRationale()
    {

        /*
        int permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        // Automatically calls onRequestPermissionResults
        */


        /*
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(permission != PackageManager.PERMISSION_GRANTED)
        {
            makeRequest();
        }
        */





        /*
        if (permission != PackageManager.PERMISSION_GRANTED)
        {

            if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION ))
            {
                Log.d("MainActivity", "Should show permission rationale");
                Toast.makeText(MainActivity.this, "Show rationale", Toast.LENGTH_SHORT);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("You must grant permission to access your location.").setTitle("Permission required");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        makeRequest();
                    }
                });
                builder.setNegativeButton("DENY ACCESS\n(currently pointless)", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        finish();
                    }
                });

                //builder.create().show();

            }
            else
            {
                makeRequest();
            }
        }
        */

    }



    protected void makeRequest()
    {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 2);
    }






    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(this, "Application will terminate. You did not grant permission. ", Toast.LENGTH_LONG).show();
            finish();
        }

        return;
    }


}
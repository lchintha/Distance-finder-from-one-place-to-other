package com.example.chint.distancefinder;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chint.distancefinder.Model.Details;
import com.example.chint.distancefinder.Model.Distance;
import com.example.chint.distancefinder.api.Client;
import com.example.chint.distancefinder.api.Service;
import android.location.LocationListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.resultMiles)
    TextView distance;
    @BindView(R.id.resultTime)
    TextView time;
    @BindView(R.id.searchArea)
    AutoCompleteTextView destination;
    LocationManager locationManager;
    LocationListener locationListener;
    double targetLat, targetLng, currentLat, currentLng;
    private final int ACCESS_COARSE_LOCATION_CODE = 1;

    ArrayAdapter a;
    HashSet<String> oldSet, newSet;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        oldSet = new HashSet<>();
        sp = getSharedPreferences("History", Context.MODE_PRIVATE);
        newSet = new HashSet<>();
        HashSet<String> stored = (HashSet<String>) sp.getStringSet("Hist", new HashSet<String>());
        ArrayList<String> list = new ArrayList<>(stored);

        a = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line);
        for(int i=0; i<list.size(); i++){
            a.add(list.get(i));
        }
        destination.setThreshold(2);
        destination.setAdapter(a);

        askPermission(Manifest.permission.ACCESS_COARSE_LOCATION,ACCESS_COARSE_LOCATION_CODE);
    }

    private void askPermission(String permission, int requestCode){
        if(ContextCompat.checkSelfPermission(this,permission)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }else{
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    currentLat = location.getLatitude();
                    currentLng = location.getLongitude();
                    locationManager.removeUpdates(locationListener);


                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            };
            
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }


    public void findDistance(View view) throws IOException {
        String target = destination.getText().toString().trim();

        Toast.makeText(MainActivity.this, Double.toString(currentLat)+" "+Double.toString(targetLat), Toast.LENGTH_SHORT).show();

        SharedPreferences.Editor editor = sp.edit();
        oldSet = (HashSet<String>) sp.getStringSet("His", new HashSet<String>());
        newSet.add(target);
        newSet.addAll(oldSet);

        editor.putStringSet("Hist", newSet);
        editor.commit();

        distance.setText("Distance Between Your Current Location and "+target+" is ");
        time.setText("To reach, It will take ");

        Geocoder geo = new Geocoder(this);
        List<Address> list = geo.getFromLocationName(target, 1);
        Address location = list.get(0);
        targetLat = location.getLatitude();
        targetLng = location.getLongitude();

        String origin = Double.toString(currentLat)+","+Double.toString(currentLng);
        String targ = Double.toString(targetLat)+","+Double.toString(targetLng);

        Client r = new Client();
        Service s = r.getClient().create(Service.class);

        retrofit2.Call<Details> call = s.getDetailsInfo(origin, targ);
        call.enqueue(new Callback<Details>() {
            @Override
            public void onResponse(retrofit2.Call<Details> call, Response<Details> response) {

                ArrayList<Distance> result = new ArrayList<>(response.body().getRoutes());

                if(result.size()>0) {
                    String miles = "<b>"+result.get(0).getLegs().get(0).getResult().getDistance()+"</b>";
                    String minutes = "<b>"+result.get(0).getLegs().get(0).getTime().getText()+"</b>";

                    distance.append(Html.fromHtml(miles));
                    time.append(Html.fromHtml(minutes));

                    distance.setVisibility(View.VISIBLE);
                    time.setVisibility(View.VISIBLE);
                }else{
                    Toast.makeText(MainActivity.this, "Taking too long.. Try Again", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(retrofit2.Call<Details> call, Throwable t) {

            }
        });

    }

}

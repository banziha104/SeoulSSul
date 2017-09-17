package com.veryworks.iyeongjun.seoulssul.Domain;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by iyeongjun on 2017. 9. 17..
 */

public class UserLocation {
    boolean isGPSEnabled;
    boolean isNetworkEnabled;
    boolean locationServiceEnabled;
    Context context;
    Geocoder geocoder;
    Retrofit retrofit;
    public UserLocation(Context context) {
        this.context = context;
        geocoder = new Geocoder(context);
    }

    public void getLocation() {

        LocationManager locationManager =
                (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if (!isNetworkEnabled && !isGPSEnabled)    {
            // cannot get location
            locationServiceEnabled = false;
        }
        if(isNetworkEnabled){
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER
                    , Const.GPS.GPS_MIN_LENGTH
                    , Const.GPS.GPS_MILE_SECOND
                    , locationListener);
            Log.d("LOCATION","NETWORK START");
        }
        if(isGPSEnabled){
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER
                    , Const.GPS.GPS_MIN_LENGTH
                    , Const.GPS.GPS_MILE_SECOND
                    , locationListener);
            Log.d("LOCATION","GPS START");
        }
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.d("LOCATION Confirm", location.getLatitude()+" / "+location.getLongitude()+" / " +location.getAccuracy() );
            String tempLocation = location.getLatitude()+","+location.getLongitude();
            reverseGeocoder(tempLocation);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
            Log.d("LOCATION",s);
        }

        @Override
        public void onProviderEnabled(String s) {
            Log.d("LOCATION",s);
        }

        @Override
        public void onProviderDisabled(String s) {
            Log.d("LOCATION",s);
        }
    };
    interface InterfaceForGeoCoding{
        @GET("/maps/api/geocode/json")
        Call<ResponseBody> reverseGeoCoding(
                @Query("latlng") String location,
                @Query("key") String key
                );
    }
    public void reverseGeocoder(String latlng){
        retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com")
                .build();
        InterfaceForGeoCoding interfaceGeocoding
                = retrofit.create(InterfaceForGeoCoding.class);

        Call<ResponseBody> result
                =interfaceGeocoding.reverseGeoCoding(latlng,Const.Key.GOOGLE_MAP_KEY);
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("LOCATION",response.body()+"");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}

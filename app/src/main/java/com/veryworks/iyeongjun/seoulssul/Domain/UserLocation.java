package com.veryworks.iyeongjun.seoulssul.Domain;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
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
    public static String currentUserDivision;
    public static Location currentUserLocation;
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
            currentUserLocation = location;
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
                @Query("key") String key,
                @Query("language") String lang
                );
    }
    public void reverseGeocoder(String latlng){
        retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com")
                .build();
        InterfaceForGeoCoding interfaceGeocoding
                = retrofit.create(InterfaceForGeoCoding.class);

        Call<ResponseBody> result
                =interfaceGeocoding.reverseGeoCoding(latlng,Const.Key.GOOGLE_MAP_KEY,"ko");
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    ResponseBody body = response.body();
                    String str = body.string();
                    getAdress(str);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    private void getAdress(String body){
        try {
            JSONObject obj = new JSONObject(body);
            currentUserDivision = setUserDivision(obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private String setUserDivision(JSONObject obj){
        String result = "";
        try {
            String addressCompnents = obj.getJSONArray("results")
                    .getJSONObject(1)
                    .getJSONArray("address_components")
                    .getJSONObject(2)
                    .getString("short_name");
            String subAddress = obj.getJSONArray("results")
                    .getJSONObject(1)
                    .getJSONArray("address_components")
                    .getJSONObject(3)
                    .getString("short_name");
            String format = obj.getJSONArray("results")
                    .getJSONObject(2)
                    .getString("formatted_address");
            MyDivision myDivision = isEndwithDivision(format);
            if (addressCompnents.endsWith("구")) result = addressCompnents;
            else if(subAddress.endsWith("구")) result = subAddress;
            else if(myDivision.isEndwithGu()) result = myDivision.getDivision();
            else result = "failed";

            Log.d("LOCATION RESULT", "RESULT : " + result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
    private MyDivision isEndwithDivision(String str){
        MyDivision myDivision = new MyDivision();
        String[] strArr =str.split(" ");
        for(String arr : strArr){
            if(arr.endsWith("구")){
                myDivision.setDivision(arr);
                myDivision.setEndwithGu(true);
            }
        }
        return myDivision;
    }

    class MyDivision{
        private boolean isEndwithGu = false;
        private String division;

        public boolean isEndwithGu() {
            return isEndwithGu;
        }

        public void setEndwithGu(boolean endwithGu) {
            isEndwithGu = endwithGu;
        }

        public String getDivision() {
            return division;
        }

        public void setDivision(String division) {
            this.division = division;
        }
    }
}

package com.veryworks.iyeongjun.seoulssul.Domain;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * Created by iyeongjun on 2017. 9. 17..
 */

public class UserLocation {
    public static Location user_location;

    public static void getLocation(Context context) {
        LocationManager locationManager =
                (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER
                    , Const.GPS.GPS_MIN_LENGTH
                    , Const.GPS.GPS_MILE_SECOND
                    , locationListener);
        }
    }

    private static LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            user_location = location;
            Log.d("LOCATION Confirm",user_location.getAltitude()+"/"+user_location.getLongitude()+"");
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
}

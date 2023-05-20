package com.example.egar_admin.controllers;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.example.egar_admin.controllers.AppController;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationUtilsController {

    private static LocationUtilsController instance;

    private LocationManager locationManager;
    private Geocoder geocoder;
    private LocationListener locationListener;

    private LocationUtilsController(LocationManager locationManager, Geocoder geocoder) {
        this.locationManager = locationManager;
        this.geocoder = geocoder;
    }

    public static synchronized LocationUtilsController getInstance(LocationManager locationManager, Geocoder geocoder) {
        if (instance == null) {
            instance = new LocationUtilsController(locationManager, geocoder);
        }
        return instance;
    }

    public void getCurrentLocation() {
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                // يتم استدعاء هذه الدالة عند تغيير الموقع الحالي
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                // استخدم المعلومات الجغرافية للقيام بأي عمليات إضافية
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {

            }
        };

        // تأكد من إضافة الإذن المناسب في ملف Manifest
        if (ActivityCompat.checkSelfPermission(AppController.getInstance().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AppController.getInstance().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    public String getNearestCity(double latitude, double longitude) {
        String nearestCity = "";

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                nearestCity = address.getLocality();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return nearestCity;
    }

    public void stopLocationUpdates() {
        locationManager.removeUpdates(locationListener);
    }
}

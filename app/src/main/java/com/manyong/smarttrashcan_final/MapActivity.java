package com.manyong.smarttrashcan_final;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    dbHelper helper;
    SQLiteDatabase sql_db;
    private GoogleMap mMap;
//    SupportMapFragment mapFragment;
    private MapFragment mapFragment;
    private FragmentManager fragmentManager;
    LocationManager manager;
    GPSListener gpslistener;
    double clat, clon;
    float[] distance = new float[1]; // distance 배열의 크기가 1이면 0번에 거리가, 2면 1번에, 3이상이면 2번에 거리 값이 들어갑니다.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        helper = new dbHelper(this);
        try {
            sql_db = helper.getWritableDatabase();
        } catch (SQLiteException e) {
            sql_db = helper.getReadableDatabase();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        fragmentManager = getFragmentManager();
        mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        startLocationService();


    }

    private void startLocationService() {
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false, network_enabled = false;
        gpslistener = new GPSListener();
        long minTime = 5000;
        float minDistance = 1;

        try {
            gps_enabled = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }
        try {
            network_enabled = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        //don't start listeners if no provider is enabled
        if (!gps_enabled && !network_enabled) { /* 둘다 사용 못할 경우*/
            Toast.makeText(MapActivity.this, "GPS를 켜주세요", Toast.LENGTH_SHORT).show();
        }
        if (gps_enabled) {
            try {
                manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, gpslistener);
            } catch (SecurityException e) {
            }
        }
        if (network_enabled) {
            try {
                manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, gpslistener);
            } catch (SecurityException e) {
            }
        }
    }

    private class GPSListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            double lat = location.getLatitude();
            double lon = location.getLongitude();
            clat = lat;
            clon = lon;
            Toast.makeText(MapActivity.this, "위치 : " + lat + " " + lon, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    }

  /*  private void showCurrentLocation(Double lat, Double lon) {
        LatLng curPoint = new LatLng(lat, lon);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));
    }*/

    @Override
    public void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
        }
        mapFragment.onResume();
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
        }
        mapFragment.onPause();
        manager.removeUpdates(gpslistener);
        mMap.setMyLocationEnabled(true);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMyLocationEnabled(true);
        // Add a marker in Sydney and move the camera
        LatLng hoseo = new LatLng(36.736283, 127.074431);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hoseo, 17));

        MarkerOptions marker1 = new MarkerOptions().position(new LatLng(36.737425, 127.075984)).title("장영실관").icon(BitmapDescriptorFactory.fromResource(R.drawable.mappin));
        MarkerOptions marker2 = new MarkerOptions().position(new LatLng(36.737680, 127.074972)).title("대학본부").icon(BitmapDescriptorFactory.fromResource(R.drawable.mappin));
        MarkerOptions marker3 = new MarkerOptions().position(new LatLng(36.737574, 127.072892)).title("체육관").icon(BitmapDescriptorFactory.fromResource(R.drawable.mappin));
        MarkerOptions marker4 = new MarkerOptions().position(new LatLng(36.737128, 127.073495)).title("제2공학관").icon(BitmapDescriptorFactory.fromResource(R.drawable.mappin));
        MarkerOptions marker5 = new MarkerOptions().position(new LatLng(36.736600, 127.074849)).title("제1공학관").icon(BitmapDescriptorFactory.fromResource(R.drawable.mappin));
        MarkerOptions marker6 = new MarkerOptions().position(new LatLng(36.735977, 127.074716)).title("강석규교육관").icon(BitmapDescriptorFactory.fromResource(R.drawable.mappin));
        MarkerOptions marker7 = new MarkerOptions().position(new LatLng(36.736247, 127.075738)).title("우체국근처").icon(BitmapDescriptorFactory.fromResource(R.drawable.mappin));
        MarkerOptions marker8 = new MarkerOptions().position(new LatLng(36.735187, 127.074256)).title("조형관").icon(BitmapDescriptorFactory.fromResource(R.drawable.mappin));
        MarkerOptions marker9 = new MarkerOptions().position(new LatLng(36.735169, 127.075037)).title("예술관").icon(BitmapDescriptorFactory.fromResource(R.drawable.mappin));
        MarkerOptions marker10 = new MarkerOptions().position(new LatLng(36.736227, 127.072320)).title("자연대").icon(BitmapDescriptorFactory.fromResource(R.drawable.mappin));
        MarkerOptions marker11 = new MarkerOptions().position(new LatLng(36.739448041025646, 127.07539088909981)).title("세출호").icon(BitmapDescriptorFactory.fromResource(R.drawable.mappin));
        mMap.addMarker(marker1);
        mMap.addMarker(marker2);
        mMap.addMarker(marker3);
        mMap.addMarker(marker4);
        mMap.addMarker(marker5);
        mMap.addMarker(marker6);
        mMap.addMarker(marker7);
        mMap.addMarker(marker8);
        mMap.addMarker(marker9);
        mMap.addMarker(marker10);
        mMap.addMarker(marker11);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }


        // 지도에 아이콘 클릭했을시 이벤트 주는거
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker) {
                String tname = "";
                String ttime = "";
                Cursor cursor;

                double speed= 4;
                double km = 0;
                int Htime = 0;
                double Mtime = 0;

                cursor = sql_db.rawQuery("SELECT tname, ttime FROM trash WHERE tname ='" + marker.getTitle() + "';", null);
                while (cursor.moveToNext()) {
                    if (cursor != null) {
                        tname = cursor.getString(0);
                        ttime = cursor.getString(1);
                    }
                }
                //예상 시간
                km = Math.floor(distance[0]) / 1000;
                if((Htime = (int)km / (int)speed) > 60){
                    Htime += Htime;
                }
                if((Mtime = ((km / speed) - Htime) * 100) > 60){
                    Mtime += Mtime;
                }
                // 거리 계산
                Location.distanceBetween(clat, clon, marker.getPosition().latitude, marker.getPosition().longitude, distance);

                // 출력
                String text = tname + "\n" +
                        "\n거리 = " + Math.round(distance[0]*10d)/10d + "m" +
                        "\n청소 날짜 = " + ttime +
                        "\n예상 시간 = " + Htime + "시간" + (int)Mtime + "분";

                AlertDialog.Builder alert = new AlertDialog.Builder(MapActivity.this);
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();     //닫기
                    }
                });
                alert.setMessage(text);
                alert.show();
                return false;
            }
        });

    }

}

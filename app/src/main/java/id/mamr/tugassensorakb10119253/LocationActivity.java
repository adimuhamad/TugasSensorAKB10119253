package id.mamr.tugassensorakb10119253;

// NIM : 10119253
// NAMA : Mochamad Adi Maulia Rahman
// KELAS : IF-7

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import id.mamr.tugassensorakb10119253.databinding.ActivityLocationBinding;

public class LocationActivity extends FragmentActivity {

    private ActivityLocationBinding binding;
    private SupportMapFragment mapFragment;
    private FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        client = LocationServices.getFusedLocationProviderClient(this);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        getCurrentLocation();
    }

        @SuppressLint("NonConstantResourceId")
        private void getCurrentLocation() {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(LocationActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
            }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_location);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_profile:
                    startActivity(new Intent(getApplicationContext()
                            , MainActivity.class));
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                case R.id.nav_location:
                    return true;
                case R.id.nav_about:
                    startActivity(new Intent(getApplicationContext()
                            , AboutActivity.class));
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
            }
            return false;
        });

            Task<Location> task = client.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location != null){
                        mapFragment.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(@NonNull GoogleMap googleMap) {
                                LatLng lokasi = new LatLng(location.getLatitude(),location.getLongitude());
                                LatLng rumah = new LatLng(-6.821522948916411, 107.3910009967293);
                                LatLng warungemak = new LatLng(-6.822582909906176, 107.38894642473112);
                                LatLng warungayu = new LatLng(-6.819394090185485, 107.39561332609071);
                                LatLng warungani = new LatLng(-6.822569106304825, 107.38932780646623);
                                LatLng rmmandiri = new LatLng(-6.821661426547381, 107.39910126758153);
                                LatLng rmmuara = new LatLng(-6.820781457027698, 107.39461209083017);

                                MarkerOptions mlokasi = new MarkerOptions().position(lokasi).title("Lokasi Saat Ini");
                                MarkerOptions mrumah = new MarkerOptions().position(rumah).title("Rumah Adi (10119253)");
                                MarkerOptions mwarungemak = new MarkerOptions().position(warungemak).title("Warung Emak");
                                MarkerOptions mwarungayu = new MarkerOptions().position(warungayu).title("Warung Ayu");
                                MarkerOptions mwarungani = new MarkerOptions().position(warungani).title("Waroeng Kopi & Kuliner Teh Ani");
                                MarkerOptions mrmmandiri = new MarkerOptions().position(rmmandiri).title("Rumah Makan Mandiri");
                                MarkerOptions mrmmuara = new MarkerOptions().position(rmmuara).title("Rumah Makan Muara Duo");

                                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lokasi,17));
                                googleMap.addMarker(mlokasi);
                                googleMap.addMarker(mrumah);
                                googleMap.addMarker(mwarungemak);
                                googleMap.addMarker(mwarungayu);
                                googleMap.addMarker(mwarungani);
                                googleMap.addMarker(mrmmandiri);
                                googleMap.addMarker(mrmmuara);
                            }
                        });
                    }
                }
            });
        }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LocationActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
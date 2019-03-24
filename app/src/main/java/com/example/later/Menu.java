package com.example.later;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class Menu extends AppCompatActivity {

    private Button start_Btn;
    private Button flag_Btn;
    private TextView coordinates_TextView;

    private FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        requestPermission();

        client = LocationServices.getFusedLocationProviderClient(this);

        start_Btn = findViewById(R.id.start_Btn);
        flag_Btn = findViewById(R.id.flag_Btn);
        start_Btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(Menu.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {

                    return;
                }
                client.getLastLocation().addOnSuccessListener(Menu.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location != null){
                            coordinates_TextView = findViewById(R.id.coorTextView);

                            double Latitude = location.getLatitude();
                            double Longitude = location.getLongitude();
                            coordinates_TextView.setText(Latitude + " " + Longitude);
                            if(availableCoordinates(Latitude, Longitude)){
                                flag_Btn.setTextColor(Color.GREEN);
                            }else{
                                flag_Btn.setTextColor(Color.RED);
                            }


                        }
                    }
                });
            }
        });
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);

    }

    private boolean availableCoordinates(double latit, double longit) {
        double North = 31.264972441750654; // latitude
        double South = 31.260913230180165; // latitude
        double East = 34.80587469500858; // longitude
        double West = 34.798327688240306; // longitude'

        if(North > latit && South < latit && East > longit && West < longit)
            return true;
        return false;

    }
}

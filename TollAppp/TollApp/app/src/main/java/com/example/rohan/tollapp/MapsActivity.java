package com.example.rohan.tollapp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, DirectionFinderListener {

   // public String mb;
    public GoogleMap mMap;
    String selected;
    private Button btnFindPath, calculate, payment;
    private EditText etOrigin;
    private EditText etDestination;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;
    public TextView tv1, tolls_selected;
    private Dialog d;
    String Vehicle_No,Journey,mb;

    String Jney, Vehi;
    String tolls;

    int toll = 0;
    int flag = 0;
    int fees1 = 0, fees2 = 0, fees3 = 0, fees4 = 0, fees5 = 0, fees6 = 0, fees7 = 0, fees8 = 0, fees9 = 0, fees10 = 0, fees11 = 0, fees12 = 0, fees13 = 0, fees14 = 0, fees15 = 0, fees16 = 0, fees17 = 0, fees18 = 0, fees19 = 0, fees20 = 0, fees21 = 0, fees22 = 0, fees23 = 0, fees24 = 0, fees25 = 0, fees26 = 0, fees27 = 0, fees28 = 0, fees29 = 0, fees30 = 0, fees31 = 0, fees32 = 0, fees33 = 0, fees34 = 0, fees35 = 0, fees36 = 0, fees37 = 0, fees38 = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        selected=new String();
        Bundle b=getIntent().getExtras();
        mb=b.getString("Mobile");
        Jney=b.getString("Journey");
        Vehi=b.getString("Vehicle");
        Vehicle_No=b.getString("Vehicle_No");
        Toast.makeText(this, ""+mb+" "+Jney+" "+Vehi+" "+Vehicle_No, Toast.LENGTH_SHORT).show();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        tolls = new String();
        tolls_selected = (TextView) findViewById(R.id.tolls_selected);

//        calculate= (Button)findViewById(R.id.btnCalculate);
//        payment=(Button)findViewById(R.id.Payment);


//        calculate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                final Dialog d= new Dialog(MapsActivity.this);
//                d.setContentView(R.layout.calcdialog);
//
//                payment.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v)
//                    {
//                        Toast.makeText(MapsActivity.this, "Hurrayyy....!!!!!", Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//                d.show();
//
//            }
//        });
       /* Bundle info = getIntent().getExtras();
        Jney = info.getString("Journey");
        Vehi = info.getString("Vehicle");*/


        Toast.makeText(this, Jney + " Journey in " + Vehi, Toast.LENGTH_SHORT).show();

        btnFindPath = (Button) findViewById(R.id.btnFindPath);
        etOrigin = (EditText) findViewById(R.id.etOrigin);
        etDestination = (EditText) findViewById(R.id.etDestination);
//        tv1=(TextView)findViewById(R.id.Total);
        btnFindPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
            }
        });
    }

    private void sendRequest() {
        String origin = etOrigin.getText().toString();
        String destination = etDestination.getText().toString();
        if (origin.isEmpty()) {
            Toast.makeText(this, "Enter origin", Toast.LENGTH_SHORT).show();
            return;
        }
        if (destination.isEmpty()) {
            Toast.makeText(this, "Enter destination", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            new DirectionFinder(this, origin, destination).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng aurangabad = new LatLng(19.8762, 75.3433);
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(pune));
        float zoomLevel = 7; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(aurangabad, zoomLevel));

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

//            int fees=0;


            @Override
            public boolean onMarkerClick(Marker mark) {
                MarkerOptions mo=new MarkerOptions();
                switch (mark.getTitle()) {


                    case "Moshi": {
                        if (flag == 0) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(18.686770, 73.846658)).title("Moshi").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                            flag = 1;
                            tolls = tolls + " " + mark.getTitle();
                            switch (Vehi) {
                                case "Car": {
                                    switch (Jney) {
                                        case "Single":
                                            fees1 = 10;
                                            break;

                                        case "Return":
                                            fees1 = 38;
                                            break;

                                        case "Monthly Pass":
                                            fees1 = 750;
                                            break;
                                    }
                                    break;
                                }
                                case "LCV": {
                                    switch (Jney) {
                                        case "Single":
                                            fees1 = 43;
                                            break;

                                        case "Return":
                                            fees1 = 65;
                                            break;

                                        case "Monthly Pass":
                                            fees1 = 1290;
                                            break;
                                    }
                                    break;
                                }

                                case "Bus": {
                                    switch (Jney) {
                                        case "Single":
                                            fees1 = 85;
                                            break;

                                        case "Return":
                                            fees1 = 128;
                                            break;

                                        case "Monthly Pass":
                                            fees1 = 2550;
                                            break;
                                    }
                                    break;
                                }

                                case "Three": {
                                    switch (Jney) {
                                        case "Single":
                                            fees1 = 182;
                                            break;

                                        case "Return":
                                            fees1 = 273;
                                            break;

                                        case "Monthly Pass":
                                            fees1 = 5460;
                                            break;
                                    }
                                    break;
                                }
                            }
                        } else if (flag == 1) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(18.686770, 73.846658)).title("Moshi").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            flag = 0;
                            toll = toll - fees1;
                            fees1 = 0;
                        }
                        break;
                    }

                    case "Chandloi": {
                        if (flag == 0) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(18.837905, 73.879984)).title("Chandloi").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                            tolls = tolls + "\n" + mark.getTitle();
                            flag = 1;
                            switch (Vehi) {
                                case "Car": {
                                    switch (Jney) {
                                        case "Single":
                                            fees2 = 25;
                                            break;

                                        case "Return":
                                            fees2 = 38;
                                            break;

                                        case "Monthly Pass":
                                            fees2 = 750;
                                            break;
                                    }
                                    break;
                                }
                                case "LCV": {
                                    switch (Jney) {
                                        case "Single":
                                            fees2 = 43;
                                            break;

                                        case "Return":
                                            fees2 = 65;
                                            break;

                                        case "Monthly Pass":
                                            fees2 = 1290;
                                            break;
                                    }
                                    break;
                                }
                                case "Bus": {
                                    switch (Jney) {
                                        case "Single":
                                            fees2 = 85;
                                            break;

                                        case "Return":
                                            fees2 = 128;
                                            break;

                                        case "Monthly Pass":
                                            fees2 = 2550;
                                            break;
                                    }
                                    break;
                                }
                                case "Three": {
                                    switch (Jney) {
                                        case "Single":
                                            fees2 = 182;
                                            break;

                                        case "Return":
                                            fees2 = 273;
                                            break;

                                        case "Monthly Pass":
                                            fees2 = 5460;
                                            break;
                                    }
                                    break;
                                }


                            }

                        } else if (flag == 1) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(18.837905, 73.879984)).title("Chandloi").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            flag = 0;
                            toll = toll - fees2;
                            fees2 = 0;

                        }

                        break;
                    }

                    case "Khedshivapur": {
                        if (flag == 0) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(18.331367, 73.852710)).title("Khedshivapur").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                            flag = 1;
                            switch (Vehi) {
                                case "Car": {
                                    switch (Jney) {
                                        case "Single":
                                            fees3 = 10;
                                            break;

                                        case "Return":
                                            fees3 = 125;
                                            break;

                                        case "Monthly Pass":
                                            fees3 = 2740;
                                            break;
                                    }
                                    break;
                                }
                                case "LCV": {
                                    switch (Jney) {
                                        case "Single":
                                            fees3 = 135;
                                            break;

                                        case "Return":
                                            fees3 = 200;
                                            break;

                                        case "Monthly Pass":
                                            fees3 = 4430;
                                            break;
                                    }
                                    break;
                                }
                                case "Bus": {
                                    switch (Jney) {
                                        case "Single":
                                            fees3 = 280;
                                            break;

                                        case "Return":
                                            fees3 = 420;
                                            break;

                                        case "Monthly Pass":
                                            fees3 = 9280;
                                            break;
                                    }
                                    break;
                                }
                                case "Three": {
                                    switch (Jney) {
                                        case "Single":
                                            fees3 = 435;
                                            break;

                                        case "Return":
                                            fees3 = 655;
                                            break;

                                        case "Monthly Pass":
                                            fees3 = 14550;
                                            break;
                                    }
                                    break;
                                }

                            }
                        } else if (flag == 1) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(18.331367, 73.852710)).title("Khedshivapur").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            flag = 0;
                            toll = toll - fees3;
                            fees3 = 0;
                        }

                        break;
                    }


                    case "Anewadi": {

                        if (flag == 0) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(17.809379, 73.971840)).title("Anewadi").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                            flag = 1;
                            switch (Vehi) {
                                case "Car": {
                                    switch (Jney) {
                                        case "Single":
                                            fees4 = 55;
                                            break;

                                        case "Return":
                                            fees4 = 85;
                                            break;

                                        case "Monthly Pass":
                                            fees4 = 1905;
                                            break;
                                    }
                                    break;
                                }
                                case "LCV": {
                                    switch (Jney) {
                                        case "Single":
                                            fees4 = 90;
                                            break;

                                        case "Return":
                                            fees4 = 140;
                                            break;

                                        case "Monthly Pass":
                                            fees4 = 3075;
                                            break;
                                    }
                                    break;
                                }
                                case "Bus": {
                                    switch (Jney) {
                                        case "Single":
                                            fees4 = 195;
                                            break;

                                        case "Return":
                                            fees4 = 290;
                                            break;

                                        case "Monthly Pass":
                                            fees4 = 6440;
                                            break;
                                    }
                                    break;
                                }
                                case "Three": {
                                    switch (Jney) {
                                        case "Single":
                                            fees4 = 435;
                                            break;

                                        case "Return":
                                            fees4 = 655;
                                            break;

                                        case "Monthly Pass":
                                            fees4 = 14550;
                                            break;
                                    }
                                    break;
                                }
                            }
                        } else if (flag == 1) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(17.809379, 73.971840)).title("Anewadi").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            flag = 0;
                            toll = toll - fees4;
                            fees4 = 0;
                        }

                        break;
                    }

                    case "Tasawade": {

                        if (flag == 0) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(17.365755, 74.123068)).title("Tasawade").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            flag = 1;
                            switch (Vehi) {
                                case "Car": {
                                    switch (Jney) {
                                        case "Single":
                                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                                            fees5 = 70;
                                            break;

                                        case "Return":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees5 = 0;
                                            break;

                                        case "Monthly Pass":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees5 = 0;
                                            break;
                                    }
                                    break;
                                }
                                case "LCV": {
                                    switch (Jney) {
                                        case "Single":
                                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                                            fees5 = 120;
                                            break;

                                        case "Return":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees5 = 0;
                                            break;

                                        case "Monthly Pass":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees5 = 0;
                                            break;
                                    }
                                    break;
                                }
                                case "Bus": {
                                    switch (Jney) {
                                        case "Single":
                                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                                            fees5 = 235;
                                            break;

                                        case "Return":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees5 = 0;
                                            break;

                                        case "Monthly Pass":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees5 = 0;
                                            break;
                                    }
                                    break;
                                }
                                case "Three": {
                                    switch (Jney) {
                                        case "Single":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees5 = 0;
                                            break;

                                        case "Return":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees5 = 0;
                                            break;

                                        case "Monthly Pass":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees5 = 0;
                                            break;
                                    }
                                    break;
                                }
                            }
                        } else if (flag == 1) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(17.365755, 74.123068)).title("Tasawade").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            flag = 0;
                            toll = toll - fees5;
                            fees5 = 0;
                        }

                        break;
                    }

                    case "Kini": {


                        if (flag == 0) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(16.876691, 74.298431)).title("Kini").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            flag = 1;
                            switch (Vehi) {
                                case "Car": {
                                    switch (Jney) {
                                        case "Single":
                                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                                            fees6 = 70;
                                            break;

                                        case "Return":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees6 = 0;
                                            break;

                                        case "Monthly Pass":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees6 = 0;
                                            break;
                                    }
                                    break;
                                }
                                case "LCV": {
                                    switch (Jney) {
                                        case "Single":
                                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                                            fees6 = 120;
                                            break;

                                        case "Return":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees6 = 0;
                                            break;

                                        case "Monthly Pass":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees6 = 0;
                                            break;
                                    }
                                    break;
                                }
                                case "Bus": {
                                    switch (Jney) {
                                        case "Single":
                                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                                            fees6 = 235;
                                            break;

                                        case "Return":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees6 = 0;
                                            break;

                                        case "Monthly Pass":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees6 = 0;
                                            break;
                                    }
                                    break;
                                }
                                case "Three": {
                                    switch (Jney) {
                                        case "Single":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees6 = 0;
                                            break;

                                        case "Return":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees6 = 0;
                                            break;

                                        case "Monthly Pass":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees6 = 0;
                                            break;
                                    }
                                    break;
                                }
                            }
                        } else if (flag == 1) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(16.876691, 74.298431)).title("Kini").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            flag = 0;
                            toll = toll - fees6;
                            fees6 = 0;
                        }

                        break;
                    }

                    case "Kharegaon": {


                        if (flag == 0) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(19.211991, 73.007039)).title("Kharegaon").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                            flag = 1;
                            switch (Vehi) {
                                case "Car": {
                                    switch (Jney) {
                                        case "Single":
                                            fees7 = 29;
                                            break;

                                        case "Return":
                                            fees7 = 44;
                                            break;

                                        case "Monthly Pass":
                                            fees7 = 870;
                                            break;
                                    }
                                    break;
                                }
                                case "LCV": {
                                    switch (Jney) {
                                        case "Single":
                                            fees7 = 51;
                                            break;

                                        case "Return":
                                            fees7 = 77;
                                            break;

                                        case "Monthly Pass":
                                            fees7 = 1530;
                                            break;
                                    }
                                    break;
                                }
                                case "Bus": {
                                    switch (Jney) {
                                        case "Single":
                                            fees7 = 103;
                                            break;

                                        case "Return":
                                            fees7 = 155;
                                            break;

                                        case "Monthly Pass":
                                            fees7 = 3090;
                                            break;
                                    }
                                    break;
                                }
                                case "Three": {
                                    switch (Jney) {
                                        case "Single":
                                            fees7 = 220;
                                            break;

                                        case "Return":
                                            fees7 = 330;
                                            break;

                                        case "Monthly Pass":
                                            fees7 = 6600;
                                            break;
                                    }
                                    break;
                                }
                            }
                        } else if (flag == 1) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(19.211991, 73.007039)).title("Kharegaon").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            flag = 0;
                            toll = toll - fees7;
                            fees7 = 0;
                        }

                        break;
                    }

                    case "Arjunali": {

                        if (flag == 0) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(19.361324, 73.166008)).title("Arjunali").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
//                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                            flag = 1;
                            switch (Vehi) {
                                case "Car": {
                                    switch (Jney) {
                                        case "Single":
                                            fees8 = 100;
                                            break;

                                        case "Return":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees8 = 0;
                                            break;

                                        case "Monthly Pass":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees8 = 0;
                                            break;
                                    }
                                    break;
                                }
                                case "LCV": {
                                    switch (Jney) {
                                        case "Single":
                                            fees8 = 170;
                                            break;

                                        case "Return":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees8 = 0;
                                            break;

                                        case "Monthly Pass":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees8 = 0;
                                            break;
                                    }
                                    break;
                                }
                                case "Bus": {
                                    switch (Jney) {
                                        case "Single":
                                            fees8 = 345;
                                            break;

                                        case "Return":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees8 = 0;
                                            break;

                                        case "Monthly Pass":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees8 = 0;
                                            break;
                                    }
                                    break;
                                }
                                case "Three": {
                                    switch (Jney) {
                                        case "Single":
                                            fees8 = 555;
                                            break;

                                        case "Return":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees8 = 0;
                                            break;

                                        case "Monthly Pass":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees8 = 0;
                                            break;
                                    }
                                    break;
                                }
                            }
                        } else if (flag == 1) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(19.361324, 73.166008)).title("Arjunali").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            flag = 0;
                            toll = toll - fees8;
                            fees8 = 0;
                        }

                        break;
                    }

                    case "Ghoti": {


                        if (flag == 0) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(19.708666, 73.614578)).title("Ghoti").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
//                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                            flag = 1;
                            switch (Vehi) {
                                case "Car": {
                                    switch (Jney) {
                                        case "Single":
                                            fees9 = 100;
                                            break;

                                        case "Return":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees9 = 0;
                                            break;

                                        case "Monthly Pass":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees9 = 0;
                                            break;
                                    }
                                    break;
                                }
                                case "LCV": {
                                    switch (Jney) {
                                        case "Single":
                                            fees9 = 170;
                                            break;

                                        case "Return":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees9 = 0;
                                            break;

                                        case "Monthly Pass":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees9 = 0;
                                            break;
                                    }
                                    break;
                                }
                                case "Bus": {
                                    switch (Jney) {
                                        case "Single":
                                            fees9 = 345;
                                            break;

                                        case "Return":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees9 = 0;
                                            break;

                                        case "Monthly Pass":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees9 = 0;
                                            break;
                                    }
                                    break;
                                }
                                case "Three": {
                                    switch (Jney) {
                                        case "Single":
                                            fees9 = 555;
                                            break;

                                        case "Return":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees9 = 0;
                                            break;

                                        case "Monthly Pass":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees9 = 0;
                                            break;
                                    }
                                    break;
                                }
                            }
                        } else if (flag == 1) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(19.708666, 73.614578)).title("Ghoti").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            flag = 0;
                            toll = toll - fees9;
                            fees9 = 0;
                        }

                        break;
                    }


                    case "Kawdipath": {


                        if (flag == 0) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(18.493111, 73.993094)).title("Kawdipath").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                            flag = 1;
                            switch (Vehi) {
                                case "Car": {
                                    switch (Jney) {
                                        case "Single":
                                            fees10 = 25;
                                            break;

                                        case "Return":
                                            fees10 = 38;
                                            break;

                                        case "Monthly Pass":
                                            fees10 = 750;
                                            break;
                                    }
                                    break;
                                }
                                case "LCV": {
                                    switch (Jney) {
                                        case "Single":
                                            fees10 = 25;
                                            break;

                                        case "Return":
                                            fees10 = 38;
                                            break;

                                        case "Monthly Pass":
                                            fees10 = 750;
                                            break;
                                    }
                                    break;
                                }
                                case "Bus": {
                                    switch (Jney) {
                                        case "Single":
                                            fees10 = 60;
                                            break;

                                        case "Return":
                                            fees10 = 90;
                                            break;

                                        case "Monthly Pass":
                                            fees10 = 1800;
                                            break;
                                    }
                                    break;
                                }
                                case "Three": {
                                    switch (Jney) {
                                        case "Single":
                                            fees10 = 60;
                                            break;

                                        case "Return":
                                            fees10 = 90;
                                            break;

                                        case "Monthly Pass":
                                            fees10 = 1800;
                                            break;
                                    }
                                    break;
                                }
                            }
                        } else if (flag == 1) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(18.493111, 73.993094)).title("Kawdipath").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            flag = 0;
                            toll = toll - fees10;
                            fees10 = 0;
                        }

                        break;
                    }

                    case "Kasurdi": {

                        if (flag == 0) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(18.483234, 74.234969)).title("Kasurdi").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                            flag = 1;
                            switch (Vehi) {
                                case "Car": {
                                    switch (Jney) {
                                        case "Single":
                                            fees11 = 25;
                                            break;

                                        case "Return":
                                            fees11 = 38;
                                            break;

                                        case "Monthly Pass":
                                            fees11 = 750;
                                            break;
                                    }
                                    break;
                                }
                                case "LCV": {
                                    switch (Jney) {
                                        case "Single":
                                            fees11 = 25;
                                            break;

                                        case "Return":
                                            fees11 = 38;
                                            break;

                                        case "Monthly Pass":
                                            fees11 = 750;
                                            break;
                                    }
                                    break;
                                }
                                case "Bus": {
                                    switch (Jney) {
                                        case "Single":
                                            fees11 = 60;
                                            break;

                                        case "Return":
                                            fees11 = 90;
                                            break;

                                        case "Monthly Pass":
                                            fees11 = 1800;
                                            break;
                                    }
                                    break;
                                }
                                case "Three": {
                                    switch (Jney) {
                                        case "Single":
                                            fees11 = 60;
                                            break;

                                        case "Return":
                                            fees11 = 90;
                                            break;

                                        case "Monthly Pass":
                                            fees11 = 1800;
                                            break;
                                    }
                                    break;
                                }
                            }
                        } else if (flag == 1) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(18.483234, 74.234969)).title("Kasurdi").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            flag = 0;
                            toll = toll - fees11;
                            fees11 = 0;
                        }

                        break;
                    }

                    case "Patas": {


                        if (flag == 0) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(18.424488, 74.466066)).title("patas").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                            flag = 1;
                            switch (Vehi) {
                                case "Car": {
                                    switch (Jney) {
                                        case "Single":
                                            fees12 = 65;
                                            break;

                                        case "Return":
                                            fees12 = 95;
                                            break;

                                        case "Monthly Pass":
                                            fees12 = 2125;
                                            break;
                                    }
                                    break;
                                }
                                case "LCV": {
                                    switch (Jney) {
                                        case "Single":
                                            fees12 = 105;
                                            break;

                                        case "Return":
                                            fees12 = 155;
                                            break;

                                        case "Monthly Pass":
                                            fees12 = 3430;
                                            break;
                                    }
                                    break;
                                }
                                case "Bus": {
                                    switch (Jney) {
                                        case "Single":
                                            fees12 = 215;
                                            break;

                                        case "Return":
                                            fees12 = 325;
                                            break;

                                        case "Monthly Pass":
                                            fees12 = 7185;
                                            break;
                                    }
                                    break;
                                }
                                case "Three": {
                                    switch (Jney) {
                                        case "Single":
                                            fees12 = 340;
                                            break;

                                        case "Return":
                                            fees12 = 505;
                                            break;

                                        case "Monthly Pass":
                                            fees12 = 11270;
                                            break;
                                    }
                                    break;
                                }
                            }
                        } else if (flag == 1) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(18.424488, 74.466066)).title("Patas").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            flag = 0;
                            toll = toll - fees12;
                            fees12 = 0;
                        }

                        break;
                    }

                    case "Varwade": {

                        if (flag == 0) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(17.957650, 75.331092)).title("Varwade").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                            flag = 1;
                            switch (Vehi) {
                                case "Car": {
                                    switch (Jney) {
                                        case "Single":
                                            fees13 = 50;
                                            break;

                                        case "Return":
                                            fees13 = 75;
                                            break;

                                        case "Monthly Pass":
                                            fees13 = 1720;
                                            break;
                                    }
                                    break;
                                }
                                case "LCV": {
                                    switch (Jney) {
                                        case "Single":
                                            fees13 = 85;
                                            break;

                                        case "Return":
                                            fees13 = 125;
                                            break;

                                        case "Monthly Pass":
                                            fees13 = 2780;
                                            break;
                                    }
                                    break;
                                }
                                case "Bus": {
                                    switch (Jney) {
                                        case "Single":
                                            fees13 = 175;
                                            break;

                                        case "Return":
                                            fees13 = 260;
                                            break;

                                        case "Monthly Pass":
                                            fees13 = 5825;
                                            break;
                                    }
                                    break;
                                }
                                case "Three": {
                                    switch (Jney) {
                                        case "Single":
                                            fees13 = 275;
                                            break;

                                        case "Return":
                                            fees13 = 410;
                                            break;

                                        case "Monthly Pass":
                                            fees13 = 9135;
                                            break;
                                    }
                                    break;
                                }
                            }
                        } else if (flag == 1) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(17.957650, 75.331092)).title("Varwade").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            flag = 0;
                            toll = toll - fees13;
                            fees13 = 0;
                        }

                        break;
                    }

                    case "Sawaleshwar": {


                        if (flag == 0) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(17.764688, 75.755915)).title("Sawaleshwar").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                            flag = 1;
                            switch (Vehi) {
                                case "Car": {
                                    switch (Jney) {
                                        case "Single":
                                            fees14 = 50;
                                            break;

                                        case "Return":
                                            fees14 = 75;
                                            break;

                                        case "Monthly Pass":
                                            fees14 = 1720;
                                            break;
                                    }
                                    break;
                                }
                                case "LCV": {
                                    switch (Jney) {
                                        case "Single":
                                            fees14 = 85;
                                            break;

                                        case "Return":
                                            fees14 = 125;
                                            break;

                                        case "Monthly Pass":
                                            fees14 = 2780;
                                            break;
                                    }
                                    break;
                                }
                                case "Bus": {
                                    switch (Jney) {
                                        case "Single":
                                            fees14 = 175;
                                            break;

                                        case "Return":
                                            fees14 = 260;
                                            break;

                                        case "Monthly Pass":
                                            fees14 = 5825;
                                            break;
                                    }
                                    break;
                                }
                                case "Three": {
                                    switch (Jney) {
                                        case "Single":
                                            fees14 = 275;
                                            break;

                                        case "Return":
                                            fees14 = 410;
                                            break;

                                        case "Monthly Pass":
                                            fees14 = 9135;
                                            break;
                                    }
                                    break;
                                }

                            }
                        } else if (flag == 1) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(17.764688, 75.755915)).title("Sawaleshwar").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            flag = 0;
                            toll = toll - fees14;
                            fees14 = 0;
                        }

                        break;
                    }

                    case "Wadakbal": {

                        if (flag == 0) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(17.534345, 75.880097)).title("Wadakbal").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                            flag = 1;
                            switch (Vehi) {
                                case "Car": {
                                    switch (Jney) {
                                        case "Single":
                                            fees15 = 5;
                                            break;

                                        case "Return":
                                            fees15 = 8;
                                            break;

                                        case "Monthly Pass":
                                            fees15 = 150;
                                            break;
                                    }
                                    break;
                                }
                                case "LCV": {
                                    switch (Jney) {
                                        case "Single":
                                            fees15 = 10;
                                            break;

                                        case "Return":
                                            fees15 = 15;
                                            break;

                                        case "Monthly Pass":
                                            fees15 = 300;
                                            break;
                                    }
                                    break;
                                }
                                case "Bus": {
                                    switch (Jney) {
                                        case "Single":
                                            fees15 = 15;
                                            break;

                                        case "Return":
                                            fees15 = 23;
                                            break;

                                        case "Monthly Pass":
                                            fees15 = 300;
                                            break;
                                    }
                                    break;
                                }
                                case "Three": {
                                    switch (Jney) {
                                        case "Single":
                                            fees15 = 15;
                                            break;

                                        case "Return":
                                            fees15 = 23;
                                            break;

                                        case "Monthly Pass":
                                            fees15 = 450;
                                            break;
                                    }
                                    break;
                                }
                            }
                        } else if (flag == 1) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(17.534345, 75.880097)).title("Wadakbal").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            flag = 0;
                            toll = toll - fees15;
                            fees15 = 0;
                        }

                        break;
                    }

                    case "Savitri": {

                        if (flag == 0) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(18.084995, 73.462346)).title("Savitri").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                            flag = 1;
                            switch (Vehi) {
                                case "Car": {
                                    switch (Jney) {
                                        case "Single":
                                            fees16 = 5;
                                            break;

                                        case "Return":
                                            fees16 = 8;
                                            break;

                                        case "Monthly Pass":
                                            fees16 = 150;
                                            break;
                                    }
                                    break;
                                }
                                case "LCV": {
                                    switch (Jney) {
                                        case "Single":
                                            fees16 = 5;
                                            break;

                                        case "Return":
                                            fees16 = 8;
                                            break;

                                        case "Monthly Pass":
                                            fees16 = 150;
                                            break;
                                    }
                                    break;
                                }

                                case "Bus": {
                                    switch (Jney) {
                                        case "Single":
                                            fees16 = 15;
                                            break;

                                        case "Return":
                                            fees16 = 23;
                                            break;

                                        case "Monthly Pass":
                                            fees16 = 450;
                                            break;
                                    }
                                    break;
                                }

                                case "Three": {
                                    switch (Jney) {
                                        case "Single":
                                            fees16 = 20;
                                            break;

                                        case "Return":
                                            fees16 = 30;
                                            break;

                                        case "Monthly Pass":
                                            fees16 = 600;
                                            break;
                                    }
                                    break;
                                }
                            }
                        } else if (flag == 1) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(18.084995, 73.462346)).title("Savitri").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            flag = 0;
                            toll = toll - fees16;
                            fees16 = 0;
                        }


                        break;
                    }

                    case "Kharpada": {

                        if (flag == 0) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(18.844797, 73.094545)).title("Kharpada").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                            flag = 1;
                            switch (Vehi) {
                                case "Car": {
                                    switch (Jney) {
                                        case "Single":
                                            fees17 = 10;
                                            break;

                                        case "Return":
                                            fees17 = 15;
                                            break;

                                        case "Monthly Pass":
                                            fees17 = 300;
                                            break;
                                    }
                                    break;
                                }
                                case "LCV": {
                                    switch (Jney) {
                                        case "Single":
                                            fees17 = 30;
                                            break;

                                        case "Return":
                                            fees17 = 45;
                                            break;

                                        case "Monthly Pass":
                                            fees17 = 900;
                                            break;
                                    }
                                    break;
                                }

                                case "Bus": {
                                    switch (Jney) {
                                        case "Single":
                                            fees17 = 30;
                                            break;

                                        case "Return":
                                            fees17 = 75;
                                            break;

                                        case "Monthly Pass":
                                            fees17 = 1500;
                                            break;
                                    }
                                    break;
                                }

                                case "Three": {
                                    switch (Jney) {
                                        case "Single":
                                            fees17 = 50;
                                            break;

                                        case "Return":
                                            fees17 = 75;
                                            break;

                                        case "Monthly Pass":
                                            fees17 = 1500;
                                            break;
                                    }
                                    break;
                                }
                            }
                        } else if (flag == 1) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(18.844797, 73.094545)).title("Kharpada").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            flag = 0;
                            toll = toll - fees17;
                            fees17 = 0;
                        }


                        break;
                    }

                    case "Khaniwade": {

                        if (flag == 0) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(19.518966, 72.916865)).title("Khaniwade").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                            flag = 1;
                            switch (Vehi) {
                                case "Car": {
                                    switch (Jney) {
                                        case "Single":
                                            fees18 = 65;
                                            break;

                                        case "Return":
                                            fees18 = 95;
                                            break;

                                        case "Monthly Pass":
                                            fees18 = 1905;
                                            break;
                                    }
                                    break;
                                }
                                case "LCV": {
                                    switch (Jney) {
                                        case "Single":
                                            fees18 = 110;
                                            break;

                                        case "Return":
                                            fees18 = 165;
                                            break;

                                        case "Monthly Pass":
                                            fees18 = 3330;
                                            break;
                                    }
                                    break;
                                }

                                case "Bus": {
                                    switch (Jney) {
                                        case "Single":
                                            fees18 = 220;
                                            break;

                                        case "Return":
                                            fees18 = 335;
                                            break;

                                        case "Monthly Pass":
                                            fees18 = 6665;
                                            break;
                                    }
                                    break;
                                }

                                case "Three": {
                                    switch (Jney) {
                                        case "Single":
                                            fees18 = 50;
                                            break;

                                        case "Return":
                                            fees18 = 75;
                                            break;

                                        case "Monthly Pass":
                                            fees18 = 1500;
                                            break;
                                    }
                                    break;
                                }
                            }
                        } else if (flag == 1) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(19.518966, 72.916865)).title("Khaniwade").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            flag = 0;
                            toll = toll - fees18;
                            fees18 = 0;
                        }


                        break;
                    }

                    case "Charoti": {
                        if (flag == 0) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(19.890574, 72.942598)).title("Charoti").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                            flag = 1;
                            switch (Vehi) {
                                case "Car": {
                                    switch (Jney) {
                                        case "Single":
                                            fees19 = 60;
                                            break;

                                        case "Return":
                                            fees19 = 85;
                                            break;

                                        case "Monthly Pass":
                                            fees19 = 1735;
                                            break;
                                    }
                                    break;
                                }
                                case "LCV": {
                                    switch (Jney) {
                                        case "Single":
                                            fees19 = 100;
                                            break;

                                        case "Return":
                                            fees19 = 150;
                                            break;

                                        case "Monthly Pass":
                                            fees19 = 3035;
                                            break;
                                    }
                                    break;
                                }

                                case "Bus": {
                                    switch (Jney) {
                                        case "Single":
                                            fees19 = 200;
                                            break;

                                        case "Return":
                                            fees19 = 305;
                                            break;

                                        case "Monthly Pass":
                                            fees19 = 6070;
                                            break;
                                    }
                                    break;
                                }

                                case "Three": {
                                    switch (Jney) {
                                        case "Single":
                                            fees19 = 320;
                                            break;

                                        case "Return":
                                            fees19 = 490;
                                            break;

                                        case "Monthly Pass":
                                            fees19 = 9755;
                                            break;
                                    }
                                    break;
                                }
                            }
                        } else if (flag == 1) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(19.890574, 72.942598)).title("Charoti").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            flag = 0;
                            toll = toll - fees19;
                            fees19 = 0;
                        }
                        break;
                    }


                    case "Baswant": {
                        if (flag == 0) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(20.141639, 73.976480)).title("Baswant").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                            flag = 1;
                            switch (Vehi) {
                                case "Car": {
                                    switch (Jney) {
                                        case "Single":
                                            fees20 = 125;
                                            break;

                                        case "Return":
                                            fees20 = 185;
                                            break;

                                        case "Monthly Pass":
                                            fees20 = 4120;
                                            break;
                                    }
                                    break;
                                }
                                case "LCV": {
                                    switch (Jney) {
                                        case "Single":
                                            fees20 = 200;
                                            break;

                                        case "Return":
                                            fees20 = 300;
                                            break;

                                        case "Monthly Pass":
                                            fees20 = 6660;
                                            break;
                                    }
                                    break;
                                }

                                case "Bus": {
                                    switch (Jney) {
                                        case "Single":
                                            fees20 = 420;
                                            break;

                                        case "Return":
                                            fees20 = 630;
                                            break;

                                        case "Monthly Pass":
                                            fees20 = 13950;
                                            break;
                                    }
                                    break;
                                }

                                case "Three": {
                                    switch (Jney) {
                                        case "Single":
                                            fees20 = 320;
                                            break;

                                        case "Return":
                                            fees20 = 490;
                                            break;

                                        case "Monthly Pass":
                                            fees20 = 9755;
                                            break;
                                    }
                                    break;
                                }
                            }
                        } else if (flag == 1) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(20.141639, 73.976480)).title("Baswant").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            flag = 0;
                            toll = toll - fees20;
                            fees20 = 0;
                        }
                        break;
                    }

                    case "Chandwad": {
                        if (flag == 0) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(20.325233, 74.210712)).title("Chandwad").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
//                            Toast.makeText(MapsActivity.this, ""+mark.getTitle(), Toast.LENGTH_SHORT).show();
                            flag = 1;
                            switch (Vehi) {
                                case "Car": {
                                    switch (Jney) {
                                        case "Single":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees20 = 120;
                                            break;

                                        case "Return":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees20 = 0;
                                            break;

                                        case "Monthly Pass":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees20 = 0;
                                            break;
                                    }
                                    break;
                                }
                                case "LCV": {
                                    switch (Jney) {
                                        case "Single":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees20 = 205;
                                            break;

                                        case "Return":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees20 = 0;
                                            break;

                                        case "Monthly Pass":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees20 = 0;
                                            break;
                                    }
                                    break;
                                }

                                case "Bus": {
                                    switch (Jney) {
                                        case "Single":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees20 = 415;
                                            break;

                                        case "Return":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees20 = 0;
                                            break;

                                        case "Monthly Pass":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees20 = 0;
                                            break;
                                    }
                                    break;
                                }

                                case "Three": {
                                    switch (Jney) {
                                        case "Single":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees20 = 665;
                                            break;

                                        case "Return":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees20 = 0;
                                            break;

                                        case "Monthly Pass":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees20 = 0;
                                            break;
                                    }
                                    break;
                                }
                            }
                        } else if (flag == 1) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(20.325233, 74.210712)).title("Chandwad").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            flag = 0;
                            toll = toll - fees20;
                            fees20 = 0;
                        }
                        break;
                    }

                    case "Laling (Dhule)": {
                        if (flag == 0) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(20.833106, 74.754127)).title("Laling (Dhule)").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
//                            Toast.makeText(MapsActivity.this, ""+mark.getTitle(), Toast.LENGTH_SHORT).show();
                            flag = 1;
                            switch (Vehi) {
                                case "Car": {
                                    switch (Jney) {
                                        case "Single":
                                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                                            fees21 = 120;
                                            break;

                                        case "Return":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees21 = 0;
                                            break;

                                        case "Monthly Pass":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees21 = 0;
                                            break;
                                    }
                                    break;
                                }
                                case "LCV": {
                                    switch (Jney) {
                                        case "Single":
                                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                                            fees21 = 205;
                                            break;

                                        case "Return":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees20 = 0;
                                            break;

                                        case "Monthly Pass":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees21 = 0;
                                            break;
                                    }
                                    break;
                                }

                                case "Bus": {
                                    switch (Jney) {
                                        case "Single":
                                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                                            fees21 = 415;
                                            break;

                                        case "Return":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees21 = 0;
                                            break;

                                        case "Monthly Pass":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees21 = 0;
                                            break;
                                    }
                                    break;
                                }

                                case "Three": {
                                    switch (Jney) {
                                        case "Single":
                                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                                            fees21 = 665;
                                            break;

                                        case "Return":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees21 = 0;
                                            break;

                                        case "Monthly Pass":
                                            Toast.makeText(MapsActivity.this, "This Option is not Available for this Toll", Toast.LENGTH_SHORT).show();
                                            fees21 = 0;
                                            break;
                                    }
                                    break;
                                }
                            }
                        } else if (flag == 1) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(20.833106, 74.754127)).title("Laling (Dhule)").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            flag = 0;
                            toll = toll - fees21;
                            fees21 = 0;
                        }
                        break;
                    }


                    case "Songir": {
                        if (flag == 0) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(21.051069, 74.785102)).title("Songir").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                            flag = 1;
                            switch (Vehi) {
                                case "Car": {
                                    switch (Jney) {
                                        case "Single":
                                            fees22 = 55;
                                            break;

                                        case "Return":
                                            fees22 = 85;
                                            break;

                                        case "Monthly Pass":
                                            fees22 = 1915;
                                            break;
                                    }
                                    break;
                                }
                                case "LCV": {
                                    switch (Jney) {
                                        case "Single":
                                            fees22 = 90;
                                            break;

                                        case "Return":
                                            fees22 = 135;
                                            break;

                                        case "Monthly Pass":
                                            fees22 = 3005;
                                            break;
                                    }
                                    break;
                                }

                                case "Bus": {
                                    switch (Jney) {
                                        case "Single":
                                            fees22 = 185;
                                            break;

                                        case "Return":
                                            fees22 = 280;
                                            break;

                                        case "Monthly Pass":
                                            fees22 = 6195;
                                            break;
                                    }
                                    break;
                                }

                                case "Three": {
                                    switch (Jney) {
                                        case "Single":
                                            fees22 = 285;
                                            break;

                                        case "Return":
                                            fees22 = 430;
                                            break;

                                        case "Monthly Pass":
                                            fees22 = 9540;
                                            break;
                                    }
                                    break;
                                }
                            }
                        } else if (flag == 1) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(21.051069, 74.785102)).title("Songir").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            flag = 0;
                            toll = toll - fees22;
                            fees22 = 0;
                        }
                        break;
                    }

                    case "Nardana": {
                        if (flag == 0) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(21.211815, 74.831540)).title("Nardana").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                            flag = 1;
                            switch (Vehi) {
                                case "Car": {
                                    switch (Jney) {
                                        case "Single":
                                            fees23 = 8;
                                            break;

                                        case "Return":
                                            fees23 = 12;
                                            break;

                                        case "Monthly Pass":
                                            fees23 = 240;
                                            break;
                                    }
                                    break;
                                }
                                case "LCV": {
                                    switch (Jney) {
                                        case "Single":
                                            fees23 = 8;
                                            break;

                                        case "Return":
                                            fees23 = 12;
                                            break;

                                        case "Monthly Pass":
                                            fees23 = 240;
                                            break;
                                    }
                                    break;
                                }

                                case "Bus": {
                                    switch (Jney) {
                                        case "Single":
                                            fees23 = 25;
                                            break;

                                        case "Return":
                                            fees23 = 38;
                                            break;

                                        case "Monthly Pass":
                                            fees23 = 750;
                                            break;
                                    }
                                    break;
                                }

                                case "Three": {
                                    switch (Jney) {
                                        case "Single":
                                            fees23 = 30;
                                            break;

                                        case "Return":
                                            fees23 = 45;
                                            break;

                                        case "Monthly Pass":
                                            fees23 = 900;
                                            break;
                                    }
                                    break;
                                }
                            }
                        } else if (flag == 1) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(21.211815, 74.831540)).title("Nardana").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            flag = 0;
                            toll = toll - fees23;
                            fees23 = 0;
                        }
                        break;
                    }

                    case "Shirpur": {
                        if (flag == 0) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(21.319701, 74.889354)).title("Shirpur").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                            flag = 1;
                            switch (Vehi) {
                                case "Car": {
                                    switch (Jney) {
                                        case "Single":
                                            fees24 = 90;
                                            break;

                                        case "Return":
                                            fees24 = 135;
                                            break;

                                        case "Monthly Pass":
                                            fees24 = 3015;
                                            break;
                                    }
                                    break;
                                }
                                case "LCV": {
                                    switch (Jney) {
                                        case "Single":
                                            fees24 = 140;
                                            break;

                                        case "Return":
                                            fees24 = 210;
                                            break;

                                        case "Monthly Pass":
                                            fees24 = 4700;
                                            break;
                                    }
                                    break;
                                }

                                case "Bus": {
                                    switch (Jney) {
                                        case "Single":
                                            fees24 = 290;
                                            break;

                                        case "Return":
                                            fees24 = 435;
                                            break;

                                        case "Monthly Pass":
                                            fees24 = 9635;
                                            break;
                                    }
                                    break;
                                }

                                case "Three": {
                                    switch (Jney) {
                                        case "Single":
                                            fees24 = 445;
                                            break;

                                        case "Return":
                                            fees24 = 665;
                                            break;

                                        case "Monthly Pass":
                                            fees24 = 14755;
                                            break;
                                    }
                                    break;
                                }
                            }
                        } else if (flag == 1) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(21.319701, 74.889354)).title("Shirpur").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            flag = 0;
                            toll = toll - fees24;
                            fees24 = 0;
                        }
                        break;
                    }

                    case "Nashirabad": {
                        if (flag == 0) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(21.013392, 75.670451)).title("Nashirabad").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                            flag = 1;
                            switch (Vehi) {
                                case "Car": {
                                    switch (Jney) {
                                        case "Single":
                                            fees25 = 10;
                                            break;

                                        case "Return":
                                            fees25 = 15;
                                            break;

                                        case "Monthly Pass":
                                            fees25 = 300;
                                            break;
                                    }
                                    break;
                                }
                                case "LCV": {
                                    switch (Jney) {
                                        case "Single":
                                            fees25 = 10;
                                            break;

                                        case "Return":
                                            fees25 = 15;
                                            break;

                                        case "Monthly Pass":
                                            fees25 = 300;
                                            break;
                                    }
                                    break;
                                }

                                case "Bus": {
                                    switch (Jney) {
                                        case "Single":
                                            fees25 = 30;
                                            break;

                                        case "Return":
                                            fees25 = 45;
                                            break;

                                        case "Monthly Pass":
                                            fees25 = 900;
                                            break;
                                    }
                                    break;
                                }

                                case "Three": {
                                    switch (Jney) {
                                        case "Single":
                                            fees25 = 40;
                                            break;

                                        case "Return":
                                            fees25 = 60;
                                            break;

                                        case "Monthly Pass":
                                            fees25 = 1200;
                                            break;
                                    }
                                    break;
                                }
                            }
                        } else if (flag == 1) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(21.013392, 75.670451)).title("Nashirabad").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            flag = 0;
                            toll = toll - fees25;
                            fees25 = 0;
                        }
                        break;
                    }


                    case "Fekri": {
                        if (flag == 0) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(21.040052, 75.829229)).title("Fekri").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                            flag = 1;
                            switch (Vehi) {
                                case "Car": {
                                    switch (Jney) {
                                        case "Single":
                                            fees26 = 10;
                                            break;

                                        case "Return":
                                            fees26 = 15;
                                            break;

                                        case "Monthly Pass":
                                            fees26 = 300;
                                            break;
                                    }
                                    break;
                                }
                                case "LCV": {
                                    switch (Jney) {
                                        case "Single":
                                            fees26 = 10;
                                            break;

                                        case "Return":
                                            fees26 = 15;
                                            break;

                                        case "Monthly Pass":
                                            fees26 = 300;
                                            break;
                                    }
                                    break;
                                }

                                case "Bus": {
                                    switch (Jney) {
                                        case "Single":
                                            fees26 = 35;
                                            break;

                                        case "Return":
                                            fees26 = 53;
                                            break;

                                        case "Monthly Pass":
                                            fees26 = 1050;
                                            break;
                                    }
                                    break;
                                }

                                case "Three": {
                                    switch (Jney) {
                                        case "Single":
                                            fees26 = 45;
                                            break;

                                        case "Return":
                                            fees26 = 68;
                                            break;

                                        case "Monthly Pass":
                                            fees26 = 1350;
                                            break;
                                    }
                                    break;
                                }
                            }
                        } else if (flag == 1) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(21.040052, 75.829229)).title("Fekri").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            flag = 0;
                            toll = toll - fees26;
                            fees26 = 0;
                        }
                        break;
                    }

                    case "Nandgaon Peth": {
                        if (flag == 0) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(20.950980, 77.788382)).title("Nandgaon Peth").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                            flag = 1;
                            switch (Vehi) {
                                case "Car": {
                                    switch (Jney) {
                                        case "Single":
                                            fees27 = 80;
                                            break;

                                        case "Return":
                                            fees27 = 120;
                                            break;

                                        case "Monthly Pass":
                                            fees27 = 2720;
                                            break;
                                    }
                                    break;
                                }
                                case "LCV": {
                                    switch (Jney) {
                                        case "Single":
                                            fees27 = 130;
                                            break;

                                        case "Return":
                                            fees27 = 190;
                                            break;

                                        case "Monthly Pass":
                                            fees27 = 4265;
                                            break;
                                    }
                                    break;
                                }

                                case "Bus": {
                                    switch (Jney) {
                                        case "Single":
                                            fees27 = 265;
                                            break;

                                        case "Return":
                                            fees27 = 395;
                                            break;

                                        case "Monthly Pass":
                                            fees27 = 8770;
                                            break;
                                    }
                                    break;
                                }

                                case "Three": {
                                    switch (Jney) {
                                        case "Single":
                                            fees27 = 405;
                                            break;

                                        case "Return":
                                            fees27 = 605;
                                            break;

                                        case "Monthly Pass":
                                            fees27 = 13490;
                                            break;
                                    }
                                    break;
                                }
                            }
                        } else if (flag == 1) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(20.950980, 77.788382)).title("Nandgaon Peth").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            flag = 0;
                            toll = toll - fees27;
                            fees27 = 0;
                        }
                        break;
                    }

                    case "Tiwasa": {
                        if (flag == 0) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(21.082875, 78.058584)).title("Tiwasa").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                            flag = 1;
                            switch (Vehi) {
                                case "Car": {
                                    switch (Jney) {
                                        case "Single":
                                            fees28 = 10;
                                            break;

                                        case "Return":
                                            fees28 = 15;
                                            break;

                                        case "Monthly Pass":
                                            fees28 = 300;
                                            break;
                                    }
                                    break;
                                }
                                case "LCV": {
                                    switch (Jney) {
                                        case "Single":
                                            fees28 = 10;
                                            break;

                                        case "Return":
                                            fees28 = 15;
                                            break;

                                        case "Monthly Pass":
                                            fees28 = 300;
                                            break;
                                    }
                                    break;
                                }

                                case "Bus": {
                                    switch (Jney) {
                                        case "Single":
                                            fees28 = 35;
                                            break;

                                        case "Return":
                                            fees28 = 52;
                                            break;

                                        case "Monthly Pass":
                                            fees28 = 1050;
                                            break;
                                    }
                                    break;
                                }

                                case "Three": {
                                    switch (Jney) {
                                        case "Single":
                                            fees28 = 35;
                                            break;

                                        case "Return":
                                            fees28 = 52;
                                            break;

                                        case "Monthly Pass":
                                            fees28 = 1050;
                                            break;
                                    }
                                    break;
                                }
                            }
                        } else if (flag == 1) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(21.082875, 78.058584)).title("Tiwasa").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            flag = 0;
                            toll = toll - fees28;
                            fees28 = 0;
                        }
                        break;
                    }

                    case "Karanja": {
                        if (flag == 0) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(21.157523, 78.388095)).title("Karanja").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                            flag = 1;
                            switch (Vehi) {
                                case "Car": {
                                    switch (Jney) {
                                        case "Single":
                                            fees29 = 50;
                                            break;

                                        case "Return":
                                            fees29 = 75;
                                            break;

                                        case "Monthly Pass":
                                            fees29 = 1495;
                                            break;
                                    }
                                    break;
                                }
                                case "LCV": {
                                    switch (Jney) {
                                        case "Single":
                                            fees29 = 85;
                                            break;

                                        case "Return":
                                            fees29 = 130;
                                            break;

                                        case "Monthly Pass":
                                            fees29 = 2620;
                                            break;
                                    }
                                    break;
                                }

                                case "Bus": {
                                    switch (Jney) {
                                        case "Single":
                                            fees29 = 175;
                                            break;

                                        case "Return":
                                            fees29 = 260;
                                            break;

                                        case "Monthly Pass":
                                            fees29 = 5240;
                                            break;
                                    }
                                    break;
                                }

                                case "Three": {
                                    switch (Jney) {
                                        case "Single":
                                            fees29 = 280;
                                            break;

                                        case "Return":
                                            fees29 = 420;
                                            break;

                                        case "Monthly Pass":
                                            fees29 = 8420;
                                            break;
                                    }
                                    break;
                                }
                            }
                        } else if (flag == 1) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(21.157523, 78.388095)).title("Karanja").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            flag = 0;
                            toll = toll - fees29;
                            fees29 = 0;
                        }
                        break;
                    }

                    case "Gondhkhairi": {
                        if (flag == 0) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(21.136904, 78.896468)).title("Gondhkhairi").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                            flag = 1;
                            switch (Vehi) {
                                case "Car": {
                                    switch (Jney) {
                                        case "Single":
                                            fees30 = 40;
                                            break;

                                        case "Return":
                                            fees30 = 60;
                                            break;

                                        case "Monthly Pass":
                                            fees30 = 1250;
                                            break;
                                    }
                                    break;
                                }
                                case "LCV": {
                                    switch (Jney) {
                                        case "Single":
                                            fees30 = 70;
                                            break;

                                        case "Return":
                                            fees30 = 105;
                                            break;

                                        case "Monthly Pass":
                                            fees30 = 2105;
                                            break;
                                    }
                                    break;
                                }

                                case "Bus": {
                                    switch (Jney) {
                                        case "Single":
                                            fees30 = 140;
                                            break;

                                        case "Return":
                                            fees30 = 210;
                                            break;

                                        case "Monthly Pass":
                                            fees30 = 4215;
                                            break;
                                    }
                                    break;
                                }

                                case "Three": {
                                    switch (Jney) {
                                        case "Single":
                                            fees30 = 225;
                                            break;

                                        case "Return":
                                            fees30 = 340;
                                            break;

                                        case "Monthly Pass":
                                            fees30 = 6770;
                                            break;
                                    }
                                    break;
                                }
                            }
                        } else if (flag == 1) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(21.136904, 78.896468)).title("Gondhkhairi").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            flag = 0;
                            toll = toll - fees30;
                            fees30 = 0;
                        }
                        break;
                    }

                    case "Pattansawangi": {
                        if (flag == 0) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(21.343762, 79.009051)).title("Pattansawangi").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                            flag = 1;
                            switch (Vehi) {
                                case "Car": {
                                    switch (Jney) {
                                        case "Single":
                                            fees31 = 75;
                                            break;

                                        case "Return":
                                            fees31 = 110;
                                            break;

                                        case "Monthly Pass":
                                            fees31 = 2430;
                                            break;
                                    }
                                    break;
                                }
                                case "LCV": {
                                    switch (Jney) {
                                        case "Single":
                                            fees31 = 120;
                                            break;

                                        case "Return":
                                            fees31 = 175;
                                            break;

                                        case "Monthly Pass":
                                            fees31 = 3925;
                                            break;
                                    }
                                    break;
                                }

                                case "Bus": {
                                    switch (Jney) {
                                        case "Single":
                                            fees31 = 245;
                                            break;

                                        case "Return":
                                            fees31 = 370;
                                            break;

                                        case "Monthly Pass":
                                            fees31 = 8225;
                                            break;
                                    }
                                    break;
                                }

                                case "Three": {
                                    switch (Jney) {
                                        case "Single":
                                            fees31 = 270;
                                            break;

                                        case "Return":
                                            fees31 = 405;
                                            break;

                                        case "Monthly Pass":
                                            fees31 = 8975;
                                            break;
                                    }
                                    break;
                                }
                            }
                        } else if (flag == 1) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(21.343762, 79.009051)).title("Pattansawangi").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            flag = 0;
                            toll = toll - fees31;
                            fees31 = 0;
                        }
                        break;
                    }

                    case "Mansar": {
                        if (flag == 0) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(21.382360, 79.253203)).title("Mansar").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                            flag = 1;
                            switch (Vehi) {
                                case "Car": {
                                    switch (Jney) {
                                        case "Single":
                                            fees32 = 95;
                                            break;

                                        case "Return":
                                            fees32 = 140;
                                            break;

                                        case "Monthly Pass":
                                            fees32 = 3145;
                                            break;
                                    }
                                    break;
                                }
                                case "LCV": {
                                    switch (Jney) {
                                        case "Single":
                                            fees32 = 145;
                                            break;

                                        case "Return":
                                            fees32 = 215;
                                            break;

                                        case "Monthly Pass":
                                            fees32 = 4810;
                                            break;
                                    }
                                    break;
                                }

                                case "Bus": {
                                    switch (Jney) {
                                        case "Single":
                                            fees32 = 290;
                                            break;

                                        case "Return":
                                            fees32 = 435;
                                            break;

                                        case "Monthly Pass":
                                            fees32 = 9665;
                                            break;
                                    }
                                    break;
                                }

                                case "Three": {
                                    switch (Jney) {
                                        case "Single":
                                            fees32 = 440;
                                            break;

                                        case "Return":
                                            fees32 = 660;
                                            break;

                                        case "Monthly Pass":
                                            fees32 = 14635;
                                            break;
                                    }
                                    break;
                                }
                            }
                        } else if (flag == 1) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(21.382360, 79.253203)).title("Mansar").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            flag = 0;
                            toll = toll - fees32;
                            fees32 = 0;
                        }
                        break;
                    }

                    case "WEPL Mathani": {
                        if (flag == 0) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(21.139385, 79.365109)).title("WEPL Mathani").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                            flag = 1;
                            switch (Vehi) {
                                case "Car": {
                                    switch (Jney) {
                                        case "Single":
                                            fees33 = 45;
                                            break;

                                        case "Return":
                                            fees33 = 70;
                                            break;

                                        case "Monthly Pass":
                                            fees33 = 1545;
                                            break;
                                    }
                                    break;
                                }
                                case "LCV": {
                                    switch (Jney) {
                                        case "Single":
                                            fees33 = 75;
                                            break;

                                        case "Return":
                                            fees33 = 110;
                                            break;

                                        case "Monthly Pass":
                                            fees33 = 2495;
                                            break;
                                    }
                                    break;
                                }

                                case "Bus": {
                                    switch (Jney) {
                                        case "Single":
                                            fees33 = 155;
                                            break;

                                        case "Return":
                                            fees33 = 235;
                                            break;

                                        case "Monthly Pass":
                                            fees33 = 5225;
                                            break;
                                    }
                                    break;
                                }

                                case "Three": {
                                    switch (Jney) {
                                        case "Single":
                                            fees33 = 170;
                                            break;

                                        case "Return":
                                            fees33 = 255;
                                            break;

                                        case "Monthly Pass":
                                            fees33 = 5700;
                                            break;
                                    }
                                    break;
                                }
                            }
                        } else if (flag == 1) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(21.139385, 79.365109)).title("WEPL Mathani").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            flag = 0;
                            toll = toll - fees33;
                            fees33 = 0;
                        }
                        break;
                    }

                    case "Kardha": {
                        if (flag == 0) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(21.139482, 79.675201)).title("Kardha").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                            flag = 1;
                            switch (Vehi) {
                                case "Car": {
                                    switch (Jney) {
                                        case "Single":
                                            fees34 = 21;
                                            break;

                                        case "Return":
                                            fees34 = 32;
                                            break;

                                        case "Monthly Pass":
                                            fees34 = 945;
                                            break;
                                    }
                                    break;
                                }
                                case "LCV": {
                                    switch (Jney) {
                                        case "Single":
                                            fees34 = 21;
                                            break;

                                        case "Return":
                                            fees34 = 32;
                                            break;

                                        case "Monthly Pass":
                                            fees34 = 945;
                                            break;
                                    }
                                    break;
                                }

                                case "Bus": {
                                    switch (Jney) {
                                        case "Single":
                                            fees34 = 106;
                                            break;

                                        case "Return":
                                            fees34 = 159;
                                            break;

                                        case "Monthly Pass":
                                            fees34 = 4770;
                                            break;
                                    }
                                    break;
                                }

                                case "Three": {
                                    switch (Jney) {
                                        case "Single":
                                            fees34 = 169;
                                            break;

                                        case "Return":
                                            fees34 = 254;
                                            break;

                                        case "Monthly Pass":
                                            fees34 = 7605;
                                            break;
                                    }
                                    break;
                                }
                            }
                        } else if (flag == 1) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(21.139482, 79.675201)).title("Kardha").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            flag = 0;
                            toll = toll - fees34;
                            fees34 = 0;
                        }
                        break;
                    }

                    case "Sendurwafa": {
                        if (flag == 0) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(21.088170, 80.034141)).title("Sendurwafa").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                            flag = 1;
                            switch (Vehi) {
                                case "Car": {
                                    switch (Jney) {
                                        case "Single":
                                            fees35 = 73;
                                            break;

                                        case "Return":
                                            fees35 = 109;
                                            break;

                                        case "Monthly Pass":
                                            fees35 = 2177;
                                            break;
                                    }
                                    break;
                                }
                                case "LCV": {
                                    switch (Jney) {
                                        case "Single":
                                            fees35 = 127;
                                            break;

                                        case "Return":
                                            fees35 = 191;
                                            break;

                                        case "Monthly Pass":
                                            fees35 = 3811;
                                            break;
                                    }
                                    break;
                                }

                                case "Bus": {
                                    switch (Jney) {
                                        case "Single":
                                            fees35 = 254;
                                            break;

                                        case "Return":
                                            fees35 = 381;
                                            break;

                                        case "Monthly Pass":
                                            fees35 = 7621;
                                            break;
                                    }
                                    break;
                                }

                                case "Three": {
                                    switch (Jney) {
                                        case "Single":
                                            fees35 = 408;
                                            break;

                                        case "Return":
                                            fees35 = 612;
                                            break;

                                        case "Monthly Pass":
                                            fees35 = 12248;
                                            break;
                                    }
                                    break;
                                }
                            }
                        } else if (flag == 1) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(21.088170, 80.034141)).title("Sendurwafa").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            flag = 0;
                            toll = toll - fees35;
                            fees35 = 0;
                        }
                        break;
                    }

                    case "Borkhedi": {
                        if (flag == 0) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(20.450741, 78.755042)).title("Borkhedi").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                            flag = 1;
                            switch (Vehi) {
                                case "Car": {
                                    switch (Jney) {
                                        case "Single":
                                            fees36 = 90;
                                            break;

                                        case "Return":
                                            fees36 = 135;
                                            break;

                                        case "Monthly Pass":
                                            fees36 = 2960;
                                            break;
                                    }
                                    break;
                                }
                                case "LCV": {
                                    switch (Jney) {
                                        case "Single":
                                            fees36 = 135;
                                            break;

                                        case "Return":
                                            fees36 = 205;
                                            break;

                                        case "Monthly Pass":
                                            fees36 = 4535;
                                            break;
                                    }
                                    break;
                                }

                                case "Bus": {
                                    switch (Jney) {
                                        case "Single":
                                            fees36 = 275;
                                            break;

                                        case "Return":
                                            fees36 = 410;
                                            break;

                                        case "Monthly Pass":
                                            fees36 = 9115;
                                            break;
                                    }
                                    break;
                                }

                                case "Three": {
                                    switch (Jney) {
                                        case "Single":
                                            fees36 = 415;
                                            break;

                                        case "Return":
                                            fees36 = 620;
                                            break;

                                        case "Monthly Pass":
                                            fees36 = 13810;
                                            break;
                                    }
                                    break;
                                }
                            }
                        } else if (flag == 1) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(20.450741, 78.755042)).title("Borkhedi").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            flag = 0;
                            toll = toll - fees36;
                            fees36 = 0;
                        }
                        break;
                    }

                    case "Daroada": {
                        if (flag == 0) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(19.890574, 72.942598)).title("Daroada").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                            flag = 1;
                            switch (Vehi) {
                                case "Car": {
                                    switch (Jney) {
                                        case "Single":
                                            fees37 = 60;
                                            break;

                                        case "Return":
                                            fees37 = 95;
                                            break;

                                        case "Monthly Pass":
                                            fees37 = 2065;
                                            break;
                                    }
                                    break;
                                }
                                case "LCV": {
                                    switch (Jney) {
                                        case "Single":
                                            fees37 = 100;
                                            break;

                                        case "Return":
                                            fees37 = 150;
                                            break;

                                        case "Monthly Pass":
                                            fees37 = 3340;
                                            break;
                                    }
                                    break;
                                }

                                case "Bus": {
                                    switch (Jney) {
                                        case "Single":
                                            fees37 = 210;
                                            break;

                                        case "Return":
                                            fees37 = 315;
                                            break;

                                        case "Monthly Pass":
                                            fees37 = 6995;
                                            break;
                                    }
                                    break;
                                }

                                case "Three": {
                                    switch (Jney) {
                                        case "Single":
                                            fees37 = 230;
                                            break;

                                        case "Return":
                                            fees37 = 345;
                                            break;

                                        case "Monthly Pass":
                                            fees37 = 7635;
                                            break;
                                    }
                                    break;
                                }
                            }
                        } else if (flag == 1) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(19.890574, 72.942598)).title("Daroada").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            flag = 0;
                            toll = toll - fees37;
                            fees37 = 0;
                        }
                        break;

                    }

                    case "Kelapur": {
                        if (flag == 0) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(20.019321, 78.540122)).title("Kelapur").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            Toast.makeText(MapsActivity.this, "" + mark.getTitle(), Toast.LENGTH_SHORT).show();
                            flag = 1;
                            switch (Vehi) {
                                case "Car": {
                                    switch (Jney) {
                                        case "Single":
                                            fees38 = 30;
                                            break;

                                        case "Return":
                                            fees38 = 50;
                                            break;

                                        case "Monthly Pass":
                                            fees38 = 1075;
                                            break;
                                    }
                                    break;
                                }
                                case "LCV": {
                                    switch (Jney) {
                                        case "Single":
                                            fees38 = 50;
                                            break;

                                        case "Return":
                                            fees38 = 80;
                                            break;

                                        case "Monthly Pass":
                                            fees38 = 1740;
                                            break;
                                    }
                                    break;
                                }

                                case "Bus": {
                                    switch (Jney) {
                                        case "Single":
                                            fees38 = 110;
                                            break;

                                        case "Return":
                                            fees38 = 165;
                                            break;

                                        case "Monthly Pass":
                                            fees38 = 3645;
                                            break;
                                    }
                                    break;
                                }

                                case "Three": {
                                    switch (Jney) {
                                        case "Single":
                                            fees38 = 120;
                                            break;

                                        case "Return":
                                            fees38 = 180;
                                            break;

                                        case "Monthly Pass":
                                            fees38 = 3975;
                                            break;
                                    }
                                    break;
                                }
                            }
                        } else if (flag == 1) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(20.019321, 78.540122)).title("Kelapur").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            flag = 0;
                            String a=new String();
                            toll = toll - fees38;
                            fees38 = 0;
                        }
                        break;
                    }

                }

                tolls_selected.setText(tolls);
                return true;
            }
        });

    }

    public void Calc(View v) {
        toll = fees1 + fees2 + fees3 + fees4 + fees5 + fees6 + fees7 + fees8 + fees9 + fees10 + fees11 + fees12 + fees13 + fees14 + fees15 + fees16 + fees17 + fees18 + fees19 + fees20 + fees21 + fees22 + fees23 + fees24 + fees25 + fees26 + fees27 + fees28 + fees29 + fees30 + fees31 + fees32 + fees33 + fees34 + fees35 + fees36 + fees37 + fees38;
        Toast.makeText(this, "Rs " + toll, Toast.LENGTH_SHORT).show();
        for(int i=1;i<=1;i++)
        {
            if(fees1>=10)
            {
                selected+="Moshi";
            }
            if(fees2>10)
            {
                selected+=" Chandloi";

            }
            if(fees3>10)
            {
                selected+=" Khedshivapur";

            }
            if(fees4>10)
            {
                selected+=" Anewadi";

            }
            if(fees5>10)
            {
                selected+=" Tasawade";

            }
            if(fees6>10)
            {
                selected+=" Kini";

            }
            if(fees7>10)
            {
                selected+=" Kharegaon";

            }
            if(fees8>=10)
            {
                selected+="Arjunali";
            }
            if(fees9>10)
            {
                selected+=" Ghoti";

            }
            if(fees10>10)
            {
                selected+=" Kawdipath";

            }
            if(fees11>10)
            {
                selected+=" Kasurdi";

            }
            if(fees12>10)
            {
                selected+=" Patas";

            }
            if(fees13>10)
            {
                selected+=" Varwade";

            }
            if(fees14>10)
            {
                selected+=" Sawaleshwar";

            }
            if(fees15>=10)
            {
                selected+=" Wadakbal";
            }
            if(fees16>10)
            {
                selected+=" Savitri";

            }
            if(fees17>10)
            {
                selected+=" Kharpada";

            }
            if(fees18>10)
            {
                selected+=" Khaniwade";

            }
            if(fees19>10)
            {
                selected+=" Charoti";

            }
            if(fees20>10)
            {
                selected+=" Baswant";

            }
            if(fees21>10)
            {
                selected+=" Chanwad";

            }
        }

        d = new Dialog(MapsActivity.this);
        d.setContentView(R.layout.calcdialog);
//        d.setTitle("Payment Portal");

//        tv1.setText("Your Total Amount is Rs. "+toll+" /-");
//        d.setTitle("Your Total Amount is Rs. "+toll+"/-");
//        AlertDialog.Builder b= new AlertDialog.Builder(this);
//        LayoutInflater inf= getLayoutInflater();

        tv1 = (TextView) d.findViewById(R.id.Total);
//        d.setTitle("Your Total Amount is Rs. "+toll+"/-");
        tv1.setText("Your Total amount is Rs. " + toll + "/-");


        //Toast.makeText(this, ""+mb, Toast.LENGTH_SHORT).show();
        Button Payment = (Button) d.findViewById(R.id.Payment);
        Payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(MapsActivity.this, GatewayActivity.class);
                i1.putExtra("tollgates",Integer.toString(toll));
               // i1.putExtra("mb2",mb);
                i1.putExtra("select",selected);
                i1.putExtra("source",etOrigin.getText().toString());
                i1.putExtra("dest",etDestination.getText().toString());
                i1.putExtra("Journey", Jney);
                i1.putExtra("Vehicle", Vehi);
                i1.putExtra("Vehicle_No", Vehicle_No);
                Toast.makeText(MapsActivity.this, "Our rate is"+Integer.toString(toll), Toast.LENGTH_SHORT).show();
               // i1.putExtra("amount", toll);
                i1.putExtra("Mobile",mb);
                Toast.makeText(MapsActivity.this, ""+mb, Toast.LENGTH_SHORT).show();
                Toast.makeText(MapsActivity.this, ""+toll, Toast.LENGTH_SHORT).show();
               // Toast.makeText(MapsActivity.this,""+mb+" "+selected+" "+Jney+" "+" "+Vehi+" "+Vehicle_No+" "+mb+" "+etOrigin.getText().toString()+" "+etDestination.getText().toString(), Toast.LENGTH_SHORT).show();

               startActivity(i1);

            }
        });
        d.show();
    }

    public void Payment(View view) {
        //  Intent i1= new Intent(MapsActivity.this,PaymentActivity1.class);
        //startActivity(i1);
    }

    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Please wait",
                "Processing...", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }


        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(route.startLocation));
            float zoomLevel = 8; //This goes up to 21
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, zoomLevel));
//            ((TextView) findViewById(R.id.tvDuration)).setText(route.duration.text);
//            ((TextView) findViewById(R.id.tvDistance)).setText(route.distance.text);

            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue40))
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green40))
                    .title(route.endAddress)
                    .position(route.endLocation)));


            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));
            polylinePaths.add(mMap.addPolyline(polylineOptions));

            for (Polyline polyline : polylinePaths) {
                for (LatLng polyCoords : polyline.getPoints()) {
                    float[] results = new float[1];

                    Location.distanceBetween(18.686770, 73.846658, polyCoords.latitude, polyCoords.longitude, results);
                    if (results[0] < 1000) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(18.686770, 73.846658)).title("Moshi").icon(BitmapDescriptorFactory.defaultMarker()));
                    }
                    Location.distanceBetween(19.211991, 73.007039, polyCoords.latitude, polyCoords.longitude, results);
                    if (results[0] < 1000) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(19.211991, 73.007039)).title("Kharegaon").icon(BitmapDescriptorFactory.defaultMarker()));
                    }
                    Location.distanceBetween(19.890574, 72.942598, polyCoords.latitude, polyCoords.longitude, results);
                    if (results[0] < 1000) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(19.890574, 72.942598)).title("Charoti").icon(BitmapDescriptorFactory.defaultMarker()));
                    }
                    Location.distanceBetween(19.518966, 72.916865, polyCoords.latitude, polyCoords.longitude, results);
                    if (results[0] < 1000) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(19.518966, 72.916865)).title("Khaniwade").icon(BitmapDescriptorFactory.defaultMarker()));
                    }
                    Location.distanceBetween(18.844917, 73.094552, polyCoords.latitude, polyCoords.longitude, results);
                    if (results[0] < 1000) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(18.844917, 73.094552)).title("Sardewadi").icon(BitmapDescriptorFactory.defaultMarker()));
                    }
                    Location.distanceBetween(18.844797, 73.094545, polyCoords.latitude, polyCoords.longitude, results);
                    if (results[0] < 1000) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(18.844797, 73.094545)).title("Kharpada").icon(BitmapDescriptorFactory.defaultMarker()));
                    }
                    Location.distanceBetween(18.084995, 73.462346, polyCoords.latitude, polyCoords.longitude, results);
                    if (results[0] < 1000) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(18.084995, 73.462346)).title("Savitri").icon(BitmapDescriptorFactory.defaultMarker()));
                    }
                    Location.distanceBetween(16.876691, 74.298431, polyCoords.latitude, polyCoords.longitude, results);
                    if (results[0] < 1000) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(16.876691, 74.298431)).title("Kini").icon(BitmapDescriptorFactory.defaultMarker()));
                    }
                    Location.distanceBetween(17.365755, 74.123068, polyCoords.latitude, polyCoords.longitude, results);
                    if (results[0] < 1000) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(17.365755, 74.123068)).title("Tasawade").icon(BitmapDescriptorFactory.defaultMarker()));
                    }
                    Location.distanceBetween(17.809379, 73.971840, polyCoords.latitude, polyCoords.longitude, results);
                    if (results[0] < 1000) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(17.809379, 73.971840)).title("Anewadi").icon(BitmapDescriptorFactory.defaultMarker()));
                    }
                    Location.distanceBetween(18.331367, 73.852710, polyCoords.latitude, polyCoords.longitude, results);
                    if (results[0] < 1000) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(18.331367, 73.852710)).title("Khedshivapur").icon(BitmapDescriptorFactory.defaultMarker()));
                    }
                    Location.distanceBetween(18.493111, 73.993094, polyCoords.latitude, polyCoords.longitude, results);
                    if (results[0] < 1000) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(18.493111, 73.993094)).title("Kawdipath").icon(BitmapDescriptorFactory.defaultMarker()));
                    }
                    Location.distanceBetween(18.483234, 74.234969, polyCoords.latitude, polyCoords.longitude, results);
                    if (results[0] < 1000) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(18.483234, 74.234969)).title("Kasurdi").icon(BitmapDescriptorFactory.defaultMarker()));
                    }
                    Location.distanceBetween(18.424488, 74.466066, polyCoords.latitude, polyCoords.longitude, results);
                    if (results[0] < 1000) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(18.424488, 74.466066)).title("Patas").icon(BitmapDescriptorFactory.defaultMarker()));
                    }
                    Location.distanceBetween(17.957650, 75.331092, polyCoords.latitude, polyCoords.longitude, results);
                    if (results[0] < 1000) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(17.957650, 75.331092)).title("Varwade").icon(BitmapDescriptorFactory.defaultMarker()));
                    }
                    Location.distanceBetween(17.764688, 75.755915, polyCoords.latitude, polyCoords.longitude, results);
                    if (results[0] < 1000) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(17.764688, 75.755915)).title("Sawaleshwar").icon(BitmapDescriptorFactory.defaultMarker()));
                    }
                    Location.distanceBetween(17.534345, 75.880097, polyCoords.latitude, polyCoords.longitude, results);
                    if (results[0] < 1000) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(17.534345, 75.880097)).title("Wadakbal").icon(BitmapDescriptorFactory.defaultMarker()));
                    }
                    Location.distanceBetween(19.361324, 73.166008, polyCoords.latitude, polyCoords.longitude, results);
                    if (results[0] < 1000) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(19.361324, 73.166008)).title("Arjunali").icon(BitmapDescriptorFactory.defaultMarker()));
                    }
                    Location.distanceBetween(19.708666, 73.614578, polyCoords.latitude, polyCoords.longitude, results);
                    if (results[0] < 1000) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(19.708666, 73.614578)).title("Ghoti").icon(BitmapDescriptorFactory.defaultMarker()));
                    }
                    Location.distanceBetween(20.141639, 73.976480, polyCoords.latitude, polyCoords.longitude, results);
                    if (results[0] < 1000) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(20.141639, 73.976480)).title("Baswant").icon(BitmapDescriptorFactory.defaultMarker()));
                    }
                    Location.distanceBetween(20.325233, 74.210712, polyCoords.latitude, polyCoords.longitude, results);
                    if (results[0] < 1000) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(20.325233, 74.210712)).title("Chandwad").icon(BitmapDescriptorFactory.defaultMarker()));
                    }
                    Location.distanceBetween(20.833106, 74.754127, polyCoords.latitude, polyCoords.longitude, results);
                    if (results[0] < 1000) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(20.833106, 74.754127)).title("Laling (Dhule)").icon(BitmapDescriptorFactory.defaultMarker()));
                    }
                    Location.distanceBetween(21.051069, 74.785102, polyCoords.latitude, polyCoords.longitude, results);
                    if (results[0] < 1000) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(21.051069, 74.785102)).title("Songir").icon(BitmapDescriptorFactory.defaultMarker()));
                    }
                    Location.distanceBetween(21.211815, 74.831540, polyCoords.latitude, polyCoords.longitude, results);
                    if (results[0] < 1000) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(21.211815, 74.831540)).title("Nardana").icon(BitmapDescriptorFactory.defaultMarker()));
                    }
                    Location.distanceBetween(21.319701, 74.889354, polyCoords.latitude, polyCoords.longitude, results);
                    if (results[0] < 1000) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(21.319701, 74.889354)).title("Shirpur").icon(BitmapDescriptorFactory.defaultMarker()));
                    }
                    Location.distanceBetween(21.013392, 75.670451, polyCoords.latitude, polyCoords.longitude, results);
                    if (results[0] < 1000) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(21.013392, 75.670451)).title("Nashirabad").icon(BitmapDescriptorFactory.defaultMarker()));
                    }
                    Location.distanceBetween(21.040052, 75.829229, polyCoords.latitude, polyCoords.longitude, results);
                    if (results[0] < 1000) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(21.040052, 75.829229)).title("Fekri").icon(BitmapDescriptorFactory.defaultMarker()));
                    }
                    Location.distanceBetween(20.950980, 77.788382, polyCoords.latitude, polyCoords.longitude, results);
                    if (results[0] < 1000) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(20.950980, 77.788382)).title("Nandgaon Peth").icon(BitmapDescriptorFactory.defaultMarker()));
                    }
                    Location.distanceBetween(21.082875, 78.058584, polyCoords.latitude, polyCoords.longitude, results);
                    if (results[0] < 1000) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(21.082875, 78.058584)).title("Tiwasa").icon(BitmapDescriptorFactory.defaultMarker()));
                    }
                    Location.distanceBetween(21.157523, 78.388095, polyCoords.latitude, polyCoords.longitude, results);
                    if (results[0] < 1000) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(21.157523, 78.388095)).title("Karanja").icon(BitmapDescriptorFactory.defaultMarker()));
                    }
                    Location.distanceBetween(21.136904, 78.896468, polyCoords.latitude, polyCoords.longitude, results);
                    if (results[0] < 1000) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(21.136904, 78.896468)).title("Gondhkhairi").icon(BitmapDescriptorFactory.defaultMarker()));
                    }
                    Location.distanceBetween(21.343762, 79.009051, polyCoords.latitude, polyCoords.longitude, results);
                    if (results[0] < 1000) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(21.343762, 79.009051)).title("Pattansawangi").icon(BitmapDescriptorFactory.defaultMarker()));
                    }
                    Location.distanceBetween(21.382360, 79.253203, polyCoords.latitude, polyCoords.longitude, results);
                    if (results[0] < 1000) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(21.382360, 79.253203)).title("Mansar").icon(BitmapDescriptorFactory.defaultMarker()));
                    }
                    Location.distanceBetween(21.139385, 79.365109, polyCoords.latitude, polyCoords.longitude, results);
                    if (results[0] < 1000) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(21.139385, 79.365109)).title("WEPL Mathani").icon(BitmapDescriptorFactory.defaultMarker()));
                    }
                    Location.distanceBetween(21.139482, 79.675201, polyCoords.latitude, polyCoords.longitude, results);
                    if (results[0] < 1000) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(21.139482, 79.675201)).title("Kardha").icon(BitmapDescriptorFactory.defaultMarker()));
                    }
                    Location.distanceBetween(21.088170, 80.034141, polyCoords.latitude, polyCoords.longitude, results);
                    if (results[0] < 1000) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(21.088170, 80.034141)).title("Sendurwafa").icon(BitmapDescriptorFactory.defaultMarker()));
                    }
                    Location.distanceBetween(20.450741, 78.755042, polyCoords.latitude, polyCoords.longitude, results);
                    if (results[0] < 1000) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(20.450741, 78.755042)).title("Borkhedi").icon(BitmapDescriptorFactory.defaultMarker()));
                    }
                    Location.distanceBetween(19.890574, 72.942598, polyCoords.latitude, polyCoords.longitude, results);
                    if (results[0] < 1000) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(19.890574, 72.942598)).title("Daroada").icon(BitmapDescriptorFactory.defaultMarker()));
                    }
                    Location.distanceBetween(20.019321, 78.540122, polyCoords.latitude, polyCoords.longitude, results);
                    if (results[0] < 1000) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(20.019321, 78.540122)).title("Kelapur").icon(BitmapDescriptorFactory.defaultMarker()));
                    }
                    Location.distanceBetween(18.837905, 73.879984, polyCoords.latitude, polyCoords.longitude, results);
                    if (results[0] < 1000) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(18.837905, 73.879984)).title("Chandloi").icon(BitmapDescriptorFactory.defaultMarker()));
                    }
                }
            }
        }
    }






}

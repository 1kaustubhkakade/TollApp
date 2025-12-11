package com.example.rohan.tollapp;

import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by ThaneRO on 3/11/2017.
 */

public class Frag_SourceDestn extends Fragment
{
    MapView mMapView;
    private GoogleMap mMap;
    private Dialog dialog;
    private ImageView tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.frag_sourcedestn, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
//        tv=(ImageView)rootView.findViewById(R.id.tollinfo);
        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;

                LatLng aurangabad = new LatLng(19.8762, 75.3433);
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(pune));
                float zoomLevel = 7; //This goes up to 21
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(aurangabad, zoomLevel));
                mMap.addMarker(new MarkerOptions().position(new LatLng(19.211991, 73.007039)).title("Kharegaon").icon(BitmapDescriptorFactory.defaultMarker()));
                mMap.addMarker(new MarkerOptions().position(new LatLng(19.890574, 72.942598)).title("Charoti").icon(BitmapDescriptorFactory.defaultMarker()));
                mMap.addMarker(new MarkerOptions().position(new LatLng(19.518966, 72.916865)).title("Khaniwade").icon(BitmapDescriptorFactory.defaultMarker()));
                mMap.addMarker(new MarkerOptions().position(new LatLng(18.844917, 73.094552)).title("Sardewadi").icon(BitmapDescriptorFactory.defaultMarker()));
                mMap.addMarker(new MarkerOptions().position(new LatLng(18.844797, 73.094545)).title("Kharpada").icon(BitmapDescriptorFactory.defaultMarker()));
                mMap.addMarker(new MarkerOptions().position(new LatLng(18.084995, 73.462346)).title("Savitri").icon(BitmapDescriptorFactory.defaultMarker()));
                mMap.addMarker(new MarkerOptions().position(new LatLng(16.876691, 74.298431)).title("Kini").icon(BitmapDescriptorFactory.defaultMarker()));
                mMap.addMarker(new MarkerOptions().position(new LatLng(17.365755, 74.123068)).title("Tasawade").icon(BitmapDescriptorFactory.defaultMarker()));
                mMap.addMarker(new MarkerOptions().position(new LatLng(17.809379, 73.971840)).title("Anewadi").icon(BitmapDescriptorFactory.defaultMarker()));
                mMap.addMarker(new MarkerOptions().position(new LatLng(18.331367, 73.852710)).title("Khedshivapur").icon(BitmapDescriptorFactory.defaultMarker()));
                mMap.addMarker(new MarkerOptions().position(new LatLng(18.493111, 73.993094)).title("Kawdipath").icon(BitmapDescriptorFactory.defaultMarker()));
                mMap.addMarker(new MarkerOptions().position(new LatLng(18.483234, 74.234969)).title("Kasurdi").icon(BitmapDescriptorFactory.defaultMarker()));
                mMap.addMarker(new MarkerOptions().position(new LatLng(18.424488, 74.466066)).title("Patas").icon(BitmapDescriptorFactory.defaultMarker()));
                mMap.addMarker(new MarkerOptions().position(new LatLng(17.957650, 75.331092)).title("Varwade").icon(BitmapDescriptorFactory.defaultMarker()));
                mMap.addMarker(new MarkerOptions().position(new LatLng(17.764688, 75.755915)).title("Sawaleshwar").icon(BitmapDescriptorFactory.defaultMarker()));
                mMap.addMarker(new MarkerOptions().position(new LatLng(17.534345, 75.880097)).title("Wadakbal").icon(BitmapDescriptorFactory.defaultMarker()));
                mMap.addMarker(new MarkerOptions().position(new LatLng(19.361324, 73.166008)).title("Arjunali").icon(BitmapDescriptorFactory.defaultMarker()));
                mMap.addMarker(new MarkerOptions().position(new LatLng(19.708666, 73.614578)).title("Ghoti").icon(BitmapDescriptorFactory.defaultMarker()));
                mMap.addMarker(new MarkerOptions().position(new LatLng(20.141639, 73.976480)).title("Baswant").icon(BitmapDescriptorFactory.defaultMarker()));
                mMap.addMarker(new MarkerOptions().position(new LatLng(20.325233, 74.210712)).title("Chandwad").icon(BitmapDescriptorFactory.defaultMarker()));
                mMap.addMarker(new MarkerOptions().position(new LatLng(20.833106, 74.754127)).title("Laling (Dhule)").icon(BitmapDescriptorFactory.defaultMarker()));
                mMap.addMarker(new MarkerOptions().position(new LatLng(21.051069, 74.785102)).title("Songir").icon(BitmapDescriptorFactory.defaultMarker()));
                mMap.addMarker(new MarkerOptions().position(new LatLng(21.211815, 74.831540)).title("Nardana").icon(BitmapDescriptorFactory.defaultMarker()));
                mMap.addMarker(new MarkerOptions().position(new LatLng(21.319701, 74.889354)).title("Shirpur").icon(BitmapDescriptorFactory.defaultMarker()));
                mMap.addMarker(new MarkerOptions().position(new LatLng(21.013392, 75.670451)).title("Nashirabad").icon(BitmapDescriptorFactory.defaultMarker()));
                mMap.addMarker(new MarkerOptions().position(new LatLng(21.040052, 75.829229)).title("Fekri").icon(BitmapDescriptorFactory.defaultMarker()));
                mMap.addMarker(new MarkerOptions().position(new LatLng(20.950980, 77.788382)).title("Nandgaon Peth").icon(BitmapDescriptorFactory.defaultMarker()));
                mMap.addMarker(new MarkerOptions().position(new LatLng(21.082875, 78.058584)).title("Tiwasa").icon(BitmapDescriptorFactory.defaultMarker()));
                mMap.addMarker(new MarkerOptions().position(new LatLng(21.157523, 78.388095)).title("Karanja").icon(BitmapDescriptorFactory.defaultMarker()));
                mMap.addMarker(new MarkerOptions().position(new LatLng(21.136904, 78.896468)).title("Gondhkhairi").icon(BitmapDescriptorFactory.defaultMarker()));
                mMap.addMarker(new MarkerOptions().position(new LatLng(21.343762, 79.009051)).title("Pattansawangi").icon(BitmapDescriptorFactory.defaultMarker()));
                mMap.addMarker(new MarkerOptions().position(new LatLng(21.382360, 79.253203)).title("Mansar").icon(BitmapDescriptorFactory.defaultMarker()));
                mMap.addMarker(new MarkerOptions().position(new LatLng(21.139385, 79.365109)).title("WEPL Mathani").icon(BitmapDescriptorFactory.defaultMarker()));
                mMap.addMarker(new MarkerOptions().position(new LatLng(21.139482, 79.675201)).title("Kardha").icon(BitmapDescriptorFactory.defaultMarker()));
                mMap.addMarker(new MarkerOptions().position(new LatLng(21.088170, 80.034141)).title("Sendurwafa").icon(BitmapDescriptorFactory.defaultMarker()));
                mMap.addMarker(new MarkerOptions().position(new LatLng(20.450741, 78.755042)).title("Borkhedi").icon(BitmapDescriptorFactory.defaultMarker()));
                mMap.addMarker(new MarkerOptions().position(new LatLng(19.890574, 72.942598)).title("Daroada").icon(BitmapDescriptorFactory.defaultMarker()));
                mMap.addMarker(new MarkerOptions().position(new LatLng(20.019321, 78.540122)).title("Kelapur").icon(BitmapDescriptorFactory.defaultMarker()));
                mMap.addMarker(new MarkerOptions().position(new LatLng(18.686770, 73.846658)).title("Moshi").icon(BitmapDescriptorFactory.defaultMarker()));
                mMap.addMarker(new MarkerOptions().position(new LatLng(18.837905, 73.879984)).title("Chandloi").icon(BitmapDescriptorFactory.defaultMarker()));

                if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

                mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                    @Override
                    public View getInfoWindow(Marker marker) {
                        return null;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {
                        dialog = new Dialog(getActivity());

                      if(marker.getTitle().equals("Anewadi")) {
                            dialog.setContentView(R.layout.anewadi_costum);
                            dialog.show();
                        }
                        if(marker.getTitle().equals("Arjunali")) {
                            dialog.setContentView(R.layout.arjunali_costum);
                            dialog.show();
                        }
                        if(marker.getTitle().equals("Baswant")) {
                            dialog.setContentView(R.layout.baswant_costum);
                            dialog.show();
                        }
                        if(marker.getTitle().equals("Borkhedi")) {
                            dialog.setContentView(R.layout.borkhedi_costum);
                            dialog.show();
                        }
                        if(marker.getTitle().equals("Chandloi")) {
                            dialog.setContentView(R.layout.chandloi_costum);
                            dialog.show();
                        }
                        if(marker.getTitle().equals("Chandwad")) {
                            dialog.setContentView(R.layout.chandwad_costum);
                            dialog.show();
                        }
                        if(marker.getTitle().equals("Charoti")) {
                            dialog.setContentView(R.layout.charoti_costum);
                            dialog.show();
                        }
                       /* if(marker.getTitle().equals("Daroada")) {
                            dialog.setContentView(R.layout.daroada_costum);
                            dialog.show();
                        }*/
                        if(marker.getTitle().equals("Fekri")) {
                            dialog.setContentView(R.layout.fekir_costum);
                            dialog.show();
                        }
                        if(marker.getTitle().equals("Ghoti")) {
                            dialog.setContentView(R.layout.ghoti_costum);
                            dialog.show();
                        }
                        if(marker.getTitle().equals("Gondhkhairi")) {
                            dialog.setContentView(R.layout.gonghkhairi_costum);
                            dialog.show();
                        }
                        if(marker.getTitle().equals("Karanja")) {
                            dialog.setContentView(R.layout.karanja_costum);
                            dialog.show();
                        }
                        if(marker.getTitle().equals("Kardha")) {
                            dialog.setContentView(R.layout.kardha_costum);
                            dialog.show();
                        }
                        if(marker.getTitle().equals("Kasurdi")) {
                            dialog.setContentView(R.layout.kasurdi_costum);
                            dialog.show();
                        }
                        if(marker.getTitle().equals("Kawdipath")) {
                            dialog.setContentView(R.layout.kawdipath_costum);
                            dialog.show();
                        }if(marker.getTitle().equals("Kelapur")) {
                            dialog.setContentView(R.layout.kelapur_costum);
                            dialog.show();
                        }
                        if(marker.getTitle().equals("Khaniwade")) {
                            dialog.setContentView(R.layout.khaniwade_costum);
                            dialog.show();
                        }if(marker.getTitle().equals("Kharegaon")) {
                            dialog.setContentView(R.layout.kharegon_costum);
                            dialog.show();
                        }if(marker.getTitle().equals("Khedshivapur")) {
                            dialog.setContentView(R.layout.khedshivapur_costum);
                            dialog.show();
                        }if(marker.getTitle().equals("Kini")) {
                            dialog.setContentView(R.layout.kini_costum);
                            dialog.show();
                        }if(marker.getTitle().equals("Laling (Dhule)")) {
                            dialog.setContentView(R.layout.laling_costum);
                            dialog.show();
                        }if(marker.getTitle().equals("Mansar")) {
                            dialog.setContentView(R.layout.mansar_costum);
                            dialog.show();
                        }if(marker.getTitle().equals("Moshi")) {
                            dialog.setContentView(R.layout.moshi_costum);
                            dialog.show();
                        }if(marker.getTitle().equals("Nandgaon Peth")) {
                            dialog.setContentView(R.layout.nandgaon_costum);
                            dialog.show();
                        }if(marker.getTitle().equals("Nardana")) {
                            dialog.setContentView(R.layout.nardana_costum);
                            dialog.show();
                        }if(marker.getTitle().equals("Nashirabad")) {
                            dialog.setContentView(R.layout.nashirabad_costum);
                            dialog.show();
                        }if(marker.getTitle().equals("Patas")) {
                            dialog.setContentView(R.layout.patas_costum);
                            dialog.show();
                        }if(marker.getTitle().equals("Pattansawangi")) {
                            dialog.setContentView(R.layout.pattansawangi_costum);
                            dialog.show();
                        }if(marker.getTitle().equals("Sardewadi")) {
                            dialog.setContentView(R.layout.sardewadi_costum);
                            dialog.show();
                        }if(marker.getTitle().equals("Savitri")) {
                            dialog.setContentView(R.layout.savitri_costum);
                            dialog.show();
                        }if(marker.getTitle().equals("Sawaleshwar")) {
                            dialog.setContentView(R.layout.sawaleshwar_costum);
                            dialog.show();
                        }if(marker.getTitle().equals("Sendurwafa")) {
                            dialog.setContentView(R.layout.sendurwafa_costum);
                            dialog.show();
                        }if(marker.getTitle().equals("Shirpur")) {
                            dialog.setContentView(R.layout.shirpur_costum);
                            dialog.show();
                        }if(marker.getTitle().equals("Songir")) {
                            dialog.setContentView(R.layout.songir_costum);
                            dialog.show();
                        }if(marker.getTitle().equals("Tasawade")) {
                            dialog.setContentView(R.layout.tasawade_costum);
                            dialog.show();
                        }if(marker.getTitle().equals("Tiwasa")) {
                            dialog.setContentView(R.layout.tiwasa_costum);
                            dialog.show();
                        }if(marker.getTitle().equals("Varwade")) {
                            dialog.setContentView(R.layout.varwade_costum);
                            dialog.show();
                        }if(marker.getTitle().equals("Wadakbal")) {
                            dialog.setContentView(R.layout.wadakbal_costum);
                            dialog.show();
                        }if(marker.getTitle().equals("WEPL Mathani")) {
                            dialog.setContentView(R.layout.wepl_costum);
                            dialog.show();
                        }


                        return null;
                    }
                });
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


}
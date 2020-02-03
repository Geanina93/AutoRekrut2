package com.example.autorekrut.Controler;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.example.autorekrut.R;

public class Contact extends MainActivity /*implements OnMapReadyCallback */ {
    public TrimitereFormularROListener trimitereFormularListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        MapFragment mapFragment = (MapFragment) getFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onMapReady(GoogleMap map) {
//        LatLng sydney = new LatLng(-33.867, 151.206);
//
//       // map.setMyLocationEnabled(true);
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
//
//        map.addMarker(new MarkerOptions()
//                .title("Sydney")
//                .snippet("The most populous city in Australia.")
//                .position(sydney));
//    }

    private void addActionListeners() {
        Button trimiteMesaj = (Button) findViewById(R.id.trimitereMesajButon);//TODO: de adaugat butonu in interfata
        trimitereFormularListener = new TrimitereFormularROListener();
        trimiteMesaj.setOnClickListener(trimitereFormularListener);
        trimitereFormularListener.setContact(this);
    }
}


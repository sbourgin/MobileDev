package com.example.helloworld;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.MapFragment;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class DisplayCity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_city);
		
		Bundle locReceiveBundle = this.getIntent().getExtras();
	    final String locCityName = locReceiveBundle.getString("cityName");
	    final double locLatitude = locReceiveBundle.getDouble("latitude");
	    final double locLongitude = locReceiveBundle.getDouble("longitude");
	    
	    
		  // Get a handle to the Map Fragment
        GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(
                R.id.map)).getMap();

        LatLng locCityToDisplay = new LatLng(locLatitude, locLongitude);
        
        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(locCityToDisplay, 13));
        
        
        map.addMarker(new MarkerOptions()
                .title(locCityName)
                .position(locCityToDisplay));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}


	
}

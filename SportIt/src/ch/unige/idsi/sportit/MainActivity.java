package ch.unige.idsi.sportit;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.app.Application;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends FragmentActivity implements android.location.LocationListener, OnMapReadyCallback {
	
	private LocationManager lm;
	private double latitude;
	private double longitude;
	private double altitude;
	private float accuracy;
	private GoogleMap map;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Application app = getApplication();
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
           // WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		GoogleMapOptions mapOptions = new GoogleMapOptions();
		
		mapOptions.compassEnabled(true)
			.rotateGesturesEnabled(false)
			.tiltGesturesEnabled(false);
		
		map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		map.setMyLocationEnabled(true);
		map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		
	}
	@Override
	protected void onResume() {
		super.onResume();
		
		lm = (LocationManager) this.getSystemService(LOCATION_SERVICE);
		
		if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER))
				lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);
		
		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		lm.removeUpdates(this);
	}
	
	@Override
	public void onLocationChanged(Location location) {
		latitude = location.getLatitude();
		longitude = location.getLongitude();
		altitude = location.getAltitude();
		accuracy = location.getAccuracy();

		String msg = String.format(
				getResources().getString(R.string.new_location), latitude,
				longitude, altitude, accuracy);
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
		
		LatLng latLng = new LatLng(latitude,longitude);
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
		map.animateCamera(cameraUpdate);
		lm.removeUpdates(this);
		PathParser parser = new PathParser();
		AssetManager mng = getAssets();
		try {
			InputStream str = mng.open("doc.kml");
			ArrayList<ArrayList<LatLng>> list = parser.getCoordinateArrays(str);
			for (ArrayList<LatLng> arrayList : list) {
				PolylineOptions rectOptions = new PolylineOptions();
				for (LatLng latLong : arrayList) {
					//System.out.println(latLng.latitude + " - " + latLng.longitude);
					LatLng temp = new LatLng(latLong.longitude, latLong.latitude);
					rectOptions.add(temp);
				}
				Polyline polyline = map.addPolyline(rectOptions);
				polyline.setWidth(10);
				polyline.setColor(Color.RED);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void onProviderDisabled(String provider) {
		String msg = String.format(
				getResources().getString(R.string.provider_disabled), provider);
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onProviderEnabled(String provider) {
		String msg = String.format(
				getResources().getString(R.string.provider_enabled), provider);
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		String newStatus = "";
		switch (status) {
		case LocationProvider.OUT_OF_SERVICE:
			newStatus = "OUT_OF_SERVICE";
			break;
		case LocationProvider.TEMPORARILY_UNAVAILABLE:
			newStatus = "TEMPORARILY_UNAVAILABLE";
			break;
		case LocationProvider.AVAILABLE:
			newStatus = "AVAILABLE";
			break;
		}
		String msg = String.format(
				getResources().getString(R.string.provider_disabled), provider,
				newStatus);
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onMapReady(GoogleMap arg0) {
		// TODO Auto-generated method stub
	    /*map.addMarker(new MarkerOptions()
        .position(new LatLng(0, 0))
        .title("Marker"));*/
			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}

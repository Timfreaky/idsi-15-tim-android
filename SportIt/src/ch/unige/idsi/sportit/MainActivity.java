package ch.unige.idsi.sportit;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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
		setContentView(R.layout.activity_main);
		GoogleMapOptions mapOptions = new GoogleMapOptions();
		
		mapOptions.compassEnabled(true)
			.rotateGesturesEnabled(false)
			.tiltGesturesEnabled(false);
		
		map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		map.setMyLocationEnabled(true);
		map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		
		PathParser parser = new PathParser();
		AssetManager mng = getAssets();
		try {
			InputStream str = mng.open("doc.kml");
			ArrayList<ArrayList<LatLng>> list = parser.getCoordinateArrays(str);
			for (ArrayList<LatLng> arrayList : list) {
				PolylineOptions rectOptions = new PolylineOptions();
				for (LatLng latLong : arrayList) {
					LatLng temp = new LatLng(latLong.longitude, latLong.latitude);
					rectOptions.add(temp);
				}
				Polyline polyline = map.addPolyline(rectOptions);
				polyline.setWidth(7);
				polyline.setColor(Color.YELLOW);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		InfrParser inf = new InfrParser();
		AssetManager manag = getAssets();
		
		try {
			
			InputStream str = manag.open("doc_inf.kml");
			InputStream str2 = manag.open("doc_inf.kml");
			ArrayList<LatLng> listArr = inf.getCoordinateArrays(str);
			ArrayList<String> listArray = inf.getNamesArrays(str2);
			
			int i = 0;
				
				for (LatLng latLng : listArr) {
					
					MarkerOptions markerOpt	= new MarkerOptions();
					LatLng tempo = new LatLng(latLng.longitude, latLng.latitude);
					markerOpt.position(tempo);
					System.out.println(latLng.latitude + " - " + latLng.longitude);
				
				
				map.addMarker(markerOpt)
				.setTitle(listArray.get(i));
				i++;
			}				
								
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	protected void onResume() {
		super.onResume();
		
		lm = (LocationManager) this.getSystemService(LOCATION_SERVICE);
		
		if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER))
				lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 300000, 0, this);
		
		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 300000, 0, this);
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
	   
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return super.onCreateOptionsMenu(menu);
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

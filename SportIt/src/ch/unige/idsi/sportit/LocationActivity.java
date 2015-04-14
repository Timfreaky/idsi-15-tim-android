package ch.unige.idsi.sportit;

import java.util.List;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class LocationActivity extends ActionBarActivity {
	
Geocoder geocoder; 
String bestProvider; 
List<Address> user = null; 
double lat; 
double lng; 

	/*protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

	
	LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE); 
	
	Criteria criteria = new Criteria(); 
	bestProvider = lm.getBestProvider(criteria, false); 
	Location location = lm.getLastKnownLocation(bestProvider); 
	
	if (location == null){ 
		Toast.makeText(this,"Location Not found",Toast.LENGTH_LONG).show(); 
		}
	else{ 
		geocoder = new Geocoder(this); 
		try { 
			user = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); 
			lat=(double)user.get(0).getLatitude(); 
			lng=(double)user.get(0).getLongitude(); 
			System.out.println(" DDD lat: " +lat+", longitude: "+lng); 
			}catch (Exception e) { 
				e.printStackTrace(); 
				} 
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.location, menu);
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
	}*/
}

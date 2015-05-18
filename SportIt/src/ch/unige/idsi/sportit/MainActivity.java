package ch.unige.idsi.sportit;

import java.io.IOException;

import java.io.InputStream;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * Activit� principale de l'application SportIt qui g�n�re la Google Map et les
 * activit�s qui lui sont li�es.
 * 
 * @author Timothy McGarry & Florine Monnier
 * @version 0.1
 *
 */
public class MainActivity extends FragmentActivity implements
		android.location.LocationListener, OnMapReadyCallback {

	private LocationManager lm;
	private double latitude;
	private double longitude;
	private double altitude;
	private float accuracy;
	private GoogleMap map;
	final CharSequence[] items = { "Afficher les infrastructures", "Cacher les infrastructures" };
	private MarkerOptions markerOpt;
	private ArrayList<Marker> mArray = new ArrayList<Marker>();
	private int my_previous_selected = -1;
	private Dialog dialogBienvenu;
	private Dialog dialogAbout;
	private Button buttonAbout;
	private Button buttonInfo;

	/**
	 * M�thode OnCreate o� l'on instancie la Google Map avec ses options.
	 * Instanciation d'un objet de la classe PathParser pour dessiner les
	 * polylines en fonction des coordonn�es pars�es.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/**
		 * On lance la m�thode startWindow qui affiche un message de bienvenue
		 * au d�marrage de l'application
		 * 
		 * @see startWindow()
		 */
		startWindow();

		map = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		map.setMyLocationEnabled(true);
		map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(new LatLng(46.177349, 6.124060)) // Sets the center of
															// the map to
															// Mountain View
				.zoom(9) // Sets the zoom
				.build(); // Creates a CameraPosition from the builder
		map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

		GoogleMapOptions mapOptions = new GoogleMapOptions();
		mapOptions.compassEnabled(true).rotateGesturesEnabled(false)
				.tiltGesturesEnabled(false);

		/**
		 * Instanciation d'un objet de la classe PathParser 
		 * On r�cup�re les donn�es pars�es dans les arraylist pour cr�er les polylines sur la Google Map
		 * 
		 * @see PathParser
		 */
		PathParser parser = new PathParser();
		AssetManager mng = getAssets();
		try {
			InputStream str = mng.open("doc.kml");
			ArrayList<ArrayList<LatLng>> list = parser.getCoordinateArrays(str);
			for (ArrayList<LatLng> arrayList : list) {
				PolylineOptions rectOptions = new PolylineOptions();
				for (LatLng latLong : arrayList) {
					LatLng temp = new LatLng(latLong.longitude,
							latLong.latitude);
					rectOptions.add(temp);
				}
				Polyline polyline = map.addPolyline(rectOptions);
				polyline.setWidth(7);
				polyline.setColor(Color.YELLOW);
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 * Le locationManager lm relance l'op�ration de recherche de fournisseur de service
	 * � la reprise de l'application
	 */
	@Override
	protected void onResume() {
		super.onResume();

		lm = (LocationManager) this.getSystemService(LOCATION_SERVICE);

		if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER))
			lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 300000, 0,
					this);

		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 300000, 0,
				this);
	}

	/**
	 * Lorsque l'application est mise en pause (arri�re plan), on arr�te la
	 * recherche de fournisseur de service
	 */
	@Override
	protected void onPause() {
		super.onPause();
		lm.removeUpdates(this);
	}

	/**
	 * M�thode qui g�re la localisation. � chaque d�placement de l'utilisateur,
	 * cette m�thode cherche les nouvelles coordonn�es. La cam�ra se d�placera en
	 * fonction de la position
	 * 
	 * @param location
	 *            qui repr�sente la :
	 *            <ul>
	 *            <li>la latitude</li>
	 *            <li>la longitude</li>
	 *            <li>l'altitude</li>
	 *            <li>la pr�cision (accuracy)</li>
	 *            </ul>
	 */
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

		LatLng latLng = new LatLng(latitude, longitude);
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,
				15);
		map.animateCamera(cameraUpdate);
		lm.removeUpdates(this);

	}

	/**
	 * 
	 * 
	 */
	@Override
	public void onProviderDisabled(String provider) {
		String msg = String.format(
				getResources().getString(R.string.provider_disabled), provider);
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * 
	 */
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

	/**
	 * 
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * Cette m�thode g�re la s�l�ction des �l�ments du menu. Chaque �l�ment ou
	 * "item" d�marre une action sp�cifique g�n�r�e par les diff�rentes m�thodes
	 * 
	 * @param item
	 * @return item qui repr�sente un �l�ment du menu
	 * 
	 * @see aboutWindow()
	 * @see startWindow()
	 * @see openAlertSettings()
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_about:
			aboutWindow();

			break;

		case R.id.menu_help:
			startWindow();

			break;
		case R.id.menu_settings:
			openAlertSettings(null);

			break;

		default:
			break;

		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 
	 * @param view
	 */
	private void openAlertSettings(View view) {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				MainActivity.this);

		alertDialogBuilder.setTitle("R�glages");

		// alertDialogBuilder.setMessage("El�ments affich�s");

		alertDialogBuilder.setSingleChoiceItems(items, my_previous_selected,
				new DialogInterface.OnClickListener() {

					/**
					 * 
					 */
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						my_previous_selected = which;
						switch (which) {
						case 0:
							InfrParser inf = new InfrParser();
							AssetManager manag = getAssets();

							try {

								InputStream str = manag.open("doc_inf.kml");
								InputStream str2 = manag.open("doc_inf.kml");
								ArrayList<LatLng> listArr = inf
										.getCoordinateArrays(str);
								ArrayList<String> listArray = inf
										.getNamesArrays(str2);

								int i = 0;

								for (LatLng latLng : listArr) {

									markerOpt = new MarkerOptions();
									LatLng tempo = new LatLng(latLng.longitude,
											latLng.latitude);
									markerOpt.position(tempo);
									markerOpt.title(listArray.get(i));

									System.out.println("test"
											+ listArray.get(i));
									Marker marker = map.addMarker(markerOpt);
									mArray.add(marker);
									i++;
								}

							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							break;
						case 1:
							for (Marker m : mArray) {

								m.remove();
							}
							mArray.clear();
						default:
							break;
						}

					}

				});

		// set positive button: Yes message
		alertDialogBuilder.setPositiveButton("Ok",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int id) {

					}

				});

		AlertDialog alertDialog = alertDialogBuilder.create();

		// show alert

		alertDialog.show();

	}

	/**
	 * M�thode qui g�re le dialog de bienvenue au d�marrage de l'application
	 */
	public void startWindow() {
		dialogBienvenu = new Dialog(this);
		dialogBienvenu.setContentView(R.layout.popup_window_info);
		dialogBienvenu.setTitle("SportIt");

		TextView txt = (TextView) dialogBienvenu.findViewById(R.id.infoTxtView);
		txt.setText(Html.fromHtml(getString(R.string.Bienvenue)));
		txt.setMovementMethod(ScrollingMovementMethod.getInstance());

		buttonInfo = (Button) dialogBienvenu.findViewById(R.id.buttonInfoClose);
		buttonInfo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialogBienvenu.dismiss();

			}
		});

		dialogBienvenu.show();
		// dialogBienvenu.setCanceledOnTouchOutside(true);
	}

	/**
	 * M�thode qui g�n�re le dialog lorsque "� propos" est cliqu�
	 */
	public void aboutWindow() {

		dialogAbout = new Dialog(this);
		dialogAbout.setContentView(R.layout.about_popup);
		dialogAbout.setTitle("� propos...");

		TextView aboutTxt = (TextView) dialogAbout.findViewById(R.id.aboutView);
		aboutTxt.setText(Html.fromHtml(getString(R.string.About)));

		dialogAbout.show();
		//dialogAbout.setCanceledOnTouchOutside(true);

		buttonAbout = (Button) dialogAbout.findViewById(R.id.buttonClose);
		buttonAbout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialogAbout.dismiss();

			}
		});

	}

}

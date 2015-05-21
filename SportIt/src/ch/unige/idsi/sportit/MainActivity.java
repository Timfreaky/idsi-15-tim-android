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
import android.text.method.LinkMovementMethod;
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
 * Activité principale de l'application SportIt qui génère la Google Map et les
 * activités qui lui sont liées.
 * 
 * @author Timothy McGarry & Florine Monnier
 * @version 1.0
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
	private int save_radio = -1;
	private Dialog dialogBienvenu;
	private Dialog dialogAbout;
	private Button buttonAbout;
	private Button buttonInfo;

	/**
	 * Méthode OnCreate où l'on instancie la Google Map avec ses options.
	 * Instanciation d'un objet de la classe PathParser pour dessiner les
	 * polylines en fonction des coordonnées parsées.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/**
		 * On lance la méthode startWindow qui affiche un message de bienvenue
		 * au démarrage de l'application
		 * 
		 * @see startWindow()
		 */
		startWindow();

		map = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		map.setMyLocationEnabled(true);
		map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(new LatLng(46.177349, 6.124060)) // Détermine le centre de la carte
				.zoom(9) // Détermine le zoom
				.build(); // Crée une position de la caméra du builder
		map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition)); //Anime la caméra vers la localisation de l'utilisateur

		GoogleMapOptions mapOptions = new GoogleMapOptions();
		mapOptions.compassEnabled(true).rotateGesturesEnabled(false)
				.tiltGesturesEnabled(false);

		/**
		 * Instanciation d'un objet de la classe PathParser 
		 * On récupère les données parsées dans les arraylist pour créer les polylines sur la Google Map
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
					//Inversion de longitude et latitude qui sont affichées comme cela dans doc.kml
					LatLng temp = new LatLng(latLong.longitude,
							latLong.latitude);
					rectOptions.add(temp);
				}
				//Dessiner les polylines sur la carte 
				Polyline polyline = map.addPolyline(rectOptions);
				polyline.setWidth(7);
				polyline.setColor(Color.YELLOW);
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 * Le locationManager lm relance l'opération de recherche de fournisseur de service
	 * à la reprise de l'application
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
	 * Lorsque l'application est mise en pause (arrière plan), on arrête la
	 * recherche de fournisseur de service
	 */
	@Override
	protected void onPause() {
		super.onPause();
		lm.removeUpdates(this);
	}

	/**
	 * Méthode qui gère la localisation. À chaque déplacement de l'utilisateur,
	 * cette méthode cherche les nouvelles coordonnées. La caméra se déplacera en
	 * fonction de la position
	 * 
	 * @param location
	 *            qui représente la :
	 *            <ul>
	 *            <li>la latitude</li>
	 *            <li>la longitude</li>
	 *            <li>l'altitude</li>
	 *            <li>la précision (accuracy)</li>
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


	@Override
	public void onProviderDisabled(String provider) {
		String msg = String.format(
				getResources().getString(R.string.provider_disabled), provider);
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show(); //indique à l'utilisateur s'il a désactivé la géolocalisation
	}
	

	@Override
	public void onProviderEnabled(String provider) {
		String msg = String.format(
				getResources().getString(R.string.provider_enabled), provider);
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show(); //indique à l'utilisateur s'il a activé la géolocalisation
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

	/**
	 * Cette méthode gère la séléction des éléments du menu. Chaque élément ou
	 * "item" démarre une action spécifique générée par les différentes méthodes
	 * 
	 * @param item
	 * @return item qui représente un élément du menu
	 * 
	 * @see aboutWindow()
	 * @see startWindow()
	 * @see openAlertSettings()
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_about:
			aboutWindow(); //exécute la méthode aboutWindow()

			break;

		case R.id.menu_help:
			startWindow();//Exécute la méthode startWindow()

			break;
		case R.id.menu_settings:
			openAlertSettings(null);//Exécute la méthode openAlertSettings

			break;

		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Méthode qui crée un alert dialog qui est lancé lorsque "réglages" est cliqué. 
	 * Dans ce dialog, il y a deux buttons radio qui permettent à l'utilisateur d'afficher ou cacher les infrastructures sportives.
	 * On va donc instantier un objet de la classe InfrParser, afin de récupérer les données parsées et de les afficher grâce à des markers sur la map.
	 * 
	 * @param view
	 * @see InfrParser
	 * @see onOptionsItemSelected
	 */
	private void openAlertSettings(View view) {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				MainActivity.this);

		alertDialogBuilder.setTitle("Réglages");
		
		//Mise en place de deux boutons radio définis dans "items"
		alertDialogBuilder.setSingleChoiceItems(items, save_radio,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// Mémorise la séléction de l'utilisateur dans la variable save_radio
						save_radio = which;
						switch (which) {
						case 0:
							InfrParser inf = new InfrParser();
							AssetManager manag = getAssets();

							try {
								//Pour les coordonnées des infrastructures
								InputStream str = manag.open("doc_inf.kml");
								//Pour les titres des infrastructures
								InputStream str2 = manag.open("doc_inf.kml");
								ArrayList<LatLng> listArr = inf
										.getCoordinateArrays(str); //On récupère l'arraylist getCoordinatesArrays de InfrParser
								ArrayList<String> listArray = inf
										.getNamesArrays(str2); //On récupère l'arraylist getNamesArrays de InfrParser

								int i = 0;

								for (LatLng latLng : listArr) {

									markerOpt = new MarkerOptions();
									//Inversion de longitude et latitude 
									LatLng tempo = new LatLng(latLng.longitude,
											latLng.latitude);
									markerOpt.position(tempo);
									//On récupère chaque titre correspondant à une paire de coordonnées pour les afficher en tant que titre sur les markers
									markerOpt.title(listArray.get(i));

									Marker marker = map.addMarker(markerOpt);
									//Nous avons créé une variable globale qui est une arraylist de marker. Cette liste est utilisée pour afficher ou cacher les infrastructures
									mArray.add(marker);
									i++;
								}

							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							break;
						case 1:
							//On parcourt l'arraylist de marker 
							for (Marker m : mArray) {
								
								//On supprime les markers de la carte
								m.remove();
							}
							//On vide l'arraylist pour éviter de la redondance lorsque l'utilisateur souhaite de nouveau afficher les infrastructures
							mArray.clear();
						default:
							break;
						}

					}

				});


		alertDialogBuilder.setPositiveButton("Ok",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int id) {

					}

				});

		AlertDialog alertDialog = alertDialogBuilder.create();

		// Affichage de l'alertdialog

		alertDialog.show();

	}

	/**
	 * Méthode qui genère le dialog de bienvenue au démarrage de l'application
	 */
	public void startWindow() {
		dialogBienvenu = new Dialog(this);
		dialogBienvenu.setContentView(R.layout.popup_window_info);
		dialogBienvenu.setTitle("SportIt");

		TextView txt = (TextView) dialogBienvenu.findViewById(R.id.infoTxtView);
		txt.setText(Html.fromHtml(getString(R.string.Bienvenue))); //On récupère le string de strings.xml qui affiche du HTML.
		txt.setMovementMethod(ScrollingMovementMethod.getInstance()); //Permet au textview de permettre le scroll
		txt.setMovementMethod(LinkMovementMethod.getInstance()); // Etant donné qu'il y a un hyperlien dans le text HTML, ceci est nécessaire pour qu'il soit clickable

		buttonInfo = (Button) dialogBienvenu.findViewById(R.id.buttonInfoClose);
		buttonInfo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialogBienvenu.dismiss();

			}
		});

		dialogBienvenu.show();
	}

	/**
	 * Méthode qui génère le dialog lorsque "à propos" est cliqué
	 */
	public void aboutWindow() {

		dialogAbout = new Dialog(this);
		dialogAbout.setContentView(R.layout.about_popup);
		dialogAbout.setTitle("À propos...");

		TextView aboutTxt = (TextView) dialogAbout.findViewById(R.id.aboutView);
		aboutTxt.setText(Html.fromHtml(getString(R.string.About)));
		aboutTxt.setMovementMethod(LinkMovementMethod.getInstance());//Etant donné qu'il y a un hyperlien dans le text HTML, ceci est nécessaire pour qu'il soit clickable

		dialogAbout.show();

		buttonAbout = (Button) dialogAbout.findViewById(R.id.buttonClose);
		buttonAbout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialogAbout.dismiss();

			}
		});

	}

}

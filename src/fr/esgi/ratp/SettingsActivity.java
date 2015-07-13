package fr.esgi.ratp;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import fr.esgi.ratp.asyncTask.Asyntask;
import fr.esgi.ratp.db.DataBaseOperations;
import fr.esgi.ratp.utils.Constants;

public class SettingsActivity extends Activity {

	// ayntask
	Asyntask ayntask;
	// Line
	int idLine = 0;
	String nameLine = "";
	String departureLine = "";
	String arrivalLine = "";
	String typeLine = "";

	// Station
	int idStation = 0;
	String nameStation = "";
	String localisation = "";
	// String typeLine = "";
	String latitude = "";
	String longitude = "";

	// Database
	final DataBaseOperations db = new DataBaseOperations(this);
	
	// UI Elements
	Button btPurge, btLoad;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		//Hide Action Bar
		getActionBar().hide();

		// Button Purge Data
		btPurge = (Button) findViewById(R.id.btPurgeData);
		btPurge.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final ProgressDialog progressDialog = ProgressDialog.show(SettingsActivity.this, getResources().getString(R.string.waitTitle), getResources().getString(R.string.waitDescriptionPurge), false);
				progressDialog.setCancelable(false);
				new Thread(new Runnable() {
					@Override
					public void run() {
						db.purgeLineTable();
						db.purgeStationTable();
						progressDialog.dismiss();
					}
				}).start();
			}
		});
		
		// Button Load Data
		btLoad = (Button) findViewById(R.id.btLoadData);
		btLoad.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			/*	final ProgressDialog progressDialog = ProgressDialog.show(SettingsActivity.this, getResources().getString(R.string.waitTitle), getResources().getString(R.string.waitDescriptionLoad), false);
				progressDialog.setCancelable(false);*/
				new Asyntask(db,SettingsActivity.this).execute("stations","lines");
			/*new Thread(new Runnable() {
					@Override
					public void run() {
					//	loadLines(db);
					//	loadStations(db);
					
				//		progressDialog.dismiss();
					}
				}).start();*/
			}
		});
	}

	private void loadStations(final DataBaseOperations db) {		
		AssetManager assetManager = getAssets();
		try {
			
			
		/*	BufferedReader fichierStation = new BufferedReader(new InputStreamReader(assetManager.open(Constants.FILE_RATP_STATION)));	
			String chaine;
			while ((chaine=fichierStation.readLine())!=null) {
				String[] tabChaineStation = chaine.split("#");
				idStation = Integer.parseInt(tabChaineStation[0]);
				nameStation = tabChaineStation[3].trim();
				localisation = tabChaineStation[4];
				typeLine = tabChaineStation[5];
				latitude = tabChaineStation[1];
				longitude = tabChaineStation[2];
				Log.d("station",idStation + "|" + nameStation + "|" + localisation + "|" + typeLine + "|" + latitude + "|" + longitude);
				db.insertStation(idStation, nameStation, localisation, typeLine, latitude, longitude);
			}
			fichierStation.close();*/
		} catch (Exception e) { /* Nothing */ }
	}

	private void loadLines(final DataBaseOperations db) {
		AssetManager assetManager = getAssets();
		try {
		/*	BufferedReader fichierLigne = new BufferedReader(new InputStreamReader(assetManager.open(Constants.FILE_RATP_LINE)));	
			String chaine;
			while((chaine = fichierLigne.readLine())!= null) {
				String[] tabChaine = chaine.split("#",3);
				if ((tabChaine[1].substring(tabChaine[1].indexOf("/")-1,tabChaine[1].indexOf(")")+1).equals(")"))&&(tabChaine[1].substring(tabChaine[1].indexOf("(")-1,tabChaine[1].indexOf("/")+1).equals("/"))) {
					nameLine = tabChaine[1].substring(0,tabChaine[1].indexOf("(")).trim();
					departureLine= "";
					arrivalLine = "";
					typeLine = tabChaine[2].trim();
					idLine = Integer.parseInt(tabChaine[0]);
					Log.d("line",nameLine + "|" + departureLine + "|" + arrivalLine + "|" + typeLine + "|" + idLine);
					db.insertLine(nameLine, departureLine, arrivalLine, typeLine, idLine);
				}
				else if (tabChaine[1].substring(tabChaine[1].indexOf("/")-1,tabChaine[1].indexOf(")")+1).equals(")")) {
					nameLine = tabChaine[1].substring(0,tabChaine[1].indexOf("(")).trim();
					departureLine= tabChaine[1].substring(tabChaine[1].indexOf("(")+1,tabChaine[1].indexOf("/")).trim();
					arrivalLine = "";
					typeLine = tabChaine[2].trim();
					idLine = Integer.parseInt(tabChaine[0]);
					Log.d("line",nameLine + "|" + departureLine + "|" + arrivalLine + "|" + typeLine + "|" + idLine);
					db.insertLine(nameLine, departureLine, arrivalLine, typeLine, idLine);
				}
				else if (tabChaine[1].substring(tabChaine[1].indexOf("(")-1,tabChaine[1].indexOf("/")+1).equals("/")) {
					nameLine = tabChaine[1].substring(0,tabChaine[1].indexOf("(")).trim();
					departureLine= "";
					arrivalLine = tabChaine[1].substring(tabChaine[1].indexOf("/")+1,tabChaine[1].indexOf(")")).trim();
					typeLine = tabChaine[2].trim();
					idLine = Integer.parseInt(tabChaine[0]);
					Log.d("line",nameLine + "|" + departureLine + "|" + arrivalLine + "|" + typeLine + "|" + idLine);
					db.insertLine(nameLine, departureLine, arrivalLine, typeLine, idLine);
				}
				else {
					nameLine = tabChaine[1].substring(0,tabChaine[1].indexOf("(")).trim();
					departureLine= tabChaine[1].substring(tabChaine[1].indexOf("(")+1,tabChaine[1].indexOf("/")).trim();
					arrivalLine = tabChaine[1].substring(tabChaine[1].indexOf("/")+1,tabChaine[1].indexOf(")")).trim();
					typeLine = tabChaine[2].trim();
					idLine = Integer.parseInt(tabChaine[0]);
					Log.d("line",nameLine + "|" + departureLine + "|" + arrivalLine + "|" + typeLine + "|" + idLine);
					db.insertLine(nameLine, departureLine, arrivalLine, typeLine, idLine);
				}

			}
			fichierLigne.close();*/
		} catch (Exception e) { /* Nothing */ }
	}
}

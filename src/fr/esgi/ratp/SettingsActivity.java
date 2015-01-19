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
import fr.esgi.ratp.db.DataBaseOperations;
import fr.esgi.ratp.utils.Constants;

public class SettingsActivity extends Activity {

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		//Hide Action Bar
		getActionBar().hide();

		// Button Load Data
		final Button button = (Button) findViewById(R.id.btLoadData);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				cleanData();
			}
		});
	}

	private void loadStations(final DataBaseOperations db) {		
		AssetManager assetManager = getAssets();
		try {
			BufferedReader fichierStation = new BufferedReader(new InputStreamReader(assetManager.open(Constants.FILE_RATP_STATION)));	
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
			fichierStation.close();
		} catch (Exception e) {
			//TODO: Write exception.
		}
	}

	private void loadLines(final DataBaseOperations db) {
		AssetManager assetManager = getAssets();
		try {
			BufferedReader fichierLigne = new BufferedReader(new InputStreamReader(assetManager.open(Constants.FILE_RATP_LINE)));	
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
			fichierLigne.close();
		} catch (Exception e) {

		}
	}

	public void cleanData() {
		final ProgressDialog progressDialog = ProgressDialog.show(SettingsActivity.this, "Veuillez patienter...", "Chargement des lignes et stations à partir du fichier RATP", false);
		progressDialog.setCancelable(false);
		new Thread(new Runnable() {
			@Override
			public void run() {
				db.purgeLineTable();
				db.purgeStationTable();
				loadLines(db);
				loadStations(db);
				progressDialog.dismiss();
			}
		}).start();
	}
}

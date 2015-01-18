package fr.esgi.ratp;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import fr.esgi.ratp.db.DataBaseOperations;
import fr.esgi.ratp.util.Constants;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends Activity {

	String nameLine = "";
	String departureLine = "";
	String arrivalLine = "";
	String typeLine = "";
	int iDStation = 0;

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

	private void purgeData(DataBaseOperations db) {
		db.purgeData();
	}

	private void loadLines(final DataBaseOperations db) {
		AssetManager assetManager = getAssets();
		try {
			BufferedReader fichierLigne = new BufferedReader(new InputStreamReader(assetManager.open(Constants.FILE_RATP_LINE)));	
			String chaine;
			int i = 1;
			while((chaine = fichierLigne.readLine())!= null) {
				if(i > 1) {
					String[] tabChaine = chaine.split("#",3);
					if ((tabChaine[1].substring(tabChaine[1].indexOf("/")-1,tabChaine[1].indexOf(")")+1).equals(")"))&&(tabChaine[1].substring(tabChaine[1].indexOf("(")-1,tabChaine[1].indexOf("/")+1).equals("/"))) {
						nameLine = tabChaine[1].substring(0,tabChaine[1].indexOf("(")).trim();
						departureLine= "";
						arrivalLine = "";
						typeLine = tabChaine[2].trim();
						iDStation = Integer.parseInt(tabChaine[0]);
						Log.d("line",nameLine + "|" + departureLine + "|" + arrivalLine + "|" + typeLine + "|" + iDStation);
						db.insertLine(nameLine, departureLine, arrivalLine, typeLine, iDStation);
					}
					else if (tabChaine[1].substring(tabChaine[1].indexOf("/")-1,tabChaine[1].indexOf(")")+1).equals(")")) {
						nameLine = tabChaine[1].substring(0,tabChaine[1].indexOf("(")).trim();
						departureLine= tabChaine[1].substring(tabChaine[1].indexOf("(")+1,tabChaine[1].indexOf("/")).trim();
						arrivalLine = "";
						typeLine = tabChaine[2].trim();
						iDStation = Integer.parseInt(tabChaine[0]);
						Log.d("line",nameLine + "|" + departureLine + "|" + arrivalLine + "|" + typeLine + "|" + iDStation);
						db.insertLine(nameLine, departureLine, arrivalLine, typeLine, iDStation);
					}
					else if (tabChaine[1].substring(tabChaine[1].indexOf("(")-1,tabChaine[1].indexOf("/")+1).equals("/")) {
						nameLine = tabChaine[1].substring(0,tabChaine[1].indexOf("(")).trim();
						departureLine= "";
						arrivalLine = tabChaine[1].substring(tabChaine[1].indexOf("/")+1,tabChaine[1].indexOf(")")).trim();
						typeLine = tabChaine[2].trim();
						iDStation = Integer.parseInt(tabChaine[0]);
						Log.d("line",nameLine + "|" + departureLine + "|" + arrivalLine + "|" + typeLine + "|" + iDStation);
						db.insertLine(nameLine, departureLine, arrivalLine, typeLine, iDStation);
					}
					else {
						nameLine = tabChaine[1].substring(0,tabChaine[1].indexOf("(")).trim();
						departureLine= tabChaine[1].substring(tabChaine[1].indexOf("(")+1,tabChaine[1].indexOf("/")).trim();
						arrivalLine = tabChaine[1].substring(tabChaine[1].indexOf("/")+1,tabChaine[1].indexOf(")")).trim();
						typeLine = tabChaine[2].trim();
						iDStation = Integer.parseInt(tabChaine[0]);
						Log.d("line",nameLine + "|" + departureLine + "|" + arrivalLine + "|" + typeLine + "|" + iDStation);
						db.insertLine(nameLine, departureLine, arrivalLine, typeLine, iDStation);
					}
				}
				i++;
			}
			fichierLigne.close();
		} catch (Exception e) {
			//TODO: Write exception.
		}
	}

	public void cleanData() {
		final ProgressDialog ringProgressDialog = ProgressDialog.show(SettingsActivity.this, "Veuillez patienter...", "Chargement des lignes et stations à partir du fichier RATP", false);
		ringProgressDialog.setCancelable(true);
		new Thread(new Runnable() {
			@Override
			public void run() {
				purgeData(db);
				loadLines(db);
				ringProgressDialog.dismiss();
			}
		}).start();
	}
}

package fr.esgi.ratp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import fr.esgi.ratp.asynctask.Asyntask;
import fr.esgi.ratp.db.DataBaseOperations;

public class SettingsActivity extends Activity {

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
				// Load all lines and stations in database.
				new Asyntask(db,SettingsActivity.this).execute("stations","lines");
			}
		});
	}
}

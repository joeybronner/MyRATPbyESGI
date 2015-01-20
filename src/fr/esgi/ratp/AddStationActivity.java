package fr.esgi.ratp;

import java.util.Random;

import fr.esgi.ratp.db.DataBaseOperations;
import fr.esgi.ratp.objects.Station;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddStationActivity extends Activity {

	int newID;
	String line, type;
	EditText etNewStationName, etNewStationLocalisation, etNewStationLatitude, etNewStationLongitude;
	String errormsg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_station);

		// Hide action bar
		getActionBar().hide();

		// Load type of lines
		Intent myIntent = getIntent();
		type = myIntent.getStringExtra("type");
		line = myIntent.getStringExtra("line");

		// Database
		final DataBaseOperations db = new DataBaseOperations(this);

		// Create unique ID
		do {
			Random rand = new Random();
			newID = rand.nextInt((100000 - 0) + 1) + 0;
		} while(!db.isStationIDUnique(newID));

		final EditText etNewStationID = (EditText) findViewById(R.id.etNewStationID);
		etNewStationID.setText(String.valueOf(newID));

		final EditText etNewStationLine = (EditText) findViewById(R.id.etNewStationLine);
		etNewStationLine.setText(line.substring(6));
		
		final EditText etNewStationType = (EditText) findViewById(R.id.etNewStationType);
		etNewStationType.setText(type);
		
		etNewStationName = (EditText) findViewById(R.id.etNewStationName);
		etNewStationLocalisation = (EditText) findViewById(R.id.etNewStationLocalisation);
		etNewStationLatitude = (EditText) findViewById(R.id.etNewStationLatitude);
		etNewStationLongitude = (EditText) findViewById(R.id.etNewStationLongitude);
		
		// Button Save changes
		final Button button = (Button) findViewById(R.id.btAddNewStation);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					// Check if all fields are completed
					if (!fieldsCompleted()) {
						errormsg = getResources().getString(R.string.msgErrorFieldsNotCompleted);
						throw new Exception();
					}
					
					// Check if station exists
					Station s = db.getStation(etNewStationName.getText().toString().trim(), type);
					if (s.getIDStation()!=0) {
						errormsg = getResources().getString(R.string.msgErrorStationExists);
						throw new Exception();
					}
					
					// Insert into station table
					db.insertLine(etNewStationLine.getText().toString().trim(), "", "", 
							etNewStationType.getText().toString(),
							Integer.parseInt(etNewStationID.getText().toString()));
					
					// Insert into line table
					db.insertStation(Integer.parseInt(etNewStationID.getText().toString()), 
							etNewStationName.getText().toString().trim(), 
							etNewStationLocalisation.getText().toString().trim(), 
							etNewStationType.getText().toString().trim(), 
							etNewStationLatitude.getText().toString().trim(), 
							etNewStationLongitude.getText().toString().trim());
					
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.msgInsertOK), Toast.LENGTH_SHORT).show();
					finish();
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), errormsg, Toast.LENGTH_SHORT).show();
				}

			}

			private boolean fieldsCompleted() {
				// Name
				if (etNewStationName.getText().toString().trim().equals("")) {
					return false;
				}
				
				// Localisation
				if (etNewStationLocalisation.getText().toString().trim().equals("")) {
					return false;
				}
				
				// Latitude
				if (etNewStationLatitude.getText().toString().trim().equals("")) {
					return false;
				}
				
				// Longitude
				if (etNewStationLongitude.getText().toString().trim().equals("")) {
					return false;
				}
				return true;
			}
		});
		
	}

}

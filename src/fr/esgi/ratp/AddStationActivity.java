package fr.esgi.ratp;

import java.util.Random;

import fr.esgi.ratp.db.DataBaseOperations;
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

		EditText etNewStationID = (EditText) findViewById(R.id.etNewStationID);
		etNewStationID.setText(String.valueOf(newID));

		EditText etNewStationLine = (EditText) findViewById(R.id.etNewStationLine);
		etNewStationLine.setText(line.substring(6));
		
		EditText etNewStationType = (EditText) findViewById(R.id.etNewStationType);
		etNewStationType.setText(type);
		
		// Button Save changes
		final Button button = (Button) findViewById(R.id.btAddNewStation);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					// Check if all fields are completed
					// Check if station exixsts
					// Insert into station table
					// Insert into line table
					
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.msgInsertOK), Toast.LENGTH_SHORT).show();
					finish();
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.msgInsertError), Toast.LENGTH_SHORT).show();
				}

			}
		});
	}

}

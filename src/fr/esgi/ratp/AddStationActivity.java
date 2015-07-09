package fr.esgi.ratp;

import java.util.Random;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import fr.esgi.ratp.db.DataBaseOperations;
import fr.esgi.ratp.objects.Station;
import fr.esgi.ratp.utils.Constants;

public class AddStationActivity extends Activity {

	int newID;
	String line, type;
	EditText etNewStationName, etNewStationLocalisation, etNewStationLatitude, etNewStationLongitude;
	String errormsg;
	Button bttAdd;
	DataBaseOperations db;
	EditText etNewStationID,etNewStationLine, etNewStationType; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_station);

		// Load type of lines
		try {
		Intent myIntent = getIntent();
		type = myIntent.getStringExtra("type");
		line = myIntent.getStringExtra("line");
		} catch (Exception e) {
			type = Constants.TYPE;
			line = Constants.LINE;
		}

		bttAdd = (Button) findViewById(R.id.btAddNewStation);

		setActivityAndButtonColors();

		// Database
		db = new DataBaseOperations(this);
		
		// Actionbar Title
		setTitle(getResources().getString(R.string.titleAddStation));
		getActionBar().setIcon(R.drawable.station);

		// Create unique ID
		newID = generateNewID();
		
		// Load all EditText values & fields
		loadAllEditText();

		// Button Save changes
		bttAdd.setOnClickListener(new View.OnClickListener() {
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
		});
	}
	
	private boolean fieldsCompleted() {
		// Name
		if (etNewStationName.getText().toString().trim().equals(""))
			return false;

		// Localisation
		if (etNewStationLocalisation.getText().toString().trim().equals(""))
			return false;

		// Latitude
		if (etNewStationLatitude.getText().toString().trim().equals(""))
			return false;

		// Longitude
		if (etNewStationLongitude.getText().toString().trim().equals(""))
			return false;
		
		return true;
	}
	
	private int generateNewID() {
		int id;
		do {
			Random rand = new Random();
			id = rand.nextInt((100000 - 0) + 1) + 0;
		} while(!db.isStationIDUnique(newID));
		return id;
	}
	
	private void loadAllEditText() {
		etNewStationID = (EditText) findViewById(R.id.etNewStationID);
		etNewStationID.setText(String.valueOf(newID));
		etNewStationLine = (EditText) findViewById(R.id.etNewStationLine);
		etNewStationLine.setText(line.substring(6));
		etNewStationType = (EditText) findViewById(R.id.etNewStationType);
		etNewStationType.setText(type);
		etNewStationName = (EditText) findViewById(R.id.etNewStationName);
		etNewStationLocalisation = (EditText) findViewById(R.id.etNewStationLocalisation);
		etNewStationLatitude = (EditText) findViewById(R.id.etNewStationLatitude);
		etNewStationLongitude = (EditText) findViewById(R.id.etNewStationLongitude);
	}

	private void setActivityAndButtonColors() {
		if (type.equals("metro")) {
			setActivityBackgroundColor(getResources().getColor(R.color.orange));
			bttAdd.setBackgroundColor(getResources().getColor(R.color.darkorange));;
			getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.orange)));
		} else if (type.equals("rer")) {
			setActivityBackgroundColor(getResources().getColor(R.color.black));
			bttAdd.setBackgroundColor(getResources().getColor(R.color.silver));
			getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));
		} else if (type.equals("tram")) {
			setActivityBackgroundColor(getResources().getColor(R.color.darkblue));
			bttAdd.setBackgroundColor(getResources().getColor(R.color.blue));
			getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.darkblue)));
		} else if (type.equals("bus")) {
			setActivityBackgroundColor(getResources().getColor(R.color.green));
			bttAdd.setBackgroundColor(getResources().getColor(R.color.lightgreen));
			getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.green)));
		}
	}

	private void setActivityBackgroundColor(int color) {
		Fragment currentFragment = this.getFragmentManager().findFragmentById(R.id.fragment_lines);
		View view = currentFragment.getView();
		view.setBackgroundColor(color);
	}

}

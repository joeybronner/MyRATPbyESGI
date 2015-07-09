package fr.esgi.ratp;

import android.annotation.SuppressLint;
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

@SuppressLint("DefaultLocale")
public class StationActivity extends Activity {

	String stationID, stationName, stationLatitude, stationLongitude, stationType, stationLine, stationLocalisation;
	Button btSave;
	DataBaseOperations db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_station);

		// Load type of lines
		Intent myIntent = getIntent();
		stationType = myIntent.getStringExtra("type");
		stationLine = myIntent.getStringExtra("line");
		stationName = myIntent.getStringExtra("station");
		
		btSave = (Button) findViewById(R.id.btSave);
		
		// Set Image following the type of transport
		setImageOfTransportType();

		// Change Title content
		getActionBar().setTitle(stationName.toUpperCase());

		// Load data
		db = new DataBaseOperations(this);
		Station s = db.getStation(stationName, stationType);

		EditText etStationID = (EditText) findViewById(R.id.etStationID);
		stationID = String.valueOf(s.getIDStation());
		etStationID.setText(stationID);

		final EditText etStationName = (EditText) findViewById(R.id.etStationName);
		etStationName.setText(stationName);

		EditText etStationType = (EditText) findViewById(R.id.etStationType);
		etStationType.setText(stationType);

		final EditText etStationLatitude = (EditText) findViewById(R.id.etStationLatitude);
		stationLatitude = s.getLatitude();
		etStationLatitude.setText(s.getLatitude());

		final EditText etStationLocalisation = (EditText) findViewById(R.id.etStationLocalisation);
		stationLocalisation = s.getLocalisation();
		etStationLocalisation.setText(stationLocalisation);

		final EditText etStationLongitude = (EditText) findViewById(R.id.etStationLongitude);
		stationLongitude = s.getLongitude();
		etStationLongitude.setText(s.getLongitude());

		// Button Save changes
		btSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					db.updateStation(stationID, etStationName.getText().toString(), etStationLatitude.getText().toString(), 
							etStationLongitude.getText().toString(), stationType, etStationLocalisation.getText().toString());
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.msgUpdateOK), Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.msgUpdateError), Toast.LENGTH_SHORT).show();
				}

			}
		});
	}
	
	private void setImageOfTransportType() {
		if (stationType.equals("metro")) {
			getActionBar().setIcon(R.drawable.metro);
			getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.orange)));
			setActivityBackgroundColor(getResources().getColor(R.color.orange));
			btSave.setBackgroundColor(getResources().getColor(R.color.darkorange));
		} else if (stationType.equals("rer")) {
			getActionBar().setIcon(R.drawable.rer);
			getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));
			setActivityBackgroundColor(getResources().getColor(R.color.black));
			btSave.setBackgroundColor(getResources().getColor(R.color.silver));
		} else if (stationType.equals("tram")) {
			getActionBar().setIcon(R.drawable.tramway);
			getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.darkblue)));
			setActivityBackgroundColor(getResources().getColor(R.color.darkblue));
			btSave.setBackgroundColor(getResources().getColor(R.color.blue));
		} else if (stationType.equals("bus")) {
			getActionBar().setIcon(R.drawable.bus);
			getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.green)));
			setActivityBackgroundColor(getResources().getColor(R.color.green));
			btSave.setBackgroundColor(getResources().getColor(R.color.lightgreen));
		}
	}

	private void setActivityBackgroundColor(int color) {
		Fragment currentFragment = this.getFragmentManager().findFragmentById(R.id.fragment_lines);
		View view = currentFragment.getView();
		view.setBackgroundColor(color);
	}

}

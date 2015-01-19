package fr.esgi.ratp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import fr.esgi.ratp.db.DataBaseOperations;
import fr.esgi.ratp.objects.Station;

@SuppressLint("DefaultLocale")
public class StationActivity extends Activity {

	String stationID, stationName, stationLatitude, stationLongitude, stationType, stationLine, stationLocalisation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_station);
		
		// Hide action bar
		getActionBar().hide();
		
		// Load type of lines
		Intent myIntent = getIntent();
		stationType = myIntent.getStringExtra("type");
		stationLine = myIntent.getStringExtra("line");
		stationName = myIntent.getStringExtra("station");
		
		// Change Title content
		TextView tvTitle = (TextView) findViewById(R.id.tvTitleStation);
		tvTitle.setText(stationName.toUpperCase());
		
		// Load data
		final DataBaseOperations db = new DataBaseOperations(this);
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
		final Button button = (Button) findViewById(R.id.btSave);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				db.updateStation(stationID, etStationName.getText().toString(), etStationLatitude.getText().toString(), 
						etStationLongitude.getText().toString(), stationType, etStationLocalisation.getText().toString());
			}
		});
	}

}
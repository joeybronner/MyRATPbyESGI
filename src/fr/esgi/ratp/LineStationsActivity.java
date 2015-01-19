package fr.esgi.ratp;

import fr.esgi.ratp.db.DataBaseOperations;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class LineStationsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_line_stations);
		
		// Hide Action Bar
		getActionBar().hide();
		
		// Get Stations
		final DataBaseOperations db = new DataBaseOperations(this);
		int nb = db.numberOfRowsTableStation();
		
		Toast.makeText(getApplicationContext(), "size ; " + nb, Toast.LENGTH_SHORT).show();
	}
}

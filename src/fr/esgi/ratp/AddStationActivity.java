package fr.esgi.ratp;

import android.app.Activity;
import android.os.Bundle;

public class AddStationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_station);
		
		// Hide action bar
		getActionBar().hide();
		
		
	}

}

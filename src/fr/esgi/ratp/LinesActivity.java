package fr.esgi.ratp;

import android.app.Activity;
import android.os.Bundle;

public class LinesActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lines);
		
		// Hide Action Bar
		getActionBar().hide();
	}

}

package fr.esgi.ratp.utils;

import fr.esgi.ratp.R;
import android.app.Application;

public class LaunchApp extends Application {
	
	// Global variables
	public static String underground;
	public static String bus;
	public static String tramway;
	public static String rer;
	public static String settings;

	@Override
	public void onCreate() {
		super.onCreate();
		
		// Retrieve data from resources
		underground = getString(R.string.underground);
		bus = getString(R.string.bus);
		tramway = getString(R.string.tramway);
		rer = getString(R.string.rer);
		settings = getString(R.string.settings);
	}

}

package fr.esgi.ratp;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import fr.esgi.ratp.db.DataBaseOperations;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;

public class LinesActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lines);

		// Hide Action Bar
		getActionBar().hide();

	}
}
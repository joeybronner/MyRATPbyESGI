package fr.esgi.ratp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import fr.esgi.ratp.db.DataBaseOperations;
import fr.esgi.ratp.objects.Line;

public class LinesActivity extends Activity {

	ListView listLine;
	ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lines);

		// Hide Action Bar
		getActionBar().hide();

		// Load type of lines
		Intent myIntent = getIntent();
		String type = myIntent.getStringExtra("type");

		// Load data from database
		final DataBaseOperations db = new DataBaseOperations(this);
		ArrayList<Line> lines = db.getAllLinesByType(type);
		String[] allLines = new String[lines.size()];
		int i = 0;
		for (Line line : lines) {
			allLines[i] = "Ligne " + line.getNameLine();
			i++;
		}

		// Load lines
		listLine = (ListView) findViewById(R.id.listLine);

		// Assign adapter to ListView
		adapter = new ArrayAdapter<String>(this, R.layout.textview_style, allLines);
		listLine.setAdapter(adapter);
		listLine.setTextFilterEnabled(true);

		// ListView Item Click Listener
		listLine.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// View line detail
			}
		});

		EditText etSearch = (EditText) findViewById(R.id.etSearch);
		etSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				adapter.getFilter().filter(s.toString());
			}
		});

	}
}
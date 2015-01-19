package fr.esgi.ratp;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import fr.esgi.ratp.db.DataBaseOperations;
import fr.esgi.ratp.objects.Station;
import fr.esgi.ratp.utils.Utilities;

@SuppressLint("DefaultLocale")
public class LineStationsActivity extends Activity {

	ListView listStation;
	ArrayAdapter<String> adapter;
	String type, line, station;
	int selectedPosition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_line_stations);

		// Hide Action Bar
		getActionBar().hide();

		// Get Stations
		final DataBaseOperations db = new DataBaseOperations(this);

		// Load type of lines
		Intent myIntent = getIntent();
		type = myIntent.getStringExtra("type");
		line = myIntent.getStringExtra("line");

		// Change Title content
		TextView tvTitle = (TextView) findViewById(R.id.tvTitleWithStation);
		tvTitle.setText(type.toUpperCase() + " > " + line);

		// Change image
		ImageView ivType = (ImageView) findViewById(R.id.ivTypeLineStation);
		if (type.equals("metro")) {
			ivType.setImageResource(R.drawable.metro);
		} else if (type.equals("rer")) {
			ivType.setImageResource(R.drawable.rer);
		} else if (type.equals("tram")) {
			ivType.setImageResource(R.drawable.tramway);
		} else if (type.equals("bus")) {
			ivType.setImageResource(R.drawable.bus);
		}

		// Load data from database
		ArrayList<Station> stations = db.getAllStationsByLine(type, line.substring(6));
		String[] allStations = new String[stations.size()];
		int i = 0;
		for (Station station : stations) {
			allStations[i] = station.getNameStation();
			i++;
		}
		
		// Remove null values
		allStations = removeNullValues(allStations);

		// Load station
		listStation = (ListView) findViewById(R.id.listStation);

		// Assign adapter to ListView
		adapter = new ArrayAdapter<String>(this, R.layout.textview_style, allStations);
		listStation.setAdapter(adapter);
		listStation.setTextFilterEnabled(true);

		// ListView Item Click Listener
		listStation.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// Open station detail
				String selectedStation = listStation.getItemAtPosition(position).toString();
				Utilities.openView(LineStationsActivity.this, StationActivity.class, type, line, selectedStation);
			}
		});

		// ListView Item LONGClick Listener
		listStation.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {

				selectedPosition = pos;

				DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which){
						case DialogInterface.BUTTON_POSITIVE:
							String selectedStation = listStation.getItemAtPosition(selectedPosition).toString();
							Station s = db.getStation(selectedStation, type);
							db.deleteStation(s.getIDStation());
							break;

						case DialogInterface.BUTTON_NEGATIVE:
							break; // Nothing
						}
					}
				};
				AlertDialog.Builder builder = new AlertDialog.Builder(LineStationsActivity.this);
				builder.setMessage(getResources().getString(R.string.msgDelete))
				.setPositiveButton(getResources().getString(R.string.yes), dialogClickListener)
				.setNegativeButton(getResources().getString(R.string.no), dialogClickListener).show();
				return true;
			}
		}); 

		EditText etSearch = (EditText) findViewById(R.id.etSearchStation);
		etSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				adapter.getFilter().filter(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

			@Override
			public void afterTextChanged(Editable s) { }
		});

		ImageView btAddStation = (ImageView) findViewById(R.id.btAddStation);
		btAddStation.setOnClickListener(new View.OnClickListener() {
			//@Override
			public void onClick(View v) {
				Utilities.openView(LineStationsActivity.this, AddStationActivity.class, type, line);
			}        
		});
	}

	private String[] removeNullValues(String[] firstArray) {
		List<String> list = new ArrayList<String>();

		for(String s : firstArray) {
			if(s != null && s.length() > 0) {
				list.add(s);
			}
		}
		return list.toArray(new String[list.size()]);
	}
}

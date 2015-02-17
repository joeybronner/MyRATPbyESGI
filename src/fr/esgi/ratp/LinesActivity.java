package fr.esgi.ratp;

import java.util.ArrayList;
import java.util.Arrays;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import fr.esgi.ratp.db.DataBaseOperations;
import fr.esgi.ratp.objects.Line;
import fr.esgi.ratp.utils.Utilities;

public class LinesActivity extends Activity {

	ListView listLine;
	ArrayAdapter<String> adapter;
	String type, lineName;
	EditText etSearch;
	TextView tvTitle;
	ImageView ivType;
	DataBaseOperations db;
	String[] allLines, allLinesString;
	int[] allLinesInteger;
	

	@SuppressLint("DefaultLocale") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lines);

		// Hide Action Bar
		getActionBar().hide();

		// Load type of lines
		Intent myIntent = getIntent();
		type = myIntent.getStringExtra("type");

		// Load data from database
		db = new DataBaseOperations(this);
		ArrayList<Line> lines = db.getAllLinesByType(type);
		allLines= new String[lines.size()];
		
		// Add all Lines to Array
		loadAllLinesToArray(lines);
		
		// Sort Values
		sortLines(lines);
		
		// Change Title content
		tvTitle = (TextView) findViewById(R.id.tvTitle);
		tvTitle.setText(type.toUpperCase());

		// Change image
		ivType = (ImageView) findViewById(R.id.ivType);
		setImageOfTransportType();

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
				// Open Line detail
				String selectedLine = listLine.getItemAtPosition(position).toString();
				Utilities.openView(LinesActivity.this, LineStationsActivity.class, type, selectedLine);
			}
		});

		etSearch = (EditText) findViewById(R.id.etSearch);
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
	
	private void sortLines(ArrayList<Line> lines) {
		sort(allLinesInteger);
		for (int j=0;j<allLinesInteger.length;j++){
			allLines[j] = "Ligne "+allLinesInteger[j];
		}
		// Sort string value 
		Arrays.sort(allLinesString);

		for (int h=0;h<lines.size()-allLinesInteger.length;h++){
			allLines[h+allLinesInteger.length] = "Ligne "+allLinesString[h];
		}
	}
	
	private void loadAllLinesToArray(ArrayList<Line> lines) {
		int i = 0;
		int k = 0;
		allLinesInteger = new int[numberInteger(lines)];
		allLinesString = new String[lines.size()-numberInteger(lines)];
		for (Line line : lines) {
			// all lines integer 
			if (isInteger(line.getNameLine())==true){
				allLinesInteger[i] = Integer.parseInt(line.getNameLine());
				i++;
			}
			// all lignes contains strings values
			else {
				allLinesString[k] = line.getNameLine();
				k++;
			}
		}
	}

	private static boolean isInteger(String val) {
		boolean state=true;
		byte[] bytes = val.getBytes();
		for (int i = 0; i < bytes.length; i++) {
			if (!Character.isDigit((char) bytes[i])) {
				state=false;
			}
		}
		return state;
	}

	private static int numberInteger(ArrayList<Line> lines)
	{
		int numberInteger=0;
		for (Line line : lines) {
			Log.d("value",line.getNameLine());
			if (isInteger(line.getNameLine())==true){
				numberInteger++;
			}
		}
		return numberInteger;
	}

	private int[] sort(int[] table) {
		for(int i=0;i<table.length;i++) {
			for(int j=i+1;j<table.length;j++) {
				if(table[i]>table[j]) {
					int temp=table[i];
					table[i]=table[j];
					table[j]=temp;
				}
			}
		}
		return table;
	}

	private void setImageOfTransportType() {
		if (type.equals("metro")) {
			ivType.setImageResource(R.drawable.metro);
			setActivityBackgroundColor(getResources().getColor(R.color.orange));
		} else if (type.equals("rer")) {
			ivType.setImageResource(R.drawable.rer);
			setActivityBackgroundColor(getResources().getColor(R.color.black));
		} else if (type.equals("tram")) {
			ivType.setImageResource(R.drawable.tramway);
			setActivityBackgroundColor(getResources().getColor(R.color.darkblue));
		} else if (type.equals("bus")) {
			ivType.setImageResource(R.drawable.bus);
			setActivityBackgroundColor(getResources().getColor(R.color.green));
		}
	}

	private void setActivityBackgroundColor(int color) {
		View view = this.getWindow().getDecorView();
		view.setBackgroundColor(color);
	}
}
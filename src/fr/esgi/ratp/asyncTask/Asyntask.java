package fr.esgi.ratp.asynctask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import fr.esgi.ratp.R;
import fr.esgi.ratp.db.DataBaseOperations;

public class Asyntask extends AsyncTask<String, Void, String> {

	public  DataBaseOperations db; 
	public Context context;

	public Asyntask(DataBaseOperations db,Context context) {
		super();
		this.db = db;
		this.context=context;
	}

	// Line
	int idLine = 0;
	String nameLine = "";
	String departureLine = "";
	String arrivalLine = "";
	String typeLine = "";

	// Station
	int idStation = 0;
	String nameStation = "";
	String localisation = "";
	// String typeLine = "";
	String latitude = "";
	String longitude = "";

	// Database
	public ProgressDialog progressDialog ;
	public static final String stationUrl=" http://joeybronner.fr/myratpbyesgi/ratpStation.csv";

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progressDialog = ProgressDialog.show(context,context.getApplicationContext().getResources().getString(R.string.waitTitle), context.getApplicationContext().getResources().getString(R.string.waitDescriptionLoad), false);
		progressDialog.setCancelable(false);
	}
	
	@Override
	protected String doInBackground(String... params) {
		URL lineUrl ;
		try {
			if (params[1]=="lines") {
				lineUrl= new URL("http://joeybronner.fr/myratpbyesgi/ratp.csv") ;
				BufferedReader in = new BufferedReader(new InputStreamReader(lineUrl.openStream()));
				String line = null;
				while ((line = in.readLine()) != null) {
					String[] tabChaine = line.split("#",3);
					if ((tabChaine[1].substring(tabChaine[1].indexOf("/")-1,tabChaine[1].indexOf(")")+1).equals(")"))&&(tabChaine[1].substring(tabChaine[1].indexOf("(")-1,tabChaine[1].indexOf("/")+1).equals("/"))) {
						nameLine = tabChaine[1].substring(0,tabChaine[1].indexOf("(")).trim();
						departureLine= "";
						arrivalLine = "";
						typeLine = tabChaine[2].trim();
						idLine = Integer.parseInt(tabChaine[0]);
						db.insertLine(nameLine, departureLine, arrivalLine, typeLine, idLine);
					}
					else if (tabChaine[1].substring(tabChaine[1].indexOf("/")-1,tabChaine[1].indexOf(")")+1).equals(")")) {
						nameLine = tabChaine[1].substring(0,tabChaine[1].indexOf("(")).trim();
						departureLine= tabChaine[1].substring(tabChaine[1].indexOf("(")+1,tabChaine[1].indexOf("/")).trim();
						arrivalLine = "";
						typeLine = tabChaine[2].trim();
						idLine = Integer.parseInt(tabChaine[0]);
						db.insertLine(nameLine, departureLine, arrivalLine, typeLine, idLine);
					}
					else if (tabChaine[1].substring(tabChaine[1].indexOf("(")-1,tabChaine[1].indexOf("/")+1).equals("/")) {
						nameLine = tabChaine[1].substring(0,tabChaine[1].indexOf("(")).trim();
						departureLine= "";
						arrivalLine = tabChaine[1].substring(tabChaine[1].indexOf("/")+1,tabChaine[1].indexOf(")")).trim();
						typeLine = tabChaine[2].trim();
						idLine = Integer.parseInt(tabChaine[0]);
						db.insertLine(nameLine, departureLine, arrivalLine, typeLine, idLine);
					}
					else {
						nameLine = tabChaine[1].substring(0,tabChaine[1].indexOf("(")).trim();
						departureLine= tabChaine[1].substring(tabChaine[1].indexOf("(")+1,tabChaine[1].indexOf("/")).trim();
						arrivalLine = tabChaine[1].substring(tabChaine[1].indexOf("/")+1,tabChaine[1].indexOf(")")).trim();
						typeLine = tabChaine[2].trim();
						idLine = Integer.parseInt(tabChaine[0]);
						db.insertLine(nameLine, departureLine, arrivalLine, typeLine, idLine);
					}
				}

			}
			if (params[0]=="stations") {
				lineUrl= new URL("http://joeybronner.fr/myratpbyesgi/ratpStation.csv") ;
				BufferedReader in = new BufferedReader(new InputStreamReader(lineUrl.openStream()));
				String line = null;
				while ((line = in.readLine()) != null) {
					String[] tabChaineStation = line.split("#");
					idStation = Integer.parseInt(tabChaineStation[0]);
					nameStation = tabChaineStation[3].trim();
					localisation = tabChaineStation[4];
					typeLine = tabChaineStation[5];
					latitude = tabChaineStation[1];
					longitude = tabChaineStation[2];
					db.insertStation(idStation, nameStation, localisation, typeLine, latitude, longitude);
				}
				in.close();
			}
		} catch(MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	protected void onPostExecute(String result) {
		progressDialog.dismiss();
	}

}

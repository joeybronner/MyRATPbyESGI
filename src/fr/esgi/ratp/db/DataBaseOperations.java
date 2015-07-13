package fr.esgi.ratp.db;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import fr.esgi.ratp.asyncTask.Asyntask;
import fr.esgi.ratp.objects.Line;
import fr.esgi.ratp.objects.Station;

public class DataBaseOperations extends SQLiteOpenHelper {

	// GLOBAL
	public static final String DATABASE_NAME = "ratp";

	// LINE
	public static final String LINE_TABLE_NAME = "line";
	public static final String LINE_COLUMN_IDLINE = "idLine";
	public static final String LINE_COLUMN_NAMELINE = "nameLine";
	public static final String LINE_COLUMN_DEPARTURELINE = "departureLine";
	public static final String LINE_COLUMN_ARRIVALLINE = "arrivalLine";
	public static final String LINE_COLUMN_TYPELINE = "typeLine";

	// STATION
	public static final String STATION_TABLE_NAME = "station";
	public static final String STATION_COLUMN_IDSTATION = "idStation";
	public static final String STATION_COLUMN_NAMESTATION= "nameStation";
	public static final String STATION_COLUMN_LATITUDE = "latitudeStation";
	public static final String STATION_COLUMN_LONGITUDE = "longitudeStation";
	public static final String STATION_COLUMN_LOCALISATION = "localisationStation";
	public static final String STATION_COLUMN_TYPE = "typeStation";

	public DataBaseOperations(Context context) {
		super(context, DATABASE_NAME , null, 1);
	}



	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(
				"create table line " +
						"(idLine integer primary key,nameLine text,departureLine text,arrivalLine text, typeLine text,iDStation integer)"
				);

		db.execSQL(
				"create table station " +
						"(idStation integer primary key,nameStation text,latitudeStation text,longitudeStation text, typeStation text, localisationStation text)"
				);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + LINE_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + STATION_TABLE_NAME);
		onCreate(db);
	}

	public boolean insertStation(int idStation, String nameStation, String localisation, 
			String typeLine, String latitude, String longitude) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(STATION_COLUMN_IDSTATION, String.valueOf(idStation));
		contentValues.put(STATION_COLUMN_NAMESTATION, nameStation);
		contentValues.put(STATION_COLUMN_LATITUDE, latitude);	
		contentValues.put(STATION_COLUMN_LONGITUDE, longitude);
		contentValues.put(STATION_COLUMN_TYPE, typeLine);
		contentValues.put(STATION_COLUMN_LOCALISATION, localisation);
		db.insert(STATION_TABLE_NAME, null, contentValues);
		return true;
	}

	public boolean insertLine(String nameLine, String departureLine, String arrivalLine, String typeLine, int idLine) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(LINE_COLUMN_NAMELINE, nameLine);
		contentValues.put(LINE_COLUMN_DEPARTURELINE, departureLine);
		contentValues.put(LINE_COLUMN_ARRIVALLINE, arrivalLine);	
		contentValues.put(LINE_COLUMN_TYPELINE, typeLine);
		contentValues.put(LINE_COLUMN_IDLINE, String.valueOf(idLine));
		db.insert(LINE_TABLE_NAME, null, contentValues);
		return true;
	}

	public int numberOfRowsTableLine(){
		SQLiteDatabase db = this.getReadableDatabase();
		int numRows = (int) DatabaseUtils.queryNumEntries(db, LINE_TABLE_NAME);
		return numRows;
	}

	public int numberOfRowsTableStation(){
		SQLiteDatabase db = this.getReadableDatabase();
		int numRows = (int) DatabaseUtils.queryNumEntries(db, STATION_TABLE_NAME);
		return numRows;
	}

	public void purgeLineTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DELETE FROM "+ LINE_TABLE_NAME);
	}

	public void purgeStationTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DELETE FROM "+ STATION_TABLE_NAME);
	}

	public boolean updateStation(String stationID, String stationName, String stationLatitude, 
			String stationLongitude, String stationType, String stationLocalisation) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(STATION_COLUMN_NAMESTATION, stationName);
		contentValues.put(STATION_COLUMN_LONGITUDE, stationLongitude);
		contentValues.put(STATION_COLUMN_LATITUDE, stationLatitude);
		contentValues.put(STATION_COLUMN_LOCALISATION, stationLocalisation);	
		contentValues.put(STATION_COLUMN_IDSTATION, stationID);
		db.update("station", contentValues, STATION_COLUMN_IDSTATION + " = ? ", new String[] { stationID } );
		return true;
	}

	public Station getStation(String name, String type) {
		// Seach Station
		Station s = new Station();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res =  db.rawQuery("SELECT * " +
				" FROM " + STATION_TABLE_NAME +
				" WHERE " + STATION_COLUMN_NAMESTATION + "=\"" + name + "\"" +
				" AND " + STATION_COLUMN_TYPE + "=\"" + type + "\"", 
				null );
		res.moveToFirst();
		while(res.isAfterLast() == false) {
			s.setIDStation(res.getInt(res.getColumnIndex(STATION_COLUMN_IDSTATION)));
			s.setNameStation(res.getString(res.getColumnIndex(STATION_COLUMN_NAMESTATION)));
			s.setLatitude(res.getString(res.getColumnIndex(STATION_COLUMN_LATITUDE)));
			s.setLongitude(res.getString(res.getColumnIndex(STATION_COLUMN_LONGITUDE)));
			s.setTypeLine(res.getString(res.getColumnIndex(STATION_COLUMN_TYPE)));
			s.setLocalisation(res.getString(res.getColumnIndex(STATION_COLUMN_LOCALISATION)));
			res.moveToNext();
		}
		return s;
	}

	public void deleteStation (Integer id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(STATION_TABLE_NAME, STATION_COLUMN_IDSTATION + " = ? ", new String[] { Integer.toString(id) });
	}

	public ArrayList<Station> getAllStationsByLine(String type, String line) {
		// Seach IDs
		ArrayList<Station> stations = new ArrayList<Station>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res =  db.rawQuery("SELECT " + LINE_COLUMN_IDLINE +
				" FROM " + LINE_TABLE_NAME +
				" WHERE " + LINE_COLUMN_NAMELINE + "=\"" + line + "\"" +
				" AND " + LINE_COLUMN_TYPELINE + "=\"" + type + "\"", 
				null );
		res.moveToFirst();
		while(res.isAfterLast() == false){
			Station s = new Station();
			s.setIDStation(res.getInt(res.getColumnIndex(LINE_COLUMN_IDLINE)));
			stations.add(s);
			res.moveToNext();
		}

		for (Station s : stations) {
			Cursor res2 =  db.rawQuery("SELECT * " +
					" FROM " + STATION_TABLE_NAME +
					" WHERE " + STATION_COLUMN_IDSTATION + "=\"" + s.getIDStation() + "\"", 
					null );
			res2.moveToFirst();
			while(res2.isAfterLast() == false) {
				try {
					String name = res2.getString(res2.getColumnIndex(STATION_COLUMN_NAMESTATION));
					s.setNameStation(name);
				} catch (Exception e) {
					// Nothing
				}
				res2.moveToNext();
			}
		}
		return stations;
	}

	public boolean isStationIDUnique(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res =  db.rawQuery("SELECT " + STATION_COLUMN_IDSTATION +
				" FROM " + STATION_TABLE_NAME +
				" WHERE " + STATION_COLUMN_IDSTATION + "=\"" + id + "\"", 
				null );
		if (res.getCount()>0) {
			return false;
		} else {
			return true;
		}
	}

	public ArrayList<Line> getAllLinesByType(String type)
	{
		ArrayList<Line> lines = new ArrayList<Line>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res =  db.rawQuery("SELECT DISTINCT " + LINE_COLUMN_NAMELINE +
				" FROM " + LINE_TABLE_NAME +
				" WHERE " + LINE_COLUMN_TYPELINE + "=\"" + type + "\"", 
				null );
		res.moveToFirst();
		while(res.isAfterLast() == false){
			Line line = new Line(res.getString(res.getColumnIndex(LINE_COLUMN_NAMELINE)),"", "", type, 0);
			lines.add(line);
			res.moveToNext();
		}
		return lines;
	}
}
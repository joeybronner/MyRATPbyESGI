package fr.esgi.ratp.db;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseOperationsStation extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "ratp";
	public static final String STATION_TABLE_NAME = "station";
	public static final String STATION_COLUMN_IDSTATION = "idStation";
	public static final String STATION_COLUMN_NAMESTATION= "nameStation";
	public static final String STATION_COLUMN_LATITUDE = "latitudeStation";
	public static final String LINE_COLUMN_LONGITUDE = "logitudeStation";

	private HashMap hp;

	public DataBaseOperationsStation(Context context)
	{
		super(context, DATABASE_NAME , null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(
				"create table line " +
						"(idStation integer primary key,nameStation text,longitudeStation text,longitudeStation text)"
				);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS station");
		onCreate(db);
	}

	public boolean insertStation  (String nameStation, String longitudeStation, String latitudeStation,int iDStation)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("nameStation", nameStation);
		contentValues.put("longitudeStation", longitudeStation);
		contentValues.put("latitudeStation", latitudeStation);	
		contentValues.put("iDStation", String.valueOf(iDStation));
		db.insert("station", null, contentValues);
		return true;
	}
	public Cursor getData(int id){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res =  db.rawQuery( "select * from station where idStation="+id+"", null );
		return res;
	}
	public int numberOfRows(){
		SQLiteDatabase db = this.getReadableDatabase();
		int numRows = (int) DatabaseUtils.queryNumEntries(db,STATION_TABLE_NAME);
		return numRows;
	}
	public boolean updateStation  (int id,String nameStation, String longitudeStation, String latitudeStation,int iDStation)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("nameStation", nameStation);
		contentValues.put("longitudeStation", longitudeStation);
		contentValues.put("latitudeStation", latitudeStation);	
		contentValues.put("iDStation", String.valueOf(iDStation));
		db.update("station", contentValues, "idStation = ? ", new String[] { Integer.toString(id) } );
		return true;
	}

	public Integer deleteStation (Integer id) {
		SQLiteDatabase db = this.getWritableDatabase();
		return db.delete("station", 
				"idStation = ? ", 
				new String[] { Integer.toString(id) });
	}

	public ArrayList getAllLine() {
		ArrayList array_list = new ArrayList();
		//hp = new HashMap();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res =  db.rawQuery( "select * from station", null );
		res.moveToFirst();
		while(res.isAfterLast() == false){
			array_list.add(res.getString(res.getColumnIndex(STATION_COLUMN_NAMESTATION)));
			res.moveToNext();
		}
		return array_list;
	}
}
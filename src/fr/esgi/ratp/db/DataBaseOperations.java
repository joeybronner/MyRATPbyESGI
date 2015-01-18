package fr.esgi.ratp.db;
import java.util.ArrayList;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import fr.esgi.ratp.objects.Line;

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
	public static final String LINE_COLUMN_IDSTATION = "iDStation";
	
	
	// STATION
	public static final String STATION_TABLE_NAME = "station";
	public static final String STATION_COLUMN_IDSTATION = "idStation";
	public static final String STATION_COLUMN_NAMESTATION= "nameStation";
	public static final String STATION_COLUMN_LATITUDE = "latitudeStation";
	public static final String STATION_COLUMN_LONGITUDE = "longitudeStation";
	public static final String STATION_COLUMN_TYPE = "typeStation";

	public DataBaseOperations(Context context)
	{
		super(context, DATABASE_NAME , null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(
				"create table line " +
						"(idLine integer primary key,nameLine text,departureLine text,arrivalLine text, typeLine text,iDStation integer)"
				);
		
		db.execSQL(
				"create table station " +
						"(idStation integer primary key,nameStation text,latitudeStation text,longitudeStation text, typeStation text)"
				);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS line");
		db.execSQL("DROP TABLE IF EXISTS station");
		onCreate(db);
	}

	public boolean insertLine(String nameLine, String departureLine, String arrivalLine, String typeLine,int iDStation)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("nameLine", nameLine);
		contentValues.put("departureLine", departureLine);
		contentValues.put("arrivalLine", arrivalLine);	
		contentValues.put("typeLine", typeLine);
		contentValues.put("iDStation", String.valueOf(iDStation));
		db.insert("line", null, contentValues);
		return true;
	}
	public Cursor getData(int id){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res =  db.rawQuery( "select * from line where idLine="+id+"", null );
		return res;
	}
	public int numberOfRows(){
		SQLiteDatabase db = this.getReadableDatabase();
		int numRows = (int) DatabaseUtils.queryNumEntries(db, LINE_TABLE_NAME);
		return numRows;
	}
	public boolean updateLine (Integer id,String nameLine, String departureLine, String arrivalLine, String typeLine,integer iDStation)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("nameLine", nameLine);
		contentValues.put("departureLine", departureLine);
		contentValues.put("arrivalLine", arrivalLine);	
		contentValues.put("typeLine", typeLine);
		contentValues.put("iDStation", String.valueOf(iDStation));
		db.update("line", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
		return true;
	}

	public Integer deleteLine(Integer id) {
		SQLiteDatabase db = this.getWritableDatabase();
		return db.delete("line", 
				"id = ? ", 
				new String[] { Integer.toString(id) });
	}

	public void purgeLineTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DELETE FROM "+ LINE_TABLE_NAME);
	}
	
	public void purgeStationTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DELETE FROM "+ STATION_TABLE_NAME);
	}

	public ArrayList<Line> getAllLinesByType(String type)
	{
		ArrayList<Line> lines = new ArrayList<Line>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res =  db.rawQuery("SELECT DISTINCT " + LINE_COLUMN_NAMELINE +
				" FROM " + LINE_TABLE_NAME +
				" WHERE " + LINE_COLUMN_TYPELINE + "='" + type + "'", 
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
package fr.esgi.ratp.db;
import java.util.ArrayList;
import java.util.HashMap;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseOperations extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "ratp";
	public static final String LINE_TABLE_NAME = "line";
	public static final String LINE_COLUMN_IDLINE = "idLine";
	public static final String LINE_COLUMN_NAMELINE = "nameLine";
	public static final String LINE_COLUMN_DEPARTURELINE = "departureLine";
	public static final String LINE_COLUMN_ARRIVALLINE = "arrivalLine";
	public static final String LINE_COLUMN_TYPELINE = "typeLine";
	public static final String LINE_COLUMN_IDSTATION = "iDStation";

	private HashMap hp;

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
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS line");
		onCreate(db);
	}

	public boolean insertLine  (String nameLine, String departureLine, String arrivalLine, String typeLine,int iDStation)
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

	public Integer deleteLine (Integer id)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		return db.delete("line", 
				"id = ? ", 
				new String[] { Integer.toString(id) });
	}

	public ArrayList<String> getAllLine()
	{
		ArrayList<String> array_list = new ArrayList<String>();
		//hp = new HashMap();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res =  db.rawQuery( "select * from line", null );
		res.moveToFirst();
		while(res.isAfterLast() == false){
			array_list.add(res.getString(res.getColumnIndex(LINE_COLUMN_NAMELINE)));
			res.moveToNext();
		}
		return array_list;
	}
}
package fr.esgi.ratp.utils;

import android.content.Context;
import android.content.Intent;

public class Utilities {

	public static boolean isFinishedTimer(int _m, int _s, int _ms) {
		if(_m == 0 && _s == 0 && _ms == 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static void openView(Context c, Class<?> cla) {
		Intent intent = new Intent(c, cla);
		c.startActivity(intent);
	}
	
	public static void openView(Context c, Class<?> cla, String type) {
		Intent intent = new Intent(c, cla);
		intent.putExtra("type",type);
		c.startActivity(intent);
	}
	
	public static void openView(Context c, Class<?> cla, String type, String line) {
		Intent intent = new Intent(c, cla);
		intent.putExtra("type",type);
		intent.putExtra("line",line);
		c.startActivity(intent);
	}
	
	public static void openView(Context c, Class<?> cla, String type, String line, String station) {
		Intent intent = new Intent(c, cla);
		intent.putExtra("type",type);
		intent.putExtra("line",line);
		intent.putExtra("station",station);
		c.startActivity(intent);
	}

}

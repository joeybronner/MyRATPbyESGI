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
	
	public static void openView(Context c, Class<?> cla, String param) {
		Intent intent = new Intent(c, cla);
		intent.putExtra("type",param);
		c.startActivity(intent);
	}

}

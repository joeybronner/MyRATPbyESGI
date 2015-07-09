package fr.esgi.ratp;

import fr.esgi.ratp.utils.Constants;
import fr.esgi.ratp.utils.LaunchApp;
import fr.esgi.ratp.utils.Utilities;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemClickListener
{
	private String _errormsg;
	GridView gridview;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		gridview = (GridView) MainActivity.this.findViewById(R.id.dashboard_grid);
		gridview.setAdapter(new ImageAdapter(this));
		gridview.setOnItemClickListener(this);

		// Hide Action Bar
		getActionBar().hide();

		// Type face 
		Constants.tf = Typeface.createFromAsset(this.getAssets(),"fonts/OpenSans-Light.ttf");

		// Hack to disable GridView scrolling
		gridview.setOnTouchListener(new OnTouchListener() {
			@SuppressLint("ClickableViewAccessibility") @Override
			public boolean onTouch(View v, MotionEvent event) {
				return event.getAction() == MotionEvent.ACTION_MOVE;
			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		// Here, redirect to the good view
		String icon_selected = ICONS[position].map;
		if (icon_selected.equals("metro")) {
			Constants.TYPE = "metro";
			Utilities.openView(this, LinesActivity.class, "metro");
		}
		else if (icon_selected.equals("bus")) {
			Constants.TYPE = "bus";
			Utilities.openView(this, LinesActivity.class, "bus");
		}
		else if (icon_selected.equals("tram")) {
			Constants.TYPE = "tram";
			Utilities.openView(this, LinesActivity.class, "tram");
		}
		else if (icon_selected.equals("rer")) {
			Constants.TYPE = "rer";
			Utilities.openView(this, LinesActivity.class, "rer");
		}
		else if (icon_selected.equals("settings")) {
			Constants.TYPE = "settings";
			Utilities.openView(this, SettingsActivity.class);
		}
		else {
			_errormsg = getResources().getString(R.string.err_loading_view);
			Toast toast = Toast.makeText(this,_errormsg, Toast.LENGTH_LONG);
			toast.show();
		}
	}
	
	// Custom Grid View ---
	
	final static LauncherIcon[] ICONS = {
		new LauncherIcon(R.drawable.metro, LaunchApp.underground, "metro"),
		new LauncherIcon(R.drawable.bus, LaunchApp.bus, "bus"),
		new LauncherIcon(R.drawable.tramway, LaunchApp.tramway, "tram"),
		new LauncherIcon(R.drawable.rer, LaunchApp.rer, "rer"),
		new LauncherIcon(R.drawable.settings, LaunchApp.settings, "settings"),
	};

	static class LauncherIcon {
		final String text;
		final int imgId;
		final String map;

		public LauncherIcon(int imgId, String text, String map) {
			super();
			this.imgId = imgId;
			this.text = text;
			this.map = map;
		}
	}

	static class ImageAdapter extends BaseAdapter {
		private Context mContext;

		public ImageAdapter(Context c) {
			mContext = c;
		}

		@Override
		public int getCount() {
			return ICONS.length;
		}

		@Override
		public LauncherIcon getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		static class ViewHolder {
			public ImageView icon;
			public TextView text;
		}

		// Create a new ImageView for each item referenced by the Adapter
		@SuppressLint("InflateParams") @Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			ViewHolder holder;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) mContext.getSystemService(
						Context.LAYOUT_INFLATER_SERVICE);

				v = vi.inflate(R.layout.dashboard_icon, null);
				holder = new ViewHolder();
				holder.text = (TextView) v.findViewById(R.id.dashboard_icon_text);
				holder.icon = (ImageView) v.findViewById(R.id.dashboard_icon_img);
				v.setTag(holder);
			}
			else {
				holder = (ViewHolder) v.getTag();
			}

			holder.icon.setImageResource(ICONS[position].imgId);
			holder.text.setText(ICONS[position].text);
			return v;
		}
	}
}

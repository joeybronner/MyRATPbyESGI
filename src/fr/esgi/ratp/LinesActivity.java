package fr.esgi.ratp;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import fr.esgi.ratp.db.DataBaseOperations;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;

public class LinesActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lines);

		// Hide Action Bar
		getActionBar().hide();

		// Rihab's work
		AssetManager assetManager = getAssets();

		try {

			BufferedReader fichierLigne = new BufferedReader(new InputStreamReader(assetManager.open("ratp.csv")));	
			String chaine;
			DataBaseOperations db =new DataBaseOperations(this);
			int i = 1;
			while((chaine = fichierLigne.readLine())!= null)
			{
				if(i > 1)
				{
					String[] tabChaine = chaine.split("#",3);

					/* Log.d("idStation",tabChaine[0]);
			         String station=tabChaine[1].substring(0,tabChaine[1].indexOf("("));
			         Log.d("nom",tabChaine[1].substring(tabChaine[1].indexOf("(")+1,tabChaine[1].indexOf("/")));
			        // if (tabChaine[1].substring(tabChaine[1].indexOf("/")-1,tabChaine[1].indexOf(")"))=="/")
			         Log.d("nomstation",tabChaine[1].substring(tabChaine[1].indexOf("/")-1,tabChaine[1].indexOf(")")+1));
			         Log.d("nomstation2",tabChaine[1].substring(tabChaine[1].indexOf("(")-1,tabChaine[1].indexOf("/")+1));

			         Log.d("station",tabChaine[1].substring(tabChaine[1].indexOf("(")+1,tabChaine[1].indexOf(")")));
			         Log.d("type",tabChaine[2]);
			       String stationLine=tabChaine[1].substring(tabChaine[1].indexOf("(")+1,tabChaine[1].indexOf("/"));*/
					if ((tabChaine[1].substring(tabChaine[1].indexOf("/")-1,tabChaine[1].indexOf(")")+1).equals(")"))&&(tabChaine[1].substring(tabChaine[1].indexOf("(")-1,tabChaine[1].indexOf("/")+1).equals("/")))
					{
						Log.d("result", String.valueOf(db.insertLine(tabChaine[1].substring(0,tabChaine[1].indexOf("("))," "," ", tabChaine[2],Integer.parseInt(tabChaine[0]))));
					}
					else if (tabChaine[1].substring(tabChaine[1].indexOf("/")-1,tabChaine[1].indexOf(")")+1).equals(")"))
					{
						Log.d("result", String.valueOf(db.insertLine(tabChaine[1].substring(0,tabChaine[1].indexOf("(")), tabChaine[1].substring(tabChaine[1].indexOf("(")+1,tabChaine[1].indexOf("/"))," ", tabChaine[2],Integer.parseInt(tabChaine[0]))));

					}
					else if (tabChaine[1].substring(tabChaine[1].indexOf("(")-1,tabChaine[1].indexOf("/")+1).equals("/"))
					{
						Log.d("result", String.valueOf(db.insertLine(tabChaine[1].substring(0,tabChaine[1].indexOf("(")), " ", tabChaine[1].substring(tabChaine[1].indexOf("/")+1,tabChaine[1].indexOf(")")), tabChaine[2],Integer.parseInt(tabChaine[0]))));   
					}
					else {
						Log.d("result", String.valueOf(db.insertLine(tabChaine[1].substring(0,tabChaine[1].indexOf("(")), tabChaine[1].substring(tabChaine[1].indexOf("(")+1,tabChaine[1].indexOf("/")), tabChaine[1].substring(tabChaine[1].indexOf("/")+1,tabChaine[1].indexOf(")")), tabChaine[2],Integer.parseInt(tabChaine[0]))));
					}

					//Tu effectues tes traitements avec les données contenues dans le tableau
					//La première information se trouve à l'indice 0
				}
				i++;
			}

			fichierLigne.close();  
			// fichierStation.close(); 
		} catch (Exception e) {
			//TODO: Write exception.
		}

	}
}
package fr.esgi.ratp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import fr.esgi.ratp.R;

public class FragmentLines extends Fragment {
   @Override
   public View onCreateView(LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
      
       //Inflate the layout for this fragment
      return inflater.inflate(R.layout.fragment_lines, container, false);
   }
}
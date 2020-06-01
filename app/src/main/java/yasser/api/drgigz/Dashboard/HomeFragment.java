package yasser.api.drgigz.Dashboard;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yasser.api.drgigz.Country.CountriesActivity;
import yasser.api.drgigz.Fake.FakeActivity;
import yasser.api.drgigz.Global.GlobalActivity;
import yasser.api.drgigz.Protection.ProtectionActivity;
import yasser.api.drgigz.R;
import yasser.api.drgigz.Symptoms.SymptomsActivity;
import yasser.api.drgigz.Test.TestActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    CardView global, countries, symptoms, protection, test, fake;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        global = view.findViewById(R.id.global);
        countries = view.findViewById(R.id.countries);
        symptoms = view.findViewById(R.id.symptoms);
        protection = view.findViewById(R.id.protection);
        test = view.findViewById(R.id.test);
        fake = view.findViewById(R.id.fake);

        global.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GlobalActivity.class);
                startActivity(intent);
            }
        });

        countries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CountriesActivity.class);
                startActivity(intent);
            }
        });

        symptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SymptomsActivity.class);
                startActivity(intent);
            }
        });

        protection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProtectionActivity.class);
                startActivity(intent);
            }
        });

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TestActivity.class);
                startActivity(intent);
            }
        });

        fake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FakeActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}

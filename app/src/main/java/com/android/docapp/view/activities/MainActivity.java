package com.android.docapp.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.docapp.R;
import com.android.docapp.view.fragments.AddPatientInfoFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Add patient info fragment
        getSupportFragmentManager().beginTransaction().
                replace(R.id.container, AddPatientInfoFragment.getInstance(), AddPatientInfoFragment.TAG).commit();
    }
}

package com.android.docapp.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.android.docapp.R;
import com.android.docapp.view.fragments.ViewPatientFragment;

public class ViewPatientHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient_history);
        //Set back navigation
        if(null != getSupportActionBar())
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Add patient history list fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.container, ViewPatientFragment.getInstance()
                , ViewPatientFragment.TAG).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Finish activity when back button clicked
        if(item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}

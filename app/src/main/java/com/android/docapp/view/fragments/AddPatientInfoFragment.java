package com.android.docapp.view.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.docapp.R;
import com.android.docapp.databinding.FragmentAddPatientInfoBinding;
import com.android.docapp.view.activities.ViewPatientHistoryActivity;
import com.android.docapp.viewModel.PatientInfoViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddPatientInfoFragment extends Fragment implements PatientInfoViewModel.PatientInfoListener {

    public static final String TAG = AddPatientInfoFragment.class.getName();
    private FragmentAddPatientInfoBinding viewDataBinding;
    private PatientInfoViewModel mPatientInfoViewModel;

    public AddPatientInfoFragment() {
        // Required empty public constructor
    }

    public static AddPatientInfoFragment getInstance() {
        return new AddPatientInfoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_patient_info, container, false);
        //Set view model
        mPatientInfoViewModel = new PatientInfoViewModel(getActivity(), this);
        viewDataBinding.setViewModel(mPatientInfoViewModel);
        return viewDataBinding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPatientInfoViewModel.onDestroy();
    }

    /**
     * Display todd's syndrome checker dialog
     * @param patientName
     * @param toddSymptom
     */
    @Override
    public void onPatientAdded(String patientName, int toddSymptom) {
        String msg = String.format(getActivity().getResources().getString(R.string.todd_syndrome_msg),patientName, toddSymptom+"");
        new AlertDialog.Builder(getActivity())
                .setTitle("DocApp")
                .setMessage(msg)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .show();
    }

    /**
     * Open patient history list
     */
    @Override
    public void onCheckPatientHistory() {
        Intent intent = new Intent(getActivity(), ViewPatientHistoryActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    /**
     * Clear form data
     */
    @Override
    public void clearForm() {
        viewDataBinding.fragAddPatientNameEt.setText("");
        viewDataBinding.fragAddPatientPhoneEt.setText("");
        viewDataBinding.fragAddPatientAgeEt.setText("");
        viewDataBinding.rgMigraine.check(R.id.rb_migraine_no);
        viewDataBinding.rgHallDrugs.check(R.id.rb_hallu_no);
        viewDataBinding.rgGender.check(R.id.rb_female);
    }
}

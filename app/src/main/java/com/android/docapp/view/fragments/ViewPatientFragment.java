package com.android.docapp.view.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.docapp.R;
import com.android.docapp.databinding.FragmentViewPatientBinding;
import com.android.docapp.model.PatientInfoModel;
import com.android.docapp.view.adapter.ViewPatientHistoryAdapter;
import com.android.docapp.viewModel.ViewPatientHistoryModel;

import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPatientFragment extends Fragment implements ViewPatientHistoryModel.DataListener {

    public static final String TAG = ViewPatientFragment.class.getName();
    private FragmentViewPatientBinding viewDataBinding;
    private ViewPatientHistoryModel viewPatientHistoryModel;

    public ViewPatientFragment() {
        // Required empty public constructor
    }

    public static ViewPatientFragment getInstance() {
        return new ViewPatientFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_patient, container, false);
        //Set patient history view model
        viewPatientHistoryModel = new ViewPatientHistoryModel(getActivity(), this);
        viewDataBinding.setViewModel(viewPatientHistoryModel);
        return viewDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpRecyclerView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewPatientHistoryModel.onDestroy();
    }

    /**
     * Set up recycler view
     */
    private void setUpRecyclerView()
    {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        viewDataBinding.fragViewHistoryRv.setLayoutManager(linearLayoutManager);
    }

    /**
     * Load patient history in adapter
     * @param data
     */
    @Override
    public void onPatientHistoryLoad(RealmResults<PatientInfoModel> data) {
        if(null != data && !data.isEmpty()) {
            ViewPatientHistoryAdapter adapter = new ViewPatientHistoryAdapter(getActivity(), data);
            viewDataBinding.fragViewHistoryRv.setAdapter(adapter);
        } else {
            Toast.makeText(getActivity(), R.string.no_patient_found_msg, Toast.LENGTH_LONG).show();
        }
    }
}

package com.android.docapp.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.docapp.R;
import com.android.docapp.databinding.ViewPatientHistoryItemBinding;
import com.android.docapp.model.PatientInfoModel;
import com.android.docapp.viewModel.PatientHistoryItemViewModel;

import io.realm.RealmResults;

/**
 * Created by hp pc on 28-08-2016.
 */
public class ViewPatientHistoryAdapter extends RecyclerView.Adapter<ViewPatientHistoryAdapter.PatientHistoryViewHolder> {

    private Context mContext;
    private RealmResults<PatientInfoModel> realmResults;
    public ViewPatientHistoryAdapter(Context context, RealmResults<PatientInfoModel> realmResults) {
        mContext = context;
        this.realmResults = realmResults;
    }
	
    @Override
    public PatientHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewPatientHistoryItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.view_patient_history_item,
                parent,
                false);
        return new PatientHistoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(PatientHistoryViewHolder holder, int position) {
        holder.bindPatientHistoryData(realmResults.get(position));
    }

    @Override
    public int getItemCount() {
        return realmResults.size();
    }

    public static class PatientHistoryViewHolder extends RecyclerView.ViewHolder {
        ViewPatientHistoryItemBinding binding;
        public PatientHistoryViewHolder(ViewPatientHistoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        /**
         * Bind patient history data model
         * @param patientInfoModel
         */
        void bindPatientHistoryData(PatientInfoModel patientInfoModel) {
            binding.setViewModel(new PatientHistoryItemViewModel(itemView.getContext(), patientInfoModel));
        }
    }
}

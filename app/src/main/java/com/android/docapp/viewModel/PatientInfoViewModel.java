package com.android.docapp.viewModel;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.docapp.R;
import com.android.docapp.model.PatientInfoModel;
import com.android.docapp.symptomChecker.ToddSyndromChecker;

import io.realm.Realm;

/**
 * Created by hp pc on 27-08-2016.
 */
public class PatientInfoViewModel implements ViewModel {

    private PatientInfoModel patientInfoModel;
    private Context mContext;
    private PatientInfoListener patientInfoListener;

    public PatientInfoViewModel(Context context, PatientInfoListener patientInfoListener) {
        mContext = context;
        patientInfoModel = new PatientInfoModel();
        this.patientInfoListener = patientInfoListener;
    }

    /**
     * Set patient name
     * @return
     */
    public TextWatcher patientNameTextWatcher() {

        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                patientInfoModel.setPatientName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        };
    }

    /**
     * Set patient phone number
     * @return
     */
    public TextWatcher patientPhoneTextWatcher() {

        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                patientInfoModel.setPatientPhoneNumber(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        };
    }

    /**
     * Set patient age
     * @return
     */
    public TextWatcher patientAgeTextWatcher() {
		
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence.toString()))
                    patientInfoModel.setPatientAge(Integer.parseInt(charSequence.toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        };
    }

    /**
     * Set migraine when radio group state changed
     * @return
     */
    public RadioGroup.OnCheckedChangeListener onMigraineChanged() {
        return new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                patientInfoModel.setPatientSuffersMigraine(radioGroup.getCheckedRadioButtonId() == R.id.rb_migraine_yes);
            }
        };
    }
    
	/**
     * Set hallucinogenic drug when radio group state changed
     * @return
     */
    public RadioGroup.OnCheckedChangeListener onHallucinoDrugChanged() {
        
		return new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                patientInfoModel.setPatientUseHalluDrugs(radioGroup.getCheckedRadioButtonId() == R.id.rb_hallu_yes);
            }
        };
    }

    /**
     * Set gender when radio group state changed
     * @return
     */
    public RadioGroup.OnCheckedChangeListener onGenderChanged() {
        
		return new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                patientInfoModel.setPatientMale(radioGroup.getCheckedRadioButtonId() == R.id.rb_male);
            }
        };
    }

    /**
     * Add patient to database
     * @param view
     */
    public void onAddPatient(View view) {
        
		if(validate()) {
            addPatientToDatabase();
        }
    }

    /**
     * View patient history
     * @param view
     */
    public void onViewPatientHistory(View view) {
        
		if(null != patientInfoListener)
            patientInfoListener.onCheckPatientHistory();
    }

    /**
     * Validate all form fields
     * @return
     */
    private boolean validate() {
		
        if(TextUtils.isEmpty(patientInfoModel.getPatientName())) {
            Toast.makeText(mContext, R.string.error_name_required, Toast.LENGTH_SHORT).show();
            return false;
        } else if(TextUtils.isEmpty(patientInfoModel.getPatientPhoneNumber())) {
            Toast.makeText(mContext, R.string.error_phone_required, Toast.LENGTH_SHORT).show();
            return false;
        } else if(patientInfoModel.getPatientPhoneNumber().length() != 10) {
            Toast.makeText(mContext, R.string.error_correct_phone_required, Toast.LENGTH_SHORT).show();
            return false;
        } else if(patientInfoModel.getPatientAge() == 0) {
            Toast.makeText(mContext, R.string.error_age_required, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    /**
     * Add patient to database
     */
    private void addPatientToDatabase() {
		
        int probability = checkForToddSyndrome();
        patientInfoModel.setToddSyndromeProbability(String.valueOf(probability));

        //Add patient data to database
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(patientInfoModel);
        realm.commitTransaction();

        if(null != patientInfoListener)
            patientInfoListener.onPatientAdded(patientInfoModel.getPatientName(), probability);

        //Reset patient info model
        patientInfoModel = new PatientInfoModel();
        
		//Reset form fields
        if(null != patientInfoListener)
            patientInfoListener.clearForm();
    }

    /**
     * Check for todd's synmdrome probability
     * @return
     */
    private int checkForToddSyndrome() {
		return ToddSyndromChecker.getInstance().checkToddSyndrome(patientInfoModel.getPatientAge(), patientInfoModel.isPatientSuffersMigraine()
                , patientInfoModel.isPatientMale(), patientInfoModel.isPatientUseHalluDrugs());
    }

    @Override
    public void onDestroy() {
        mContext = null;
        patientInfoListener = null;
        patientInfoModel = null;
    }

    public interface PatientInfoListener {
        public void onPatientAdded(String patientName, int toddSymptom);
        public void onCheckPatientHistory();
        public void clearForm();
    }
}

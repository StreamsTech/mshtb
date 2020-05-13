package com.streamstech.droid.mshtb.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.adapter.AutoCompleteListAdapter;
import com.streamstech.droid.mshtb.data.persistent.DatabaseManager;
import com.streamstech.droid.mshtb.data.persistent.Patient;
import com.streamstech.droid.mshtb.data.persistent.PatientDao;
import com.streamstech.droid.mshtb.data.persistent.TestResultXRay;
import com.streamstech.droid.mshtb.data.persistent.TestResultXRayDao;
import com.streamstech.droid.mshtb.data.persistent.Treatment;
import com.streamstech.droid.mshtb.data.persistent.TreatmentDao;
import com.streamstech.droid.mshtb.util.MSHTBApplication;
import com.streamstech.droid.mshtb.util.MyRadioGroup;
import com.streamstech.droid.mshtb.util.UIUtil;
import com.streamstech.droid.mshtb.util.Util;
import com.whygraphics.multilineradiogroup.MultiLineRadioGroup;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class TreatmentInitiationActivity extends AppCompatActivity implements DialogInterface.OnDismissListener {

    @BindView(R.id.txtSearch)
    AutoCompleteTextView txtSearch;
    @BindView(R.id.lblReadonlyName)
    TextView lblReadonlyName;
    @BindView(R.id.lblPatient)
    TextView lblPatient;
    @BindView(R.id.lblTime)
    TextView lblTime;

    @BindView(R.id.dtRegistrationDate)
    DatePicker dtRegistrationDate;
    @BindView(R.id.dtTreatmentInitiaionDate)
    DatePicker dtTreatmentInitiaionDate;

    @BindView(R.id.topLayout)
    LinearLayout topLayout;
    @BindView(R.id.scrollView)
    ScrollView scrollView;

    @BindView(R.id.radioCategory)
    MyRadioGroup radioCategory;
    @BindView(R.id.radioReferred)
    MyRadioGroup radioReferred;
    @BindView(R.id.radioPatientType)
    MultiLineRadioGroup radioPatientType;

    @BindView(R.id.txtTreatmentCenter)
    EditText txtTreatmentCenter;
    @BindView(R.id.txtRegistrationNumber)
    EditText txtRegistrationNumber;

    @BindView(R.id.btnSave)
    Button btnSave;
    Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment_initiation);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String pid  = bundle.getString("PID");
            PatientDao patientDao = DatabaseManager.getInstance().getSession().getPatientDao();
            patient = patientDao.queryBuilder()
                    .where(PatientDao.Properties.Patientid.eq(pid))
                    .where(PatientDao.Properties.Tb.eq(true))
                    .unique();
            if (patient == null){
                finish();
            }else{
                topLayout.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                lblReadonlyName.setVisibility(View.VISIBLE);
                lblReadonlyName.setText(patient.getName());
                findInforamation();
                btnSave.setVisibility(View.GONE);
            }
        }

        txtSearch.setThreshold(1);//will start working from first character

        List<Patient> patientList = MSHTBApplication.getInstance().getTreatmentInput();
        AutoCompleteListAdapter adapter = new AutoCompleteListAdapter(this,
                R.layout.autocomplete_list_row, patientList);
        txtSearch.setAdapter(adapter);
        txtSearch.setOnItemClickListener(onItemClickListener);
        lblTime.setText(Util.getFormattedDateTime());
    }

    private AdapterView.OnItemClickListener onItemClickListener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    patient = (Patient) adapterView.getItemAtPosition(i);
                    lblPatient.setText(patient.getName() + "\n" + patient.getPatientid());
                    txtSearch.setText(patient.getName());
                    MSHTBApplication.getInstance().hideKeyboard(TreatmentInitiationActivity.this);
                    findInforamation();
                }
            };


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btnSave)
    public void onClickAdd() {

        if (patient == null){
            UIUtil.displayError(this, "Patient");
            return;
        }else if (!radioCategory.anyoneChecked()) {
            UIUtil.displayError(this, "Category");
            return;
        } else if (!radioReferred.anyoneChecked()) {
            UIUtil.displayError(this, "Referred");
            return;
        } else if (radioPatientType.getCheckedRadioButtonIndex() == -1) {
            UIUtil.displayError(this, "Patient type");
            return;
        }
        TreatmentDao  treatmentDao = DatabaseManager.getInstance().getSession().getTreatmentDao();
        treatmentDao.save(new Treatment(null, patient.getPatientid(), Util.getDateFromDatePicker(dtRegistrationDate).getTime(),
                radioCategory.getSelectedIndex(), radioReferred.getSelectedIndex(), txtTreatmentCenter.getText().toString().trim(),
                txtRegistrationNumber.getText().toString().trim(), radioPatientType.getCheckedRadioButtonIndex(),
                Util.getDateFromDatePicker(dtTreatmentInitiaionDate).getTime(), new Date().getTime(), false, 0.0, 0.0));

        UIUtil.showInfoDialog(this, SweetAlertDialog.SUCCESS_TYPE,  "Success", "Treatment information added", this);
    }

    private void findInforamation(){
        TreatmentDao treatmentDao = DatabaseManager.getInstance().getSession().getTreatmentDao();
        Treatment treatment = treatmentDao.queryBuilder().where(TreatmentDao.Properties.Patientid.eq(patient.getPatientid())).unique();
        if (treatment != null) {
            MSHTBApplication.getInstance().hideKeyboard(this);
            Util.setDate(treatment.getRegistrationdate(), dtRegistrationDate);
            ((RadioButton)radioCategory.getChildAt(treatment.getCategory())).setChecked(true);
            ((RadioButton)radioReferred.getChildAt(treatment.getReferred())).setChecked(true);
            radioPatientType.checkAt(treatment.getPatienttype());
            Util.setDate(treatment.getTreatmentstartdate(), dtTreatmentInitiaionDate);
            txtRegistrationNumber.setText(treatment.getRegistrationno());
            txtTreatmentCenter.setText(treatment.getTreatmentcenter());
            topLayout.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            btnSave.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        finish();
    }
}
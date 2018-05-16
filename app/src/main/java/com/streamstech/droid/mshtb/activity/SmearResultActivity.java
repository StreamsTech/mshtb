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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.adapter.AutoCompleteListAdapter;
import com.streamstech.droid.mshtb.data.persistent.DatabaseManager;
import com.streamstech.droid.mshtb.data.persistent.Patient;
import com.streamstech.droid.mshtb.data.persistent.PatientDao;
import com.streamstech.droid.mshtb.data.persistent.TestResultSmear;
import com.streamstech.droid.mshtb.data.persistent.TestResultSmearDao;
import com.streamstech.droid.mshtb.data.persistent.TestResultXRay;
import com.streamstech.droid.mshtb.data.persistent.TestResultXRayDao;
import com.streamstech.droid.mshtb.util.MyRadioGroup;
import com.streamstech.droid.mshtb.util.UIUtil;
import com.streamstech.droid.mshtb.util.Util;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class SmearResultActivity extends AppCompatActivity implements DialogInterface.OnDismissListener {

    @BindView(R.id.txtSearch)
    AutoCompleteTextView txtSearch;
    @BindView(R.id.lblPatient)
    TextView lblPatient;
    @BindView(R.id.lblTime)
    TextView lblTime;

    @BindView(R.id.dtOrderDate)
    DatePicker dtOrderDate;
    @BindView(R.id.dtResultDate)
    DatePicker dtResultDate;

    @BindView(R.id.topLayout)
    LinearLayout topLayout;
    @BindView(R.id.scrollView)
    ScrollView scrollView;

    @BindView(R.id.radioMonth)
    MyRadioGroup radioMonth;
    @BindView(R.id.radioAFB)
    MyRadioGroup radioAFB;

    @BindView(R.id.btnSave)
    Button btnSave;

    Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smear_result);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String pid  = bundle.getString("PID");
            PatientDao patientDao = DatabaseManager.getInstance().getSession().getPatientDao();
            patient = patientDao.queryBuilder().where(PatientDao.Properties.Patientid.eq(pid)).unique();
            if (patient == null){
                finish();
            }else{
                topLayout.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                findInforamation();
                btnSave.setVisibility(View.GONE);
            }
        }

        txtSearch.setThreshold(1);//will start working from first character

        PatientDao patientDao = DatabaseManager.getInstance().getSession().getPatientDao();
        List<Patient> patientList = patientDao.loadAll();
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
        }else if (!radioMonth.anyoneChecked()) {
            UIUtil.displayError(this, "Month of treatment");
            return;
        } else if (!radioAFB.anyoneChecked()) {
            UIUtil.displayError(this, "AFB smear microscopy");
            return;
        }

        TestResultSmearDao testResultSmearDao = DatabaseManager.getInstance().getSession().getTestResultSmearDao();
        testResultSmearDao.save(new TestResultSmear(null, patient.getPatientid(), Util.getDateFromDatePicker(dtOrderDate),
                radioMonth.getSelectedIndex(), Util.getDateFromDatePicker(dtResultDate), radioAFB.getSelectedIndex(),
                new Date(), false, 0.0, 0.0));

        UIUtil.showInfoDialog(this, SweetAlertDialog.SUCCESS_TYPE,  "Success", "AFB smear information added", this);
    }

    private void findInforamation(){
        TestResultSmearDao testResultSmearDao = DatabaseManager.getInstance().getSession().getTestResultSmearDao();
        TestResultSmear testResultSmear = testResultSmearDao.queryBuilder().where(TestResultSmearDao.Properties.Patientid.eq(patient.getPatientid())).unique();
        if (testResultSmear != null) {
            ((RadioButton)radioMonth.getChildAt(testResultSmear.getMonthof_treatment())).setChecked(true);
            ((RadioButton)radioAFB.getChildAt(testResultSmear.getResult())).setChecked(true);
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

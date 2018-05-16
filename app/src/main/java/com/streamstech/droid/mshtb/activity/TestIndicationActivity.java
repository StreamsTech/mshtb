package com.streamstech.droid.mshtb.activity;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.adapter.AutoCompleteListAdapter;
import com.streamstech.droid.mshtb.data.persistent.DatabaseManager;
import com.streamstech.droid.mshtb.data.persistent.Patient;
import com.streamstech.droid.mshtb.data.persistent.PatientDao;
import com.streamstech.droid.mshtb.data.persistent.TestIndication;
import com.streamstech.droid.mshtb.data.persistent.TestIndicationDao;
import com.streamstech.droid.mshtb.util.MyRadioGroup;
import com.streamstech.droid.mshtb.util.UIUtil;
import com.streamstech.droid.mshtb.util.Util;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class TestIndicationActivity extends AppCompatActivity implements DialogInterface.OnDismissListener {

    @BindView(R.id.txtSearch)
    AutoCompleteTextView txtSearch;
    @BindView(R.id.lblPatient)
    TextView lblPatient;
    @BindView(R.id.lblTime)
    TextView lblTime;

    @BindView(R.id.topLayout)
    LinearLayout topLayout;
    @BindView(R.id.scrollView)
    ScrollView scrollView;

    @BindView(R.id.radioXray)
    MyRadioGroup radioXray;
    @BindView(R.id.radioXpert)
    MyRadioGroup radioXpert;
    @BindView(R.id.radioSmear)
    MyRadioGroup radioSmear;
    @BindView(R.id.radioUltrasound)
    MyRadioGroup radioUltrasound;
    @BindView(R.id.radioHistopathology)
    MyRadioGroup radioHistopathology;
    @BindView(R.id.radioCTScan)
    MyRadioGroup radioCTScan;

    @BindView(R.id.txtHistopathologySite)
    EditText txtHistopathologySite;
    @BindView(R.id.txtOthers)
    EditText txtOthers;

    @BindView(R.id.btnSave)
    Button btnSave;

    Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_indication);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
//        radioXray.anyoneChecked()
        radioHistopathology.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioHistopathology.getSelectedIndex() == 0) {
                    txtHistopathologySite.setVisibility(View.VISIBLE);
                } else {
                    txtHistopathologySite.setVisibility(View.GONE);
                }
            }
        });

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
        if (!radioXray.anyoneChecked()) {
            UIUtil.displayError(this, "X-Ray");
            return;
        } else if (!radioXpert.anyoneChecked()) {
            UIUtil.displayError(this, "XPert");
            return;
        } else if (!radioSmear.anyoneChecked()) {
            UIUtil.displayError(this, "Smear");
            return;
        } else if (!radioUltrasound.anyoneChecked()) {
            UIUtil.displayError(this, "Ultrasound");
            return;
        } else if (!radioHistopathology.anyoneChecked()) {
            UIUtil.displayError(this, "Histopathology");
            return;
        } else if (!radioCTScan.anyoneChecked()) {
            UIUtil.displayError(this, "CT scan/MRI");
            return;
        }

        TestIndicationDao testIndicationDao = DatabaseManager.getInstance().getSession().getTestIndicationDao();
        testIndicationDao.save(new TestIndication(null, patient.getPatientid(), radioXray.getSelectedIndex(),
                radioXpert.getSelectedIndex(), radioSmear.getSelectedIndex(), radioUltrasound.getSelectedIndex(),
                radioHistopathology.getSelectedIndex(), radioCTScan.getSelectedIndex(), txtHistopathologySite.getText().toString(),
                txtOthers.getText().toString(), new Date(), false, 0.0, 0.0));

        UIUtil.showInfoDialog(this, SweetAlertDialog.SUCCESS_TYPE,  "Success", "Test indication completed", this);
    }

    private void findInforamation(){
        TestIndicationDao testIndicationDao = DatabaseManager.getInstance().getSession().getTestIndicationDao();
        TestIndication testIndication = testIndicationDao.queryBuilder().where(TestIndicationDao.Properties.Patientid.eq(patient.getPatientid())).unique();
        if (testIndication != null) {
            ((RadioButton)radioXray.getChildAt(testIndication.getXray())).setChecked(true);
            ((RadioButton)radioXpert.getChildAt(testIndication.getXpert())).setChecked(true);
            ((RadioButton)radioSmear.getChildAt(testIndication.getSmear())).setChecked(true);
            ((RadioButton)radioUltrasound.getChildAt(testIndication.getUltrasound())).setChecked(true);
            ((RadioButton)radioHistopathology.getChildAt(testIndication.getHistopathology())).setChecked(true);
            ((RadioButton)radioCTScan.getChildAt(testIndication.getCtmri())).setChecked(true);

            txtHistopathologySite.setText(testIndication.getHistopathology_sample());
            txtOthers.setText(testIndication.getOther());
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

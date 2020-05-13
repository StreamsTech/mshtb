package com.streamstech.droid.mshtb.activity;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.streamstech.droid.mshtb.util.MSHTBApplication;
import com.streamstech.droid.mshtb.util.UIUtil;
import com.streamstech.droid.mshtb.util.Util;
import com.whygraphics.multilineradiogroup.MultiLineRadioGroup;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class TestIndicationActivity extends AppCompatActivity implements DialogInterface.OnDismissListener {

    @BindView(R.id.txtSearch)
    AutoCompleteTextView txtSearch;
    @BindView(R.id.lblReadonlyName)
    TextView lblReadonlyName;
    @BindView(R.id.lblPatient)
    TextView lblPatient;
    @BindView(R.id.lblTime)
    TextView lblTime;

    @BindView(R.id.topLayout)
    LinearLayout topLayout;
    @BindView(R.id.scrollView)
    ScrollView scrollView;

    @BindView(R.id.radioXray)
    MultiLineRadioGroup radioXray;
    @BindView(R.id.radioXpert)
    MultiLineRadioGroup radioXpert;
    @BindView(R.id.radioSmear)
    MultiLineRadioGroup radioSmear;
    @BindView(R.id.radioUltrasound)
    MultiLineRadioGroup radioUltrasound;
    @BindView(R.id.radioHistopathology)
    MultiLineRadioGroup radioHistopathology;
    @BindView(R.id.radioCTScan)
    MultiLineRadioGroup radioCTScan;

    @BindView(R.id.txtHistopathologySite)
    EditText txtHistopathologySite;
    @BindView(R.id.txtOthers)
    EditText txtOthers;

    @BindView(R.id.btnSave)
    Button btnSave;

    Patient patient;
    TestIndication testIndication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_indication_new);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
//        radioXray.anyoneChecked()
//        radioHistopathology.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                if (radioHistopathology.getSelectedIndex() == 0) {
//                    txtHistopathologySite.setVisibility(View.VISIBLE);
//                } else {
//                    txtHistopathologySite.setVisibility(View.GONE);
//                }
//            }
//        });
        radioHistopathology.setOnCheckedChangeListener(new MultiLineRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ViewGroup viewGroup, RadioButton radioButton) {
                if (radioButton.isChecked() && radioButton.getText().toString().equalsIgnoreCase("yes")) {
                    txtHistopathologySite.setVisibility(View.VISIBLE);
                } else {
                    txtHistopathologySite.setVisibility(View.GONE);
                }
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String pid = bundle.getString("PID");
            PatientDao patientDao = DatabaseManager.getInstance().getSession().getPatientDao();
            patient = patientDao.queryBuilder().where(PatientDao.Properties.Patientid.eq(pid)).unique();
            if (patient == null) {
                finish();
            } else {
                topLayout.setVisibility(View.GONE);
                lblReadonlyName.setVisibility(View.VISIBLE);
                lblReadonlyName.setText(patient.getName());
                MSHTBApplication.getInstance().hideKeyboard(this);
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
                    MSHTBApplication.getInstance().hideKeyboard(TestIndicationActivity.this);
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

        if (patient == null) {
            UIUtil.displayError(this, "Patient");
            return;
        }
//        if (!radioXray.anyoneChecked()) {
//            UIUtil.displayError(this, "X-Ray");
//            return;
//        } else if (!radioXpert.anyoneChecked()) {
//            UIUtil.displayError(this, "XPert");
//            return;
//        } else if (!radioSmear.anyoneChecked()) {
//            UIUtil.displayError(this, "Smear");
//            return;
//        } else if (!radioUltrasound.anyoneChecked()) {
//            UIUtil.displayError(this, "Ultrasound");
//            return;
//        } else if (!radioHistopathology.anyoneChecked()) {
//            UIUtil.displayError(this, "Histopathology");
//            return;
//        } else if (!radioCTScan.anyoneChecked()) {
//            UIUtil.displayError(this, "CT scan/MRI");
//            return;
//        }

        TestIndicationDao testIndicationDao = DatabaseManager.getInstance().getSession().getTestIndicationDao();
        if (testIndication != null && btnSave.getText().toString().equalsIgnoreCase("update")) {
            testIndication.setXray(radioXray.getCheckedRadioButtonIndex());
            testIndication.setXpert(radioXpert.getCheckedRadioButtonIndex());
            testIndication.setSmear(radioSmear.getCheckedRadioButtonIndex());
            testIndication.setUltrasound(radioUltrasound.getCheckedRadioButtonIndex());
            testIndication.setHistopathology(radioHistopathology.getCheckedRadioButtonIndex());
            testIndication.setCtmri(radioCTScan.getCheckedRadioButtonIndex());
            testIndication.setOther(Util.getText(txtOthers));
            testIndication.setUploaded(false);
            testIndicationDao.update(testIndication);
            UIUtil.showInfoDialog(this, SweetAlertDialog.SUCCESS_TYPE, "Success", "Test indication updated", this);
            return;
        }

        testIndicationDao.save(new TestIndication(null, patient.getPatientid(), radioXray.getCheckedRadioButtonIndex(),
                radioXpert.getCheckedRadioButtonIndex(), radioSmear.getCheckedRadioButtonIndex(), radioUltrasound.getCheckedRadioButtonIndex(),
                radioHistopathology.getCheckedRadioButtonIndex(), radioCTScan.getCheckedRadioButtonIndex(), txtHistopathologySite.getText().toString(),
                txtOthers.getText().toString(), new Date().getTime(), false, 0.0, 0.0));

        UIUtil.showInfoDialog(this, SweetAlertDialog.SUCCESS_TYPE, "Success", "Test indication completed", this);
    }

    private void findInforamation() {
        TestIndicationDao testIndicationDao = DatabaseManager.getInstance().getSession().getTestIndicationDao();
        testIndication = testIndicationDao.queryBuilder().where(TestIndicationDao.Properties.Patientid.eq(patient.getPatientid())).unique();
        if (testIndication != null) {
//            ((RadioButton) radioXray.getChildAt(testIndication.getXray())).setChecked(true);
//            ((RadioButton) radioXpert.getChildAt(testIndication.getXpert())).setChecked(true);
//            ((RadioButton) radioSmear.getChildAt(testIndication.getSmear())).setChecked(true);
//            ((RadioButton) radioUltrasound.getChildAt(testIndication.getUltrasound())).setChecked(true);
//            ((RadioButton) radioHistopathology.getChildAt(testIndication.getHistopathology())).setChecked(true);
//            ((RadioButton) radioCTScan.getChildAt(testIndication.getCtmri())).setChecked(true);

            radioXray.checkAt(testIndication.getXray());
            radioXpert.checkAt(testIndication.getXpert());
            radioSmear.checkAt(testIndication.getSmear());
            radioUltrasound.checkAt(testIndication.getUltrasound());
            radioHistopathology.checkAt(testIndication.getHistopathology());
            radioCTScan.checkAt(testIndication.getCtmri());

            txtHistopathologySite.setText(testIndication.getHistopathology_sample());
            txtOthers.setText(testIndication.getOther());
            topLayout.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            btnSave.setText("Update");
//            btnSave.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        finish();
    }
}

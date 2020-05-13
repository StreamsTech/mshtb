package com.streamstech.droid.mshtb.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.adapter.AutoCompleteListAdapter;
import com.streamstech.droid.mshtb.data.persistent.DatabaseManager;
import com.streamstech.droid.mshtb.data.persistent.Outcome;
import com.streamstech.droid.mshtb.data.persistent.OutcomeDao;
import com.streamstech.droid.mshtb.data.persistent.Patient;
import com.streamstech.droid.mshtb.data.persistent.PatientDao;
import com.streamstech.droid.mshtb.data.persistent.Screening;
import com.streamstech.droid.mshtb.data.persistent.ScreeningDao;
import com.streamstech.droid.mshtb.data.persistent.TestIndication;
import com.streamstech.droid.mshtb.data.persistent.TestIndicationDao;
import com.streamstech.droid.mshtb.data.persistent.TestResultHistopathology;
import com.streamstech.droid.mshtb.data.persistent.TestResultHistopathologyDao;
import com.streamstech.droid.mshtb.data.persistent.TestResultSmear;
import com.streamstech.droid.mshtb.data.persistent.TestResultSmearDao;
import com.streamstech.droid.mshtb.data.persistent.TestResultXPert;
import com.streamstech.droid.mshtb.data.persistent.TestResultXPertDao;
import com.streamstech.droid.mshtb.data.persistent.TestResultXRay;
import com.streamstech.droid.mshtb.data.persistent.TestResultXRayDao;
import com.streamstech.droid.mshtb.data.persistent.Treatment;
import com.streamstech.droid.mshtb.data.persistent.TreatmentDao;
import com.streamstech.droid.mshtb.util.MyRadioGroup;
import com.streamstech.droid.mshtb.util.UIUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class PatientDetailsActivity extends AppCompatActivity implements SweetAlertDialog.OnDismissListener {

    @BindView(R.id.txtSearch)
    AutoCompleteTextView txtSearch;
    @BindView(R.id.lblName)
    TextView lblName;
    @BindView(R.id.lblID)
    TextView lblID;
    @BindView(R.id.lblAge)
    TextView lblAge;
    @BindView(R.id.lblGender)
    TextView lblGender;
    @BindView(R.id.lblContactNo)
    TextView lblContactNo;

    @BindView(R.id.btnDoctorsDecision)
    Button btnDoctorsDecision;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.checkedTextTB)
    CheckedTextView checkedTextTB;
    @BindView(R.id.radioSensivity)
    MyRadioGroup radioSensivity;
    @BindView(R.id.radioSiteofDisease)
    MyRadioGroup radioSiteofDisease;
    @BindView(R.id.tbAttribute)
    LinearLayout tbAttribute;

    @BindView(R.id.checkedTextPTB)
    CheckedTextView checkedTextPTB;
    @BindView(R.id.checkedTextScreening)
    CheckedTextView checkedTextScreening;

    @BindView(R.id.checkedTextTest)
    CheckedTextView checkedTextTest;
    @BindView(R.id.checkedTextResultXray)
    CheckedTextView checkedTextResultXray;
    @BindView(R.id.checkedTextResultXPert)
    CheckedTextView checkedTextResultXPert;
    @BindView(R.id.checkedTextResultSmear)
    CheckedTextView checkedTextResultSmear;
    @BindView(R.id.checkedTextResultHistopathology)
    CheckedTextView checkedTextResultHistopathology;
    @BindView(R.id.checkedTextTreatmentInitiation)
    CheckedTextView checkedTextTreatmentInitiation;
    @BindView(R.id.checkedTextTreatmentOutcome)
    CheckedTextView checkedTextTreatmentOutcome;

    Patient patient = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String pid = bundle.getString("PID");
            PatientDao patientDao = DatabaseManager.getInstance().getSession().getPatientDao();
            try {
                patient = patientDao.queryBuilder().where(PatientDao.Properties.Patientid.eq(pid)).unique();
                if (patient == null) {
                    finish();
                } else {
                    txtSearch.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                    findInforamation();
                }
            } catch (Exception exp) {
                UIUtil.showInfoDialog(this, SweetAlertDialog.ERROR_TYPE, "Duplicate", "Probable duplicate entry found", this);
                return;
            }
        } else {
            txtSearch.setThreshold(1);//will start working from first character
            PatientDao patientDao = DatabaseManager.getInstance().getSession().getPatientDao();
            List<Patient> patientList = patientDao.loadAll();
            AutoCompleteListAdapter adapter = new AutoCompleteListAdapter(this,
                    R.layout.autocomplete_list_row, patientList);
            txtSearch.setAdapter(adapter);
            txtSearch.setOnItemClickListener(onItemClickListener);
        }
    }

    @OnClick(R.id.btnDoctorsDecision)
    public void onClickDoctorsDecision() {
        if (patient == null) {
            return;
        }
        Intent intent = new Intent(this, DoctorsDecisionActivity.class);
        intent.putExtra("PID", patient.getPatientid());
        startActivity(intent);
    }

    @OnClick(R.id.checkedTextScreening)
    public void onClickScreening() {
        Intent intent = new Intent(this, ScreeningActivity.class);
        intent.putExtra("PID", patient.getPatientid());
        startActivity(intent);
    }

    @OnClick(R.id.checkedTextTest)
    public void onClickTest() {
        Intent intent = new Intent(this, TestIndicationActivity.class);
        intent.putExtra("PID", patient.getPatientid());
        startActivity(intent);
    }

    @OnClick(R.id.checkedTextResultXray)
    public void onClickXRay() {
        Intent intent = new Intent(this, XRayResultActivity.class);
        intent.putExtra("PID", patient.getPatientid());
        startActivity(intent);
    }

    @OnClick(R.id.checkedTextResultXPert)
    public void onClickXPert() {
        Intent intent = new Intent(this, XPertResultActivity.class);
        intent.putExtra("PID", patient.getPatientid());
        startActivity(intent);
    }

    @OnClick(R.id.checkedTextResultSmear)
    public void onClickSmear() {
        Intent intent = new Intent(this, SmearResultActivity.class);
        intent.putExtra("PID", patient.getPatientid());
        startActivity(intent);
    }

    @OnClick(R.id.checkedTextResultHistopathology)
    public void onClickHistopathology() {
        Intent intent = new Intent(this, HistiopathologyResultActivity.class);
        intent.putExtra("PID", patient.getPatientid());
        startActivity(intent);
    }

    @OnClick(R.id.checkedTextTreatmentInitiation)
    public void onClickTreatmentInitiation() {
        Intent intent = new Intent(this, TreatmentInitiationActivity.class);
        intent.putExtra("PID", patient.getPatientid());
        startActivity(intent);
    }

    @OnClick(R.id.checkedTextTreatmentOutcome)
    public void onClickTreatmentOutcome() {
        Intent intent = new Intent(this, TreatmentOutcomeActivity.class);
        intent.putExtra("PID", patient.getPatientid());
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private AdapterView.OnItemClickListener onItemClickListener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    patient = (Patient) adapterView.getItemAtPosition(i);
//                    lblPatient.setText(patient.getName() + "\n" + patient.getPatientid());
                    lblName.setText(patient.getName());
                    lblID.setText(patient.getPatientid());
                    lblAge.setText(String.valueOf(patient.getAge()));
                    lblGender.setText(patient.getGender());
                    txtSearch.setText(patient.getName());
                    scrollView.setVisibility(View.VISIBLE);
                    findInforamation();
                }
            };


    @Override
    protected void onResume() {
        if (patient != null) {
            PatientDao patientDao = DatabaseManager.getInstance().getSession().getPatientDao();
            patient = patientDao.queryBuilder().where(PatientDao.Properties.Patientid.eq(patient.getPatientid())).unique();
            findInforamation();
        }
        super.onResume();
    }

    private void findInforamation() {

        lblName.setText(patient.getName());
        lblID.setText(patient.getPatientid());
        lblAge.setText(String.valueOf(patient.getAge()));
        lblGender.setText(patient.getGender());
        lblContactNo.setText(patient.getContantno());

        ScreeningDao screeningDao = DatabaseManager.getInstance().getSession().getScreeningDao();
        Screening screening = null;
        try {
            screening = screeningDao.queryBuilder().where(ScreeningDao.Properties.Patientid.eq(patient.getPatientid())).unique();
        } catch (Exception exp) {
            UIUtil.showInfoDialog(this, SweetAlertDialog.ERROR_TYPE, "Duplicate", "Probable duplicate entry found", this);
            return;
        }
        checkedTextTB.setChecked(patient.getTb());
        checkedTextTB.setCheckMarkDrawable(patient.getTb() ? R.drawable.tick : R.drawable.unknown);

        if (patient.getTb()) {
            tbAttribute.setVisibility(View.VISIBLE);
            if (patient.getSensivity() != -1) {
                ((RadioButton) radioSensivity.getChildAt(patient.getSensivity())).setChecked(true);
            }
            if (patient.getSiteofdisease() != -1) {
                ((RadioButton) radioSiteofDisease.getChildAt(patient.getSiteofdisease())).setChecked(true);
            }
        } else {
            tbAttribute.setVisibility(View.GONE);
        }

        checkedTextPTB.setChecked(patient.getPresumtivetb());
        checkedTextPTB.setCheckMarkDrawable(patient.getPresumtivetb() ? R.drawable.tick : R.drawable.no);

        if (screening != null) {
            checkedTextScreening.setChecked(true);
            checkedTextScreening.setCheckMarkDrawable(R.drawable.tick);
        } else {
            checkedTextScreening.setChecked(false);
            checkedTextScreening.setCheckMarkDrawable(R.drawable.no);
        }

        TestIndicationDao testIndicationDao = DatabaseManager.getInstance().getSession().getTestIndicationDao();
        TestIndication testIndication = testIndicationDao.queryBuilder().where(TestIndicationDao.Properties.Patientid.eq(patient.getPatientid())).unique();
        if (testIndication != null) {
            checkedTextTest.setVisibility(View.VISIBLE);
            checkedTextTest.setChecked(true);
            checkedTextTest.setCheckMarkDrawable(R.drawable.tick);

            TestResultXRayDao testResultXRayDao = DatabaseManager.getInstance().getSession().getTestResultXRayDao();
            TestResultXRay testResultXRay = testResultXRayDao.queryBuilder().where(TestResultXRayDao.Properties.Patientid.eq(patient.getPatientid())).unique();
            if (testResultXRay != null) {
                checkedTextResultXray.setVisibility(View.VISIBLE);
                checkedTextResultXray.setChecked(true);
                checkedTextResultXray.setCheckMarkDrawable(R.drawable.tick);
            }

            TestResultXPertDao testResultXPertDao = DatabaseManager.getInstance().getSession().getTestResultXPertDao();
            TestResultXPert testResultXPert = testResultXPertDao.queryBuilder().where(TestResultXPertDao.Properties.Patientid.eq(patient.getPatientid())).unique();
            if (testResultXPert != null) {
                checkedTextResultXPert.setVisibility(View.VISIBLE);
                checkedTextResultXPert.setChecked(true);
                checkedTextResultXPert.setCheckMarkDrawable(R.drawable.tick);
            }

            TestResultSmearDao testResultSmearDao = DatabaseManager.getInstance().getSession().getTestResultSmearDao();
            TestResultSmear testResultSmear = testResultSmearDao.queryBuilder().where(TestResultSmearDao.Properties.Patientid.eq(patient.getPatientid())).unique();
            if (testResultSmear != null) {
                checkedTextResultSmear.setVisibility(View.VISIBLE);
                checkedTextResultSmear.setChecked(true);
                checkedTextResultSmear.setCheckMarkDrawable(R.drawable.tick);
            }

            TestResultHistopathologyDao testResultHistopathologyDao = DatabaseManager.getInstance().getSession().getTestResultHistopathologyDao();
            TestResultHistopathology testResultHistopathology = testResultHistopathologyDao.queryBuilder().where(TestResultHistopathologyDao.Properties.Patientid.eq(patient.getPatientid())).unique();
            if (testResultHistopathology != null) {
                checkedTextResultHistopathology.setVisibility(View.VISIBLE);
                checkedTextResultHistopathology.setChecked(true);
                checkedTextResultHistopathology.setCheckMarkDrawable(R.drawable.tick);
            }
        }

        TreatmentDao treatmentDao = DatabaseManager.getInstance().getSession().getTreatmentDao();
        Treatment treatment = treatmentDao.queryBuilder().where(TreatmentDao.Properties.Patientid.eq(patient.getPatientid())).unique();
        if (treatment != null) {
            checkedTextTreatmentInitiation.setVisibility(View.VISIBLE);
            checkedTextTreatmentInitiation.setChecked(true);
            checkedTextTreatmentInitiation.setCheckMarkDrawable(R.drawable.tick);
        }

        OutcomeDao outcomeDao = DatabaseManager.getInstance().getSession().getOutcomeDao();
        Outcome outcome = outcomeDao.queryBuilder().where(OutcomeDao.Properties.Patientid.eq(patient.getPatientid())).unique();
        if (outcome != null) {
            checkedTextTreatmentOutcome.setVisibility(View.VISIBLE);
            checkedTextTreatmentOutcome.setChecked(true);
            checkedTextTreatmentOutcome.setCheckMarkDrawable(R.drawable.tick);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        finish();
    }
}

package com.streamstech.droid.mshtb.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.CheckedTextView;
import android.widget.ScrollView;

import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.adapter.AutoCompleteListAdapter;
import com.streamstech.droid.mshtb.data.persistent.DatabaseManager;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PatientDetailsActivity extends AppCompatActivity {

    @BindView(R.id.txtSearch)
    AutoCompleteTextView txtSearch;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.checkedTextTB)
    CheckedTextView checkedTextTB;
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
            String pid  = bundle.getString("PID");
            PatientDao patientDao = DatabaseManager.getInstance().getSession().getPatientDao();
            patient = patientDao.queryBuilder().where(PatientDao.Properties.Patientid.eq(pid)).unique();
            if (patient == null){
                finish();
            }else{
                txtSearch.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                findInforamation();
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

    @OnClick(R.id.checkedTextScreening)
    public void onClickScreening(){
        Intent intent = new Intent(this, ScreeningActivity.class);
        intent.putExtra("PID", patient.getPatientid());
        startActivity(intent);
    }
    @OnClick(R.id.checkedTextTest)
    public void onClickTest(){
        Intent intent = new Intent(this, TestIndicationActivity.class);
        intent.putExtra("PID", patient.getPatientid());
        startActivity(intent);
    }
    @OnClick(R.id.checkedTextResultXray)
    public void onClickXRay(){
        Intent intent = new Intent(this, XRayResultActivity.class);
        intent.putExtra("PID", patient.getPatientid());
        startActivity(intent);
    }
    @OnClick(R.id.checkedTextResultXPert)
    public void onClickXPert(){
        Intent intent = new Intent(this, XPertResultActivity.class);
        intent.putExtra("PID", patient.getPatientid());
        startActivity(intent);
    }
    @OnClick(R.id.checkedTextResultSmear)
    public void onClickSmear(){
        Intent intent = new Intent(this, SmearResultActivity.class);
        intent.putExtra("PID", patient.getPatientid());
        startActivity(intent);
    }
    @OnClick(R.id.checkedTextResultHistopathology)
    public void onClickHistopathology(){
        Intent intent = new Intent(this, HistiopathologyResultActivity.class);
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
                    txtSearch.setText(patient.getName());
                    scrollView.setVisibility(View.VISIBLE);
                    findInforamation();
                }
            };

    private void findInforamation(){
        ScreeningDao screeningDao = DatabaseManager.getInstance().getSession().getScreeningDao();
        Screening screening = screeningDao.queryBuilder().where(ScreeningDao.Properties.Patientid.eq(patient.getPatientid())).unique();
        checkedTextTB.setChecked(patient.getTb());
        checkedTextTB.setCheckMarkDrawable(patient.getTb() ? R.drawable.tick : R.drawable.unknown);

        checkedTextPTB.setChecked(patient.getPresumtivetb());
        checkedTextPTB.setCheckMarkDrawable(patient.getPresumtivetb() ? R.drawable.tick : R.drawable.no);

        if (screening != null){
            checkedTextScreening.setChecked(true);
            checkedTextScreening.setCheckMarkDrawable(R.drawable.tick);
        }else{
            checkedTextScreening.setChecked(false);
            checkedTextScreening.setCheckMarkDrawable(R.drawable.no);
        }

        TestIndicationDao testIndicationDao = DatabaseManager.getInstance().getSession().getTestIndicationDao();
        TestIndication testIndication = testIndicationDao.queryBuilder().where(TestIndicationDao.Properties.Patientid.eq(patient.getPatientid())).unique();
        if (testIndication != null){
            checkedTextTest.setVisibility(View.VISIBLE);
            checkedTextTest.setChecked(true);
            checkedTextTest.setCheckMarkDrawable(R.drawable.tick);

            TestResultXRayDao testResultXRayDao = DatabaseManager.getInstance().getSession().getTestResultXRayDao();
            TestResultXRay testResultXRay = testResultXRayDao.queryBuilder().where(TestResultXRayDao.Properties.Patientid.eq(patient.getPatientid())).unique();
            if (testResultXRay != null){
                checkedTextResultXray.setVisibility(View.VISIBLE);
                checkedTextResultXray.setChecked(true);
                checkedTextResultXray.setCheckMarkDrawable(R.drawable.tick);
            }

            TestResultXPertDao testResultXPertDao = DatabaseManager.getInstance().getSession().getTestResultXPertDao();
            TestResultXPert testResultXPert = testResultXPertDao.queryBuilder().where(TestResultXPertDao.Properties.Patientid.eq(patient.getPatientid())).unique();
            if (testResultXPert != null){
                checkedTextResultXPert.setVisibility(View.VISIBLE);
                checkedTextResultXPert.setChecked(true);
                checkedTextResultXPert.setCheckMarkDrawable(R.drawable.tick);
            }

            TestResultSmearDao testResultSmearDao = DatabaseManager.getInstance().getSession().getTestResultSmearDao();
            TestResultSmear testResultSmear = testResultSmearDao.queryBuilder().where(TestResultSmearDao.Properties.Patientid.eq(patient.getPatientid())).unique();
            if (testResultSmear != null){
                checkedTextResultSmear.setVisibility(View.VISIBLE);
                checkedTextResultSmear.setChecked(true);
                checkedTextResultSmear.setCheckMarkDrawable(R.drawable.tick);
            }

            TestResultHistopathologyDao testResultHistopathologyDao = DatabaseManager.getInstance().getSession().getTestResultHistopathologyDao();
            TestResultHistopathology testResultHistopathology = testResultHistopathologyDao.queryBuilder().where(TestResultHistopathologyDao.Properties.Patientid.eq(patient.getPatientid())).unique();
            if (testResultHistopathology != null){
                checkedTextResultHistopathology.setVisibility(View.VISIBLE);
                checkedTextResultHistopathology.setChecked(true);
                checkedTextResultHistopathology.setCheckMarkDrawable(R.drawable.tick);
            }
        }
    }
}

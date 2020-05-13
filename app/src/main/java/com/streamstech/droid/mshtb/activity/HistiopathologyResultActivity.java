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
import com.streamstech.droid.mshtb.data.persistent.TestIndicationDao;
import com.streamstech.droid.mshtb.data.persistent.TestResultHistopathology;
import com.streamstech.droid.mshtb.data.persistent.TestResultHistopathologyDao;
import com.streamstech.droid.mshtb.data.persistent.TestResultXRay;
import com.streamstech.droid.mshtb.data.persistent.TestResultXRayDao;
import com.streamstech.droid.mshtb.util.MSHTBApplication;
import com.streamstech.droid.mshtb.util.MyRadioGroup;
import com.streamstech.droid.mshtb.util.UIUtil;
import com.streamstech.droid.mshtb.util.Util;
import com.whygraphics.multilineradiogroup.MultiLineRadioGroup;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class HistiopathologyResultActivity extends AppCompatActivity implements DialogInterface.OnDismissListener {

    @BindView(R.id.txtSearch)
    AutoCompleteTextView txtSearch;
    @BindView(R.id.lblReadonlyName)
    TextView lblReadonlyName;
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

    @BindView(R.id.radioHistopathology)
    MultiLineRadioGroup radioHistopathology;
    @BindView(R.id.radioHistopathologyResult)
    MultiLineRadioGroup radioHistopathologyResult;

    @BindView(R.id.txtHistopathologySite)
    EditText txtHistopathologySite;

    @BindView(R.id.btnSave)
    Button btnSave;

    Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histopathology_result);
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
                lblReadonlyName.setVisibility(View.VISIBLE);
                lblReadonlyName.setText(patient.getName());
                MSHTBApplication.getInstance().hideKeyboard(this);
                findInforamation();
                btnSave.setVisibility(View.GONE);
            }
        }

        txtSearch.setThreshold(1);//will start working from first character

        List<Patient> patientList = MSHTBApplication.getInstance().getPatientForResultInput(TestIndicationDao.Properties.Histopathology);
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
                    MSHTBApplication.getInstance().hideKeyboard(HistiopathologyResultActivity.this);
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
        }else if (radioHistopathology.getCheckedRadioButtonIndex() == -1) {
            UIUtil.displayError(this, "Histopathology");
            return;
        } else if (radioHistopathologyResult.getCheckedRadioButtonIndex() == -1) {
            UIUtil.displayError(this, "Histopathology result");
            return;
        }

        TestResultHistopathologyDao testResultHistopathologyDao= DatabaseManager.getInstance().getSession().getTestResultHistopathologyDao();
        testResultHistopathologyDao.save(new TestResultHistopathology(null, patient.getPatientid(), Util.getDateFromDatePicker(dtOrderDate).getTime(),
                radioHistopathology.getCheckedRadioButtonIndex(), txtHistopathologySite.getText().toString(),
                Util.getDateFromDatePicker(dtResultDate).getTime(), radioHistopathologyResult.getCheckedRadioButtonIndex(),
                new Date().getTime(), false, 0.0, 0.0));

//        if (radioHistopathologyResult.getSelectedIndex() == 1){
//            patient.setTb(true);
//            DatabaseManager.getInstance().getSession().getPatientDao().update(patient);
//        }
        UIUtil.showInfoDialog(this, SweetAlertDialog.SUCCESS_TYPE,  "Success", "Histopathology result added", this);
    }

    private void findInforamation(){
        TestResultHistopathologyDao testResultHistopathologyDao= DatabaseManager.getInstance().getSession().getTestResultHistopathologyDao();
        TestResultHistopathology testResultHistopathology = testResultHistopathologyDao.queryBuilder().where(TestResultHistopathologyDao.Properties.Patientid.eq(patient.getPatientid())).unique();
        if (testResultHistopathology != null) {
            radioHistopathology.checkAt(testResultHistopathology.getHistopathology());
            radioHistopathologyResult.checkAt(testResultHistopathology.getResult());
            txtHistopathologySite.setText(testResultHistopathology.getHistopathology_site());
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

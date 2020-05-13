package com.streamstech.droid.mshtb.activity;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.adapter.AutoCompleteListAdapter;
import com.streamstech.droid.mshtb.adapter.PETContactAutoCompleteListAdapter;
import com.streamstech.droid.mshtb.data.Constant;
import com.streamstech.droid.mshtb.data.persistent.DBColumnKeys;
import com.streamstech.droid.mshtb.data.persistent.DatabaseManager;
import com.streamstech.droid.mshtb.data.persistent.PETEnrollment;
import com.streamstech.droid.mshtb.data.persistent.PETEnrollmentDao;
import com.streamstech.droid.mshtb.data.persistent.PETSymptom;
import com.streamstech.droid.mshtb.data.persistent.PETSymptomDao;
import com.streamstech.droid.mshtb.data.persistent.Patient;
import com.streamstech.droid.mshtb.data.persistent.PatientDao;
import com.streamstech.droid.mshtb.data.persistent.TestIndication;
import com.streamstech.droid.mshtb.data.persistent.TestIndicationDao;
import com.streamstech.droid.mshtb.util.MSHTBApplication;
import com.streamstech.droid.mshtb.util.MyRadioGroup;
import com.streamstech.droid.mshtb.util.UIUtil;
import com.streamstech.droid.mshtb.util.Util;
import com.whygraphics.multilineradiogroup.MultiLineRadioGroup;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class PETSymptomActivity extends BaseSearchActivity implements BaseSearchActivity.PatientSelectionEventListener,
        DialogInterface.OnDismissListener {

    ScrollView scrollView;
    MultiLineRadioGroup radioGroup1;
    MultiLineRadioGroup radioGroup2;
    MultiLineRadioGroup radioGroup3;
    MultiLineRadioGroup radioGroup4;
    MultiLineRadioGroup radioGroup5;
    MultiLineRadioGroup radioGroup6;
    MultiLineRadioGroup radioGroup7;
    MultiLineRadioGroup radioGroup8;

    Button btnSave;
    PETEnrollment patient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View child = getLayoutInflater().inflate(R.layout.activity_petsymptom, null);
        container.addView(child);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        radioGroup1 = (MultiLineRadioGroup) container.findViewById(R.id.radioGroup1);
        radioGroup2 = (MultiLineRadioGroup) container.findViewById(R.id.radioGroup2);
        radioGroup3 = (MultiLineRadioGroup) container.findViewById(R.id.radioGroup3);
        radioGroup4 = (MultiLineRadioGroup) container.findViewById(R.id.radioGroup4);
        radioGroup5 = (MultiLineRadioGroup) container.findViewById(R.id.radioGroup5);
        radioGroup6 = (MultiLineRadioGroup) container.findViewById(R.id.radioGroup6);
        radioGroup7 = (MultiLineRadioGroup) container.findViewById(R.id.radioGroup7);
        radioGroup8 = (MultiLineRadioGroup) container.findViewById(R.id.radioGroup8);
        btnSave = (Button) container.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAdd();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String pid  = bundle.getString("PID");
            PETEnrollmentDao dao = DatabaseManager.getInstance().getSession().getPETEnrollmentDao();
            patient = dao.queryBuilder().where(PETEnrollmentDao.Properties.Qr.eq(pid)).unique();
            if (patient == null){
                finish();
            }else{
                topLayout.setVisibility(View.GONE);
                btnSave.setVisibility(View.GONE);
                findInforamation();
                MSHTBApplication.getInstance().hideKeyboard(this);
            }
        }
        setListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickAdd() {

        if ("true".equalsIgnoreCase(MSHTBApplication.getInstance().getSettings(DBColumnKeys.MEDIC))) {
            UIUtil.showInfoDialog(this, SweetAlertDialog.WARNING_TYPE, "Warning", String.format("Not allowed to updated this information"));
            return;
        }
        if (getEnrolledPatient() == null) {
            UIUtil.displayError(this, "Contact details");
            return;
        }

        PETSymptomDao dao = DatabaseManager.getInstance().getSession().getPETSymptomDao();
        dao.save(new PETSymptom(null, getEnrolledPatient().getQr(), MSHTBApplication.getInstance().getSettingsInt(DBColumnKeys.SCREENER_ID)/*Constant.SCREENER_ID*/,
                radioGroup1.getCheckedRadioButtonIndex(), radioGroup2.getCheckedRadioButtonIndex(),
                radioGroup3.getCheckedRadioButtonIndex(), radioGroup4.getCheckedRadioButtonIndex(),
                radioGroup5.getCheckedRadioButtonIndex(), radioGroup6.getCheckedRadioButtonIndex(),
                radioGroup7.getCheckedRadioButtonIndex(), radioGroup8.getCheckedRadioButtonIndex(),
                new Date().getTime(), false, 0.0, 0.0));

        UIUtil.showInfoDialog(this, SweetAlertDialog.SUCCESS_TYPE,  "Success", " Contact symptom screening completed", this);
    }


    private void findInforamation(){
        PETSymptomDao dao = DatabaseManager.getInstance().getSession().getPETSymptomDao();
        PETSymptom screening = dao.queryBuilder().where(PETSymptomDao.Properties.Contactqr.eq(patient.getQr())).unique();
        if (screening != null) {
            radioGroup1.checkAt(screening.getQ1());
            radioGroup2.checkAt(screening.getQ2());
            radioGroup3.checkAt(screening.getQ3());
            radioGroup4.checkAt(screening.getQ4());
            radioGroup5.checkAt(screening.getQ5());
            radioGroup6.checkAt(screening.getQ6());
            radioGroup7.checkAt(screening.getQ7());
            radioGroup8.checkAt(screening.getQ8());
            btnSave.setVisibility(View.GONE);
        } else {

        }
    }

    @Override
    public void onPatientSelected(PETEnrollment patient) {
        this.patient = patient;
        findInforamation();
        System.out.println("Selected patient qr: " + patient.getQr());
    }

    @Override
    public void onClearClicked () {
        radioGroup1.checkAt(1);
        radioGroup2.checkAt(1);
        radioGroup3.checkAt(1);
        radioGroup4.checkAt(1);
        radioGroup5.checkAt(1);
        radioGroup6.checkAt(1);
        radioGroup7.checkAt(1);
        radioGroup8.checkAt(1);
        btnSave.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        finish();
    }
}

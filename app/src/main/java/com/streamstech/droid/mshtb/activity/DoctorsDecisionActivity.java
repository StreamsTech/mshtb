package com.streamstech.droid.mshtb.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.streamstech.droid.mshtb.util.MyRadioGroup;
import com.whygraphics.multilineradiogroup.MultiLineRadioGroup;
import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.data.persistent.DatabaseManager;
import com.streamstech.droid.mshtb.data.persistent.Patient;
import com.streamstech.droid.mshtb.data.persistent.PatientDao;
import com.streamstech.droid.mshtb.util.UIUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class DoctorsDecisionActivity extends AppCompatActivity implements DialogInterface.OnDismissListener {

    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.txtID)
    TextView txtID;
    @BindView(R.id.radioTB)
    MultiLineRadioGroup radioTB;
    @BindView(R.id.radioSensivity)
    MultiLineRadioGroup radioSensivity;
    @BindView(R.id.radioSiteofDisease)
    MultiLineRadioGroup radioSiteofDisease;
    @BindView(R.id.layoutSensitivity)
    LinearLayout layoutSensitivity;
    @BindView(R.id.layoutSite)
    LinearLayout layoutSite;

    Patient patient = null;
    @BindView(R.id.btnSave)
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_decision);
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
                findInforamation();
            }
        }

//        radioTB.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                if ( radioTB.getSelectedIndex() == 0){
//                    layoutSite.setVisibility(View.VISIBLE);
//                    layoutSensitivity.setVisibility(View.VISIBLE);
//                }else{
//                    layoutSensitivity.setVisibility(View.GONE);
//                    layoutSite.setVisibility(View.GONE);
//                }
//            }
//        });

        radioTB.setOnCheckedChangeListener(new MultiLineRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ViewGroup viewGroup, RadioButton radioButton) {
                if (radioButton.isChecked() && radioButton.getText().toString().equalsIgnoreCase("yes")) {
                    layoutSite.setVisibility(View.VISIBLE);
                    layoutSensitivity.setVisibility(View.VISIBLE);
                } else {
                    layoutSensitivity.setVisibility(View.GONE);
                    layoutSite.setVisibility(View.GONE);
                }
            }
        });
    }

    @OnClick(R.id.btnSave)
    public void onClickAdd() {
        patient.setTb(radioTB.getCheckedRadioButtonIndex() == 0);//  ((RadioButton)radioTB.getChildAt(0)).isChecked());
        patient.setSensivity(radioSensivity.getCheckedRadioButtonIndex());
        patient.setSiteofdisease(radioSiteofDisease.getCheckedRadioButtonIndex());
        patient.setDirty(true);
        DatabaseManager.getInstance().getSession().getPatientDao().update(patient);
        UIUtil.showInfoDialog(this, SweetAlertDialog.SUCCESS_TYPE,  "Success", "Patient information updated", this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void findInforamation(){

        txtName.setText(patient.getName());
        txtID.setText(patient.getPatientid());
        if (patient.getTb()) {
            radioTB.checkAt(0);
//            ((RadioButton)radioTB.getChildAt(0)).setChecked(true);
            if (patient.getSensivity() != -1) {
                radioSensivity.checkAt(patient.getSensivity());
//                ((RadioButton) radioSensivity.getChildAt(patient.getSensivity())).setChecked(true);
            } if (patient.getSiteofdisease() != -1) {
//                ((RadioButton) radioSiteofDisease.getChildAt(patient.getSiteofdisease())).setChecked(true);
                radioSiteofDisease.checkAt(patient.getSiteofdisease());
            }
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        finish();
    }
}

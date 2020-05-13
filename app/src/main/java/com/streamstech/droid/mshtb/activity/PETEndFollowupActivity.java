package com.streamstech.droid.mshtb.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.data.Constant;
import com.streamstech.droid.mshtb.data.persistent.DBColumnKeys;
import com.streamstech.droid.mshtb.data.persistent.DatabaseManager;
import com.streamstech.droid.mshtb.data.persistent.PETAdverseEventFollowup;
import com.streamstech.droid.mshtb.data.persistent.PETAdverseEventFollowupDao;
import com.streamstech.droid.mshtb.data.persistent.PETEnrollment;
import com.streamstech.droid.mshtb.data.persistent.PETEnrollmentDao;
import com.streamstech.droid.mshtb.data.persistent.PETFollowupEnd;
import com.streamstech.droid.mshtb.data.persistent.PETFollowupEndDao;
import com.streamstech.droid.mshtb.util.MSHTBApplication;
import com.streamstech.droid.mshtb.util.UIUtil;
import com.streamstech.droid.mshtb.util.Util;
import com.whygraphics.multilineradiogroup.MultiLineRadioGroup;
import com.xeoh.android.checkboxgroup.CheckBoxGroup;

import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PETEndFollowupActivity extends BaseSearchActivity implements BaseSearchActivity.PatientSelectionEventListener,
        DialogInterface.OnDismissListener  {

    EditText txtComment;
    EditText txtOther;
    MultiLineRadioGroup radioGroup1;
    MultiLineRadioGroup radioGroup2;
    LinearLayout layout2a;

    Button btnSave;
    PETEnrollment patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View child = getLayoutInflater().inflate(R.layout.activity_petend_followup, null);
        container.addView(child);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

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

        layout2a = (LinearLayout) container.findViewById(R.id.layout2a);
        txtComment = (EditText)container.findViewById(R.id.txtComment);
        txtOther = (EditText)container.findViewById(R.id.txtOther);
        radioGroup1 = (MultiLineRadioGroup) container.findViewById(R.id.radioGroup1);
        radioGroup2 = (MultiLineRadioGroup) container.findViewById(R.id.radioGroup2);
        radioGroup2.setOnCheckedChangeListener(new MultiLineRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ViewGroup viewGroup, RadioButton radioButton) {
                if (radioGroup2.getCheckedRadioButtonIndex() == 6){
                    layout2a.setVisibility(View.VISIBLE);
                } else {
                    layout2a.setVisibility(View.GONE);
                }
            }
        });

        btnSave = (Button) container.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAdd();
            }
        });
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

        if (getEnrolledPatient() == null) {
            UIUtil.displayError(this, "Contact details");
            return;
        } else if (radioGroup1.getCheckedRadioButtonIndex() == 0) {
            UIUtil.displayError(this, "Treatment duration");
            return;
        } else if (radioGroup2.getCheckedRadioButtonIndex() == -1) {
            UIUtil.displayError(this, "End reason");
            return;
        }

        PETFollowupEndDao dao = DatabaseManager.getInstance().getSession().getPETFollowupEndDao();
        dao.save(new PETFollowupEnd(null, getEnrolledPatient().getQr(), MSHTBApplication.getInstance().getSettingsInt(DBColumnKeys.SCREENER_ID)/*Constant.SCREENER_ID*/,
                radioGroup1.getCheckedRadioButtonIndex(), radioGroup2.getCheckedRadioButtonIndex(),
                Util.getText(txtOther), Util.getText(txtComment), new Date().getTime(), false, 0.0, 0.0));

        UIUtil.showInfoDialog(this, SweetAlertDialog.SUCCESS_TYPE,  "Success", "Treatment ended", this);
    }

    private void findInforamation(){
//        PETAdherenceDao dao = DatabaseManager.getInstance().getSession().getPETAdherenceDao();
//        PETAdherence obj = dao.queryBuilder().where(PETAdherenceDao.Properties.Contactqr.eq(patient.getQr())).unique();
//        if (obj != null) {
//            radioGroup1.checkAt(obj.getMonthoftreatment());
//            radioGroup2.checkAt(obj.getWeekoftreatment());
//            radioGroup3.checkAt(obj.getAdrerence());
//            txtAdverseEffect.setText(obj.getAdverseeffect());
//            radioGroup4.checkAt(obj.getSeriousadverse());
//            txtComment.setText(obj.getComment());
//            radioGroup5.checkAt(obj.getDoctornotified());
//            btnSave.setVisibility(View.GONE);
//        }
    }

    @Override
    public void onPatientSelected(PETEnrollment patient) {
        this.patient = patient;
        findInforamation();
        System.out.println("Selected patient qr: " + patient.getQr());
    }

    @Override
    public void onClearClicked () {
        radioGroup1.checkAt(2);
        radioGroup2.checkAt(11);
        txtOther.setText("");
        txtComment.setText("");
        btnSave.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        finish();
    }
}

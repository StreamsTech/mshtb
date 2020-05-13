package com.streamstech.droid.mshtb.activity;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.data.Constant;
import com.streamstech.droid.mshtb.data.persistent.DBColumnKeys;
import com.streamstech.droid.mshtb.data.persistent.DatabaseManager;
import com.streamstech.droid.mshtb.data.persistent.PETAdherence;
import com.streamstech.droid.mshtb.data.persistent.PETAdherenceDao;
import com.streamstech.droid.mshtb.data.persistent.PETEnrollment;
import com.streamstech.droid.mshtb.data.persistent.PETEnrollmentDao;
import com.streamstech.droid.mshtb.data.persistent.PETTreatmentStart;
import com.streamstech.droid.mshtb.data.persistent.PETTreatmentStartDao;
import com.streamstech.droid.mshtb.util.MSHTBApplication;
import com.streamstech.droid.mshtb.util.UIUtil;
import com.streamstech.droid.mshtb.util.Util;
import com.whygraphics.multilineradiogroup.MultiLineRadioGroup;

import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PETTreatmentAdherenceActivity extends BaseSearchActivity implements BaseSearchActivity.PatientSelectionEventListener,
        DialogInterface.OnDismissListener {

    MultiLineRadioGroup radioGroup1;
    MultiLineRadioGroup radioGroup2;
    MultiLineRadioGroup radioGroup3;
    MultiLineRadioGroup radioGroup4;
    MultiLineRadioGroup radioGroup5;

    EditText txtAdverseEffect;
    EditText txtComment;

    Button btnSave;
    PETEnrollment patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View child = getLayoutInflater().inflate(R.layout.activity_pettreatment_adherence, null);
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
        txtAdverseEffect = (EditText)container.findViewById(R.id.txtAdverseEffect) ;
        txtComment = (EditText)container.findViewById(R.id.txtComment) ;
        radioGroup1 = (MultiLineRadioGroup) container.findViewById(R.id.radioGroup1);
        radioGroup2 = (MultiLineRadioGroup) container.findViewById(R.id.radioGroup2);
        radioGroup3 = (MultiLineRadioGroup) container.findViewById(R.id.radioGroup3);
        radioGroup4 = (MultiLineRadioGroup) container.findViewById(R.id.radioGroup4);
        radioGroup5 = (MultiLineRadioGroup) container.findViewById(R.id.radioGroup5);
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
        }

        PETAdherenceDao dao = DatabaseManager.getInstance().getSession().getPETAdherenceDao();
        dao.save(new PETAdherence(null, getEnrolledPatient().getQr(), MSHTBApplication.getInstance().getSettingsInt(DBColumnKeys.SCREENER_ID)/*Constant.SCREENER_ID*/,
                radioGroup1.getCheckedRadioButtonIndex(),  radioGroup2.getCheckedRadioButtonIndex(),
                radioGroup3.getCheckedRadioButtonIndex(), Util.getText(txtAdverseEffect),
                radioGroup4.getCheckedRadioButtonIndex(),  Util.getText(txtComment),
                radioGroup5.getCheckedRadioButtonIndex(), new Date().getTime(), false, 0.0, 0.0));

        UIUtil.showInfoDialog(this, SweetAlertDialog.SUCCESS_TYPE,  "Success", "Treatment adherence form completed", this);
    }

    private void findInforamation(){
        PETAdherenceDao dao = DatabaseManager.getInstance().getSession().getPETAdherenceDao();
        PETAdherence obj = dao.queryBuilder().where(PETAdherenceDao.Properties.Contactqr.eq(patient.getQr())).unique();
        if (obj != null) {
            radioGroup1.checkAt(obj.getMonthoftreatment());
            radioGroup2.checkAt(obj.getWeekoftreatment());
            radioGroup3.checkAt(obj.getAdrerence());
            txtAdverseEffect.setText(obj.getAdverseeffect());
            radioGroup4.checkAt(obj.getSeriousadverse());
            txtComment.setText(obj.getComment());
            radioGroup5.checkAt(obj.getDoctornotified());
            btnSave.setVisibility(View.GONE);
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
        radioGroup1.checkAt(2);
        radioGroup2.checkAt(11);
        radioGroup3.checkAt(0);
        radioGroup4.checkAt(1);
        radioGroup5.checkAt(1);
        txtAdverseEffect.setText("");
        txtComment.setText("");
        btnSave.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        finish();
    }
}

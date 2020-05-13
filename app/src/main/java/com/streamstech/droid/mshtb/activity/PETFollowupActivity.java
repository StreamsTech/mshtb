package com.streamstech.droid.mshtb.activity;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.streamstech.droid.mshtb.data.persistent.PETFollowup;
import com.streamstech.droid.mshtb.data.persistent.PETFollowupDao;
import com.streamstech.droid.mshtb.data.persistent.PETTreatmentStart;
import com.streamstech.droid.mshtb.data.persistent.PETTreatmentStartDao;
import com.streamstech.droid.mshtb.util.MSHTBApplication;
import com.streamstech.droid.mshtb.util.UIUtil;
import com.streamstech.droid.mshtb.util.Util;
import com.whygraphics.multilineradiogroup.MultiLineRadioGroup;
import com.xeoh.android.checkboxgroup.CheckBoxGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PETFollowupActivity extends BaseSearchActivity implements BaseSearchActivity.PatientSelectionEventListener,
        DialogInterface.OnDismissListener {

    MultiLineRadioGroup radioGroup1;
    MultiLineRadioGroup radioGroup2;
    MultiLineRadioGroup radioGroup3;

    CheckBoxGroup<String> checkBoxGroup;
    CheckBoxGroup<String> checkBoxGroup2;

    CheckBox chk1;
    CheckBox chk2;
    CheckBox chk3;
    CheckBox chk4;
    CheckBox chk5;
    CheckBox chk6;
    CheckBox chk7;
    CheckBox chk8;
    CheckBox chk9;
    CheckBox chk10;
    CheckBox chk11;
    CheckBox chk12;
    CheckBox chk13;
    CheckBox chk14;
    CheckBox chk15;

    EditText txtSymptom;
    EditText txtOther;
    EditText txtAdherence;

    Button btnSave;
    PETEnrollment patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View child = getLayoutInflater().inflate(R.layout.activity_petfollowup, null);
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

        txtAdherence = (EditText)container.findViewById(R.id.txtAdherence);
        txtOther = (EditText)container.findViewById(R.id.txtOther);
        txtSymptom = (EditText)container.findViewById(R.id.txtSymptom);

        radioGroup1 = (MultiLineRadioGroup) container.findViewById(R.id.radioGroup1);
        radioGroup2 = (MultiLineRadioGroup) container.findViewById(R.id.radioGroup2);
        radioGroup3 = (MultiLineRadioGroup) container.findViewById(R.id.radioGroup3);

        HashMap<CheckBox, String> checkBoxMap = new HashMap<>();
        checkBoxMap.put((CheckBox) findViewById(R.id.chk1), "0");
        checkBoxMap.put((CheckBox) findViewById(R.id.chk2), "1");
        checkBoxMap.put((CheckBox) findViewById(R.id.chk3), "2");
        checkBoxMap.put((CheckBox) findViewById(R.id.chk4), "3");
        checkBoxMap.put((CheckBox) findViewById(R.id.chk5), "4");

        checkBoxGroup = new CheckBoxGroup<>(checkBoxMap,
                new CheckBoxGroup.CheckedChangeListener<String>() {
                    @Override
                    public void onCheckedChange(ArrayList<String> options) {
                    }
                });


        HashMap<CheckBox, String> checkBoxMap2 = new HashMap<>();
        checkBoxMap.put((CheckBox) findViewById(R.id.chk6), "0");
        checkBoxMap.put((CheckBox) findViewById(R.id.chk7), "1");
        checkBoxMap.put((CheckBox) findViewById(R.id.chk8), "2");
        checkBoxMap.put((CheckBox) findViewById(R.id.chk9), "3");
        checkBoxMap.put((CheckBox) findViewById(R.id.chk10), "4");
        checkBoxMap.put((CheckBox) findViewById(R.id.chk11), "5");
        checkBoxMap.put((CheckBox) findViewById(R.id.chk12), "6");
        checkBoxMap.put((CheckBox) findViewById(R.id.chk13), "7");
        checkBoxMap.put((CheckBox) findViewById(R.id.chk14), "8");
        checkBoxMap.put((CheckBox) findViewById(R.id.chk15), "9");

        checkBoxGroup2 = new CheckBoxGroup<>(checkBoxMap2,
                new CheckBoxGroup.CheckedChangeListener<String>() {
                    @Override
                    public void onCheckedChange(ArrayList<String> options) {
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

        ArrayList<String> selectedValues = checkBoxGroup.getValues();
        ArrayList<String> selectedValues2 = checkBoxGroup.getValues();

        if (getEnrolledPatient() == null) {
            UIUtil.displayError(this, "Contact details");
            return;
        } else if (radioGroup1.getCheckedRadioButtonIndex() == 0 && selectedValues.isEmpty()) {
            UIUtil.displayError(this, "TB symptom");
            return;
        }

        PETFollowupDao dao = DatabaseManager.getInstance().getSession().getPETFollowupDao();
        dao.save(new PETFollowup(null, patient.getQr(), MSHTBApplication.getInstance().getSettingsInt(DBColumnKeys.SCREENER_ID)/*Constant.SCREENER_ID*/,
                radioGroup1.getCheckedRadioButtonIndex(),  radioGroup2.getCheckedRadioButtonIndex() == 0 ? true : false, selectedValues.toString().replaceAll("[\\[\\]]", ""),
                Util.getText(txtSymptom), selectedValues2.toString().replaceAll("[\\[\\]]", ""),
                Util.getText(txtOther), radioGroup3.getCheckedRadioButtonIndex(), Util.getText(txtAdherence),
                new Date().getTime(), false, 0.0, 0.0));

        UIUtil.showInfoDialog(this, SweetAlertDialog.SUCCESS_TYPE,  "Success", "Followup completed", this);
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
        radioGroup3.checkAt(0);
//        radioGroup4.checkAt(1);
//        radioGroup5.checkAt(1);
//        txtAdverseEffect.setText("");
//        txtComment.setText("");
        btnSave.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        finish();
    }
}

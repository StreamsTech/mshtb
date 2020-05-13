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
import com.streamstech.droid.mshtb.data.persistent.PETClinicianFollowup;
import com.streamstech.droid.mshtb.data.persistent.PETClinicianFollowupDao;
import com.streamstech.droid.mshtb.data.persistent.PETClinicianTBReview;
import com.streamstech.droid.mshtb.data.persistent.PETClinicianTBReviewDao;
import com.streamstech.droid.mshtb.data.persistent.PETEnrollment;
import com.streamstech.droid.mshtb.data.persistent.PETEnrollmentDao;
import com.streamstech.droid.mshtb.util.MSHTBApplication;
import com.streamstech.droid.mshtb.util.UIUtil;
import com.streamstech.droid.mshtb.util.Util;
import com.whygraphics.multilineradiogroup.MultiLineRadioGroup;
import com.xeoh.android.checkboxgroup.CheckBoxGroup;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PETClinicianTBFollowupActivicity extends BaseSearchActivity implements BaseSearchActivity.PatientSelectionEventListener,
        DialogInterface.OnDismissListener  {

    MultiLineRadioGroup radioGroup1;
    CheckBoxGroup<String> checkBoxGroup;
    CheckBoxGroup<String> checkBoxGroup2;
    EditText txtInstruction;
    DatePicker dtReturnVisitDate;
    Button btnSave;
    PETEnrollment patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View child = getLayoutInflater().inflate(R.layout.activity_petclinician_tbfollowup_activicity, null);
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

        radioGroup1 = (MultiLineRadioGroup) container.findViewById(R.id.radioGroup1);
        txtInstruction = (EditText) container.findViewById(R.id.txtInstruction);
        dtReturnVisitDate = (DatePicker) container.findViewById(R.id.dtReturnVisitDate);

        HashMap<CheckBox, String> checkBoxMap = new HashMap<>();
        checkBoxMap.put((CheckBox) findViewById(R.id.chk1), "0");
        checkBoxMap.put((CheckBox) findViewById(R.id.chk2), "1");
        checkBoxMap.put((CheckBox) findViewById(R.id.chk3), "2");
        checkBoxMap.put((CheckBox) findViewById(R.id.chk4), "3");
        checkBoxMap.put((CheckBox) findViewById(R.id.chk5), "4");
        checkBoxMap.put((CheckBox) findViewById(R.id.chk6), "5");

        checkBoxGroup = new CheckBoxGroup<>(checkBoxMap,
                new CheckBoxGroup.CheckedChangeListener<String>() {
                    @Override
                    public void onCheckedChange(ArrayList<String> options) {
                    }
                });

        HashMap<CheckBox, String> checkBoxMap2 = new HashMap<>();
        checkBoxMap.put((CheckBox) findViewById(R.id.chk7), "0");
        checkBoxMap.put((CheckBox) findViewById(R.id.chk8), "1");
        checkBoxMap.put((CheckBox) findViewById(R.id.chk9), "2");
        checkBoxMap.put((CheckBox) findViewById(R.id.chk10), "3");
        checkBoxMap.put((CheckBox) findViewById(R.id.chk11), "4");
        checkBoxMap.put((CheckBox) findViewById(R.id.chk12), "5");

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
            UIUtil.displayError(this, "Adherence");
            return;
        }

//            public PETClinicianTBReview(Long id, @NotNull String contactqr, int screenerid,
//        int adherence, @NotNull String labtests, @NotNull String plans,
//        String newinstructions, long returnvisitdate, long createdtime,
//        boolean uploaded, double longitude, double latitude)

        PETClinicianTBReviewDao dao = DatabaseManager.getInstance().getSession().getPETClinicianTBReviewDao();
        dao.save(new PETClinicianTBReview(null, patient.getQr(), MSHTBApplication.getInstance().getSettingsInt(DBColumnKeys.SCREENER_ID)/*Constant.SCREENER_ID*/,
                radioGroup1.getCheckedRadioButtonIndex(),
                selectedValues.toString().replaceAll("[\\[\\]]", ""),
                selectedValues2.toString().replaceAll("[\\[\\]]", ""),
                Util.getText(txtInstruction), Util.getDateFromDatePicker(dtReturnVisitDate).getTime(),
                new Date().getTime(), false, 0.0, 0.0));

        UIUtil.showInfoDialog(this, SweetAlertDialog.SUCCESS_TYPE,  "Success", "Clinician followup (part two) completed", this);
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
        radioGroup1.checkAt(0);
        btnSave.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        finish();
    }
}

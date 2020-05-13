package com.streamstech.droid.mshtb.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.data.Constant;
import com.streamstech.droid.mshtb.data.persistent.DBColumnKeys;
import com.streamstech.droid.mshtb.data.persistent.DatabaseManager;
import com.streamstech.droid.mshtb.data.persistent.PETEligibilitySymptom;
import com.streamstech.droid.mshtb.data.persistent.PETEligibilitySymptomDao;
import com.streamstech.droid.mshtb.data.persistent.PETEnrollment;
import com.streamstech.droid.mshtb.util.MSHTBApplication;
import com.streamstech.droid.mshtb.util.UIUtil;
import com.whygraphics.multilineradiogroup.MultiLineRadioGroup;
import java.util.Date;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class PETEligibilityQuestionnaireActivity extends BaseSearchActivity
        implements BaseSearchActivity.PatientSelectionEventListener, DialogInterface.OnDismissListener {

//    @BindView(R.id.layout1a)
    LinearLayout layout1a;
//    @BindView(R.id.layout1b)
    LinearLayout layout1b;
    LinearLayout layout1c;
    LinearLayout layout1d;
//    @BindView(R.id.layout3a)
    LinearLayout layout3a;

//    @BindView(R.id.radioGroup1)
    MultiLineRadioGroup radioGroup1;
//    @BindView(R.id.radioGroup1a)
    MultiLineRadioGroup radioGroup1a;
//    @BindView(R.id.radioGroup1b)
    MultiLineRadioGroup radioGroup1b;
    MultiLineRadioGroup radioGroup1c;
    MultiLineRadioGroup radioGroup1d;
//    @BindView(R.id.radioGroup2)
    MultiLineRadioGroup radioGroup2;
//    @BindView(R.id.radioGroup3)
    MultiLineRadioGroup radioGroup3;
//    @BindView(R.id.radioGroup3a)
    MultiLineRadioGroup radioGroup3a;
//    @BindView(R.id.radioGroup4)
    MultiLineRadioGroup radioGroup4;

//    @BindView(R.id.txtSite)
    EditText txtSite;
//    @BindView(R.id.btnSave)
    Button btnSave;
    PETEnrollment patient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        View child = getLayoutInflater().inflate(R.layout.activity_eligibility_questionnaire, null);
        container.addView(child);
        layout1a = (LinearLayout) container.findViewById(R.id.layout1a);
        layout1b = (LinearLayout) container.findViewById(R.id.layout1b);
        layout1c = (LinearLayout) container.findViewById(R.id.layout1c);
        layout1d = (LinearLayout) container.findViewById(R.id.layout1d);
        layout3a = (LinearLayout) container.findViewById(R.id.layout3a);

        radioGroup1 = (MultiLineRadioGroup) container.findViewById(R.id.radioGroup1);
        radioGroup1a = (MultiLineRadioGroup) container.findViewById(R.id.radioGroup1a);
        radioGroup1b = (MultiLineRadioGroup) container.findViewById(R.id.radioGroup1b);
        radioGroup1c = (MultiLineRadioGroup) container.findViewById(R.id.radioGroup1c);
        radioGroup1d = (MultiLineRadioGroup) container.findViewById(R.id.radioGroup1d);
        radioGroup2 = (MultiLineRadioGroup) container.findViewById(R.id.radioGroup2);
        radioGroup3 = (MultiLineRadioGroup) container.findViewById(R.id.radioGroup3);
        radioGroup3a = (MultiLineRadioGroup) container.findViewById(R.id.radioGroup3a);
        radioGroup4 = (MultiLineRadioGroup) container.findViewById(R.id.radioGroup4);

        txtSite = (EditText) container.findViewById(R.id.txtSite);
        btnSave = (Button) container.findViewById(R.id.btnSave);
//        ButterKnife.bind(child);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveClicked();
            }
        });

//        ButterKnife.bind(child);
//        setContentView(R.layout.activity_eligibility_questionnaire);
//        ButterKnife.bind()

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            topLayout.setVisibility(View.GONE);
            btnSave.setVisibility(View.GONE);
            findInforamation();
            MSHTBApplication.getInstance().hideKeyboard(this);
        }
        radioGroup1.setOnCheckedChangeListener(new MultiLineRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ViewGroup viewGroup, RadioButton radioButton) {
                alterVisbleState(radioButton, layout1a);
                alterVisbleState(radioButton, layout1b);
                alterVisbleState(radioButton, layout1c);
                alterVisbleState(radioButton, layout1d);
            }
        });
        radioGroup3.setOnCheckedChangeListener(new MultiLineRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ViewGroup viewGroup, RadioButton radioButton) {
                alterVisbleState(radioButton, layout3a);
            }
        });
        setListener(this);
    }

//    @OnClick(R.id.btnSave)
    public void onSaveClicked(){

        if ("true".equalsIgnoreCase(MSHTBApplication.getInstance().getSettings(DBColumnKeys.MEDIC))) {
            UIUtil.showInfoDialog(this, SweetAlertDialog.WARNING_TYPE, "Warning", String.format("Not allowed to updated this information"));
            return;
        }
        if (getEnrolledPatient() == null) {
            UIUtil.displayError(this, "Contact details");
            return;
        }
//        boolean presumptiveTB = radioGroup3a.getCheckedRadioButtonIndex() == 2 || radioGroup3a.getCheckedRadioButtonIndex() == 4;
        if (radioGroup1.getCheckedRadioButtonIndex() == 0 && (radioGroup1a.getCheckedRadioButtonIndex() == -1 || radioGroup1b.getCheckedRadioButtonIndex() == -1)) {
            UIUtil.displayError(this, "1a and 1b");
            return;
        } else if (radioGroup3.getCheckedRadioButtonIndex() == 0 && radioGroup3a.getCheckedRadioButtonIndex() == -1) {
            UIUtil.showInfoDialog(this, SweetAlertDialog.ERROR_TYPE, "Warning", "3a", null);
            return;
        }
        PETEligibilitySymptomDao dao = DatabaseManager.getInstance().getSession().getPETEligibilitySymptomDao();

        dao.insert(new PETEligibilitySymptom(null, getEnrolledPatient().getQr(), MSHTBApplication.getInstance().getSettingsInt(DBColumnKeys.SCREENER_ID)/*Constant.SCREENER_ID*/,
                radioGroup1.getCheckedRadioButtonIndex(), radioGroup1a.getCheckedRadioButtonIndex(),
                radioGroup1b.getCheckedRadioButtonIndex(), radioGroup1c.getCheckedRadioButtonIndex(),
                radioGroup1d.getCheckedRadioButtonIndex(), radioGroup2.getCheckedRadioButtonIndex(),
                radioGroup3.getCheckedRadioButtonIndex(), radioGroup3a.getCheckedRadioButtonIndex(),
                radioGroup4.getCheckedRadioButtonIndex(), txtSite.getText().toString(), new Date().getTime(),
                false, 0.0, 0.0));

        UIUtil.showInfoDialog(this,SweetAlertDialog.SUCCESS_TYPE, "Success", "Eligible questionnaires saved ", this);
    }

    private void alterVisbleState(RadioButton radioButton, LinearLayout linearLayout){
        if (radioButton.isChecked() && radioButton.getText().toString().equalsIgnoreCase("yes")){
            linearLayout.setVisibility(View.VISIBLE);
        }else{
            linearLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPatientSelected(PETEnrollment patient) {
        this.patient = patient;
        findInforamation();
        System.out.println("Selected patient qr: " + patient.getQr());
    }

    private void findInforamation(){
        PETEligibilitySymptomDao symptomDao = DatabaseManager.getInstance().getSession().getPETEligibilitySymptomDao();
        PETEligibilitySymptom screening = symptomDao.queryBuilder().where(PETEligibilitySymptomDao.Properties.Contactqr.eq(patient.getQr())).unique();
        if (screening != null) {
            radioGroup1.checkAt(screening.getTbdiagnosed());
            radioGroup1a.checkAt(screening.getTbdiagnosedhow());
            radioGroup1b.checkAt(screening.getTbtype());
            radioGroup2.checkAt(screening.getReferred());
            txtSite.setText(screening.getFacilityname());
            radioGroup3.checkAt(screening.getTbruledout());
            radioGroup3a.checkAt(screening.getTbruledouthow());
            radioGroup4.checkAt(screening.getPeteligible());
            btnSave.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        finish();
    }

    @Override
    public void onClearClicked () {
        radioGroup1.checkAt(1);
        radioGroup2.checkAt(1);
        txtSite.setText("");
        radioGroup3.checkAt(1);
        radioGroup4.checkAt(1);
        btnSave.setVisibility(View.VISIBLE);
    }
}

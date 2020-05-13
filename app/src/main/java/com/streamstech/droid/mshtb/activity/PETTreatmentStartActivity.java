package com.streamstech.droid.mshtb.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.data.Constant;
import com.streamstech.droid.mshtb.data.persistent.DBColumnKeys;
import com.streamstech.droid.mshtb.data.persistent.DatabaseManager;
import com.streamstech.droid.mshtb.data.persistent.PETEnrollment;
import com.streamstech.droid.mshtb.data.persistent.PETEnrollmentDao;
import com.streamstech.droid.mshtb.data.persistent.PETTestResult;
import com.streamstech.droid.mshtb.data.persistent.PETTestResultDao;
import com.streamstech.droid.mshtb.data.persistent.PETTreatmentStart;
import com.streamstech.droid.mshtb.data.persistent.PETTreatmentStartDao;
import com.streamstech.droid.mshtb.util.MSHTBApplication;
import com.streamstech.droid.mshtb.util.UIUtil;
import com.streamstech.droid.mshtb.util.Util;
import com.whygraphics.multilineradiogroup.MultiLineRadioGroup;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PETTreatmentStartActivity extends BaseSearchActivity implements BaseSearchActivity.PatientSelectionEventListener,
        DialogInterface.OnDismissListener {

    MultiLineRadioGroup radioGroup1;
    MultiLineRadioGroup radioGroup2;

    EditText txtDrugone;
    EditText txtDrugtwo;
    EditText txtSupporter;
    EditText txtSupporterContactNo;
    EditText txtOtherRelationship;
    DatePicker dtStartDate;

    LinearLayout layout2;
    LinearLayout layout2a;
    LinearLayout layout6a;
    Button btnSave;
    PETEnrollment patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View child = getLayoutInflater().inflate(R.layout.activity_pettreatmentstart, null);
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
        layout2 = (LinearLayout)container.findViewById(R.id.layout2) ;
        layout2a = (LinearLayout)container.findViewById(R.id.layout2a) ;
        layout6a = (LinearLayout)container.findViewById(R.id.layout6a) ;
        txtDrugone = (EditText)container.findViewById(R.id.txtDrugone) ;
        txtDrugtwo = (EditText)container.findViewById(R.id.txtDrugtwo) ;
        txtSupporter = (EditText)container.findViewById(R.id.txtSupporter) ;
        txtSupporterContactNo = (EditText)container.findViewById(R.id.txtSupporterContactNo) ;
        txtOtherRelationship = (EditText)container.findViewById(R.id.txtOtherRelationship) ;

        radioGroup1 = (MultiLineRadioGroup) container.findViewById(R.id.radioGroup1);
        radioGroup1.setOnCheckedChangeListener(new MultiLineRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ViewGroup viewGroup, RadioButton radioButton) {
                if (radioGroup1.getCheckedRadioButtonIndex() == 0){
                    layout2.setVisibility(View.VISIBLE);
                    layout2a.setVisibility(View.GONE);
                } else {
                    layout2a.setVisibility(View.VISIBLE);
                    layout2.setVisibility(View.GONE);
                }
            }
        });
        radioGroup2 = (MultiLineRadioGroup) container.findViewById(R.id.radioGroup2);
        radioGroup2.setOnCheckedChangeListener(new MultiLineRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ViewGroup viewGroup, RadioButton radioButton) {
                if (radioGroup2.getCheckedRadioButtonIndex() == 7){
                    layout6a.setVisibility(View.VISIBLE);
                } else {
                    layout6a.setVisibility(View.GONE);
                }
            }
        });
        dtStartDate = (DatePicker) container.findViewById(R.id.dtStartDate);

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

        if ("true".equalsIgnoreCase(MSHTBApplication.getInstance().getSettings(DBColumnKeys.MEDIC))) {
            UIUtil.showInfoDialog(this, SweetAlertDialog.WARNING_TYPE, "Warning", String.format("Not allowed to updated this information"));
            return;
        }
        if (getEnrolledPatient() == null) {
            UIUtil.displayError(this, "Contact details");
            return;
        } else if (radioGroup1.getCheckedRadioButtonIndex() == -1) {
            UIUtil.displayError(this, "Regimen information");
            return;
        } else if ( Util.getText(txtDrugone).isEmpty() && Util.getText(txtDrugtwo).isEmpty()) {
            UIUtil.displayError(this, "Drug information");
            return;
        }

        PETTreatmentStartDao dao = DatabaseManager.getInstance().getSession().getPETTreatmentStartDao();
        dao.save(new PETTreatmentStart(null, getEnrolledPatient().getQr(), MSHTBApplication.getInstance().getSettingsInt(DBColumnKeys.SCREENER_ID)/*Constant.SCREENER_ID*/,
                radioGroup1.getCheckedRadioButtonIndex(), Util.getText(txtDrugone), Util.getText(txtDrugtwo), Util.getDateFromDatePicker(dtStartDate).getTime(),
                Util.getText(txtSupporter), Util.getText(txtSupporterContactNo), radioGroup2.getCheckedRadioButtonIndex(),
                Util.getText(txtOtherRelationship), new Date().getTime(), false, 0.0, 0.0));

        UIUtil.showInfoDialog(this, SweetAlertDialog.SUCCESS_TYPE,  "Success", "Treatment start form completed", this);
    }

    private void findInforamation(){
        PETTreatmentStartDao dao = DatabaseManager.getInstance().getSession().getPETTreatmentStartDao();
        PETTreatmentStart obj = dao.queryBuilder().where(PETTreatmentStartDao.Properties.Contactqr.eq(patient.getQr())).unique();
        if (obj != null) {
            radioGroup1.checkAt(obj.getRegimen());
            radioGroup2.checkAt(obj.getSupporterrelation());
            txtDrugone.setText(obj.getDrugone());
            txtDrugtwo.setText(obj.getDrugtwo());
            Util.setDate(obj.getTreatmentstartdate(), dtStartDate);
            txtSupporter.setText(obj.getSupportername());
            txtSupporterContactNo.setText(obj.getSupporternumber());
            txtOtherRelationship.setText(obj.getSupporterother());
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
        radioGroup1.checkAt(-1);
        radioGroup2.checkAt(-1);
        txtDrugone.setText("");
        txtDrugtwo.setText("");
        txtSupporter.setText("");
        txtSupporterContactNo.setText("");
        txtOtherRelationship.setText("");
        btnSave.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        finish();
    }
}

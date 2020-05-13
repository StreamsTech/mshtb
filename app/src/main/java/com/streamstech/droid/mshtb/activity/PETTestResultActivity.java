package com.streamstech.droid.mshtb.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.adapter.PETContactAutoCompleteListAdapter;
import com.streamstech.droid.mshtb.data.Constant;
import com.streamstech.droid.mshtb.data.persistent.DBColumnKeys;
import com.streamstech.droid.mshtb.data.persistent.DatabaseManager;
import com.streamstech.droid.mshtb.data.persistent.PETEnrollment;
import com.streamstech.droid.mshtb.data.persistent.PETEnrollmentDao;
import com.streamstech.droid.mshtb.data.persistent.PETTestOrder;
import com.streamstech.droid.mshtb.data.persistent.PETTestOrderDao;
import com.streamstech.droid.mshtb.data.persistent.PETTestResult;
import com.streamstech.droid.mshtb.data.persistent.PETTestResultDao;
import com.streamstech.droid.mshtb.util.MSHTBApplication;
import com.streamstech.droid.mshtb.util.UIUtil;
import com.streamstech.droid.mshtb.util.Util;
import com.whygraphics.multilineradiogroup.MultiLineRadioGroup;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class PETTestResultActivity extends BaseSearchActivity implements BaseSearchActivity.PatientSelectionEventListener,
        DialogInterface.OnDismissListener {

    MultiLineRadioGroup radioGroup1;
    MultiLineRadioGroup radioGroup2;
    MultiLineRadioGroup radioGroup3;
    MultiLineRadioGroup radioGroup4;
    MultiLineRadioGroup radioGroup5;
    EditText txtHB;
    EditText txtTLC;
    EditText txtMonocytes;
    EditText txtPlatelets;
    EditText txtESR;

    LinearLayout layout5a;
    MultiLineRadioGroup radioGroup6;
    MultiLineRadioGroup radioGroup7;
    MultiLineRadioGroup radioGroup8;
    Button btnSave;

    PETEnrollment patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View child = getLayoutInflater().inflate(R.layout.activity_pettestresult, null);
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

        layout5a = (LinearLayout) container.findViewById(R.id.layout5a);
        txtHB = (EditText)container.findViewById(R.id.txtHB);
        txtTLC = (EditText)container.findViewById(R.id.txtTLC);
        txtMonocytes = (EditText)container.findViewById(R.id.txtMonocytes);
        txtPlatelets = (EditText)container.findViewById(R.id.txtPlatelets);
        txtESR = (EditText)container.findViewById(R.id.txtESR) ;

        radioGroup1 = (MultiLineRadioGroup) container.findViewById(R.id.radioGroup1);
        radioGroup2 = (MultiLineRadioGroup) container.findViewById(R.id.radioGroup2);
        radioGroup3 = (MultiLineRadioGroup) container.findViewById(R.id.radioGroup3);
        radioGroup4 = (MultiLineRadioGroup) container.findViewById(R.id.radioGroup4);
        radioGroup5 = (MultiLineRadioGroup) container.findViewById(R.id.radioGroup5);
        radioGroup5.setOnCheckedChangeListener(new MultiLineRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ViewGroup viewGroup, RadioButton radioButton) {
                if (radioGroup5.getCheckedRadioButtonIndex() == 0){
                    layout5a.setVisibility(View.VISIBLE);
                } else {
                    layout5a.setVisibility(View.GONE);
                }
            }
        });
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

        PETTestResultDao dao = DatabaseManager.getInstance().getSession().getPETTestResultDao();
        dao.save(new PETTestResult(null, getEnrolledPatient().getQr(), MSHTBApplication.getInstance().getSettingsInt(DBColumnKeys.SCREENER_ID)/*Constant.SCREENER_ID*/,
                radioGroup1.getCheckedRadioButtonIndex(), radioGroup2.getCheckedRadioButtonIndex(),
                radioGroup3.getCheckedRadioButtonIndex(), radioGroup4.getCheckedRadioButtonIndex(),
                getValue(txtHB), getValue(txtTLC), getValue(txtMonocytes), getValue(txtPlatelets),
                getValue(txtESR), radioGroup6.getCheckedRadioButtonIndex(),
                "", radioGroup7.getCheckedRadioButtonIndex(), radioGroup8.getCheckedRadioButtonIndex(),
                new Date().getTime(), false, 0.0, 0.0));

        UIUtil.showInfoDialog(this, SweetAlertDialog.SUCCESS_TYPE,  "Success", "Test result completed", this);
    }

    private double getValue (EditText editText) {
        if (editText.getText().toString().trim().equals("")) {
            return -1;
        } else {
            return Double.valueOf(editText.getText().toString());
        }
    }
    private void findInforamation(){
        PETTestResultDao dao = DatabaseManager.getInstance().getSession().getPETTestResultDao();
        PETTestResult obj = dao.queryBuilder().where(PETTestResultDao.Properties.Contactqr.eq(patient.getQr())).unique();
        if (obj != null) {
            radioGroup1.checkAt(obj.getXray());
            radioGroup2.checkAt(obj.getSmear());
            radioGroup3.checkAt(obj.getXpert());
            radioGroup4.checkAt(obj.getTstmt());
            txtHB.setText(String.valueOf(obj.getCbchb()));
            txtTLC.setText(String.valueOf(obj.getCbctlc()));
            txtMonocytes.setText(String.valueOf(obj.getCbcmonocytes()));
            txtPlatelets.setText(String.valueOf(obj.getCbcplatelets()));
            txtESR.setText(String.valueOf(obj.getCbcesr()));
            radioGroup6.checkAt(obj.getHistopathology());
            radioGroup7.checkAt(obj.getCtmri());
            radioGroup8.checkAt(obj.getUltrasound());
            btnSave.setVisibility(View.GONE);
        }

        PETTestOrderDao testIndicationDao = DatabaseManager.getInstance().getSession().getPETTestOrderDao();
        PETTestOrder order = testIndicationDao.queryBuilder().where(PETTestOrderDao.Properties.Contactqr.eq(patient.getQr())).unique();
        if (order != null) {
            radioGroup5.checkAt(order.getCbcesr());
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
        txtHB.setText("");
        txtTLC.setText("");
        txtMonocytes.setText("");
        txtPlatelets.setText("");
        txtESR.setText("");
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

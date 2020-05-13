package com.streamstech.droid.mshtb.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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
import com.streamstech.droid.mshtb.data.persistent.PETTestOrder;
import com.streamstech.droid.mshtb.data.persistent.PETTestOrderDao;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class PETTestOrderActivity extends BaseSearchActivity
        implements BaseSearchActivity.PatientSelectionEventListener, DialogInterface.OnDismissListener  {

//    @BindView(R.id.txtSearch)
//    AutoCompleteTextView txtSearch;
//    @BindView(R.id.lblReadonlyName)
//    TextView lblReadonlyName;
//    @BindView(R.id.lblPatient)
//    TextView lblPatient;
//    @BindView(R.id.lblTime)
//    TextView lblTime;
//
//    @BindView(R.id.topLayout)
//    LinearLayout topLayout;
//    @BindView(R.id.scrollView)
//    ScrollView scrollView;

//    @BindView(R.id.container)
//    LinearLayout container;

//    @BindView(R.id.radioGroup1)
    MultiLineRadioGroup radioGroup1;
//    @BindView(R.id.radioGroup2)
    MultiLineRadioGroup radioGroup2;
//    @BindView(R.id.radioGroup3)
    MultiLineRadioGroup radioGroup3;
//    @BindView(R.id.radioGroup4)
    MultiLineRadioGroup radioGroup4;
//    @BindView(R.id.radioGroup5)
    MultiLineRadioGroup radioGroup5;
//    @BindView(R.id.radioGroup6)
    MultiLineRadioGroup radioGroup6;
//    @BindView(R.id.radioGroup7)
    MultiLineRadioGroup radioGroup7;
//    @BindView(R.id.radioGroup8)
    MultiLineRadioGroup radioGroup8;

//    @BindView(R.id.btnSave)
    Button btnSave;

    PETEnrollment patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View child = getLayoutInflater().inflate(R.layout.activity_pettestorder, null);
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
            topLayout.setVisibility(View.GONE);
            btnSave.setVisibility(View.GONE);
            findInforamation();
            MSHTBApplication.getInstance().hideKeyboard(this);
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

        PETTestOrderDao dao = DatabaseManager.getInstance().getSession().getPETTestOrderDao();
        dao.save(new PETTestOrder(null, getEnrolledPatient().getQr(), MSHTBApplication.getInstance().getSettingsInt(DBColumnKeys.SCREENER_ID)/*Constant.SCREENER_ID*/,
                radioGroup1.getCheckedRadioButtonIndex(), radioGroup2.getCheckedRadioButtonIndex(),
                radioGroup3.getCheckedRadioButtonIndex(), radioGroup4.getCheckedRadioButtonIndex(),
                radioGroup5.getCheckedRadioButtonIndex(), radioGroup6.getCheckedRadioButtonIndex(),
                "", radioGroup7.getCheckedRadioButtonIndex(), radioGroup8.getCheckedRadioButtonIndex(),
                new Date().getTime(), false, 0.0, 0.0));

        UIUtil.showInfoDialog(this, SweetAlertDialog.SUCCESS_TYPE,  "Success", "Test order completed", this);
    }

    private void findInforamation(){
        PETTestOrderDao testIndicationDao = DatabaseManager.getInstance().getSession().getPETTestOrderDao();
        PETTestOrder obj = testIndicationDao.queryBuilder().where(PETTestOrderDao.Properties.Contactqr.eq(patient.getQr())).unique();
        if (obj != null) {
            radioGroup1.checkAt(obj.getXray());
            radioGroup2.checkAt(obj.getSmear());
            radioGroup3.checkAt(obj.getXpert());
            radioGroup4.checkAt(obj.getTstmt());
            radioGroup5.checkAt(obj.getCbcesr());
            radioGroup6.checkAt(obj.getHistopathology());
            radioGroup7.checkAt(obj.getCtmri());
            radioGroup8.checkAt(obj.getUltrasound());
            btnSave.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        finish();
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
}

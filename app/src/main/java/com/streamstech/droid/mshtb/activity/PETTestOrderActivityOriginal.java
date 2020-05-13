package com.streamstech.droid.mshtb.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
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

public class PETTestOrderActivityOriginal extends AppCompatActivity implements DialogInterface.OnDismissListener {

    @BindView(R.id.txtSearch)
    AutoCompleteTextView txtSearch;
    @BindView(R.id.lblReadonlyName)
    TextView lblReadonlyName;
    @BindView(R.id.lblPatient)
    TextView lblPatient;
    @BindView(R.id.lblTime)
    TextView lblTime;

    @BindView(R.id.topLayout)
    LinearLayout topLayout;
    @BindView(R.id.scrollView)
    ScrollView scrollView;

    @BindView(R.id.radioGroup1)
    MultiLineRadioGroup radioGroup1;
    @BindView(R.id.radioGroup2)
    MultiLineRadioGroup radioGroup2;
    @BindView(R.id.radioGroup3)
    MultiLineRadioGroup radioGroup3;
    @BindView(R.id.radioGroup4)
    MultiLineRadioGroup radioGroup4;
    @BindView(R.id.radioGroup5)
    MultiLineRadioGroup radioGroup5;
    @BindView(R.id.radioGroup6)
    MultiLineRadioGroup radioGroup6;
    @BindView(R.id.radioGroup7)
    MultiLineRadioGroup radioGroup7;
    @BindView(R.id.radioGroup8)
    MultiLineRadioGroup radioGroup8;

    @BindView(R.id.btnSave)
    Button btnSave;

    PETEnrollment patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pettestorder);
        ButterKnife.bind(this);
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
                lblReadonlyName.setVisibility(View.VISIBLE);
                lblReadonlyName.setText(patient.getName());
                MSHTBApplication.getInstance().hideKeyboard(this);
                findInforamation();
                btnSave.setVisibility(View.GONE);
            }
        }

        txtSearch.setThreshold(1);//will start working from first character

        PETEnrollmentDao dao = DatabaseManager.getInstance().getSession().getPETEnrollmentDao();
        List<PETEnrollment> patientList = dao.loadAll();
        PETContactAutoCompleteListAdapter adapter = new PETContactAutoCompleteListAdapter(this,
                R.layout.autocomplete_list_row, patientList);
        txtSearch.setAdapter(adapter);
        txtSearch.setOnItemClickListener(onItemClickListener);
        lblTime.setText(Util.getFormattedDateTime());
    }

    private AdapterView.OnItemClickListener onItemClickListener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    patient = (PETEnrollment) adapterView.getItemAtPosition(i);
                    lblPatient.setText(patient.getName() + "\n" + patient.getQr());
                    txtSearch.setText(patient.getName());
                    MSHTBApplication.getInstance().hideKeyboard(PETTestOrderActivityOriginal.this);
                    findInforamation();
                }
            };


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btnSave)
    public void onClickAdd() {

        if (patient == null) {
            UIUtil.displayError(this, "Contact details");
            return;
        }

        PETTestOrderDao dao = DatabaseManager.getInstance().getSession().getPETTestOrderDao();
        dao.save(new PETTestOrder(null, patient.getQr(), MSHTBApplication.getInstance().getSettingsInt(DBColumnKeys.SCREENER_ID)/*Constant.SCREENER_ID*/,
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

            topLayout.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            btnSave.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        finish();
    }
}

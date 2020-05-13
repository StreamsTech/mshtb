package com.streamstech.droid.mshtb.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.adapter.PETContactAutoCompleteListAdapter;
import com.streamstech.droid.mshtb.data.Constant;
import com.streamstech.droid.mshtb.data.persistent.DatabaseManager;
import com.streamstech.droid.mshtb.data.persistent.PETEnrollment;
import com.streamstech.droid.mshtb.data.persistent.PETEnrollmentDao;
import com.streamstech.droid.mshtb.data.persistent.PETRegistration;
import com.streamstech.droid.mshtb.data.persistent.PETRegistrationDao;
import com.streamstech.droid.mshtb.util.MSHTBApplication;
import com.streamstech.droid.mshtb.util.UIUtil;
import com.streamstech.droid.mshtb.util.Util;

import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class BaseSearchActivity extends AppCompatActivity {

    @BindView(R.id.topLayout)
    LinearLayout topLayout;
    @BindView(R.id.txtSearch)
    AutoCompleteTextView txtSearch;
    @BindView(R.id.imgQR)
    ImageView imgQR;
    @BindView(R.id.imgClear)
    ImageView imgClear;
    @BindView(R.id.container)
    LinearLayout container;

    @BindView(R.id.lblIndexPatientName)
    TextView lblIndexPatientName;
    @BindView(R.id.lblIndexPatientCode)
    TextView lblIndexPatientCode;
    @BindView(R.id.lblEnrollMemberName)
    TextView lblEnrollMemberName;
    @BindView(R.id.lblEnrollMemberCode)
    TextView lblEnrollMemberCode;

    private int QR_REQUEST_CODE = 987;

    PETEnrollment patient;
    PatientSelectionEventListener patientSelectionEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_search);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String pid  = bundle.getString("PID");
            PETEnrollmentDao dao = DatabaseManager.getInstance().getSession().getPETEnrollmentDao();
            patient = dao.queryBuilder().where(PETEnrollmentDao.Properties.Qr.eq(pid)).unique();
            if (patient == null){
                finish();
            }else{
                topLayout.setVisibility(View.GONE);
                updateText();
                MSHTBApplication.getInstance().hideKeyboard(this);
            }
        }

        txtSearch.setThreshold(1);
        PETEnrollmentDao dao = DatabaseManager.getInstance().getSession().getPETEnrollmentDao();
        List<PETEnrollment> patientList = dao.loadAll();
        PETContactAutoCompleteListAdapter adapter = new PETContactAutoCompleteListAdapter(this,
                R.layout.autocomplete_list_row, patientList);
        txtSearch.setAdapter(adapter);
        txtSearch.setOnItemClickListener(onItemClickListener);
    }

    public void setListener (PatientSelectionEventListener patientSelectionEventListener){
        this.patientSelectionEventListener = patientSelectionEventListener;
    }

    private AdapterView.OnItemClickListener onItemClickListener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    patient = (PETEnrollment) adapterView.getItemAtPosition(i);
                    updateText();
                    MSHTBApplication.getInstance().hideKeyboard(BaseSearchActivity.this);
                    if (patientSelectionEventListener != null) {
                        patientSelectionEventListener.onPatientSelected(patient);
                    }
                }
            };

    private void updateText () {
        PETRegistration petRegistration = getIndexPatient();
        txtSearch.setText(patient.getName());
        txtSearch.dismissDropDown();
        lblIndexPatientName.setText(petRegistration.getName());
        lblIndexPatientCode.setText(petRegistration.getQr());
        lblEnrollMemberName.setText(patient.getName());
        lblEnrollMemberCode.setText(patient.getQr());
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == QR_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                final String contents = intent.getStringExtra("SCAN_RESULT").trim();
                Pattern checkRegex = Pattern.compile(Constant.QR_REGEX);
                if (!checkRegex.matcher(contents.trim()).matches()) {
                    txtSearch.setText("");
                    UIUtil.showInfoDialog(this, SweetAlertDialog.ERROR_TYPE, "Warning", "Not a valid QR");
                    return;
                }

                PETEnrollmentDao dao = DatabaseManager.getInstance().getSession().getPETEnrollmentDao();
                patient = dao.queryBuilder().where(PETEnrollmentDao.Properties.Qr.eq(contents)).unique();
                if (patient == null) {
                    UIUtil.showInfoDialog(this, SweetAlertDialog.ERROR_TYPE, "Error", "No enrolled member found");
                    return;
                }
                updateText();
                MSHTBApplication.getInstance().hideKeyboard(BaseSearchActivity.this);
                if (patientSelectionEventListener != null) {
                    patientSelectionEventListener.onPatientSelected(patient);
                }
            }
        }
    }

    public PETEnrollment getEnrolledPatient() {
        return patient;
    }

    public PETRegistration getIndexPatient() {

        return DatabaseManager.getInstance()
                .getSession().getPETRegistrationDao()
                .queryBuilder()
                .where(PETRegistrationDao.Properties.Qr.eq(patient.getIndexpatientqr()))
                .unique();
    }

    @OnClick(R.id.imgClear)
    public void onClickClear() {
        txtSearch.setText("");
        patient = null;
        lblIndexPatientName.setText("");
        lblIndexPatientCode.setText("");
        lblEnrollMemberName.setText("");
        lblEnrollMemberCode.setText("");
        if (patientSelectionEventListener != null) {
            patientSelectionEventListener.onClearClicked();
        }
    }

    @OnClick(R.id.imgQR)
    public void scanQR() {
        try {
            Intent intent = new Intent(Constant.ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, QR_REQUEST_CODE);
        } catch (ActivityNotFoundException anfe) {
            anfe.printStackTrace();
        }
    }

    public interface PatientSelectionEventListener {
        void onPatientSelected (PETEnrollment patient);
        void onClearClicked();
    }
}

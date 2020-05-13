package com.streamstech.droid.mshtb.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.adapter.NameValueAdapter;
import com.streamstech.droid.mshtb.adapter.PETAutoCompleteListAdapter;
import com.streamstech.droid.mshtb.adapter.PETPatientRecyclerViewAdapter;
import com.streamstech.droid.mshtb.adapter.PatientRecyclerViewAdapter;
import com.streamstech.droid.mshtb.data.Constant;
import com.streamstech.droid.mshtb.data.Forms;
import com.streamstech.droid.mshtb.data.FormsType;
import com.streamstech.droid.mshtb.data.NameValue;
import com.streamstech.droid.mshtb.data.persistent.DatabaseManager;
import com.streamstech.droid.mshtb.data.persistent.Location;
import com.streamstech.droid.mshtb.data.persistent.LocationDao;
import com.streamstech.droid.mshtb.data.persistent.PETEnrollment;
import com.streamstech.droid.mshtb.data.persistent.PETEnrollmentDao;
import com.streamstech.droid.mshtb.data.persistent.PETRegistration;
import com.streamstech.droid.mshtb.data.persistent.PETRegistrationDao;
import com.streamstech.droid.mshtb.data.persistent.Patient;
import com.streamstech.droid.mshtb.data.persistent.PatientDao;
import com.streamstech.droid.mshtb.fragement.FASTFormsFragment;
import com.streamstech.droid.mshtb.util.MSHTBApplication;
import com.streamstech.droid.mshtb.util.UIUtil;
import com.streamstech.droid.mshtb.util.Util;
import com.whygraphics.multilineradiogroup.MultiLineRadioGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class PETProfileActivity extends AppCompatActivity
        implements DialogInterface.OnDismissListener,
        FASTFormsFragment.OnListFragmentInteractionListener{


    @BindView(R.id.txtID)
    AutoCompleteTextView txtID;
    @BindView(R.id.lblSite)
    TextView lblSite;
    @BindView(R.id.lblName)
    TextView lblName;
    @BindView(R.id.lblAge)
    TextView lblAge;
    @BindView(R.id.lblGender)
    TextView lblGender;
    @BindView(R.id.lblAddress)
    TextView lblAddress;
    @BindView(R.id.lblNID)
    TextView lblNID;
    @BindView(R.id.lblContactNo)
    TextView lblContactNo;
    @BindView(R.id.lblTRNumber)
    TextView lblTRNumber;
    @BindView(R.id.lblMemberCount)
    TextView lblMemberCount;
    @BindView(R.id.lblTBType)
    TextView lblTBType;
    @BindView(R.id.lblDiagnosisType)
    TextView lblDiagnosisType;
    @BindView(R.id.list)
    RecyclerView list;

    @BindView(R.id.imgQR)
    ImageView imgQR;
    @BindView(R.id.imgClear)
    ImageView imgClear;

//    @BindView(R.id.btnSave)
//    Button btnSave;

    private String patientid = "";
//    private NameValueAdapter nameValueAdapter;
    PETRegistration patient = null;
    List<Location> locationList;

    private FASTFormsFragment.OnListFragmentInteractionListener mListener;
    private PETPatientRecyclerViewAdapter assetRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petprofile);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        LocationDao dao = DatabaseManager.getInstance().getSession().getLocationDao();
        locationList = dao.queryBuilder()
                .orderAsc(LocationDao.Properties.Name)
                .list();
        List<NameValue> nameValues = new ArrayList<>();
        for (Location location : locationList) {
            nameValues.add(new NameValue(location.getName(), location.getId()));
        }
//        nameValueAdapter = new NameValueAdapter(this, R.layout.name_value_row,
//                nameValues);
//        spinnerSite.setAdapter(nameValueAdapter);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String pid = bundle.getString("INDEX_PID");
            PETRegistrationDao petRegistrationDao = DatabaseManager.getInstance().getSession().getPETRegistrationDao();
            PETRegistration patient = petRegistrationDao.queryBuilder().where(PETRegistrationDao.Properties.Qr.eq(pid)).unique();
            if (patient == null) {
                finish();
            } else {
                patientid = patient.getQr();
                MSHTBApplication.getInstance().hideKeyboard(this);
                searchPatient(patientid);
                txtID.setEnabled(false);
                imgClear.setVisibility(View.GONE);
                imgQR.setVisibility(View.GONE);
//                btnSave.setVisibility(View.GONE);
            }
        } else {
            txtID.setThreshold(1);
            PETRegistrationDao petRegistrationDao= DatabaseManager.getInstance().getSession().getPETRegistrationDao();
            List<PETRegistration> list = petRegistrationDao.loadAll();
            PETAutoCompleteListAdapter adapter = new PETAutoCompleteListAdapter(this,
                    R.layout.autocomplete_list_row, list);
            txtID.setAdapter(adapter);
            txtID.setOnItemClickListener(onItemClickListener);
        }


//        txtID.setFilters(new InputFilter[] {new SpecialCharacterInputFilter() });
    }


    private AdapterView.OnItemClickListener onItemClickListener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    patient = (PETRegistration) adapterView.getItemAtPosition(i);
                    MSHTBApplication.getInstance().hideKeyboard(PETProfileActivity.this);
                    findInforamation(patient);
//                    btnSave.setVisibility(View.GONE);
                }
            };


    private void searchPatient(String patientid) {
        PETRegistrationDao dao = DatabaseManager.getInstance().getSession().getPETRegistrationDao();
        PETRegistration obj = dao.queryBuilder().where(PETRegistrationDao.Properties.Qr.eq(patientid)).unique();
        if (obj == null) {
            UIUtil.showInfoDialog(this, SweetAlertDialog.ERROR_TYPE, "Error", "No index patient found", this);
            return;
        }
        findInforamation(obj);
    }

    private void findInforamation(final PETRegistration obj) {

        txtID.setText(obj.getQr());
        lblName.setText(obj.getName());
        lblAge.setText(String.valueOf(obj.getAge()));
        lblContactNo.setText(obj.getContantno());
        lblAddress.setText(obj.getAddress());
        lblNID.setText(obj.getNid());
        lblTRNumber.setText(obj.getTrnumber());
        lblMemberCount.setText(String.valueOf(obj.getTotalhouseholdmember()));
        lblGender.setText(obj.getGender());
        lblTBType.setText(getResources().getStringArray(R.array.radio_buttons_tb_type)[obj.getTbtype()]);
        lblDiagnosisType.setText(getResources().getStringArray(R.array.radio_buttons_diagnosis_type)[obj.getDiagnosistype()]);

        for (Location location : locationList) {
            if (location.getId() == obj.getLocationid()) {
                lblSite.setText(location.getName());
                break;
            }
        }

        // Read contact
        PETEnrollmentDao dao = DatabaseManager.getInstance().getSession().getPETEnrollmentDao();
        List<PETEnrollment> patientList = dao.queryBuilder()
                .orderDesc(PETEnrollmentDao.Properties.Createdtime)
                .where(PETEnrollmentDao.Properties.Indexpatientqr.eq(obj.getQr()))
                .list();

        assetRecyclerViewAdapter = new PETPatientRecyclerViewAdapter(patientList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        list.setLayoutManager(mLayoutManager);
        list.setItemAnimator(new DefaultItemAnimator());
        list.setAdapter(assetRecyclerViewAdapter);
        assetRecyclerViewAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.imgClear)
    public void onClickClear() {
        txtID.setText("");
        patient = null;
        lblName.setText("");
        lblAge.setText("");
        lblContactNo.setText("");
        lblAddress.setText("");
        lblNID.setText("");
        lblTRNumber.setText("");
        lblMemberCount.setText("");
        lblGender.setText("");
        lblSite.setText("");
        lblTBType.setText("");
        lblDiagnosisType.setText("");

    }

    @OnClick(R.id.imgQR)
    public void scanQR() {
        try {
            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(Constant.ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dialog
            showDialog(this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No");
        }
    }

    //alert dialog for downloadDialog
    private static void showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {
                    anfe.printStackTrace();
                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        downloadDialog.show();
    }

    //on ActivityResult method
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                final String contents = intent.getStringExtra("SCAN_RESULT").trim();
                Pattern checkRegex = Pattern.compile(Constant.QR_REGEX);
                if (!checkRegex.matcher(contents.trim()).matches()) {
                    txtID.setText("");
                    UIUtil.showInfoDialog(this, SweetAlertDialog.ERROR_TYPE, "Warning", "Not a valid QR");
                    return;
                }
                patientid = contents;
                searchPatient(patientid);
                txtID.setText(contents);
            }
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(Patient item) {
    }

    @Override
    public void onListFragmentInteraction(PETRegistration item) {
    }

    @Override
    public void onListFragmentInteraction(PETEnrollment item) {
        Intent intent = new Intent(this, PETEnrollmentActivity.class);
        intent.putExtra("PID", item.getQr());
        startActivity(intent);
    }

    @Override
    public void onListFragmentInteraction(Forms item) {
    }
}

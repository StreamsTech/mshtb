package com.streamstech.droid.mshtb.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.adapter.NameValueAdapter;
import com.streamstech.droid.mshtb.adapter.PETContactAutoCompleteListAdapter;
import com.streamstech.droid.mshtb.adapter.PETRegistrationAutoCompleteListAdapter;
import com.streamstech.droid.mshtb.data.Constant;
import com.streamstech.droid.mshtb.data.NameValue;
import com.streamstech.droid.mshtb.data.persistent.DBColumnKeys;
import com.streamstech.droid.mshtb.data.persistent.DatabaseManager;
import com.streamstech.droid.mshtb.data.persistent.Location;
import com.streamstech.droid.mshtb.data.persistent.LocationDao;
import com.streamstech.droid.mshtb.data.persistent.PETEnrollment;
import com.streamstech.droid.mshtb.data.persistent.PETEnrollmentDao;
import com.streamstech.droid.mshtb.data.persistent.PETRegistration;
import com.streamstech.droid.mshtb.data.persistent.PETRegistrationDao;
import com.streamstech.droid.mshtb.data.persistent.Patient;
import com.streamstech.droid.mshtb.data.persistent.PatientDao;
import com.streamstech.droid.mshtb.util.MSHTBApplication;
import com.streamstech.droid.mshtb.util.PartialRegexInputFilter;
import com.streamstech.droid.mshtb.util.SpecialCharacterInputFilter;
import com.streamstech.droid.mshtb.util.UIUtil;
import com.streamstech.droid.mshtb.util.Util;
import com.whygraphics.multilineradiogroup.MultiLineRadioGroup;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class PETEnrollmentActivity extends AppCompatActivity {

    @BindView(R.id.txtIndexPatientID)
//    EditText txtIndexPatientID;
    AutoCompleteTextView txtIndexPatientID;
    @BindView(R.id.txtIndexPatientName)
    EditText txtIndexPatientName;
    @BindView(R.id.txtID)
    EditText txtID;
//    @BindView(R.id.lblIndexPatientID)
//    TextView lblIndexPatientID;

    @BindView(R.id.spinnerSite)
    Spinner spinnerSite;
    @BindView(R.id.txtName)
    EditText txtName;
    @BindView(R.id.txtAge)
    EditText txtAge;
    @BindView(R.id.txtNID)
    EditText txtNID;
    @BindView(R.id.txtWeight)
    EditText txtWeight;
    @BindView(R.id.txtHeight)
    EditText txtHeight;
    @BindView(R.id.txtMUAC)
    EditText txtMUAC;
    @BindView(R.id.txtRelationOther)
    EditText txtRelationOther;

    @BindView(R.id.imgIndexQR)
    ImageView imgIndexQR;
    @BindView(R.id.imgQR)
    ImageView imgQR;

    @BindView(R.id.radioGender)
    MultiLineRadioGroup radioGender;
    @BindView(R.id.radioBMI)
    MultiLineRadioGroup radioBMI;
    @BindView(R.id.radioPregnancy)
    MultiLineRadioGroup radioPregnancy;
    @BindView(R.id.radioRelation)
    MultiLineRadioGroup radioRelation;

    @BindView(R.id.pregnancyLayout)
    LinearLayout pregnancyLayout;

    @BindView(R.id.bmiLayout)
    LinearLayout bmiLayout;


    @BindView(R.id.btnSave)
    Button btnSave;

    private String patientid = "";
    private NameValueAdapter nameValueAdapter;
    PETRegistration registration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petrenrollment);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Bundle bundle = getIntent().getExtras();
        PETEnrollment obj = null;
        if (bundle != null) {
            String indexpid = bundle.getString("INDEX_PID");
            if (indexpid != null) {
                PETRegistrationDao dao = DatabaseManager.getInstance().getSession().getPETRegistrationDao();
                PETRegistration petRegistration = dao.queryBuilder().where(PETRegistrationDao.Properties.Qr.eq(indexpid)).unique();
                if (petRegistration != null) {
                    txtIndexPatientID.setText(petRegistration.getQr());
                    txtIndexPatientName.setText(petRegistration.getName());
                }
            }
            String pid = bundle.getString("PID");
            if (pid != null) {
                PETEnrollmentDao dao = DatabaseManager.getInstance().getSession().getPETEnrollmentDao();
                obj = dao.queryBuilder().where(PETEnrollmentDao.Properties.Qr.eq(pid)).unique();
                if (obj == null) {
                    finish();
                } else {
                    patientid = obj.getQr();
                    MSHTBApplication.getInstance().hideKeyboard(this);
                    // Read index patient details too
                    PETRegistrationDao petRegistrationDao = DatabaseManager.getInstance().getSession().getPETRegistrationDao();
                    PETRegistration petRegistration = petRegistrationDao.queryBuilder().where(PETRegistrationDao.Properties.Qr.eq(obj.getIndexpatientqr())).unique();
                    txtIndexPatientID.setText(petRegistration.getQr());
                    txtIndexPatientName.setText(petRegistration.getName());
                    imgIndexQR.setVisibility(View.GONE);
                    findInforamation(obj);
                    btnSave.setVisibility(View.GONE);
                }
            }
        } else {
            //finish();
//            btnSave.setVisibility(View.GONE);
//            txtID.setHint("Scan contact QR code");
        }

        LocationDao dao = DatabaseManager.getInstance().getSession().getLocationDao();
        List<Location> list = dao.queryBuilder()
                .orderAsc(LocationDao.Properties.Name)
                .list();
        List<NameValue> nameValues = new ArrayList<>();
        int locationIndex = -1;
        for (Location location : list) {
            nameValues.add(new NameValue(location.getName(), location.getId()));
            if (obj != null) {
                if (location.getId() == obj.getLocationid()) {
                    locationIndex = nameValues.size() - 1;
                }
            }
        }
        nameValueAdapter = new NameValueAdapter(this, R.layout.name_value_row,
                nameValues);
        spinnerSite.setAdapter(nameValueAdapter);
        spinnerSite.setSelection(locationIndex);
//        txtIndexPatientID.setFilters(new InputFilter[]{new SpecialCharacterInputFilter()});
//        txtIndexPatientID.setFilters(
//                new InputFilter[]{
//                        new PartialRegexInputFilter(Constant.QR_REGEX)
//                }
//        );
//        txtIndexPatientID.addTextChangedListener(
//                new TextWatcher() {
//
//                    @Override
//                    public void afterTextChanged(Editable s) {
//                        String value = s.toString();
//                        if (value.matches(Constant.QR_REGEX))
//                            txtIndexPatientID.setTextColor(Color.BLACK);
//                        else
//                            txtIndexPatientID.setTextColor(Color.RED);
//                    }
//
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start,
//                                                  int count, int after) {
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence s, int start,
//                                              int before, int count) {
//                    }
//
//                }
//        );
        txtIndexPatientID.setThreshold(1);
        PETRegistrationDao registrationDao = DatabaseManager.getInstance().getSession().getPETRegistrationDao();
        List<PETRegistration> patientList = registrationDao.loadAll();
        PETRegistrationAutoCompleteListAdapter adapter = new PETRegistrationAutoCompleteListAdapter(this,
                R.layout.autocomplete_list_row, patientList);
        txtIndexPatientID.setAdapter(adapter);
        txtIndexPatientID.setOnItemClickListener(onItemClickListener);

        txtID.setFilters(new InputFilter[]{new SpecialCharacterInputFilter()});
        txtName.requestFocus();

        radioGender.setOnCheckedChangeListener(new MultiLineRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ViewGroup viewGroup, RadioButton radioButton) {
                onGenderChanged();
            }
        });

        radioRelation.setOnCheckedChangeListener(new MultiLineRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ViewGroup viewGroup, RadioButton radioButton) {
                if (radioRelation.getCheckedRadioButtonIndex() == 7){
                    txtRelationOther.setVisibility(View.VISIBLE);
                } else {
                    txtRelationOther.setVisibility(View.GONE);
                }
            }
        });


    }

    private AdapterView.OnItemClickListener onItemClickListener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                registration = (PETRegistration) adapterView.getItemAtPosition(i);
                txtIndexPatientID.setText(registration.getQr());
                txtIndexPatientName.setText(registration.getName());
                txtIndexPatientID.dismissDropDown();
                MSHTBApplication.getInstance().hideKeyboard(PETEnrollmentActivity.this);
                }
            };

    @OnClick(R.id.imgQR)
    public void scanQR() {
        scan(0);
    }

    public void onGenderChanged() {
        if (radioGender.getCheckedRadioButtonIndex() == 1){
            pregnancyLayout.setVisibility(View.VISIBLE);
        } else {
            pregnancyLayout.setVisibility(View.GONE);
        }
    }

    @OnTextChanged(R.id.txtAge)
    public void onTextChanged() {

        if (txtAge.getText().toString().trim().equals(""))
            return;

        double age = Double.valueOf(txtAge.getText().toString());
        if (age <= 5) {
            txtMUAC.setVisibility(View.VISIBLE);
            bmiLayout.setVisibility(View.GONE);
        } else {
            txtMUAC.setVisibility(View.GONE);
        }
        if (age > 18 ) {
            bmiLayout.setVisibility(View.VISIBLE);
        }
        if (age >= 5 &&  age < 18) {
            bmiLayout.setVisibility(View.GONE);
            txtMUAC.setVisibility(View.GONE);
        }
    }

    @OnTextChanged(R.id.txtWeight)
    public void onWeightTextChanged() {
        if ("".endsWith(Util.getText(txtHeight)) || "".endsWith(Util.getText(txtWeight)))
            return;
        int index = Util.getBMIIndex(txtWeight, txtHeight);
        radioBMI.checkAt(index);
    }

    @OnTextChanged(R.id.txtHeight)
    public void onHeightTextChanged() {
        if ("".endsWith(Util.getText(txtHeight)) || "".endsWith(Util.getText(txtWeight)))
            return;
        int index = Util.getBMIIndex(txtWeight, txtHeight);
        radioBMI.checkAt(index);
    }

    @OnClick(R.id.imgIndexQR)
    public void scanIndexQR() {
        scan(701);
    }

    private void scan(int code) {
        try {
            Intent intent = new Intent(Constant.ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, code);
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
                //1806050001-6
                if (!Util.validQR(contents.trim())) {
                    txtID.setText("");
                    UIUtil.showInfoDialog(this, SweetAlertDialog.ERROR_TYPE, "Error", "Not a valid QR of contact");
                    return;
                } else if (!Util.isQRAvailable(contents)) {
                    txtID.setText("");
                    UIUtil.showInfoDialog(this, SweetAlertDialog.ERROR_TYPE, "Warning", "QR code already used");
                    return;
                }
                if (!patientid.trim().isEmpty()) {
                    PETEnrollmentDao dao = DatabaseManager.getInstance().getSession().getPETEnrollmentDao();
                    PETEnrollment obj = dao.queryBuilder().where(PETEnrollmentDao.Properties.Qr.eq(contents.trim())).unique();
                    if (obj != null) {
                        findInforamation(obj);
                    } else {
                        UIUtil.showInfoDialog(this, SweetAlertDialog.ERROR_TYPE, "Error", "No contact found");
                        return;
                    }
                }
                txtID.setText(contents);
            }
        } else if (requestCode == 701) {
            if (resultCode == RESULT_OK) {
                final String contents = intent.getStringExtra("SCAN_RESULT").trim();
                //1806050001-6
                if (!Util.validQR(contents)) {
                    txtIndexPatientID.setText("");
                    UIUtil.showInfoDialog(this, SweetAlertDialog.ERROR_TYPE, "Error", "Not a valid QR of index patient");
                    return;
                }
                if (patientid.trim().isEmpty()) {
                    PETRegistrationDao dao = DatabaseManager.getInstance().getSession().getPETRegistrationDao();
                    PETRegistration obj = dao.queryBuilder().where(PETRegistrationDao.Properties.Qr.eq(contents)).unique();
                    if (obj != null) {
                        txtIndexPatientID.setText(contents);
                        txtIndexPatientName.setText(obj.getName());
                    } else {
                        txtIndexPatientID.setText("");
                        UIUtil.showInfoDialog(this, SweetAlertDialog.ERROR_TYPE, "Error", "No index patient found");
                        return;
                    }
                }
            }
        }
    }

    private void findInforamation(PETEnrollment obj) {
        txtID.setText(obj.getQr());
        txtName.setText(obj.getName());
        txtAge.setText(String.valueOf(obj.getAge()));
        txtNID.setText(obj.getNid());
        txtHeight.setText(String.valueOf(obj.getHeight()));
        txtWeight.setText(String.valueOf(obj.getWeight()));
        radioGender.checkAt(obj.getGender().equalsIgnoreCase("M") ? 0 : 1);
        txtMUAC.setText(String.valueOf(obj.getMuac()));
        radioBMI.checkAt(obj.getBmi());
        radioPregnancy.checkAt(obj.getPregnancy());
        radioRelation.checkAt(obj.getRelation());
        txtRelationOther.setText(obj.getRelationother());
    }

    @OnClick(R.id.btnSave)
    public void onClickSave() {

        if (Util.isEmpty(txtID) || Util.isEmpty(txtName) || Util.isEmpty(txtHeight)
                || Util.isEmpty(txtAge) || Util.isEmpty(txtWeight)) {
            UIUtil.showInfoDialog(this, SweetAlertDialog.WARNING_TYPE, "Error", "All field(s) are mandatory");
            return;
        }
        double age = Double.parseDouble(Util.getText(txtAge));
        double muac = -1;
        if (age < 6 && Util.isEmpty(txtMUAC)) {
            UIUtil.displayError(this, "MUAC");
            return;
        } else if (age < 6 ) {
            muac = Double.valueOf(Util.getText(txtMUAC));
        } else {
            muac = -1;
        }

        if (radioGender.getCheckedRadioButtonIndex() == 1 && radioPregnancy.getCheckedRadioButtonIndex() == -1) {
            UIUtil.displayError(this, "Pregnancy profile");
            return;
        }

        if (!Util.validQR(txtIndexPatientID.getText().toString().trim()) || !Util.validQR(txtID.getText().toString().trim())) {
            UIUtil.showInfoDialog(this, SweetAlertDialog.ERROR_TYPE, "Error", "Invalid QR code");
            return;
        }

        PETEnrollmentDao dao = DatabaseManager.getInstance().getSession().getPETEnrollmentDao();
        dao.insert(new PETEnrollment(null, ((Long) ((NameValue) spinnerSite.getSelectedItem()).getValue()).intValue(),
                MSHTBApplication.getInstance().getSettingsInt(DBColumnKeys.SCREENER_ID)/*Constant.SCREENER_ID*/,
                Util.getText(txtName), Integer.valueOf(txtAge.getText().toString()), Util.getText(txtNID),
                radioGender.getCheckedRadioButtonIndex() == 0 ? "M" : "F", Util.getText(txtID),
                Util.getText(txtIndexPatientID), Double.valueOf(txtWeight.getText().toString()),
                Double.valueOf(txtHeight.getText().toString()), muac,
                radioBMI.getCheckedRadioButtonIndex(), radioPregnancy.getCheckedRadioButtonIndex(),
                radioRelation.getCheckedRadioButtonIndex(), Util.getText(txtRelationOther),
                System.currentTimeMillis(), false, 0.0, 0.0));

        new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("Enrollment")
                .setContentText("Enrollment completed, want to add more contact?")
                .setConfirmText("Yes!")
                .setCancelText("No")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(final SweetAlertDialog sDialog) {
                        sDialog.dismiss();
                        finish();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(final SweetAlertDialog sDialog) {
                        sDialog.dismiss();
                        txtID.setText("");
                        txtName.setText("");
                        txtAge.setText("");
                        txtHeight.setText("");
                        txtWeight.setText("");
                        radioGender.checkAt(-1);
                        radioBMI.checkAt(-1);
                        radioPregnancy.checkAt(-1);
                        radioRelation.checkAt(-1);
                        txtRelationOther.setText("");
                        txtMUAC.setText("");
                    }
                })
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

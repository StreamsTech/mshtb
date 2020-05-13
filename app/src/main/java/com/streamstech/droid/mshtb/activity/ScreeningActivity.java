package com.streamstech.droid.mshtb.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.data.Constant;
import com.streamstech.droid.mshtb.data.persistent.DBColumnKeys;
import com.streamstech.droid.mshtb.data.persistent.DatabaseManager;
import com.streamstech.droid.mshtb.data.persistent.Patient;
import com.streamstech.droid.mshtb.data.persistent.PatientDao;
import com.streamstech.droid.mshtb.data.persistent.Screening;
import com.streamstech.droid.mshtb.data.persistent.ScreeningDao;
import com.streamstech.droid.mshtb.data.persistent.Settings;
import com.streamstech.droid.mshtb.data.persistent.SettingsDao;
import com.streamstech.droid.mshtb.io.Paths;
import com.streamstech.droid.mshtb.util.MSHTBApplication;
import com.streamstech.droid.mshtb.util.MyRadioGroup;
import com.streamstech.droid.mshtb.util.UIUtil;
import com.streamstech.droid.mshtb.util.Util;
import com.whygraphics.multilineradiogroup.MultiLineRadioGroup;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class ScreeningActivity extends AppCompatActivity implements DialogInterface.OnDismissListener{

    @BindView(R.id.txtName)
    EditText txtName;
    @BindView(R.id.lblReadonlyName)
    TextView lblReadonlyName;
    @BindView(R.id.txtAge)
    EditText txtAge;
    @BindView(R.id.txtContactNo)
    EditText txtContactNo;
    @BindView(R.id.lblPatientID)
    TextView lblPatientID;

    @BindView(R.id.radioGender)
    MyRadioGroup radioGender;
    @BindView(R.id.btnAdd)
    Button btnAdd;
    @BindView(R.id.btnSave)
    Button btnSave;

    @BindView(R.id.topLayout)
    LinearLayout topLayout;

    @BindView(R.id.formView)
    ScrollView formView;

    @BindView(R.id.layout3a)
    LinearLayout layout3a;
    @BindView(R.id.layout3b)
    LinearLayout layout3b;
    @BindView(R.id.layout7a)
    LinearLayout layout7a;
    @BindView(R.id.layout8a)
    LinearLayout layout8a;
    @BindView(R.id.layout9a)
    LinearLayout layout9a;

    @BindView(R.id.radioGroup3)
    MultiLineRadioGroup radioGroup3;
    @BindView(R.id.radioGroup3a)
    MultiLineRadioGroup radioGroup3a;
    @BindView(R.id.radioGroup3b)
    MultiLineRadioGroup radioGroup3b;
    @BindView(R.id.radioGroup4)
    MultiLineRadioGroup radioGroup4;
    @BindView(R.id.radioGroup5)
    MultiLineRadioGroup radioGroup5;
    @BindView(R.id.radioGroup6)
    MultiLineRadioGroup radioGroup6;
    @BindView(R.id.radioGroup7)
    MultiLineRadioGroup radioGroup7;
    @BindView(R.id.radioGroup7a)
    MultiLineRadioGroup radioGroup7a;
    @BindView(R.id.radioGroup8)
    MultiLineRadioGroup radioGroup8;
    @BindView(R.id.radioGroup8a)
    MultiLineRadioGroup radioGroup8a;
    @BindView(R.id.radioGroup9)
    MultiLineRadioGroup radioGroup9;
    @BindView(R.id.radioGroup9a)
    MultiLineRadioGroup radioGroup9a;
    @BindView(R.id.radioGroup10)
    MultiLineRadioGroup radioGroup10;
    @BindView(R.id.radioGroup11)
    MultiLineRadioGroup radioGroup11;
    private String patientid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screening);
        ButterKnife.bind(this);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String pid  = bundle.getString("PID");
            PatientDao patientDao = DatabaseManager.getInstance().getSession().getPatientDao();
            Patient patient = patientDao.queryBuilder().where(PatientDao.Properties.Patientid.eq(pid)).unique();
            if (patient == null){
                finish();
            }else{
                patientid = patient.getPatientid();
                topLayout.setVisibility(View.GONE);
                formView.setVisibility(View.VISIBLE);
                lblReadonlyName.setVisibility(View.VISIBLE);
                lblReadonlyName.setText(patient.getName());
                MSHTBApplication.getInstance().hideKeyboard(this);
                findInforamation();
                btnSave.setVisibility(View.GONE);
            }
        }

        radioGroup3.setOnCheckedChangeListener(new MultiLineRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ViewGroup viewGroup, RadioButton radioButton) {
                alterVisbleState(radioButton, layout3a);
                alterVisbleState(radioButton, layout3b);
//                if (radioButton.isChecked() && radioButton.getText().toString().equalsIgnoreCase("yes")){
//                    layout3a.setVisibility(View.VISIBLE);
//                    layout3b.setVisibility(View.VISIBLE);
//                }else{
//                    layout3a.setVisibility(View.GONE);
//                    layout3b.setVisibility(View.GONE);
//                }
            }
        });
        radioGroup7.setOnCheckedChangeListener(new MultiLineRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ViewGroup viewGroup, RadioButton radioButton) {
                alterVisbleState(radioButton, layout7a);
            }
        });
        radioGroup8.setOnCheckedChangeListener(new MultiLineRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ViewGroup viewGroup, RadioButton radioButton) {
                alterVisbleState(radioButton, layout8a);
            }
        });
        radioGroup9.setOnCheckedChangeListener(new MultiLineRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ViewGroup viewGroup, RadioButton radioButton) {
                alterVisbleState(radioButton, layout9a);
            }
        });
    }

    private void alterVisbleState(RadioButton radioButton, LinearLayout linearLayout){
        if (radioButton.isChecked() && radioButton.getText().toString().equalsIgnoreCase("yes")){
            linearLayout.setVisibility(View.VISIBLE);
        }else{
            linearLayout.setVisibility(View.GONE);
        }
    }

    private void findInforamation(){
        ScreeningDao screeningDao = DatabaseManager.getInstance().getSession().getScreeningDao();
        Screening screening = screeningDao.queryBuilder().where(ScreeningDao.Properties.Patientid.eq(patientid)).unique();
        if (screening == null){
            UIUtil.showInfoDialog(this, SweetAlertDialog.ERROR_TYPE, "Error", "No screening information found", this);
            return;
        }
        radioGroup3.checkAt(screening.getQ3());
        radioGroup3a.checkAt(screening.getQ3a());
        radioGroup3b.checkAt(screening.getQ3b());
        radioGroup4.checkAt(screening.getQ4());
        radioGroup5.checkAt(screening.getQ5());
        radioGroup6.checkAt(screening.getQ6());
        radioGroup7.checkAt(screening.getQ7());
        radioGroup7a.checkAt(screening.getQ7a());
        radioGroup8.checkAt(screening.getQ8());
        radioGroup8a.checkAt(screening.getQ8a());
        radioGroup9.checkAt(screening.getQ9());
        radioGroup10.checkAt(screening.getQ10());
        radioGroup11.checkAt(screening.getQ11());
        patientid = "";
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (patientid.isEmpty()) {
                finish();
            }else{
                UIUtil.showInfoDialog(this, SweetAlertDialog.WARNING_TYPE, "Error", "You can't go back without filling up the form");
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (patientid.isEmpty()) {
            super.onBackPressed();
        }else{
            UIUtil.showInfoDialog(this, SweetAlertDialog.WARNING_TYPE, "Error", "You can't go back without filling up the form");
        }
    }

    @OnClick(R.id.btnAdd)
    public void onAddClicked() {

        if (Util.isEmpty(txtName)){
            UIUtil.displayError(this, "Name");
            return;
        }else if (Util.isEmpty(txtAge)){
            UIUtil.displayError(this, "Age");
            return;
        }else if (!radioGender.anyoneChecked()){
            UIUtil.displayError(this, "Gender");
            return;
        }

//        android.text.format.DateFormat dateFormat = new android.text.format.DateFormat();

        PatientDao patientDao = DatabaseManager.getInstance().getSession().getPatientDao();
//        long dayCount = Constant.DAY_COUNT++;
//        String pid = String.format("%s%s%s", Constant.HOSPITAL_ID, dateFormat.format(Constant.DATE_FORMAT, new Date()), String.format("%04d", dayCount));
        String pid = Util.generatePatientID(patientDao);
        patientDao.insert(new Patient(null, pid, MSHTBApplication.getInstance().getSettings(DBColumnKeys.SCREENER_ID), //  String.valueOf(Constant.SCREENER_ID),
                false, false, -1, -1, txtName.getText().toString(), Double.valueOf(txtAge.getText().toString()),
                radioGender.getSelectedIndex() == 0 ? "M" : "F", txtContactNo.getText().toString().trim(),
                new Date().getTime(), false, false, 0.0, 0.0, "", false));

        btnAdd.setVisibility(View.GONE);
        lblPatientID.setText("ID: " + pid);
        patientid = pid;
        Toast.makeText(this, "Patient created", Toast.LENGTH_LONG ).show();
        MSHTBApplication.getInstance().hideKeyboard(this);
        formView.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btnSave)
    public void onSaveClicked(){
        boolean validForm  = validateForm();
        if (!validForm || patientid.trim().equals("")){
            UIUtil.showInfoDialog(this, SweetAlertDialog.WARNING_TYPE, "Warning", "Please create a patient first", this);
            return;
        }

        boolean presumptiveTB = radioGroup3a.getCheckedRadioButtonIndex() == 2 || radioGroup3a.getCheckedRadioButtonIndex() == 4;
        if (getPositiveCounter() >= 2){
            presumptiveTB = true;
        }
//        presumptiveTB = radioGroup4.getCheckedRadioButtonIndex() == 0 && radioGroup5.getCheckedRadioButtonIndex() == 0 && radioGroup6.getCheckedRadioButtonIndex() == 0;
//        presumptiveTB = radioGroup7.getCheckedRadioButtonIndex() == 0 || radioGroup8.getCheckedRadioButtonIndex() == 0;
        ScreeningDao screeningDao = DatabaseManager.getInstance().getSession().getScreeningDao();

//        String patientid, int q3, int q3a, int q3b,
//        int q4, int q5, int q6, int q7, int q7a, int q8, int q8a, int q9,
//        int q9a, String q9aothers, int q10, int q11, int q12, int q13,
//        long createdtime, boolean uploaded, double longitude, double latitude

        screeningDao.insert(new Screening(null, patientid, radioGroup3.getCheckedRadioButtonIndex(),
                radioGroup3a.getCheckedRadioButtonIndex(), radioGroup3b.getCheckedRadioButtonIndex(),
                radioGroup4.getCheckedRadioButtonIndex(), radioGroup5.getCheckedRadioButtonIndex(),
                radioGroup6.getCheckedRadioButtonIndex(), radioGroup7.getCheckedRadioButtonIndex(),
                radioGroup7a.getCheckedRadioButtonIndex(), radioGroup8.getCheckedRadioButtonIndex(),
                radioGroup8a.getCheckedRadioButtonIndex(), radioGroup9.getCheckedRadioButtonIndex(),
                radioGroup9a.getCheckedRadioButtonIndex(), "", radioGroup10.getCheckedRadioButtonIndex(),
                radioGroup11.getCheckedRadioButtonIndex(), -1, -1, new Date().getTime(),
                false, 0.0, 0.0));

        if (presumptiveTB) {
            PatientDao patientDao = DatabaseManager.getInstance().getSession().getPatientDao();
            Patient patient = patientDao.queryBuilder().where(PatientDao.Properties.Patientid.eq(patientid)).unique();
            patient.setPresumtivetb(true);
            patientDao.update(patient);
        }
        String title = presumptiveTB ? "A presumptive TB case" : "Not a TB presumptive";
        String message = presumptiveTB ? "Take necessary action" : "Screening Complete";
        if (!presumptiveTB) {
            UIUtil.showInfoDialog(this, presumptiveTB ? SweetAlertDialog.WARNING_TYPE : SweetAlertDialog.SUCCESS_TYPE, title, message, this);
        } else {
            gePatientNumber();
        }
    }

    private void gePatientNumber() {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(this);
        View mView = layoutInflaterAndroid.inflate(R.layout.contact_input_dialog_box, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialog));
        alertDialogBuilderUserInput.setView(mView);

        final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.txtContactNo);
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Save", null);
        final AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String message = userInputDialogEditText.getText().toString().trim();
                        if (message.equals("")) {
                            Toast.makeText(ScreeningActivity.this, "Nothing to save", Toast.LENGTH_LONG).show();
                            return;
                        }
                        PatientDao patientDao = DatabaseManager.getInstance().getSession().getPatientDao();
                        Patient patient = patientDao.queryBuilder().where(PatientDao.Properties.Patientid.eq(patientid)).unique();
                        patient.setContantno(userInputDialogEditText.getText().toString().trim());
                        patientDao.update(patient);
                        alertDialogAndroid.dismiss();
                        finish();
                    }
                });
            }
        });

        alertDialogAndroid.show();
    }

    private int getPositiveCounter(){
        int counter = 0;
        if (radioGroup4.getCheckedRadioButtonIndex() == 0){
            counter++;
        }
        if (radioGroup5.getCheckedRadioButtonIndex() == 0){
            counter++;
        }
        if (radioGroup6.getCheckedRadioButtonIndex() == 0){
            counter++;
        }
        if (radioGroup7.getCheckedRadioButtonIndex() == 0){
            counter++;
        }
        if (radioGroup8.getCheckedRadioButtonIndex() == 0){
            counter++;
        }
        if (radioGroup9.getCheckedRadioButtonIndex() == 0){
            counter++;
        }
        if (radioGroup10.getCheckedRadioButtonIndex() == 0){
            counter++;
        }
        return counter;
    }
    private boolean validateForm(){
        if (radioGroup3.getCheckedRadioButtonIndex() == -1){
            UIUtil.displayError(this, "Cough");
             return false;
        }else if (radioGroup3.getCheckedRadioButtonIndex() == 0 && radioGroup3a.getCheckedRadioButtonIndex() == -1){
            UIUtil.displayError(this, "Cough duration");
             return false;
        }else if (radioGroup3.getCheckedRadioButtonIndex() == 0 && radioGroup3b.getCheckedRadioButtonIndex() == -1){
            UIUtil.displayError(this, "Cough with blood");
             return false;
        }else if (radioGroup4.getCheckedRadioButtonIndex() == -1){
            UIUtil.displayError(this, "Fever");
             return false;
        }else if (radioGroup5.getCheckedRadioButtonIndex() == -1){
            UIUtil.displayError(this, "Night sweats");
             return false;
        }else if (radioGroup6.getCheckedRadioButtonIndex() == -1){
            UIUtil.displayError(this, "Wight loss");
             return false;
        }else if (radioGroup7.getCheckedRadioButtonIndex() == -1){
            UIUtil.displayError(this, "TB before");
             return false;
        }else if (radioGroup7.getCheckedRadioButtonIndex() == 0 && radioGroup7a.getCheckedRadioButtonIndex() == -1){
            UIUtil.displayError(this, "Site of disease(TB)");
             return false;
        }else if (radioGroup8.getCheckedRadioButtonIndex() == -1){
            UIUtil.displayError(this, "Family TB");
             return false;
        }else if (radioGroup8.getCheckedRadioButtonIndex() == 0 && radioGroup8a.getCheckedRadioButtonIndex() == -1){
            UIUtil.displayError(this, "Site of family TB");
             return false;
        }else if (radioGroup9.getCheckedRadioButtonIndex() == -1){
            UIUtil.displayError(this, "Gland swelling");
             return false;
        }else if (radioGroup9.getCheckedRadioButtonIndex() == 0 && radioGroup9a.getCheckedRadioButtonIndex() == -1){
            UIUtil.displayError(this, "Site of swelling");
             return false;
        }else if (radioGroup10.getCheckedRadioButtonIndex() == -1){
            UIUtil.displayError(this, "Medical condition");
             return false;
        }else if (radioGroup11.getCheckedRadioButtonIndex() == -1){
            UIUtil.displayError(this, "Smoking");
             return false;
        }
        return true;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        finish();
    }
}

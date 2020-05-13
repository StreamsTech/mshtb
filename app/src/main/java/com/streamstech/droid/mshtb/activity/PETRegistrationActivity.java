package com.streamstech.droid.mshtb.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.adapter.AutoCompleteListAdapter;
import com.streamstech.droid.mshtb.adapter.NameValueAdapter;
import com.streamstech.droid.mshtb.adapter.PETAutoCompleteListAdapter;
import com.streamstech.droid.mshtb.data.Constant;
import com.streamstech.droid.mshtb.data.NameValue;
import com.streamstech.droid.mshtb.data.persistent.DBColumnKeys;
import com.streamstech.droid.mshtb.data.persistent.DatabaseManager;
import com.streamstech.droid.mshtb.data.persistent.Location;
import com.streamstech.droid.mshtb.data.persistent.LocationDao;
import com.streamstech.droid.mshtb.data.persistent.PETRegistration;
import com.streamstech.droid.mshtb.data.persistent.PETRegistrationDao;
import com.streamstech.droid.mshtb.data.persistent.Patient;
import com.streamstech.droid.mshtb.data.persistent.PatientDao;
import com.streamstech.droid.mshtb.data.persistent.Screening;
import com.streamstech.droid.mshtb.data.persistent.ScreeningDao;
import com.streamstech.droid.mshtb.util.MSHTBApplication;
import com.streamstech.droid.mshtb.util.SpecialCharacterInputFilter;
import com.streamstech.droid.mshtb.util.UIUtil;
import com.streamstech.droid.mshtb.util.Util;
import com.whygraphics.multilineradiogroup.MultiLineRadioGroup;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class PETRegistrationActivity extends AppCompatActivity implements DialogInterface.OnDismissListener {


    @BindView(R.id.txtID)
    EditText txtID;
    @BindView(R.id.spinnerSite)
    Spinner spinnerSite;
    @BindView(R.id.txtName)
    EditText txtName;
    @BindView(R.id.txtAge)
    EditText txtAge;
    @BindView(R.id.txtAddress)
    EditText txtAddress;
    @BindView(R.id.txtNID)
    EditText txtNID;
    @BindView(R.id.txtContactNo)
    EditText txtContactNo;
    @BindView(R.id.txtTRNUmber)
    EditText txtTRNUmber;
    @BindView(R.id.txtMemberCount)
    EditText txtMemberCount;

    @BindView(R.id.imgQR)
    ImageView imgQR;
//    @BindView(R.id.imgClear)
//    ImageView imgClear;

    @BindView(R.id.radioGender)
    MultiLineRadioGroup radioGender;
    @BindView(R.id.radioTBTYpe)
    MultiLineRadioGroup radioTBTYpe;
    @BindView(R.id.radioDiagnosisType)
    MultiLineRadioGroup radioDiagnosisType;

    @BindView(R.id.btnSave)
    Button btnSave;

    private String patientid = "";
    private NameValueAdapter nameValueAdapter;
    PETRegistration patient = null;
    List<Location> locationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petregistration);
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
        nameValueAdapter = new NameValueAdapter(this, R.layout.name_value_row,
                nameValues);
        spinnerSite.setAdapter(nameValueAdapter);
        txtName.requestFocus();
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
                //1806050001-6
                Pattern checkRegex = Pattern.compile(Constant.QR_REGEX);
                if (!checkRegex.matcher(contents.trim()).matches()) {
                    txtID.setText("");
                    UIUtil.showInfoDialog(this, SweetAlertDialog.ERROR_TYPE, "Warning", "Not a valid QR");
                    return;
                } else if (!Util.isQRAvailable(contents)) {
                    txtID.setText("");
                    UIUtil.showInfoDialog(this, SweetAlertDialog.ERROR_TYPE, "Warning", "QR code already used");
                    return;
                }
                patientid = contents;
                txtID.setText(contents);
            }
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        finish();
    }

    @OnClick(R.id.btnSave)
    public void onClickSave() {
        if (Util.isEmpty(txtID) || Util.isEmpty(txtName) || Util.isEmpty(txtAddress)
                || Util.isEmpty(txtAge) || Util.isEmpty(txtContactNo) || Util.isEmpty(txtMemberCount)
                || Util.isEmpty(txtTRNUmber)) {
            UIUtil.showInfoDialog(this, SweetAlertDialog.WARNING_TYPE, "Error", "All field(s) are mandatory");
            return;
        }

        if (!Util.validQR(txtID.getText().toString().trim())) {
            UIUtil.showInfoDialog(this, SweetAlertDialog.ERROR_TYPE, "Error", "Invalid QR code");
            return;
        }

        PETRegistrationDao petRegistrationDao = DatabaseManager.getInstance().getSession().getPETRegistrationDao();
        petRegistrationDao.save(new PETRegistration(null, ((Long) ((NameValue) spinnerSite.getSelectedItem()).getValue()).intValue(),
                MSHTBApplication.getInstance().getSettingsInt(DBColumnKeys.SCREENER_ID)/*Constant.SCREENER_ID*/,
                radioTBTYpe.getCheckedRadioButtonIndex(), txtName.getText().toString().trim(),
                Double.valueOf(txtAge.getText().toString()), radioGender.getCheckedRadioButtonIndex() == 0 ? "M" : "F",
                Util.getText(txtID), Util.getText(txtNID), radioDiagnosisType.getCheckedRadioButtonIndex(), Util.getText(txtTRNUmber),
                Integer.parseInt(Util.getText(txtMemberCount)), Util.getText(txtAddress), Util.getText(txtContactNo),
                System.currentTimeMillis(), false, 0.0, 0.0));

        new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("Registration")
                .setContentText("Index patient created, want to add family member?")
                .setConfirmText("Yes!")
                .setCancelText("No")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(final SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        Intent intent = new Intent(PETRegistrationActivity.this, PETEnrollmentActivity.class);
                        intent.putExtra("INDEX_PID", txtID.getText().toString().trim());
                        startActivity(intent);
                        finish();
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        finish();
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

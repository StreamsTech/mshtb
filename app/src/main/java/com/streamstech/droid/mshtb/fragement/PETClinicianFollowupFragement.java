package com.streamstech.droid.mshtb.fragement;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.data.Constant;
import com.streamstech.droid.mshtb.data.persistent.DBColumnKeys;
import com.streamstech.droid.mshtb.data.persistent.DatabaseManager;
import com.streamstech.droid.mshtb.data.persistent.PETAdherence;
import com.streamstech.droid.mshtb.data.persistent.PETAdherenceDao;
import com.streamstech.droid.mshtb.data.persistent.PETClinicianFollowup;
import com.streamstech.droid.mshtb.data.persistent.PETClinicianFollowupDao;
import com.streamstech.droid.mshtb.data.persistent.PETEnrollment;
import com.streamstech.droid.mshtb.data.persistent.PETFollowup;
import com.streamstech.droid.mshtb.data.persistent.PETFollowupDao;
import com.streamstech.droid.mshtb.util.MSHTBApplication;
import com.streamstech.droid.mshtb.util.UIUtil;
import com.streamstech.droid.mshtb.util.Util;
import com.whygraphics.multilineradiogroup.MultiLineRadioGroup;
import com.xeoh.android.checkboxgroup.CheckBoxGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by AKASH-LAPTOP on 6/27/2018.
 */

public class PETClinicianFollowupFragement extends Fragment {

    EditText txtWeight;
    EditText txtHeight;
    EditText txtMUAC;
    MultiLineRadioGroup radioGroup1;
    MultiLineRadioGroup radioGroup2;
    MultiLineRadioGroup radioGroup3;
    MultiLineRadioGroup radioGroup4;
    CheckBoxGroup<String> checkBoxGroup;
    CheckBoxGroup<String> checkBoxGroup2;

    LinearLayout layout6a;
    LinearLayout layout6b;
    LinearLayout layout4;
    LinearLayout layout5;

    EditText txtSymptom;
    EditText txtOther;
    TextView lblTime;
    Button btnSave;
    PETEnrollment patient;
    DialogInterface.OnDismissListener onDismissListener;

    public PETClinicianFollowupFragement() {
    }

    public void setEnrollment (PETEnrollment patient) {
        this.patient = patient;
        if (patient.getAge() < 5) {
            layout5.setVisibility(View.GONE);
        } else if (patient.getAge() >= 5 && patient.getAge() < 18) {
            layout4.setVisibility(View.GONE);
            layout5.setVisibility(View.GONE);
        }
    }

    public void seListener (DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    public void onClearClicked () {
        radioGroup1.checkAt(2);
        radioGroup2.checkAt(11);
        radioGroup3.checkAt(0);
        lblTime.setText(Util.getFormattedDateTime(System.currentTimeMillis()));
        btnSave.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_petclinician_followup, container, false);
        layout6a = (LinearLayout)rootView.findViewById(R.id.layout6a) ;
        layout6b = (LinearLayout)rootView.findViewById(R.id.layout6b);
        layout4 = (LinearLayout)rootView.findViewById(R.id.layout4);
        layout5 = (LinearLayout)rootView.findViewById(R.id.layout5) ;
        lblTime = (TextView) rootView.findViewById(R.id.lblTime);
        lblTime.setText(Util.getFormattedDateTime(System.currentTimeMillis()));
        txtWeight = (EditText)rootView.findViewById(R.id.txtWeight);
        txtWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    int index = Util.getBMIIndex(txtWeight, txtHeight);
                    radioGroup2.checkAt(index);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtHeight = (EditText)rootView.findViewById(R.id.txtHeight);
        txtHeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    int index = Util.getBMIIndex(txtWeight, txtHeight);
                    radioGroup2.checkAt(index);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtMUAC = (EditText)rootView.findViewById(R.id.txtMUAC);
        txtOther = (EditText)rootView.findViewById(R.id.txtOther);
        txtSymptom = (EditText)rootView.findViewById(R.id.txtSymptom);

        radioGroup1 = (MultiLineRadioGroup) rootView.findViewById(R.id.radioGroup1);
        radioGroup2 = (MultiLineRadioGroup) rootView.findViewById(R.id.radioGroup2);
        radioGroup3 = (MultiLineRadioGroup) rootView.findViewById(R.id.radioGroup3);
        radioGroup3.setOnCheckedChangeListener(new MultiLineRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ViewGroup viewGroup, RadioButton radioButton) {
                if (radioGroup3.getCheckedRadioButtonIndex() == 0){
                    layout6a.setVisibility(View.VISIBLE);
                    layout6b.setVisibility(View.VISIBLE);
                } else {
                    layout6a.setVisibility(View.GONE);
                    layout6b.setVisibility(View.GONE);
                }
            }
        });
        radioGroup4 = (MultiLineRadioGroup) rootView.findViewById(R.id.radioGroup4);

        HashMap<CheckBox, String> checkBoxMap = new HashMap<>();
        checkBoxMap.put((CheckBox) rootView.findViewById(R.id.chk1), "0");
        checkBoxMap.put((CheckBox) rootView.findViewById(R.id.chk2), "1");
        checkBoxMap.put((CheckBox) rootView.findViewById(R.id.chk3), "2");
        checkBoxMap.put((CheckBox) rootView.findViewById(R.id.chk4), "3");
        checkBoxMap.put((CheckBox) rootView.findViewById(R.id.chk5), "4");

        checkBoxGroup = new CheckBoxGroup<>(checkBoxMap,
                new CheckBoxGroup.CheckedChangeListener<String>() {
                    @Override
                    public void onCheckedChange(ArrayList<String> options) {
                    }
                });

        HashMap<CheckBox, String> checkBoxMap2 = new HashMap<>();
        checkBoxMap2.put((CheckBox) rootView.findViewById(R.id.chk6), "0");
        checkBoxMap2.put((CheckBox) rootView.findViewById(R.id.chk7), "1");
        checkBoxMap2.put((CheckBox) rootView.findViewById(R.id.chk8), "2");
        checkBoxMap2.put((CheckBox) rootView.findViewById(R.id.chk9), "3");
        checkBoxMap2.put((CheckBox) rootView.findViewById(R.id.chk10), "4");
        checkBoxMap2.put((CheckBox) rootView.findViewById(R.id.chk11), "5");
        checkBoxMap2.put((CheckBox) rootView.findViewById(R.id.chk12), "6");
        checkBoxMap2.put((CheckBox) rootView.findViewById(R.id.chk13), "7");
        checkBoxMap2.put((CheckBox) rootView.findViewById(R.id.chk14), "8");
        checkBoxMap2.put((CheckBox) rootView.findViewById(R.id.chk15), "9");
        checkBoxMap2.put((CheckBox) rootView.findViewById(R.id.chk16), "10");

        checkBoxGroup2 = new CheckBoxGroup<>(checkBoxMap2,
                new CheckBoxGroup.CheckedChangeListener<String>() {
                    @Override
                    public void onCheckedChange(ArrayList<String> options) {
                    }
                });

        btnSave = (Button) rootView.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAdd();
            }
        });
        return rootView;
    }

    public void onClickAdd() {

        if ("false".equalsIgnoreCase(MSHTBApplication.getInstance().getSettings(DBColumnKeys.MEDIC))) {
            UIUtil.showInfoDialog(getActivity(), SweetAlertDialog.WARNING_TYPE, "Warning", String.format("Not allowed to updated this information"));
            return;
        }
        ArrayList<String> selectedValues = checkBoxGroup.getValues();
        ArrayList<String> selectedValues2 = checkBoxGroup2.getValues();

        if (patient == null) {
            UIUtil.displayError(getActivity(), "Contact details");
            return;
        } else if (radioGroup3.getCheckedRadioButtonIndex() == 0 && selectedValues.isEmpty()) {
            UIUtil.displayError(getActivity(), "TB symptom");
            return;
        }

        PETClinicianFollowupDao dao = DatabaseManager.getInstance().getSession().getPETClinicianFollowupDao();
        dao.save(new PETClinicianFollowup(null, patient.getQr(), MSHTBApplication.getInstance().getSettingsInt(DBColumnKeys.SCREENER_ID)/*Constant.SCREENER_ID*/,
                radioGroup1.getCheckedRadioButtonIndex(),  Double.valueOf(txtWeight.getText().toString()),
                Double.valueOf(txtHeight.getText().toString()), "".equals(Util.getText(txtMUAC))? -1 : Double.valueOf(txtMUAC.getText().toString()),
                radioGroup2.getCheckedRadioButtonIndex(), radioGroup3.getCheckedRadioButtonIndex() == 0 ? true : false,
                selectedValues.toString().replaceAll("[\\[\\]]", ""),
                Util.getText(txtSymptom), selectedValues2.toString().replaceAll("[\\[\\]]", ""),
                Util.getText(txtOther), radioGroup4.getCheckedRadioButtonIndex(),
                new Date().getTime(), false, 0.0, 0.0));

        UIUtil.showInfoDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE,  "Success", "Clinician followup (part one) completed", onDismissListener);
    }
}

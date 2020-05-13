package com.streamstech.droid.mshtb.fragement;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class PETFollowUpFragement extends Fragment {

    MultiLineRadioGroup radioGroup1;
    MultiLineRadioGroup radioGroup2;
    MultiLineRadioGroup radioGroup3;

    LinearLayout layout2a;
    LinearLayout layout2b;
    CheckBoxGroup<String> checkBoxGroup;
    CheckBoxGroup<String> checkBoxGroup2;

    EditText txtSymptom;
    EditText txtOther;
    EditText txtAdherence;
    TextView lblTime;
    Button btnSave;
    PETEnrollment patient;
    DialogInterface.OnDismissListener onDismissListener;

    public PETFollowUpFragement() {
    }

    @SuppressWarnings("unused")
    public static PETFollowUpFragement newInstance(PETEnrollment patient) {
        PETFollowUpFragement fragment = new PETFollowUpFragement();
        fragment.patient = patient;
        return fragment;
    }

    public void setEnrollment (PETEnrollment patient) {
        this.patient = patient;
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

        View rootView = inflater.inflate(R.layout.fragement_pet_followup, container, false);
        layout2a = (LinearLayout)rootView.findViewById(R.id.layout2a) ;
        layout2b = (LinearLayout)rootView.findViewById(R.id.layout2b) ;
        lblTime = (TextView) rootView.findViewById(R.id.lblTime);
        lblTime.setText(Util.getFormattedDateTime(System.currentTimeMillis()));
        txtAdherence = (EditText)rootView.findViewById(R.id.txtAdherence);
        txtOther = (EditText)rootView.findViewById(R.id.txtOther);
        txtSymptom = (EditText)rootView.findViewById(R.id.txtSymptom);

        radioGroup1 = (MultiLineRadioGroup) rootView.findViewById(R.id.radioGroup1);
        radioGroup2 = (MultiLineRadioGroup) rootView.findViewById(R.id.radioGroup2);
        radioGroup2.setOnCheckedChangeListener(new MultiLineRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ViewGroup viewGroup, RadioButton radioButton) {
                if (radioGroup2.getCheckedRadioButtonIndex() == 0){
                    layout2a.setVisibility(View.VISIBLE);
                    layout2b.setVisibility(View.VISIBLE);
                } else {
                    layout2a.setVisibility(View.GONE);
                    layout2b.setVisibility(View.GONE);
                }
            }
        });
        radioGroup3 = (MultiLineRadioGroup) rootView.findViewById(R.id.radioGroup3);

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

    private void onClickAdd() {

        if ("true".equalsIgnoreCase(MSHTBApplication.getInstance().getSettings(DBColumnKeys.MEDIC))) {
            UIUtil.showInfoDialog(getActivity(), SweetAlertDialog.WARNING_TYPE, "Warning", String.format("Not allowed to updated this information"));
            return;
        }
        ArrayList<String> selectedValues = checkBoxGroup.getValues();
        ArrayList<String> selectedValues2 = checkBoxGroup2.getValues();

        if (patient == null) {
            UIUtil.displayError(getActivity(), "Contact details");
            return;
        } else if (radioGroup1.getCheckedRadioButtonIndex() == 0 && selectedValues.isEmpty()) {
            UIUtil.displayError(getActivity(), "TB symptom");
            return;
        }

        PETFollowupDao dao = DatabaseManager.getInstance().getSession().getPETFollowupDao();
        dao.save(new PETFollowup(null, patient.getQr(), MSHTBApplication.getInstance().getSettingsInt(DBColumnKeys.SCREENER_ID)/*Constant.SCREENER_ID*/,
                radioGroup1.getCheckedRadioButtonIndex(),  radioGroup2.getCheckedRadioButtonIndex() == 0 ? true : false, selectedValues.toString().replaceAll("[\\[\\]]", ""),
                Util.getText(txtSymptom), selectedValues2.toString().replaceAll("[\\[\\]]", ""),
                Util.getText(txtOther), radioGroup3.getCheckedRadioButtonIndex(), Util.getText(txtAdherence),
                new Date().getTime(), false, 0.0, 0.0));

        UIUtil.showInfoDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE,  "Success", "Followup completed", onDismissListener);
    }
}

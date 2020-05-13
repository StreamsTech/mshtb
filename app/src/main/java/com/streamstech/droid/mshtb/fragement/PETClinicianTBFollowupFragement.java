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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.data.Constant;
import com.streamstech.droid.mshtb.data.persistent.DBColumnKeys;
import com.streamstech.droid.mshtb.data.persistent.DatabaseManager;
import com.streamstech.droid.mshtb.data.persistent.PETClinicianFollowup;
import com.streamstech.droid.mshtb.data.persistent.PETClinicianFollowupDao;
import com.streamstech.droid.mshtb.data.persistent.PETClinicianTBReview;
import com.streamstech.droid.mshtb.data.persistent.PETClinicianTBReviewDao;
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

public class PETClinicianTBFollowupFragement extends Fragment {

    MultiLineRadioGroup radioGroup1;
    CheckBoxGroup<String> checkBoxGroup;
    CheckBoxGroup<String> checkBoxGroup2;
    EditText txtInstruction;
    DatePicker dtReturnVisitDate;
    TextView lblTime;
    Button btnSave;
    PETEnrollment patient;
    DialogInterface.OnDismissListener onDismissListener;

    public PETClinicianTBFollowupFragement() {
    }

    public void setEnrollment (PETEnrollment patient) {
        this.patient = patient;
    }

    public void seListener (DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    public void onClearClicked () {
        radioGroup1.checkAt(0);
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

        View rootView = inflater.inflate(R.layout.activity_petclinician_tbfollowup_activicity, container, false);
        lblTime = (TextView) rootView.findViewById(R.id.lblTime);
        lblTime.setText(Util.getFormattedDateTime(System.currentTimeMillis()));
        radioGroup1 = (MultiLineRadioGroup) rootView.findViewById(R.id.radioGroup1);
        txtInstruction = (EditText) rootView.findViewById(R.id.txtInstruction);
        dtReturnVisitDate = (DatePicker) rootView.findViewById(R.id.dtReturnVisitDate);

        HashMap<CheckBox, String> checkBoxMap = new HashMap<>();
        checkBoxMap.put((CheckBox) rootView.findViewById(R.id.chk1), "0");
        checkBoxMap.put((CheckBox) rootView.findViewById(R.id.chk2), "1");
        checkBoxMap.put((CheckBox) rootView.findViewById(R.id.chk3), "2");
        checkBoxMap.put((CheckBox) rootView.findViewById(R.id.chk4), "3");
        checkBoxMap.put((CheckBox) rootView.findViewById(R.id.chk5), "4");
        checkBoxMap.put((CheckBox) rootView.findViewById(R.id.chk6), "5");

        checkBoxGroup = new CheckBoxGroup<>(checkBoxMap,
                new CheckBoxGroup.CheckedChangeListener<String>() {
                    @Override
                    public void onCheckedChange(ArrayList<String> options) {
                    }
                });

        HashMap<CheckBox, String> checkBoxMap2 = new HashMap<>();
        checkBoxMap2.put((CheckBox) rootView.findViewById(R.id.chk7), "0");
        checkBoxMap2.put((CheckBox) rootView.findViewById(R.id.chk8), "1");
        checkBoxMap2.put((CheckBox) rootView.findViewById(R.id.chk9), "2");
        checkBoxMap2.put((CheckBox) rootView.findViewById(R.id.chk10), "3");
        checkBoxMap2.put((CheckBox) rootView.findViewById(R.id.chk11), "4");
        checkBoxMap2.put((CheckBox) rootView.findViewById(R.id.chk12), "5");

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

        if ("false".equalsIgnoreCase(MSHTBApplication.getInstance().getSettings(DBColumnKeys.MEDIC))) {
            UIUtil.showInfoDialog(getActivity(), SweetAlertDialog.WARNING_TYPE, "Warning", String.format("Not allowed to updated this information"));
            return;
        }
        ArrayList<String> selectedValues = checkBoxGroup.getValues();
        ArrayList<String> selectedValues2 = checkBoxGroup2.getValues();

        if (patient == null) {
            UIUtil.displayError(getActivity(), "Contact details");
            return;
        } else if (radioGroup1.getCheckedRadioButtonIndex() == 0 && selectedValues.isEmpty()) {
            UIUtil.displayError(getActivity(), "Adherence");
            return;
        }

        PETClinicianTBReviewDao dao = DatabaseManager.getInstance().getSession().getPETClinicianTBReviewDao();
        dao.save(new PETClinicianTBReview(null, patient.getQr(), MSHTBApplication.getInstance().getSettingsInt(DBColumnKeys.SCREENER_ID)/*Constant.SCREENER_ID*/,
                radioGroup1.getCheckedRadioButtonIndex(),
                selectedValues.toString().replaceAll("[\\[\\]]", ""),
                selectedValues2.toString().replaceAll("[\\[\\]]", ""),
                Util.getText(txtInstruction), Util.getDateFromDatePicker(dtReturnVisitDate).getTime(),
                new Date().getTime(), false, 0.0, 0.0));

        UIUtil.showInfoDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE,  "Success", "Clinician followup (part two) completed", onDismissListener);
    }
}

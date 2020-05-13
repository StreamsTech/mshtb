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
import android.widget.TextView;

import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.activity.PETTreatmentAdherenceNewActivity;
import com.streamstech.droid.mshtb.data.Constant;
import com.streamstech.droid.mshtb.data.persistent.DBColumnKeys;
import com.streamstech.droid.mshtb.data.persistent.DatabaseManager;
import com.streamstech.droid.mshtb.data.persistent.PETAdherence;
import com.streamstech.droid.mshtb.data.persistent.PETAdherenceDao;
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

public class PETAdherenceFragement extends Fragment {

    MultiLineRadioGroup radioGroup1;
    MultiLineRadioGroup radioGroup2;
    MultiLineRadioGroup radioGroup3;
    MultiLineRadioGroup radioGroup4;
    MultiLineRadioGroup radioGroup5;

    TextView lblTime;
    EditText txtAdverseEffect;
    EditText txtComment;

    Button btnSave;
    PETEnrollment patient;
    DialogInterface.OnDismissListener onDismissListener;

    public PETAdherenceFragement() {
    }

    @SuppressWarnings("unused")
    public static PETAdherenceFragement newInstance(PETEnrollment patient) {
        PETAdherenceFragement fragment = new PETAdherenceFragement();
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
        radioGroup4.checkAt(1);
        radioGroup5.checkAt(1);
        txtAdverseEffect.setText("");
        txtComment.setText("");
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

        View rootView = inflater.inflate(R.layout.activity_pettreatment_adherence, container, false);
        lblTime = (TextView) rootView.findViewById(R.id.lblTime);
        lblTime.setText(Util.getFormattedDateTime(System.currentTimeMillis()));
        txtAdverseEffect = (EditText)rootView.findViewById(R.id.txtAdverseEffect) ;
        txtComment = (EditText)rootView.findViewById(R.id.txtComment) ;
        radioGroup1 = (MultiLineRadioGroup) rootView.findViewById(R.id.radioGroup1);
        radioGroup2 = (MultiLineRadioGroup) rootView.findViewById(R.id.radioGroup2);
        radioGroup3 = (MultiLineRadioGroup) rootView.findViewById(R.id.radioGroup3);
        radioGroup4 = (MultiLineRadioGroup) rootView.findViewById(R.id.radioGroup4);
        radioGroup5 = (MultiLineRadioGroup) rootView.findViewById(R.id.radioGroup5);
        btnSave = (Button) rootView.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAdd();
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

        if ("true".equalsIgnoreCase(MSHTBApplication.getInstance().getSettings(DBColumnKeys.MEDIC))) {
            UIUtil.showInfoDialog(getActivity(), SweetAlertDialog.WARNING_TYPE, "Warning", String.format("Not allowed to updated this information"));
            return;
        }
        if (patient == null) {
            UIUtil.displayError(getActivity(), "Contact details");
            return;
        }

        PETAdherenceDao dao = DatabaseManager.getInstance().getSession().getPETAdherenceDao();
        dao.save(new PETAdherence(null, patient.getQr(), MSHTBApplication.getInstance().getSettingsInt(DBColumnKeys.SCREENER_ID)/*Constant.SCREENER_ID*/,
                radioGroup1.getCheckedRadioButtonIndex(),  radioGroup2.getCheckedRadioButtonIndex(),
                radioGroup3.getCheckedRadioButtonIndex(), Util.getText(txtAdverseEffect),
                radioGroup4.getCheckedRadioButtonIndex(),  Util.getText(txtComment),
                radioGroup5.getCheckedRadioButtonIndex(), new Date().getTime(), false, 0.0, 0.0));

        UIUtil.showInfoDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE,  "Success", "Treatment adherence form completed", onDismissListener);
    }
}

package com.streamstech.droid.mshtb.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.util.UIUtil;
import com.streamstech.droid.mshtb.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class ScreeningActivity extends AppCompatActivity {

    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.txtAge)
    TextView txtAge;
    @BindView(R.id.radioMale)
    RadioButton radioMale;
    @BindView(R.id.radioFemale)
    RadioButton radioFemale;
    @BindView(R.id.btnAdd)
    Button btnAdd;
    @BindView(R.id.formView)
    ScrollView formView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screening);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.btnAdd)
    public void onAddClicked() {
        if (Util.isEmpty(txtName)){
            UIUtil.showInfoDialog(this, SweetAlertDialog.WARNING_TYPE, "Warning", "Name missing");
            return;
        }
        formView.setVisibility(View.VISIBLE);
    }
}

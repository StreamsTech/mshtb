package com.streamstech.droid.mshtb.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.data.persistent.DBColumnKeys;
import com.streamstech.droid.mshtb.util.MSHTBApplication;
import com.streamstech.droid.mshtb.util.UIUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.lblName)
    TextView lblName;
    @BindView(R.id.lblProgram)
    TextView lblProgram;
    @BindView(R.id.lblMobileNumber)
    TextView lblMobileNumber;
    @BindView(R.id.lblID)
    TextView lblID;
    @BindView(R.id.lblUserName)
    TextView lblUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        lblName.setText(MSHTBApplication.getInstance().getSettings(DBColumnKeys.NAME));
        lblProgram.setText(MSHTBApplication.getInstance().getSettings(DBColumnKeys.PROGRAM));
        lblMobileNumber.setText(MSHTBApplication.getInstance().getSettings(DBColumnKeys.MOBILE_NO));
        lblID.setText(MSHTBApplication.getInstance().getSettings(DBColumnKeys.LOCATION_NO));
        lblUserName.setText(MSHTBApplication.getInstance().getSettings(DBColumnKeys.USER_NAME));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

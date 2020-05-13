package com.streamstech.droid.mshtb.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.data.Constant;
import com.streamstech.droid.mshtb.data.persistent.DatabaseManager;
import com.streamstech.droid.mshtb.data.persistent.PETClinicianTBReview;
import com.streamstech.droid.mshtb.data.persistent.PETClinicianTBReviewDao;
import com.streamstech.droid.mshtb.data.persistent.PETEnrollment;
import com.streamstech.droid.mshtb.data.persistent.PETEnrollmentDao;
import com.streamstech.droid.mshtb.fragement.HistoryFragmentInteractionListener;
import com.streamstech.droid.mshtb.fragement.PETClinicianFollowupFragement;
import com.streamstech.droid.mshtb.fragement.PETClinicianFollowupGroupFragment;
import com.streamstech.droid.mshtb.fragement.PETClinicianTBFollowupFragement;
import com.streamstech.droid.mshtb.fragement.PETClinicianTBFollowupGroupFragment;
import com.streamstech.droid.mshtb.fragement.ViewPagerAdapter;
import com.streamstech.droid.mshtb.util.MSHTBApplication;
import com.streamstech.droid.mshtb.util.UIUtil;
import com.streamstech.droid.mshtb.util.Util;
import com.whygraphics.multilineradiogroup.MultiLineRadioGroup;
import com.xeoh.android.checkboxgroup.CheckBoxGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PETClinicianTBFollowupNewActivicity extends BaseSearchActivity implements BaseSearchActivity.PatientSelectionEventListener,
        DialogInterface.OnDismissListener, HistoryFragmentInteractionListener {


    ViewPager mViewPager;
    PETClinicianTBFollowupGroupFragment groupFragment;
    PETClinicianTBFollowupFragement fragment;
    ViewPagerAdapter adapter;
    PETEnrollment patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View child = getLayoutInflater().inflate(R.layout.common_fragment, null);
        container.addView(child);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String pid  = bundle.getString("PID");
            PETEnrollmentDao dao = DatabaseManager.getInstance().getSession().getPETEnrollmentDao();
            patient = dao.queryBuilder().where(PETEnrollmentDao.Properties.Qr.eq(pid)).unique();
            if (patient == null) {
                finish();
            }
        }
        mViewPager = (ViewPager)child.findViewById(R.id.pager);
        setupViewPager(mViewPager);
        setListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        groupFragment = new PETClinicianTBFollowupGroupFragment();
        fragment = new PETClinicianTBFollowupFragement();
        fragment.seListener(this);

        adapter.addFragment(groupFragment);
        adapter.addFragment(fragment);
        List<String> list = new ArrayList<>();
        list.add("HISTORY");
        list.add("NEW");
        adapter.setTitle(list);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onListFragmentInteraction() {
    }


    @Override
    public void onPatientSelected(PETEnrollment patient) {
        this.patient = patient;
        fragment.setEnrollment(patient);
        groupFragment.setEnrollment(patient);
        System.out.println("Selected patient qr: " + patient.getQr());
    }

    @Override
    public void onClearClicked () {
        fragment.onClearClicked();
        groupFragment.onClearClicked();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        finish();
    }
}
package com.streamstech.droid.mshtb.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.data.Constant;
import com.streamstech.droid.mshtb.data.Forms;
import com.streamstech.droid.mshtb.data.FormsType;
import com.streamstech.droid.mshtb.data.State;
import com.streamstech.droid.mshtb.data.persistent.DBColumnKeys;
import com.streamstech.droid.mshtb.data.persistent.DatabaseManager;
import com.streamstech.droid.mshtb.data.persistent.PETEnrollment;
import com.streamstech.droid.mshtb.data.persistent.PETRegistration;
import com.streamstech.droid.mshtb.data.persistent.Patient;
import com.streamstech.droid.mshtb.data.persistent.Settings;
import com.streamstech.droid.mshtb.data.persistent.SettingsDao;
import com.streamstech.droid.mshtb.fragement.FASTFormsFragment;
import com.streamstech.droid.mshtb.fragement.PETFormsFragment;
import com.streamstech.droid.mshtb.fragement.PETPatientGroupFragment;
import com.streamstech.droid.mshtb.fragement.PatientGroupFragment;
import com.streamstech.droid.mshtb.fragement.ViewPagerAdapter;
import com.streamstech.droid.mshtb.io.FASTSyncManager;
import com.streamstech.droid.mshtb.util.MSHTBApplication;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        FASTFormsFragment.OnListFragmentInteractionListener {

    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    TextView lblUserName, lblLocationId, lblMobileno;
    //    @BindView(R.id.lstUploadList)
//    ListView lstFormList;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pager)
    ViewPager mViewPager;
    @BindView(R.id.navigation)
    BottomNavigationView bottomNavigationView;

    //    PatientFragment patientFragment;
    PatientGroupFragment patientFragment;
    PETPatientGroupFragment petPatientGroupFragment;
    FASTFormsFragment fastFormsFragment;
    PETFormsFragment petFormsFragment;
    ViewPagerAdapter adapter;
    MenuItem prevMenuItem;
    private FASTSyncManager mAuthTask = null;
//    UploadListAdapter uploadListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setTitle(getResources().getString(R.string.title_activity_main) + " (" + MSHTBApplication.getInstance().getSettings(DBColumnKeys.PROGRAM) + ")");

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        lblUserName = (TextView) headerView.findViewById(R.id.lblUserName);
        lblUserName.setText(MSHTBApplication.getInstance().getSettings(DBColumnKeys.NAME));

        lblLocationId = (TextView) headerView.findViewById(R.id.lblScreenerId);
        lblLocationId.setText(String.format("%03d", Integer.parseInt(MSHTBApplication.getInstance().getSettings(DBColumnKeys.LOCATION_NO))));

        lblMobileno = (TextView) headerView.findViewById(R.id.lblMobileno);
        lblMobileno.setText(MSHTBApplication.getInstance().getSettings(DBColumnKeys.MOBILE_NO));

        checkAndUpdateCounter();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
//        sync();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setBackgroundTintList(ColorStateList.valueOf(Color
                .parseColor("#FFFFFF")));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("FAST".equalsIgnoreCase(MSHTBApplication.getInstance().getSettings(DBColumnKeys.PROGRAM))) {
                    startActivity(new Intent(NewMainActivity.this, ScreeningActivity.class));
                } else {
                    if ("FALSE".equalsIgnoreCase(MSHTBApplication.getInstance().getSettings(DBColumnKeys.MEDIC))) {
                        startActivity(new Intent(NewMainActivity.this, PETRegistrationActivity.class));
                    }
                }
            }
        });

        final int version = Build.VERSION.SDK_INT;
        if (version >= 23)
            checkPermission();

        setupViewPager(mViewPager);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        if ("FAST".equalsIgnoreCase(MSHTBApplication.getInstance().getSettings(DBColumnKeys.PROGRAM))) {
            bottomNavigationView.inflateMenu(R.menu.navigationfast);
            bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        } else {
            bottomNavigationView.inflateMenu(R.menu.navigationpet);
            bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListenerPET);
        }

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: " + position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);
                mViewPager.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    private void checkAndUpdateCounter() {

//        PatientDao patientDao = DatabaseManager.getInstance().getSession().getPatientDao();
//        long currentTime = System.currentTimeMillis();
//        Date aDayAgo = new Date(currentTime - DateUtils.DAY_IN_MILLIS);
//        Date inADay = new Date(currentTime + DateUtils.DAY_IN_MILLIS);
//        CountQuery<Patient> countQuery = patientDao.queryBuilder().where(
//                PatientDao.Properties.Createdtime.gt(aDayAgo),
//                PatientDao.Properties.Createdtime.lt(inADay))
//                .buildCount();
//        Constant.DAY_COUNT = countQuery.count() + 1;
    }

//    private void populateData() {
//        PrescriptionDao prescriptionDao = DatabaseManager.getInstance().getSession()
//                .getPrescriptionDao();
//        List<Screening> prescriptionList = prescriptionDao.queryBuilder()
//                .orderDesc(PrescriptionDao.Properties.Id)
//                .list();
//        uploadListAdapter = new UploadListAdapter(this, prescriptionList);
//        lstFormList.setAdapter(uploadListAdapter);
//    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_patient:
                    mViewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_forms:
                    mViewPager.setCurrentItem(1);
                    return true;
            }
            return false;
        }
    };

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListenerPET
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_patient_pet:
                    mViewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_forms_pet:
                    mViewPager.setCurrentItem(1);
                    return true;
            }
            return false;
        }
    };

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        if ("FAST".equalsIgnoreCase(MSHTBApplication.getInstance().getSettings(DBColumnKeys.PROGRAM))) {
            patientFragment = new PatientGroupFragment();
            fastFormsFragment = new FASTFormsFragment();
            adapter.addFragment(patientFragment);
            adapter.addFragment(fastFormsFragment);
        } else {
            petPatientGroupFragment = new PETPatientGroupFragment();
            petFormsFragment = new PETFormsFragment();
            adapter.addFragment(petPatientGroupFragment);
            adapter.addFragment(petFormsFragment);
        }
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onListFragmentInteraction(Patient item) {
        Intent intent = new Intent(NewMainActivity.this, PatientDetailsActivity.class);
        intent.putExtra("PID", item.getPatientid());
        startActivity(intent);
    }

    @Override
    public void onListFragmentInteraction(PETRegistration item) {
        Intent intent = new Intent(NewMainActivity.this, PETProfileActivity.class);
        intent.putExtra("INDEX_PID", item.getQr());
        startActivity(intent);
    }

    @Override
    public void onListFragmentInteraction(PETEnrollment item) {
        Intent intent = new Intent(NewMainActivity.this, PETEnrollmentActivity.class);
        intent.putExtra("PID", item.getQr());
        startActivity(intent);
    }

    public void updatePatient() {
        if ("FAST".equalsIgnoreCase(MSHTBApplication.getInstance().getSettings(DBColumnKeys.PROGRAM))) {
            patientFragment.updatePatient();
        } else {
            petPatientGroupFragment.updatePatient();
        }
    }

    @Override
    public void onListFragmentInteraction(Forms item) {
        if (item.getCode() == FormsType.SCREENING) {
            startActivity(new Intent(NewMainActivity.this, ScreeningActivity.class));
        } else if (item.getCode() == FormsType.INDICATION) {
            startActivity(new Intent(NewMainActivity.this, TestIndicationActivity.class));
        } else if (item.getCode() == FormsType.RESULT_XRAY) {
            startActivity(new Intent(NewMainActivity.this, XRayResultActivity.class));
        } else if (item.getCode() == FormsType.RESULT_XPERT) {
            startActivity(new Intent(NewMainActivity.this, XPertResultActivity.class));
        } else if (item.getCode() == FormsType.RESULT_SMEAR) {
            startActivity(new Intent(NewMainActivity.this, SmearResultActivity.class));
        } else if (item.getCode() == FormsType.RESULT_HISTOPATHOLOGY) {
            startActivity(new Intent(NewMainActivity.this, HistiopathologyResultActivity.class));
        } else if (item.getCode() == FormsType.REGISTRATION) {
            startActivity(new Intent(NewMainActivity.this, PETRegistrationActivity.class));
        } else if (item.getCode() == FormsType.TREATMENT_INITIATION) {
            startActivity(new Intent(NewMainActivity.this, TreatmentInitiationActivity.class));
        } else if (item.getCode() == FormsType.TREATMENT_OUTCOME) {
            startActivity(new Intent(NewMainActivity.this, TreatmentOutcomeActivity.class));
        } else if (item.getCode() == FormsType.ENROLLMENT) {
            startActivity(new Intent(NewMainActivity.this, PETEnrollmentActivity.class));
        } else if (item.getCode() == FormsType.CONTACT_SYMPTOM) {
            startActivity(new Intent(NewMainActivity.this, PETSymptomActivity.class));
        } else if (item.getCode() == FormsType.PET_TEST_ORDER) {
            startActivity(new Intent(NewMainActivity.this, PETTestOrderActivity.class));
        } else if (item.getCode() == FormsType.PET_TEST_RESULT) {
            startActivity(new Intent(NewMainActivity.this, PETTestResultActivity.class));
        } else if (item.getCode() == FormsType.PET_ELIGIBILITY_SYMPTOM) {
            startActivity(new Intent(NewMainActivity.this, PETEligibilityQuestionnaireActivity.class));
        } else if (item.getCode() == FormsType.PET_TREATMENT_START) {
            startActivity(new Intent(NewMainActivity.this, PETTreatmentStartActivity.class));
        } else if (item.getCode() == FormsType.PET_TREATMENT_ADHERENCE) {
            startActivity(new Intent(NewMainActivity.this, PETTreatmentAdherenceNewActivity.class));
        } else if (item.getCode() == FormsType.PET_FOLLOWUP) {
            startActivity(new Intent(NewMainActivity.this, PETFollowupNewActivity.class));
        } else if (item.getCode() == FormsType.PET_CLINICIAN_FOLLOWUP) {
            startActivity(new Intent(NewMainActivity.this, PETClinicianFollowupNewActivity.class));
        } else if (item.getCode() == FormsType.PET_CLINICIAN_TB_FOLLOWUP) {
            startActivity(new Intent(NewMainActivity.this, PETClinicianTBFollowupNewActivicity.class));
        } else if (item.getCode() == FormsType.PET_ADVERSE_EVENT) {
            startActivity(new Intent(NewMainActivity.this, PETAdverseEventsNewActivity.class));
        } else if (item.getCode() == FormsType.PET_END_FOLLOWUP) {
            startActivity(new Intent(NewMainActivity.this, PETEndFollowupActivity.class));
        }

    }

    private void sync() {
//        UIUtil.showSweetProgress(this, "Syncing...", false, null);
//        Retrofit client = new Retrofit.Builder()
//                .baseUrl(Paths.SERVER_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        EndpointInterface service = client.create(EndpointInterface.class);
//        String mobile = MSHTBApplication.getInstance().getSettings(DBColumnKeys.MOBILE_NO);
//        String otp = MSHTBApplication.getInstance().getSettings(DBColumnKeys.OTP);
//        retrofit2.Call<CommonResponse> call = service.getmedia(mobile, otp);
//        call.enqueue(new Callback<CommonResponse>() {
//            @Override
//            public void onResponse(retrofit2.Call<CommonResponse> call, retrofit2.Response<CommonResponse> response) {
//                UIUtil.hideSweetProgress();
//                if (response.isSuccessful()) {
//                    Type type = new TypeToken<List<UploadedItem>>() {}.getType();
//                    List<UploadedItem> uploadedItems = new GsonBuilder()
//                            .create()
//                            .fromJson(new Gson()
//                                    .toJson(response.body()
//                                            .getResults()), type);
//                    PrescriptionDao prescriptionDao = DatabaseManager.getInstance().getSession()
//                            .getPrescriptionDao();
//                    prescriptionDao.deleteAll();
//                    for (UploadedItem uploadedItem : uploadedItems){
//                        prescriptionDao.save(new Screening(null, uploadedItem.getId(), uploadedItem.getMessage(),
//                                uploadedItem.getImageone(), uploadedItem.getImagetwo(), uploadedItem.getUploadedtime(),
//                                2, true, uploadedItem.isDelivered()));
//                    }
//                    populateData();
//                }
//            }
//
//            @Override
//            public void onFailure(retrofit2.Call<CommonResponse> call, Throwable t) {
//                t.printStackTrace();
//                UIUtil.hideSweetProgress();
//                populateData();
////                UIUtil.showInfoDialog(NewMainActivity.this, SweetAlertDialog.ERROR_TYPE, Constant.APP_NAME, Constant.UPLOAD_FALIED);
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (MSHTBApplication.getInstance().getSettingsBoolean(DBColumnKeys.SYNC_RUNNING)) {
                System.out.println("Sync in progress, can't quit");
                moveTaskToBack(true);
            } else {
                super.onBackPressed();
            }
        }
    }

    // Permission headache
    private void checkPermission() {
        List<String> permissionsNeeded = new ArrayList<>();
        final List<String> permissionsList = new ArrayList<>();
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsNeeded.add("File write");
        if (!addPermission(permissionsList, Manifest.permission.CAMERA))
            permissionsNeeded.add("Capture photo");

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                            }
                        });
                return;
            }
            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            return;
        }
    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!shouldShowRequestPermissionRationale(permission))
                return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        populateData();
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(Constant.OK, okListener)
                .setNegativeButton(Constant.CANCEL, null)
                .create()
                .show();
    }

//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigationfast view item clicks here.
//        int id = item.getItemId();
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_sync) {
            Intent intent = new Intent(this, SyncActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }
//        else if (id == R.id.nav_test) {
//            Intent intent = new Intent(this, PETEligibilityQuestionnaireActivity.class);
//            startActivity(intent);
//        }
        else if (id == R.id.nav_logout) {

            SettingsDao settingsDao = DatabaseManager.getInstance().getSession()
                    .getSettingsDao();
            Settings settings = settingsDao.queryBuilder()
                    .where(SettingsDao.Properties.Key.eq(DBColumnKeys.STATE))
                    .unique();
            if (settings != null) {
                settings.setValue(State.LOGGED_OUT.name());
                settingsDao.update(settings);
            }
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_patient) {
            if ("FAST".equalsIgnoreCase(MSHTBApplication.getInstance().getSettings(DBColumnKeys.PROGRAM))) {
                Intent intent = new Intent(this, PatientDetailsActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, PETProfileActivity.class);
                startActivity(intent);
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

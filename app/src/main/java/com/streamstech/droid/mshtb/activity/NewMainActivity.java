package com.streamstech.droid.mshtb.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.data.Constant;
import com.streamstech.droid.mshtb.data.UploadedItem;
import com.streamstech.droid.mshtb.data.persistent.DBColumnKeys;
import com.streamstech.droid.mshtb.data.persistent.DatabaseManager;
import com.streamstech.droid.mshtb.data.persistent.Screening;
import com.streamstech.droid.mshtb.io.CommonResponse;
import com.streamstech.droid.mshtb.io.EndpointInterface;
import com.streamstech.droid.mshtb.io.Paths;
import com.streamstech.droid.mshtb.util.MSHTBApplication;
import com.streamstech.droid.mshtb.util.UIUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    TextView lblUserName, lblScreenerId, lblLocationName;
    @BindView(R.id.lstUploadList)
    ListView lstFormList;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
//    UploadListAdapter uploadListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main);
        ButterKnife.bind(this);
        setTitle(Constant.APP_NAME);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        lblUserName = (TextView) headerView.findViewById(R.id.lblUserName);
        lblUserName.setText(MSHTBApplication.getInstance().getSettings(DBColumnKeys.FIRST_NAME)
                + " " + MSHTBApplication.getInstance().getSettings(DBColumnKeys.LAST_NAME));
        lblScreenerId = (TextView) headerView.findViewById(R.id.lblScreenerId);
        lblLocationName = (TextView) headerView.findViewById(R.id.lblLocationName);
//        lblMobile.setText(MSHTBApplication.getInstance().getSettings(DBColumnKeys.MOBILE_NO));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
//        sync();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setBackgroundTintList(ColorStateList.valueOf(Color
                .parseColor("#1b4283")));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NewMainActivity.this, ScreeningActivity.class));
            }
        });

        final int version = Build.VERSION.SDK_INT;
        if (version >= 23)
            checkPermission();
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

    private void sync(){
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

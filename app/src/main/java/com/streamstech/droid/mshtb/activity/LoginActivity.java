package com.streamstech.droid.mshtb.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.data.Constant;
import com.streamstech.droid.mshtb.data.State;
import com.streamstech.droid.mshtb.data.persistent.DBColumnKeys;
import com.streamstech.droid.mshtb.data.persistent.DatabaseManager;
import com.streamstech.droid.mshtb.data.persistent.Settings;
import com.streamstech.droid.mshtb.data.persistent.SettingsDao;
import com.streamstech.droid.mshtb.io.CommonResponse;
import com.streamstech.droid.mshtb.io.EndpointInterface;
import com.streamstech.droid.mshtb.io.Paths;
import com.streamstech.droid.mshtb.io.FASTSyncManager;
import com.streamstech.droid.mshtb.util.MSHTBApplication;
import com.streamstech.droid.mshtb.util.UIUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements SweetAlertDialog.OnSweetClickListener {

    @BindView(R.id.txtUserName)
    public EditText txtUserName;
    @BindView(R.id.txtPassword)
    public EditText txtPassword;
    @BindView(R.id.lblPoweredBy)
    public TextView lblPoweredBy;
    @BindView(R.id.btnVerify)
    public Button btnVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        ButterKnife.bind(this);
        MSHTBApplication.getInstance().hideKeyboard(this);
        String mobile = MSHTBApplication.getInstance().getSettings(DBColumnKeys.USER_NAME);
        txtUserName.setText(mobile);

        lblPoweredBy.setText(Html.fromHtml("Powered by <a href=\"https://streamstech.com.bd/\">Streams Tech Limited</a>"));

        SettingsDao settingsDao = DatabaseManager.getInstance().getSession()
                .getSettingsDao();
        Settings settings = settingsDao.queryBuilder()
                .where(SettingsDao.Properties.Key.eq(DBColumnKeys.SERVER_URL))
                .unique();
        if (settings != null) {
            Paths.SERVER_URL = settings.getValue();
        } else {
            settingsDao.save(new Settings(null, DBColumnKeys.SERVER_URL, Paths.SERVER_URL));
        }

        settings = settingsDao.queryBuilder()
                .where(SettingsDao.Properties.Key.eq(DBColumnKeys.STATE))
                .unique();
        if (settings != null) {
            if (settings.getValue().equals(State.VERIFIED.name())) {
                boolean offlineReady = readProfile();
                if (offlineReady) {
                    startActivity(new Intent(LoginActivity.this, NewMainActivity.class));
                    finish();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.property_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.action_serverurl) {
            changeServerURL();
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeServerURL() {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(this);
        View mView = layoutInflaterAndroid.inflate(R.layout.message_input_dialog_box, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialog));
        alertDialogBuilderUserInput.setView(mView);

        final TextView title = (TextView) mView.findViewById(R.id.lblTitle);
        title.setText("Server URL");
        final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.txtMessage);
        userInputDialogEditText.setText(MSHTBApplication.getInstance().getSettings(DBColumnKeys.SERVER_URL));

        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        String message = userInputDialogEditText.getText().toString().trim();
                        if (message.equals("")) {
                            Toast.makeText(LoginActivity.this, "Nothing to save", Toast.LENGTH_LONG).show();
                            return;
                        }
                        SettingsDao settingsDao = DatabaseManager.getInstance().getSession()
                                .getSettingsDao();
                        Settings settings = settingsDao.queryBuilder()
                                .where(SettingsDao.Properties.Key.eq(DBColumnKeys.SERVER_URL))
                                .unique();
                        if (settings != null) {
                            settings.setValue(userInputDialogEditText.getText().toString().trim());
                            settingsDao.update(settings);
                        } else {
                            settingsDao.save(new Settings(null, DBColumnKeys.SERVER_URL, userInputDialogEditText.getText().toString().trim()));
                        }
                        Paths.SERVER_URL = userInputDialogEditText.getText().toString().trim();
                    }
                })

                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();
    }

    @Override
    public void onClick(SweetAlertDialog sweetAlertDialog) {
        System.out.println("Sweet cancel clicked");
    }

    @OnClick(R.id.lblPoweredBy)
    public void onClickPoweredBy() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://streamstech.com.bd"));
        startActivity(browserIntent);
    }



    @OnClick(R.id.btnVerify)
    public void onVerifyClicked() {
        if (txtUserName.getText().toString().trim().length() < 4 || txtPassword.getText().toString().trim().length() < 4) {
            UIUtil.showInfoDialog(this, SweetAlertDialog.WARNING_TYPE, "Information", "Invalid value in user name or password");
            return;
        }

        if (!isSameUser(txtUserName.getText().toString().trim())) {
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Different User")
                    .setContentText("You are trying to login as a different user. All previous data will be deleted. Do you want to continue?")
                    .setConfirmText("Yes!")
                    .setCancelText("No")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(final SweetAlertDialog sDialog) {
                            sDialog.dismiss();
                            clearData();
                            doLogin();
                        }
                    })
                    .show();
        } else {
            doLogin();
        }
    }

    private void clearData () {
        DatabaseManager.getInstance().getSession()
                .getSettingsDao().deleteAll();
        DatabaseManager.getInstance().getSession()
                .getSettingsDao().save(new Settings(null, DBColumnKeys.SERVER_URL, Paths.SERVER_URL));
        DatabaseManager.getInstance().getSession()
                .getPatientDao().deleteAll();
        DatabaseManager.getInstance().getSession()
                .getOutcomeDao().deleteAll();

        DatabaseManager.getInstance().getSession()
                .getPETEnrollmentDao().deleteAll();
        DatabaseManager.getInstance().getSession()
                .getPETRegistrationDao().deleteAll();
        DatabaseManager.getInstance().getSession()
                .getPETEligibilitySymptomDao().deleteAll();
        DatabaseManager.getInstance().getSession()
                .getPETSymptomDao().deleteAll();
        DatabaseManager.getInstance().getSession()
                .getPETTestOrderDao().deleteAll();
        DatabaseManager.getInstance().getSession()
                .getPETTestResultDao().deleteAll();
        DatabaseManager.getInstance().getSession()
                .getPETTreatmentStartDao().deleteAll();
        DatabaseManager.getInstance().getSession()
                .getPETAdherenceDao().deleteAll();
        DatabaseManager.getInstance().getSession()
                .getPETFollowupDao().deleteAll();
        DatabaseManager.getInstance().getSession()
                .getPETClinicianFollowupDao().deleteAll();
        DatabaseManager.getInstance().getSession()
                .getPETClinicianTBReviewDao().deleteAll();
        DatabaseManager.getInstance().getSession()
                .getPETAdverseEventFollowupDao().deleteAll();
        DatabaseManager.getInstance().getSession()
                .getPETFollowupEndDao().deleteAll();

        DatabaseManager.getInstance().getSession()
                .getScreeningDao().deleteAll();
        DatabaseManager.getInstance().getSession()
                .getTestIndicationDao().deleteAll();
        DatabaseManager.getInstance().getSession()
                .getTestResultHistopathologyDao().deleteAll();
        DatabaseManager.getInstance().getSession()
                .getTestResultXPertDao().deleteAll();
        DatabaseManager.getInstance().getSession()
                .getTestResultXRayDao().deleteAll();
        DatabaseManager.getInstance().getSession()
                .getTestResultSmearDao().deleteAll();
        DatabaseManager.getInstance().getSession()
                .getTreatmentDao().deleteAll();
        DatabaseManager.getInstance().getSession()
                .getUserDao().deleteAll();
        DatabaseManager.getInstance().getSession()
                .getLocationDao().deleteAll();
    }

    private void doLogin () {

        UIUtil.showSweetProgress(this, "Signing in...", false, this);
        MSHTBApplication.getInstance().hideKeyboard(this);

//        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit client = new Retrofit.Builder()
                .baseUrl(Paths.SERVER_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EndpointInterface service = client.create(EndpointInterface.class);
        retrofit2.Call<CommonResponse> call = service.login(txtUserName.getText().toString(), txtPassword.getText().toString());

        call.enqueue(new retrofit2.Callback<CommonResponse>() {
            @Override
            public void onResponse(retrofit2.Call<CommonResponse> call, retrofit2.Response<CommonResponse> response) {

                UIUtil.hideSweetProgress();
                if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
                    Constant.TOKEN = response.body().getHeadermessage();
                    saveProfile(response.body());
                    loadLocation();
                } else if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED)
                    UIUtil.showInfoDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE, "Sign In", "Invalid user name or password");
                else {
                    UIUtil.showInfoDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE, "Sign In", "Failed to connect to the server");
                }
            }

            @Override
            public void onFailure(retrofit2.Call<CommonResponse> call, Throwable t) {
                UIUtil.hideSweetProgress();
                System.out.println(t.getMessage());
                UIUtil.showInfoDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE, "Sign In", "Unable to login");
            }
        });
    }

    private boolean isSameUser(String username) {
        // Name
        SettingsDao settingsDao = DatabaseManager.getInstance().getSession()
                .getSettingsDao();
        Settings settings = settingsDao.queryBuilder()
                .where(SettingsDao.Properties.Key.eq(DBColumnKeys.USER_NAME))
                .unique();
        if (settings != null) {
            return username.equals(settings.getValue());
        } else {
            return true;
        }
    }

    private void loadLocation () {
        UIUtil.showSweetProgress(this, "Downloading location...", false, null);
        new LocationTask().execute();
    }

    class LocationTask extends AsyncTask<Void, Void, Boolean> {

        protected Boolean doInBackground(Void... arg0) {
            new FASTSyncManager(LoginActivity.this, null).downloadLocation();
            return true;
        }

        protected void onPostExecute(Boolean result) {
            UIUtil.hideSweetProgress();
            startActivity(new Intent(LoginActivity.this, NewMainActivity.class));
            finish();
        }
    }
    private boolean readProfile () {
        SettingsDao settingsDao = DatabaseManager.getInstance().getSession()
                .getSettingsDao();

        Settings settings = settingsDao.queryBuilder()
                .where(SettingsDao.Properties.Key.eq(DBColumnKeys.LOCATION_NO))
                .unique();
        if (settings == null) {
            return false;
//            Constant.HOSPITAL_ID  =  Integer.parseInt(settings.getValue());
        }
//        else {
//            return false;
//        }

        settings = settingsDao.queryBuilder()
                .where(SettingsDao.Properties.Key.eq(DBColumnKeys.SCREENER_ID))
                .unique();
        if (settings == null) {
            return false;
//            Constant.SCREENER_ID = Integer.parseInt(settings.getValue());
        }
//        else {
//            return false;
//        }
        return  true;
    }

    private void saveProfile(CommonResponse commonResponse) {
        try {
            JSONArray jsonArray = new JSONArray(commonResponse.getResults());
            JSONObject jobj = jsonArray.getJSONObject(0);

            SettingsDao settingsDao = DatabaseManager.getInstance().getSession()
                    .getSettingsDao();
            Settings settings = settingsDao.queryBuilder()
                    .where(SettingsDao.Properties.Key.eq(DBColumnKeys.STATE))
                    .unique();
            if (settings != null) {
                settings.setValue(State.VERIFIED.name());
                settingsDao.update(settings);
            } else {
                settingsDao.save(new Settings(null, DBColumnKeys.STATE, State.VERIFIED.name()));
            }

            // Name
            settings = settingsDao.queryBuilder()
                    .where(SettingsDao.Properties.Key.eq(DBColumnKeys.NAME))
                    .unique();
            if (settings != null) {
                settings.setValue(jobj.getString("name"));
                settingsDao.update(settings);
            } else {
                settingsDao.save(new Settings(null, DBColumnKeys.NAME, jobj.getString("name")));
            }

            // Program
            settings = settingsDao.queryBuilder()
                    .where(SettingsDao.Properties.Key.eq(DBColumnKeys.PROGRAM))
                    .unique();
            if (settings != null) {
                settings.setValue(jobj.getString("program"));
                settingsDao.update(settings);
            } else {
                settingsDao.save(new Settings(null, DBColumnKeys.PROGRAM, jobj.getString("program")));
            }

            // Medic
            settings = settingsDao.queryBuilder()
                    .where(SettingsDao.Properties.Key.eq(DBColumnKeys.MEDIC))
                    .unique();
            if (settings != null) {
                settings.setValue(jobj.getString("medic"));
                settingsDao.update(settings);
            } else {
                settingsDao.save(new Settings(null, DBColumnKeys.MEDIC, jobj.getString("medic")));
            }

            // Mobile
            settings = settingsDao.queryBuilder()
                    .where(SettingsDao.Properties.Key.eq(DBColumnKeys.MOBILE_NO))
                    .unique();
            if (settings != null) {
                settings.setValue(jobj.getString("mobileno"));
                settingsDao.update(settings);
            } else {
                settingsDao.save(new Settings(null, DBColumnKeys.MOBILE_NO, jobj.getString("mobileno")));
            }

            // Locationid
            settings = settingsDao.queryBuilder()
                    .where(SettingsDao.Properties.Key.eq(DBColumnKeys.LOCATION_NO))
                    .unique();
            String locationid =  String.valueOf(jobj.getJSONObject("locationid").getInt("id"));
            if (settings != null) {
                settings.setValue(locationid);
                settingsDao.update(settings);
            } else {
                settingsDao.save(new Settings(null, DBColumnKeys.LOCATION_NO, locationid));
            }

            settings = settingsDao.queryBuilder()
                    .where(SettingsDao.Properties.Key.eq(DBColumnKeys.SCREENER_ID))
                    .unique();
            int screenerid = jobj.getInt("id");
            if (settings != null) {
                settings.setValue(String.valueOf(screenerid));
                settingsDao.update(settings);
            } else {
                settingsDao.save(new Settings(null, DBColumnKeys.SCREENER_ID, String.valueOf(screenerid)));
            }

//            Constant.SCREENER_ID = screenerid;
//            Constant.HOSPITAL_ID = jobj.getJSONObject("locationid").getInt("id");


            // User Name
            settings = settingsDao.queryBuilder()
                    .where(SettingsDao.Properties.Key.eq(DBColumnKeys.USER_NAME))
                    .unique();
            if (settings != null) {
                settings.setValue(jobj.getString("username"));
                settingsDao.update(settings);
            } else {
                settingsDao.save(new Settings(null, DBColumnKeys.USER_NAME, jobj.getString("username")));
            }

            String lastUpdateTime = null;
            settings = settingsDao.queryBuilder()
                    .where(SettingsDao.Properties.Key.eq(DBColumnKeys.LAST_UPDATE_TIME))
                    .unique();
            if (settings == null) {
                lastUpdateTime = "1970-01-01 00:00:00";
                settingsDao.save(new Settings(null, DBColumnKeys.LAST_UPDATE_TIME, lastUpdateTime));
            }
//            loadData(commonResponse.getHeadermessage(), lastUpdateTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



//    private void loadData(final String token){
//        UIUtil.showSweetProgress(this, "Loading data...", false, this);
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(90, TimeUnit.SECONDS)
//                .writeTimeout(90, TimeUnit.SECONDS)
//                .readTimeout(90, TimeUnit.SECONDS)
//                .build();
//        String jsonURL = Paths.SERVER_URL + Paths.API;
//        RequestBody formBody = new FormBody.Builder()
//                .add("token", token)
//                .add("action", "getAllData")
//                .build();
//        System.out.println("Load data Request: " + formBody.toString());
//
//        client.newCall(new Request.Builder().url(jsonURL).post(formBody).build()).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                UIUtil.hideSweetProgress();
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                UIUtil.hideSweetProgress();
//                if (response.isSuccessful()) {
//                    String strResponse = response.body().string();
//                    try {
//                        ServerResponse serverResponse = (ServerResponse) new Gson()
//                                .fromJson(new JsonReader(new StringReader(strResponse)), (Type) ServerResponse.class);
//                        if (serverResponse.isSuccess()) {
//
//                        }
////                        Registration.this.startActivity(new Intent(Registration.this, NewMainActivity.class));
////                        Registration.this.finish();
//                    } catch (Exception exp) {
//                        exp.printStackTrace();
//                    }
//                }
//            }
//        });
//    }
}

